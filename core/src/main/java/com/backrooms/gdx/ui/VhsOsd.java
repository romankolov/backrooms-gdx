package com.backrooms.gdx.ui;

import java.util.Calendar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * The camcorder's on-screen display: REC lamp, tape speed, date and counter.
 *
 * <p>Drawn from a hand-built 5x7 bitmap font instead of the DejaVu atlas. A real camcorder OSD is a
 * character generator with a fixed cell grid; scaling a proportional vector font to a few pixels
 * looks like a small UI, while blocky 5x7 glyphs snapped to whole pixels look like a camcorder.
 */
public final class VhsOsd implements Disposable {

    private static final int GLYPH_W = 5;
    private static final int GLYPH_H = 7;

    /**
     * 5x7 glyphs, one string per character, rows separated by '/'. '1' is a lit pixel.
     * Produced from the classic 5x7 character generator shapes.
     */
    private static final String[] GLYPHS = {
            "A", "01110/10001/10001/11111/10001/10001/10001",
            "B", "11110/10001/10001/11110/10001/10001/11110",
            "C", "01110/10001/10000/10000/10000/10001/01110",
            "D", "11110/10001/10001/10001/10001/10001/11110",
            "E", "11111/10000/10000/11110/10000/10000/11111",
            "F", "11111/10000/10000/11110/10000/10000/10000",
            "G", "01110/10001/10000/10111/10001/10001/01111",
            "H", "10001/10001/10001/11111/10001/10001/10001",
            "I", "11111/00100/00100/00100/00100/00100/11111",
            "J", "00111/00010/00010/00010/00010/10010/01100",
            "K", "10001/10010/10100/11000/10100/10010/10001",
            "L", "10000/10000/10000/10000/10000/10000/11111",
            "M", "10001/11011/10101/10101/10001/10001/10001",
            "N", "10001/11001/10101/10011/10001/10001/10001",
            "O", "01110/10001/10001/10001/10001/10001/01110",
            "P", "11110/10001/10001/11110/10000/10000/10000",
            "Q", "01110/10001/10001/10001/10101/10010/01101",
            "R", "11110/10001/10001/11110/10100/10010/10001",
            "S", "01111/10000/10000/01110/00001/00001/11110",
            "T", "11111/00100/00100/00100/00100/00100/00100",
            "U", "10001/10001/10001/10001/10001/10001/01110",
            "V", "10001/10001/10001/10001/10001/01010/00100",
            "W", "10001/10001/10001/10101/10101/11011/10001",
            "X", "10001/10001/01010/00100/01010/10001/10001",
            "Y", "10001/10001/01010/00100/00100/00100/00100",
            "Z", "11111/00001/00010/00100/01000/10000/11111",
            "0", "01110/10001/10011/10101/11001/10001/01110",
            "1", "00100/01100/00100/00100/00100/00100/01110",
            "2", "01110/10001/00001/00010/00100/01000/11111",
            "3", "11111/00010/00100/00010/00001/10001/01110",
            "4", "00010/00110/01010/10010/11111/00010/00010",
            "5", "11111/10000/11110/00001/00001/10001/01110",
            "6", "00110/01000/10000/11110/10001/10001/01110",
            "7", "11111/00001/00010/00100/01000/01000/01000",
            "8", "01110/10001/10001/01110/10001/10001/01110",
            "9", "01110/10001/10001/01111/00001/00010/01100",
            " ", "00000/00000/00000/00000/00000/00000/00000",
            ":", "00000/00100/00100/00000/00100/00100/00000",
            ".", "00000/00000/00000/00000/00000/00110/00110",
            "-", "00000/00000/00000/11111/00000/00000/00000",
            "+", "00000/00100/00100/11111/00100/00100/00000",
            "/", "00001/00010/00010/00100/01000/01000/10000",
            "<", "00010/00100/01000/10000/01000/00100/00010",
            ">", "01000/00100/00010/00001/00010/00100/01000",
            "?", "01110/10001/00001/00010/00100/00000/00100",
    };

    /** 5x7 pixel art, indexed by character, 7 rows of 5 bits. Built once, no GL needed. */
    private static final byte[][] GLYPH_BITS = buildFont();
    private Texture white;
    private final Color tint = new Color();
    private final Color saved = new Color();
    private final Calendar calendar = Calendar.getInstance();

