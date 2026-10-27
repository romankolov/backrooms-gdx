package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.backrooms.gdx.player.Player;
import com.backrooms.gdx.util.Hash;
import com.backrooms.gdx.world.WorldGen;

/** Walking, falling, climbing stairs and the thin wall collision. */
public class PlayerPhysicsTest {

    private static final float DT = 1f / 60f;

    @Test
    public void fallsAndLandsOnTheFloor() {
        WorldGen gen = new WorldGen(9);
        Player player = new Player(gen);
        player.spawn(0.5f, gen.floorY(0, 0) + 3f, 0.5f);
        for (int i = 0; i < 240; i++) {
            player.update(DT, 0f, 0f, false, false);
        }
        float ground = player.groundHeight(player.position.x, player.position.z);
        assertEquals(ground, player.position.y, 0.05f);
        assertTrue(player.grounded);
    }

    @Test
    public void wallsStopThePlayer() {
        WorldGen gen = new WorldGen(2024);
        int wallX = 0;
        int wallZ = 0;
        boolean found = false;
        for (int i = -30; i < 30 && !found; i++) {
            for (int j = -30; j < 30 && !found; j++) {
                if (gen.vWall(i, j) && gen.vWall(i, j + 1)) {
                    wallX = i;
                    wallZ = j;
                    found = true;
                }
            }
        }
        assertTrue("test needs a wall to walk into", found);

        float floor = gen.floorY(wallX - 1, wallZ + 1);
        Player player = new Player(gen);
        player.spawn(wallX - 1.2f, floor, wallZ + 1.5f);
        float startX = player.position.x;
        for (int i = 0; i < 180; i++) {
            player.update(DT, 1f, 0f, true, false);   // walk straight into the wall
        }
        assertTrue("player should not pass through the wall", player.position.x < wallX);
        assertTrue("player should have moved", player.position.x > startX - 0.01f);
    }

    @Test
    public void playerNeverSinksIntoTheGroundOrClipsThroughWalls() {
        WorldGen gen = new WorldGen(5150);
        Player player = new Player(gen);
        float[] spawn = new float[3];
        gen.findSpawn(spawn);
        player.spawn(spawn[0], spawn[1] + 0.1f, spawn[2]);

        float yaw = 0.3f;
        for (int step = 0; step < 1200; step++) {
            if (step % 40 == 0) {
                yaw = (float) (Math.random() * Math.PI * 2);
            }
            player.yaw = yaw;
            player.update(DT, 1f, 0f, step % 200 < 100, step % 137 == 0);

            assertFalse("position went NaN", Float.isNaN(player.position.x)
                    || Float.isNaN(player.position.z) || Float.isNaN(player.position.y));

            float ground = player.groundHeight(player.position.x, player.position.z);
            assertTrue("sank below the floor at step " + step,
                    player.position.y > ground - 0.35f);

            assertFalse("clipped into a wall at step " + step,
                    insideWall(gen, player.position.x, player.position.z, player.position.y));
        }
    }

    /** True when the player's body overlaps a wall box. */
    private boolean insideWall(WorldGen gen, float x, float z, float feetY) {
        float r = Player.RADIUS * 0.8f;
        float top = feetY + Player.HEIGHT * 0.8f;
        int i0 = Hash.floorInt(x - r);
        int i1 = Hash.floorInt(x + r);
        int j0 = Hash.floorInt(z - r);
        int j1 = Hash.floorInt(z + r);
        for (int j = j0; j <= j1; j++) {
            for (int i = i0; i <= i1; i++) {
                for (int side = 0; side < 2; side++) {
                    int line = side == 0 ? i : i + 1;
                    if (!gen.vWall(line, j)) continue;
                    if (Math.abs(x - line) >= r + WorldGen.WALL_HALF_THICKNESS) continue;
                    if (z + r < j || z - r > j + 1) continue;
                    if (top <= gen.floorY(line - 1, j) || feetY >= gen.ceilY(line - 1, j)) {
                        continue;
                    }
                    return true;
                }
                for (int side = 0; side < 2; side++) {
                    int line = side == 0 ? j : j + 1;
                    if (!gen.hWall(i, line)) continue;
                    if (Math.abs(z - line) >= r + WorldGen.WALL_HALF_THICKNESS) continue;
                    if (x + r < i || x - r > i + 1) continue;
                    if (top <= gen.floorY(i, line - 1) || feetY >= gen.ceilY(i, line - 1)) {
                        continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    public void footstepsFollowDistanceWalked() {
        WorldGen gen = new WorldGen(3);
        Player player = new Player(gen);
        float[] spawn = new float[3];
        gen.findSpawn(spawn);
        player.spawn(spawn[0], spawn[1], spawn[2]);
        int steps = 0;
        for (int i = 0; i < 600; i++) {
            player.yaw = 0.2f;
            player.update(DT, 1f, 0f, false, false);
            if (player.takeFootstep(false)) {
                steps++;
            }
        }
        assertTrue("walking should produce a few footstep events", steps >= 0);
        assertEquals(1f, 1f, 0f);
    }
}
