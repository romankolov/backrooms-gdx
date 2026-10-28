package com.backrooms.gdx.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.backrooms.gdx.util.Hash;
import com.backrooms.gdx.world.WorldGen;

/**
 * First person walker with a capsule-ish body against a world made of thin walls and stair steps.
 *
 * <p>Collision is solved directly against the generator (never against chunk meshes), so walking
 * works even in areas that have not finished streaming in yet.
 */
public final class Player {

    public static final float RADIUS = 0.30f;
    public static final float HEIGHT = 1.76f;
    public static final float EYE_HEIGHT = 1.62f;
    public static final float WALK_SPEED = 3.1f;
    public static final float SPRINT_SPEED = 5.4f;
    public static final float GRAVITY = 24f;
    public static final float JUMP_SPEED = 6.3f;
    /** Highest step the player walks up without jumping. Level 0 is flat; only the pit rims matter. */
    public static final float STEP_HEIGHT = 0.55f;
    private static final float TH = WorldGen.WALL_HALF_THICKNESS;

    public final Vector3 position = new Vector3();
    public final Vector3 velocity = new Vector3();
    public float yaw;
    public float pitch;
    public boolean grounded = true;
    public boolean torchOn = true;
    public float bobPhase;
    public float stepDistance;
    public float lastFallSpeed;
    public float landingImpact;
    public float walkedDistance;

    private final WorldGen gen;

    public Player(WorldGen gen) {
        this.gen = gen;
    }

    public void spawn(float x, float y, float z) {
        position.set(x, y + 0.02f, z);
        velocity.set(0f, 0f, 0f);
        grounded = true;
        pitch = 0f;
    }

    public void addLook(float dx, float dy) {
        yaw -= dx;
        pitch = MathUtils.clamp(pitch - dy, -1.45f, 1.45f);
        if (yaw > MathUtils.PI2) {
            yaw -= MathUtils.PI2;
        } else if (yaw < 0f) {
            yaw += MathUtils.PI2;
        }
    }

    /**
     * @param moveForward +1 forward, -1 back
     * @param moveSide    +1 right, -1 left
     */
    public void update(float delta, float moveForward, float moveSide, boolean sprint, boolean jump) {
        float len = (float) Math.sqrt(moveForward * moveForward + moveSide * moveSide);
        if (len > 1f) {
            moveForward /= len;
            moveSide /= len;
        }

        float speed = sprint ? SPRINT_SPEED : WALK_SPEED;
        float sin = MathUtils.sin(yaw);
        float cos = MathUtils.cos(yaw);
        float wishX = (-sin * moveForward + cos * moveSide) * speed;
        float wishZ = (-cos * moveForward - sin * moveSide) * speed;

        float accel = grounded ? 16f : 5f;
        float k = 1f - (float) Math.exp(-accel * delta);
        velocity.x += (wishX - velocity.x) * k;
        velocity.z += (wishZ - velocity.z) * k;

        float startX = position.x;
        float startZ = position.z;

        moveHorizontal(velocity.x * delta, velocity.z * delta);

        // Vertical movement and stairs.
        if (jump && grounded) {
            velocity.y = JUMP_SPEED;
            grounded = false;
        }
        velocity.y -= GRAVITY * delta;
        position.y += velocity.y * delta;

        float ground = groundHeight(position.x, position.z);
        if (position.y <= ground) {
            if (!grounded && velocity.y < -6f) {
                landingImpact = Math.min(1f, -velocity.y / 18f);
                lastFallSpeed = velocity.y;
            }
            position.y = ground;
            velocity.y = 0f;
            grounded = true;
        } else if (position.y > ground + 0.02f) {
            grounded = false;
        }

        float ceiling = ceilHeight(position.x, position.z);
        if (position.y + HEIGHT > ceiling) {
            position.y = ceiling - HEIGHT;
            if (velocity.y > 0f) {
                velocity.y = 0f;
            }
        }

        // Head bob and footsteps are driven by the ground covered, not by the frame rate.
        float moved = (float) Math.hypot(position.x - startX, position.z - startZ);
        if (grounded) {
            laidOutFootsteps(moved, sprint);
        }
        landingImpact *= (float) Math.exp(-7f * delta);
    }

    private void laidOutFootsteps(float moved, boolean sprint) {
        walkedDistance += moved;
        bobPhase += moved * (sprint ? 2.9f : 2.4f);
        stepDistance += moved;
    }

    /** Consumes one footstep if the player has walked far enough. Returns true when one is due. */
    public boolean takeFootstep(boolean sprint) {
        float stride = sprint ? 1.85f : 1.5f;
        if (stepDistance >= stride) {
            stepDistance -= stride;
            return true;
        }
        return false;
    }

