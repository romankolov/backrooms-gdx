package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.backrooms.gdx.audio.Acoustics;
import com.backrooms.gdx.world.WorldGen;

import org.junit.Test;

/**
 * The echo is the room: a corridor answers quickly and thinly, a hall answers late and long. These
 * checks pin the relationship between the walls around a point and the sound it makes.
 */
public class AcousticsTest {

    private static final int SEED = 20240517;

    @Test
    public void aBiggerRoomEchoesLaterAndLouder() {
        Acoustics tight = new Acoustics();
        tight.mean = 1.8f;
        tight.derive();

        Acoustics hall = new Acoustics();
        hall.mean = 18f;
        hall.derive();

        assertTrue("a hall should ring longer than a corridor",
                hall.tailDelay() > tight.tailDelay());
        assertTrue("a hall should return more sound than a corridor",
                hall.tailGain() > tight.tailGain());
        assertTrue("openness must stay in 0..1", hall.openness <= 1f && tight.openness >= 0f);
        Acoustics pressed = new Acoustics();
        pressed.mean = 0.5f;
        pressed.derive();
        assertEquals("a corridor pressed against the walls should be nearly closed", 0f,
                pressed.openness, 0.001f);
    }

    @Test
    public void propagationDelayMatchesTheSpeedOfSound() {
        // 10 m away and back is 20 m of travel, which is about 58 ms.
        assertEquals(0.058f, Acoustics.echoDelay(10f), 0.001f);
        assertTrue(Acoustics.echoDelay(2f) < Acoustics.echoDelay(8f));
    }

    @Test
    public void probingTheLevelProducesUsableNumbers() {
        WorldGen gen = new WorldGen(SEED);
        float[] spawn = new float[3];
        gen.findSpawn(spawn);
        Acoustics space = new Acoustics();
        Acoustics.probe(gen, spawn[0], spawn[2], space);

        assertTrue("nearest wall must be within range", space.nearest > 0f
                && space.nearest <= Acoustics.MAX_RANGE);
        assertTrue("mean wall distance must be between nearest and the range",
                space.mean >= space.nearest && space.mean <= Acoustics.MAX_RANGE);
        assertTrue("openness stays in 0..1", space.openness >= 0f && space.openness <= 1f);
        for (int i = 0; i < Acoustics.RAYS; i++) {
            assertTrue("ray " + i + " returned a negative distance", space.distance[i] >= 0f);
        }
    }

    /** Standing in a corridor must give a different answer from standing in an open hall. */
    @Test
    public void differentPlacesEchoDifferently() {
        WorldGen gen = new WorldGen(SEED);
        float[] spawn = new float[3];
        gen.findSpawn(spawn);

        Acoustics here = Acoustics.probe(gen, spawn[0], spawn[2], new Acoustics());
        float smallest = Float.MAX_VALUE;
        float largest = 0f;
        // Look for two clearly different rooms within a short walk of the spawn.
        for (int dx = -24; dx <= 24; dx += 3) {
            for (int dz = -24; dz <= 24; dz += 3) {
                int cx = MathUtils.floor(spawn[0]) + dx;
                int cz = MathUtils.floor(spawn[2]) + dz;
                if (!gen.isOpenSpace(cx, cz) || !gen.isSolidGround(cx, cz)) {
                    continue;
                }
                Acoustics space = Acoustics.probe(gen, cx + 0.5f, cz + 0.5f, new Acoustics());
                smallest = Math.min(smallest, space.openness);
                largest = Math.max(largest, space.openness);
                assertTrue("probe must not be NaN", !Float.isNaN(space.tailDelay()));
            }
        }
        assertTrue("the level is all one size - the echo would never change",
                largest - smallest > 0.15f);
        assertTrue("the spawn point itself should be measurable", !Float.isNaN(here.openness));
    }

    @Test
    public void sightLinesStopAtWalls() {
        WorldGen gen = new WorldGen(SEED);
        float[] spawn = new float[3];
        gen.findSpawn(spawn);
        Vector3 player = new Vector3(spawn[0], spawn[1], spawn[2]);
        // A point far enough away that some wall must be in between.
        int blocked = 0;
        for (int i = 0; i < 16; i++) {
            float angle = i * MathUtils.PI2 / 16f;
            float x = player.x + MathUtils.cos(angle) * 14f;
            float z = player.z + MathUtils.sin(angle) * 14f;
            if (!gen.hasLineOfSight(player.x, player.z, x, z)) {
                blocked++;
            }
        }
        assertTrue("nothing within 14 m is ever blocked - walls are not working", blocked > 4);
    }
}