    public VhsOsd() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.fill();
        white = new Texture(pixmap);
        pixmap.dispose();
    }

    private static byte[][] buildFont() {
        int max = 128;
        byte[][] glyphs = new byte[max][];
        for (int i = 0; i < GLYPHS.length; i += 2) {
            String text = GLYPHS[i];
            if (text.isEmpty() || text.charAt(0) >= max) {
                continue;
            }
            String[] rows = GLYPHS[i + 1].split("/");
            byte[] bits = new byte[GLYPH_H * GLYPH_W];
            for (int y = 0; y < GLYPH_H && y < rows.length; y++) {
                for (int x = 0; x < GLYPH_W && x < rows[y].length(); x++) {
                    bits[y * GLYPH_W + x] = (byte) (rows[y].charAt(x) == '1' ? 1 : 0);
                }
            }
            glyphs[text.charAt(0)] = bits;
        }
        return glyphs;
    }

    /** True when the character has a glyph; exposed for the test that keeps the table honest. */
    public static boolean has(char c) {
        return c < GLYPH_BITS.length && GLYPH_BITS[c] != null;
    }

    /** Rows of lit pixels for a character, top row first, one byte per pixel; null if absent. */
    public static byte[] bits(char c) {
        return c < GLYPH_BITS.length ? GLYPH_BITS[c] : null;
    }

    public static int glyphWidth() {
        return GLYPH_W;
    }

    public static int glyphHeight() {
        return GLYPH_H;
    }

    /**
     * Draws the OSD.
     *
     * @param tapeSeconds how long the tape has been rolling, for the counter
     * @param glitch      0..1; higher makes the OSD flicker and drop out like a bad tape
     */
    public void draw(SpriteBatch batch, int screenWidth, int screenHeight, float time,
                     float tapeSeconds, float glitch) {
        // Whole-pixel cells: 5 px glyphs at this scale look like a character generator.
        int scale = Math.max(2, Math.round(screenHeight / 240f));
        int pad = scale * 6;
        // Only the REC caption blinks - on a real camcorder the rest of the display is steady, and
        // fading the whole overlay makes it look like a UI animation instead of a character
        // generator burning into the corner of the frame.
        boolean recVisible = (time % 1.6f) < 0.9f;

        // The whole OSD jitters a little when the tape is unhappy.
        int jitter = glitch > 0.05f && ((int) (time * 30f) % 3 == 0) ? scale : 0;
        float flicker = glitch > 0.05f ? 0.6f + 0.4f * (float) Math.sin(time * 43.0) : 1f;
        float alpha = 0.95f * flicker;

        // top left: REC and its lamp
        float x = pad + jitter;
        float y = screenHeight - pad - GLYPH_H * scale;
        if (recVisible) {
            tint.set(0.85f, 0.16f, 0.12f, alpha);
            batch.setColor(tint);
            batch.draw(white, x, y + scale, scale * 3, scale * 3);
            tint.set(0.94f, 0.94f, 0.90f, alpha);
            batch.setColor(tint);
            text(batch, "REC", x + scale * 5, y, scale);
        }

        // top right: tape speed
        batch.setColor(0.94f, 0.94f, 0.90f, alpha);
        String speed = "SP";
        text(batch, speed, screenWidth - pad - width(speed, scale), y, scale);

        // bottom left: date and time from the device clock, like a camcorder that lost its settings
        calendar.setTimeInMillis(System.currentTimeMillis());
        String date = String.format(java.util.Locale.US, "%s %02d %04d",
                MONTHS[calendar.get(Calendar.MONTH)], calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.YEAR));
        String clock = String.format(java.util.Locale.US, "%02d:%02d:%02d",
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
        float bottomY = pad;
        text(batch, date, pad + jitter, bottomY + (GLYPH_H + 3) * scale, scale);
        text(batch, clock, pad + jitter, bottomY, scale);

        // bottom right: tape counter, which keeps counting across teleports like the real thing
        int total = (int) tapeSeconds;
        String counter = String.format(java.util.Locale.US, "%d:%02d:%02d",
                total / 3600, (total / 60) % 60, total % 60);
        text(batch, counter, screenWidth - pad - width(counter, scale), bottomY, scale);

        batch.setColor(Color.WHITE);
    }

    private static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG",
            "SEP", "OCT", "NOV", "DEC"};

    /**
     * Draws a string with a one-cell black drop shadow. Without it the white glyphs disappear
     * against the wallpaper, which in this level is almost as bright as they are.
     */
    private void text(SpriteBatch batch, String text, float x, float y, int scale) {
        saved.set(batch.getColor());
        batch.setColor(0f, 0f, 0f, saved.a * 0.65f);
        glyphs(batch, text, x + scale, y - scale, scale);
        batch.setColor(saved);
        glyphs(batch, text, x, y, scale);
    }

    private void glyphs(SpriteBatch batch, String text, float x, float y, int scale) {
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toUpperCase(text.charAt(i));
            byte[] bits = VhsOsd.bits(c);
            if (bits != null) {
                for (int row = 0; row < GLYPH_H; row++) {
                    for (int col = 0; col < GLYPH_W; col++) {
                        if (bits[row * GLYPH_W + col] == 0) {
                            continue;
                        }
                        batch.draw(white, x + col * scale, y + (GLYPH_H - 1 - row) * scale,
                                scale, scale);
                    }
                }
            }
            x += (GLYPH_W + 1) * scale;
        }
    }

    /** Width of a string in pixels at the given glyph scale. */
    public int width(String text, int scale) {
        return text.length() * (GLYPH_W + 1) * scale;
    }

    @Override
    public void dispose() {
        white.dispose();
    }
}
