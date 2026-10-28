package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.backrooms.gdx.entity.EntityController;
import com.backrooms.gdx.world.WorldGen;

import org.junit.Test;

/**
 * The rules of the encounter, without a renderer or a window.
 *
 * <p>It is a stalker: it appears out of sight in front of the player, walks across the view at a
 * stroll, and only goes away once a wall has taken it out of sight. It never chases, and it can
 * never catch anybody.
 */
public class EntityControllerTest {

    private static final int SEED = 20240517;

    private static Vector3 spawnPoint(WorldGen gen) {
        float[] out = new float[3];
        gen.findSpawn(out);
        return new Vector3(out[0], out[1], out[2]);
    }

    /** Finds a walkable cell at roughly the wanted distance from the player. */
    private static float[] cellNear(WorldGen gen, Vector3 player, float minDist, float maxDist) {
        for (int ring = 1; ring < 30; ring++) {
            for (int dz = -ring; dz <= ring; dz++) {
                for (int dx = -ring; dx <= ring; dx++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) {
                        continue;
                    }
                    int cx = MathUtils.floor(player.x) + dx;
                    int cz = MathUtils.floor(player.z) + dz;
                    float d = (float) Math.hypot(cx + 0.5f - player.x, cz + 0.5f - player.z);
                    if (d < minDist || d > maxDist) {
                        continue;
                    }
                    if (walkable(gen, cx, cz)) {
                        return new float[] {cx + 0.5f, cz + 0.5f};
                    }
                }
            }
        }
        throw new AssertionError("no walkable cell " + minDist + ".." + maxDist + " m out");
    }

    private static boolean walkable(WorldGen gen, int cx, int cz) {
        return gen.isOpenSpace(cx, cz) && gen.isSolidGround(cx, cz) && !gen.isHole(cx, cz);
    }

    @Test
    public void wallsBlockSight() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 1L);
        Vector3 player = spawnPoint(gen);
        entity.forceSpawn(player.x + 0.5f, player.z + 0.5f);
        assertTrue(entity.hasLineOfSight(player));

        boolean foundBlocked = false;
        for (int dx = -15; dx <= 15 && !foundBlocked; dx++) {
            for (int dz = -15; dz <= 15 && !foundBlocked; dz++) {
                int cx = MathUtils.floor(player.x) + dx;
                int cz = MathUtils.floor(player.z) + dz;
                if (!walkable(gen, cx, cz)) {
                    continue;
                }
                foundBlocked = !gen.hasLineOfSight(cx + 0.5f, cz + 0.5f, player.x, player.z);
            }
        }
        assertTrue("no walkable cell near the spawn has its view of the player blocked", foundBlocked);
    }

    /**
     * The heart of it: given time, it appears on the far side of a wall in front of the player,
     * becomes visible as it crosses, and then vanishes once it is behind another wall.
     */
    @Test
    public void itPassesByAndLeavesBehindAWall() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 7L);
        Vector3 player = spawnPoint(gen);
        float yaw = 0f;

        boolean appeared = false;
        boolean seen = false;
        boolean vanished = false;
        for (int i = 0; i < 4000; i++) {                 // up to 200 seconds
            entity.update(0.05f, player, yaw);
            if (!appeared && entity.active()) {
                appeared = true;
                assertFalse("it must not appear where the player can see it",
                        gen.hasLineOfSight(entity.position.x, entity.position.z,
                                player.x, player.z));
                assertTrue("it should appear metres away, not on top of the player",
                        entity.distance(player) > 6f);
            }
            if (appeared && entity.active() && entity.hasLineOfSight(player)) {
                seen = true;
            }
            if (appeared && seen && !entity.active()) {
                vanished = true;
                break;
            }
        }
        assertTrue("it never appeared", appeared);
        assertTrue("it walked past without ever being visible", seen);
        assertTrue("it never left again", vanished);
    }

    @Test
    public void itNeverRunsAtThePlayer() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 11L);
        Vector3 player = spawnPoint(gen);
        EntityController first = null;
        for (int i = 0; i < 6000; i++) {                 // five minutes of level
            entity.update(0.05f, player, 0f);
            assertTrue("its speed is a stroll, never a charge",
                    entity.speed() <= EntityController.STALK_SPEED + 0.001f);
            if (first == null && entity.active()) {
                first = entity;
            }
        }
        assertTrue("it never showed up at all in five minutes", first != null);
        // Stalking means staying at a distance: it should never close to touching range.
        assertTrue("it walked into the player", entity.distance(player) > 2f || !entity.active());
    }

    @Test
    public void itCanBeTurnedOffAndStaysOff() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 13L);
        Vector3 player = spawnPoint(gen);
        entity.setEnabled(false);
        for (int i = 0; i < 4000; i++) {
            entity.update(0.05f, player, 0f);
            assertFalse("a disabled entity must never be active", entity.active());
        }
        assertEquals(0f, entity.presence(), 0.001f);
    }

    @Test
    public void itStepsWhileItWalks() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 23L);
        Vector3 player = spawnPoint(gen);
        float[] start = cellNear(gen, player, 10f, 18f);
        entity.forceSpawn(start[0], start[1]);
        entity.update(0.05f, player);
        assertFalse("it should not roar the instant it appears", entity.consumeRoar());

        int steps = 0;
        for (int i = 0; i < 200; i++) {                  // ten seconds
            entity.update(0.05f, player);
            if (entity.takeFootstep()) {
                steps++;
            }
        }
        assertTrue("it walked ten metres without a single step", steps >= 4);

        // A frozen entity is a posed entity: it must not make any noise at all.
        entity.setFrozen(true);
        int frozenSteps = 0;
        for (int i = 0; i < 60; i++) {
            entity.update(0.05f, player);
            if (entity.takeFootstep()) {
                frozenSteps++;
            }
        }
        assertEquals("a frozen entity should not be walking", 0, frozenSteps);
    }

    @Test
    public void presenceIsOnlyFeltWhenItIsNear() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 17L);
        Vector3 player = spawnPoint(gen);
        entity.update(0.1f, player);
        assertEquals(0f, entity.presence(), 0.001f);

        entity.forceSpawn(player.x + 12f, player.z);
        for (int i = 0; i < 20; i++) {
            entity.update(0.05f, player);
        }
        assertTrue("something twelve metres away should disturb the picture",
                entity.presence() > 0.2f);
    }

    @Test
    public void itStaysInsideTheLevel() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 31L);
        Vector3 player = spawnPoint(gen);
        for (int i = 0; i < 3000; i++) {                 // 150 seconds, several passes
            entity.update(0.05f, player, 0.7f);
            if (!entity.active()) {
                continue;
            }
            int cx = MathUtils.floor(entity.position.x);
            int cz = MathUtils.floor(entity.position.z);
            assertTrue("the entity walked into a wall or a pit at " + cx + "," + cz,
                    walkable(gen, cx, cz));
        }
    }
}
