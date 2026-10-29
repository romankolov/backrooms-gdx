package com.backrooms.gdx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.backrooms.gdx.ui.VhsOsd;

/**
 * The camcorder OSD is drawn from a hand-built 5x7 bitmap font, and the table is easy to get subtly
 * wrong: a glyph with six rows, a missing character, a stray lit pixel. These checks run without a
 * GL context, so a broken table fails the build instead of printing garbage on screen.
 */
public class VhsOsdFontTest {

    private static final String REQUIRED = "RECSPLAY0123456789:.-/+";

    @Test
    public void everyGlyphIsFiveBySeven() {
        assertEquals(5, VhsOsd.glyphWidth());
        assertEquals(7, VhsOsd.glyphHeight());
        for (char c : REQUIRED.toCharArray()) {
            assertNotNull("missing glyph for '" + c + "'", VhsOsd.bits(c));
            assertEquals("glyph '" + c + "' has the wrong number of pixels",
                    VhsOsd.glyphWidth() * VhsOsd.glyphHeight(), VhsOsd.bits(c).length);
        }
    }

    @Test
    public void theWholeUppercaseAlphabetAndDigitsExist() {
        for (char c = 'A'; c <= 'Z'; c++) {
            assertTrue("missing glyph for " + c, VhsOsd.has(c));
        }
        for (char c = '0'; c <= '9'; c++) {
            assertTrue("missing glyph for " + c, VhsOsd.has(c));
        }
    }

    /** A glyph that is entirely dark is almost certainly a typo in the table. */
    @Test
    public void noGlyphIsBlank() {
        for (char c : REQUIRED.toCharArray()) {
            if (c == ' ') {
                continue;
            }
            int lit = 0;
            for (byte b : VhsOsd.bits(c)) {
                lit += b;
            }
            assertTrue("glyph '" + c + "' is empty", lit > 3);
        }
    }

    /** Letters must be distinguishable: O and 0 differ, I and 1 differ, and so on. */
    @Test
    public void lookalikeCharactersDiffer() {
        char[][] pairs = {{'O', '0'}, {'I', '1'}, {'S', '5'}, {'B', '8'}, {'Z', '2'}};
        for (char[] pair : pairs) {
            byte[] a = VhsOsd.bits(pair[0]);
            byte[] b = VhsOsd.bits(pair[1]);
            assertNotNull(pair[0] + " or " + pair[1] + " missing", a);
            assertNotNull(pair[0] + " or " + pair[1] + " missing", b);
            boolean different = false;
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    different = true;
                    break;
                }
            }
            assertTrue("'" + pair[0] + "' and '" + pair[1] + "' render identically", different);
        }
    }
}