    public float bobOffset() {
        if (!grounded) {
            return 0f;
        }
        return MathUtils.sin(bobPhase) * 0.035f + MathUtils.sin(bobPhase * 2f) * 0.008f;
    }

    public void applyTo(Camera camera) {
        float cp = MathUtils.cos(pitch);
        camera.position.set(position.x, position.y + EYE_HEIGHT + bobOffset() - landingImpact * 0.12f,
                position.z);
        camera.direction.set(-MathUtils.sin(yaw) * cp, MathUtils.sin(pitch),
                -MathUtils.cos(yaw) * cp);
        camera.up.set(0f, 1f, 0f);
        camera.update();
    }

    public Vector3 cameraDirection(Vector3 out) {
        float cp = MathUtils.cos(pitch);
        return out.set(-MathUtils.sin(yaw) * cp, MathUtils.sin(pitch), -MathUtils.cos(yaw) * cp);
    }

    // ---------------------------------------------------------------------------------------------
    // collision
    // ---------------------------------------------------------------------------------------------

    private void moveHorizontal(float dx, float dz) {
        if (dx == 0f && dz == 0f) {
            return;
        }
        // Two sub steps keep fast movement from tunnelling through a 16cm wall.
        int steps = 1 + (int) (Math.max(Math.abs(dx), Math.abs(dz)) / 0.18f);
        float sx = dx / steps;
        float sz = dz / steps;
        for (int s = 0; s < steps; s++) {
            if (!blocked(position.x + sx, position.z, position.y)) {
                position.x += sx;
            } else {
                velocity.x = 0f;
            }
            if (!blocked(position.x, position.z + sz, position.y)) {
                position.z += sz;
            } else {
                velocity.z = 0f;
            }
        }
    }

    private boolean blocked(float x, float z, float feetY) {
        float top = feetY + HEIGHT;
        float r = RADIUS;
        int i0 = Hash.floorInt(x - r);
        int i1 = Hash.floorInt(x + r);
        int j0 = Hash.floorInt(z - r);
        int j1 = Hash.floorInt(z + r);
        for (int j = j0; j <= j1; j++) {
            for (int i = i0; i <= i1; i++) {
                if (gen.floorY(i, j) > feetY + STEP_HEIGHT) {
                    return true;
                }
                if (wallBlocksVertical(i, j, x, z, feetY, top, r)) return true;
                if (wallBlocksVertical(i + 1, j, x, z, feetY, top, r)) return true;
                if (wallBlocksHorizontal(i, j, x, z, feetY, top, r)) return true;
                if (wallBlocksHorizontal(i, j + 1, x, z, feetY, top, r)) return true;
            }
        }
        return false;
    }

    private boolean wallBlocksVertical(int line, int row, float x, float z, float feetY, float top,
                                       float r) {
        if (Math.abs(x - line) > r + TH) return false;
        if (z + r < row || z - r > row + 1f) return false;
        if (!gen.vWall(line, row)) return false;
        float lo = Math.min(gen.floorY(line - 1, row), gen.floorY(line, row));
        float hi = Math.max(gen.ceilY(line - 1, row), gen.ceilY(line, row));
        return top > lo && feetY < hi;
    }

    private boolean wallBlocksHorizontal(int col, int line, float x, float z, float feetY, float top,
                                         float r) {
        if (Math.abs(z - line) > r + TH) return false;
        if (x + r < col || x - r > col + 1f) return false;
        if (!gen.hWall(col, line)) return false;
        float lo = Math.min(gen.floorY(col, line - 1), gen.floorY(col, line));
        float hi = Math.max(gen.ceilY(col, line - 1), gen.ceilY(col, line));
        return top > lo && feetY < hi;
    }

    public float groundHeight(float x, float z) {
        float r = RADIUS * 0.85f;
        int i0 = Hash.floorInt(x - r);
        int i1 = Hash.floorInt(x + r);
        int j0 = Hash.floorInt(z - r);
        int j1 = Hash.floorInt(z + r);
        float g = -Float.MAX_VALUE;
        for (int j = j0; j <= j1; j++) {
            for (int i = i0; i <= i1; i++) {
                g = Math.max(g, gen.floorY(i, j));
            }
        }
        return g == -Float.MAX_VALUE ? 0f : g;
    }

    public float ceilHeight(float x, float z) {
        int i = Hash.floorInt(x);
        int j = Hash.floorInt(z);
        return gen.ceilY(i, j);
    }
}
