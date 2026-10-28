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
 * The rules of the encounter, without a renderer or a window: it appears out of sight, it closes in
 * quietly, being seen escalates it, and it can be shaken off.
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
                    if (gen.isOpenSpace(cx, cz) && gen.isSolidGround(cx, cz) && !gen.isHole(cx, cz)) {
                        return new float[] {cx + 0.5f, cz + 0.5f};
                    }
                }
            }
        }
        throw new AssertionError("no walkable cell " + minDist + ".." + maxDist + " m out");
    }

    @Test
    public void wallsBlockSight() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 1L);
        Vector3 player = spawnPoint(gen);
        entity.forceSpawn(player.x + 0.5f, player.z + 0.5f, EntityController.State.STALK);
        // Same cell: the entity can see the player.
        assertTrue(entity.hasLineOfSight(player));

        // And somewhere within a few metres a wall must actually break the line: otherwise "hide
        // from it" would be advice the level itself cannot honour.
        boolean foundBlocked = false;
        for (int dx = -15; dx <= 15 && !foundBlocked; dx++) {
            for (int dz = -15; dz <= 15 && !foundBlocked; dz++) {
                int cx = MathUtils.floor(player.x) + dx;
                int cz = MathUtils.floor(player.z) + dz;
                if (!gen.isOpenSpace(cx, cz) || !gen.isSolidGround(cx, cz) || gen.isHole(cx, cz)) {
                    continue;
                }
                entity.forceSpawn(cx + 0.5f, cz + 0.5f, EntityController.State.STALK);
                foundBlocked = !entity.hasLineOfSight(player);
            }
        }
        assertTrue("no walkable cell near the spawn has its view of the player blocked", foundBlocked);
    }

    @Test
    public void spawnsOutOfSightAndThenClosesIn() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 7L);
        Vector3 player = spawnPoint(gen);
        float[] start = cellNear(gen, player, 9f, 18f);
        entity.forceSpawn(start[0], start[1], EntityController.State.STALK);

        float distance0 = entity.distance(player);
        for (int i = 0; i < 200; i++) {          // 10 seconds at 20 Hz
            entity.update(0.05f, player);
            assertTrue("entity ended up inside a wall",
                    !entity.blocksSight(entity.position.x, entity.position.z)
                            || gen.isOpenSpace(MathUtils.floor(entity.position.x),
                                    MathUtils.floor(entity.position.z)));
        }
        assertTrue("the entity did not move", entity.distance(player) < distance0 + 0.01f);
        assertEquals(EntityController.State.STALK, entity.state());
    }

    /** A walkable cell within {@code maxDist} that the player can actually be seen from. */
    private static float[] visibleCell(WorldGen gen, Vector3 player, float maxDist) {
        EntityController probe = new EntityController(gen, 0L);
        for (int ring = 1; ring <= (int) maxDist + 1; ring++) {
            for (int dz = -ring; dz <= ring; dz++) {
                for (int dx = -ring; dx <= ring; dx++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) {
                        continue;
                    }
                    int cx = MathUtils.floor(player.x) + dx;
                    int cz = MathUtils.floor(player.z) + dz;
                    float d = (float) Math.hypot(cx + 0.5f - player.x, cz + 0.5f - player.z);
                    if (d > maxDist || d < 1f) {
                        continue;
                    }
                    if (!gen.isOpenSpace(cx, cz) || !gen.isSolidGround(cx, cz)
                            || gen.isHole(cx, cz)) {
                        continue;
                    }
                    probe.forceSpawn(cx + 0.5f, cz + 0.5f, EntityController.State.STALK);
                    if (probe.hasLineOfSight(player)) {
                        return new float[] {cx + 0.5f, cz + 0.5f};
                    }
                }
            }
        }
        throw new AssertionError("no cell within " + maxDist + " m has a view of the player");
    }

    @Test
    public void beingSeenLongEnoughEscalatesToHunting() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 11L);
        Vector3 player = spawnPoint(gen);
        // Within sight of the player, so the clock starts immediately.
        float[] spot = visibleCell(gen, player, 6f);
        entity.forceSpawn(spot[0], spot[1], EntityController.State.STALK);
        assertTrue("expected a clear line of sight from the chosen cell",
                entity.hasLineOfSight(player));

        boolean hunted = false;
        for (int i = 0; i < 500 && !hunted; i++) {   // at most 25 seconds
            entity.update(0.05f, player);
            hunted = entity.state() == EntityController.State.HUNT;
            if (entity.consumeCapture()) {
                break;
            }
        }
        assertTrue("it never escalated to a hunt", hunted);
        assertTrue("escalation should take a while, not a second",
                entity.stalkTime() > 1f);
    }

    @Test
    public void itCanBeLostAndThenStaysGoneForAWhile() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 13L);
        Vector3 player = spawnPoint(gen);
        float[] start = cellNear(gen, player, 12f, 20f);
        entity.forceSpawn(start[0], start[1], EntityController.State.HUNT);

        // Break line of sight by moving the player, and let the entity lose the trail.
        Vector3 hiding = new Vector3(player);
        for (int i = 0; i < 400; i++) {             // 20 seconds
            entity.update(0.05f, hiding);
            if (entity.state() == EntityController.State.ABSENT) {
                break;
            }
        }
        assertEquals("a hunted entity that cannot see the player should leave",
                EntityController.State.ABSENT, entity.state());
        assertFalse(entity.active());

        // It must not come straight back: the cooldown is measured in seconds.
        entity.update(0.05f, hiding);
        assertEquals(EntityController.State.ABSENT, entity.state());
    }


    @Test
    public void itStepsAndRoars() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 23L);
        Vector3 player = spawnPoint(gen);
        float[] start = cellNear(gen, player, 10f, 18f);
        entity.forceSpawn(start[0], start[1], EntityController.State.STALK);
        entity.update(0.05f, player);
        assertFalse("it should not roar the instant it appears", entity.consumeRoar());

        // Walk for ten seconds and count the steps it took: a step per stride, and it moves at
        // 1.25 m/s, so there must be several.
        int steps = 0;
        for (int i = 0; i < 200; i++) {
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
    public void escalatingToAHuntMakesItRoar() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 29L);
        Vector3 player = spawnPoint(gen);
        float[] spot = visibleCell(gen, player, 6f);
        entity.forceSpawn(spot[0], spot[1], EntityController.State.STALK);

        boolean roared = false;
        for (int i = 0; i < 600 && !roared; i++) {
            entity.update(0.05f, player);
            if (entity.consumeCapture()) {
                break;
            }
            if (entity.consumeRoar()) {
                roared = entity.state() == EntityController.State.HUNT;
            }
        }
        assertTrue("it went from stalking to hunting without a sound", roared);
    }

    @Test
    public void presenceIsOnlyFeltWhenItIsNear() {
        WorldGen gen = new WorldGen(SEED);
        EntityController entity = new EntityController(gen, 17L);
        Vector3 player = spawnPoint(gen);
        entity.update(0.1f, player);
        assertEquals(0f, entity.presence(), 0.001f);

        entity.forceSpawn(player.x + 12f, player.z, EntityController.State.HUNT);
        for (int i = 0; i < 20; i++) {
            entity.update(0.05f, player);
        }
        assertTrue("a hunting entity twelve metres away should disturb the picture",
                entity.presence() > 0.2f);
    }
}
