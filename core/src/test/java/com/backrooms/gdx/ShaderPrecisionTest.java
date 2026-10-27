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

    private static final Pattern UNIFORM = Pattern.compile(
            "^\\s*uniform\\s+((?:(?:lowp|mediump|highp)\\s+)?)(\\w+)\\s+(\\w+)\\s*(?:\\[[^\\]]*\\])?\\s*;",
            Pattern.MULTILINE);
    private static final Pattern DEFAULT_PRECISION = Pattern.compile(
            "^\\s*precision\\s+(lowp|mediump|highp)\\s+float\\s*;", Pattern.MULTILINE);

    @Test
    public void sharedUniformsUseTheSamePrecision() throws IOException {
        String vertex = read("world.vert");
        String fragment = read("world.frag");

        Map<String, String> v = uniforms(vertex);
        Map<String, String> f = uniforms(fragment);
        assertTrue("no uniforms parsed out of the vertex shader", v.size() > 0);

        for (Map.Entry<String, String> entry : f.entrySet()) {
            String name = entry.getKey();
            if (!v.containsKey(name)) {
                continue;
            }
            if (!v.get(name).equals(entry.getValue())) {
                fail("uniform " + name + " is declared differently per stage: vertex "
                        + v.get(name) + " vs fragment " + entry.getValue()
                        + " - GLES2 rejects this link");
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
