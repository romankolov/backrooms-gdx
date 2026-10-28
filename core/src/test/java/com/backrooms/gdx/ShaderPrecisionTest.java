package com.backrooms.gdx;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Guards the GLSL ES rules that only bite on a real phone.
 *
 * <p>GLES2 requires a uniform that appears in both the vertex and fragment stage to have the
 * <em>identical</em> type and precision. Desktop GL silently accepts a mismatch, so a shader that
 * looks perfect on the desktop build can fail to link on Android - and because the world renderer
 * degrades instead of crashing, the only symptom is a screen full of fog colour. That is exactly
 * what happened with {@code u_time}; this test keeps it from happening again.
 */
public class ShaderPrecisionTest {

    /** Every shader that ships, so a new one cannot slip past these rules. */
    private static final String[] SHADERS = {"world.vert", "world.frag", "vhs.vert", "vhs.frag"};

    private static final Pattern UNIFORM = Pattern.compile(
            "^\\s*uniform\\s+((?:(?:lowp|mediump|highp)\\s+)?)(\\w+)\\s+(\\w+)\\s*(?:\\[[^\\]]*\\])?\\s*;",
            Pattern.MULTILINE);
    private static final Pattern DEFAULT_PRECISION = Pattern.compile(
            "^\\s*precision\\s+(lowp|mediump|highp)\\s+float\\s*;", Pattern.MULTILINE);

    @Test
    public void sharedUniformsUseTheSamePrecision() throws IOException {
        String[][] pairs = {{"world.vert", "world.frag"}, {"vhs.vert", "vhs.frag"}};
        for (String[] pair : pairs) {
            Map<String, String> v = uniforms(read(pair[0]));
            Map<String, String> f = uniforms(read(pair[1]));
            // vhs.vert legitimately declares no uniforms at all - the pass is driven entirely from
            // the fragment stage - so an empty vertex map is only a problem if the fragment has
            // uniforms we would then be unable to compare against.
            assertTrue("no uniforms parsed out of " + pair[0] + " or " + pair[1],
                    !v.isEmpty() || !f.isEmpty());

            for (Map.Entry<String, String> entry : f.entrySet()) {
                String name = entry.getKey();
                if (!v.containsKey(name)) {
                    continue;
                }
                if (!v.get(name).equals(entry.getValue())) {
                    fail(pair[0] + "/" + pair[1] + ": uniform " + name
                            + " is declared differently per stage: vertex " + v.get(name)
                            + " vs fragment " + entry.getValue() + " - GLES2 rejects this link");
                }
            }
        }
    }

    /**
     * A precision statement is GLSL ES only. Desktop GL 1.20 rejects {@code precision highp float;}
     * outright, so anything that is not inside an {@code #ifdef GL_ES} block silently kills the
     * world on the desktop build (that broke the screenshot job for one commit).
     */
    @Test
    public void precisionStatementsAreGuardedForDesktopGl() throws IOException {
        for (String name : SHADERS) {
            String[] lines = read(name).split("\n");
            int guard = 0;
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.equals("#ifdef GL_ES") || line.equals("#if defined(GL_ES)")) {
                    guard++;
                } else if (line.startsWith("#endif") && guard > 0) {
                    guard--;
                } else if (line.matches("^precision\\s+(lowp|mediump|highp)\\s+float\\s*;")) {
                    assertTrue(name + ":" + (i + 1) + " has an unguarded precision statement -"
                            + " desktop GL 1.20 cannot compile it", guard > 0);
                }
            }
        }
    }

    /** Returns uniform name -> "precision type", using the file's default float precision. */
    private static Map<String, String> uniforms(String source) {
        String defaultPrecision = "highp"; // GLES2 default for vertex shaders
        Matcher dp = DEFAULT_PRECISION.matcher(source);
        if (dp.find()) {
            defaultPrecision = dp.group(1);
        }
        Map<String, String> map = new LinkedHashMap<>();
        Matcher m = UNIFORM.matcher(source);
        while (m.find()) {
            String explicit = m.group(1).trim();
            String type = m.group(2);
            String precision = explicit.isEmpty()
                    ? (type.equals("float") || type.startsWith("vec") || type.startsWith("mat")
                            ? defaultPrecision : "n/a")
                    : explicit;
            map.put(m.group(3), precision + " " + type);
        }
        return map;
    }

    private static String read(String name) throws IOException {
        // The test task runs from core/, the shaders live in the shared assets folder.
        File[] candidates = {
                new File("../assets/shaders/" + name),
                new File("assets/shaders/" + name),
        };
        for (File file : candidates) {
            if (file.isFile()) {
                return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            }
        }
        fail("shader not found: " + name + " (looked in " + candidates[0] + " and " + candidates[1] + ")");
        return "";
    }
}
