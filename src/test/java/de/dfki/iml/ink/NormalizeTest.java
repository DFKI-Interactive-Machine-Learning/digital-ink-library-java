package de.dfki.iml.ink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class NormalizeTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Sketch generateSketch() {
        float[] x1 = new float[]{1, 2};
        float[] y1 = new float[]{0.9f, 0.3f};
        float[] x2 = new float[]{1.2f, 1.5f};
        float[] y2 = new float[]{0.6f, 0.7f};
        Stroke s1 = new Stroke(x1, y1, new long[]{}, new float[]{});
        Stroke s2 = new Stroke(x2, y2, new long[]{}, new float[]{});
        LinkedList<Stroke> strokes = new LinkedList<>();
        strokes.add(s1);
        strokes.add(s2);
        return new Sketch(strokes);
    }

    @Test
    public void defaultNormalization() {
        Sketch sketch = generateSketch();
        Stroke s1 = sketch.getStrokes().get(0);
        Stroke s2 = sketch.getStrokes().get(1);
        sketch.normalize(1f, false);

        assertEquals(0.f, s1.getX()[0]);
        assertEquals(1.f, s1.getX()[1]);
        assertEquals(0.f, s1.getY()[1]);
        assertEquals(1.f, s1.getY()[0]);
        for (int i = 0; i < s2.getSize(); i++) {
            float x = s2.getX()[i];
            float y = s2.getY()[i];
            assertTrue(x > 0.);
            assertTrue(x < 1.);
            assertTrue(y > 0.);
            assertTrue(y < 1.);
        }

        sketch.normalize(2f, false);
        assertEquals(2.f, s1.getX()[1]);
        assertEquals(2.f, s1.getY()[0]);
    }

    @Test
    public void aspectRatioPreservingNormalization() {
        Sketch sketch = generateSketch();
        Stroke s1 = sketch.getStrokes().get(0);
        sketch.normalize(1f, true);

        assertEquals(1.f, s1.getX()[1]);
        assertTrue(s1.getY()[0] < 1);

        sketch = generateSketch();
        sketch.getStrokes().get(1).getY()[1] = 20.f;
        sketch.normalize(1f, true);
        assertTrue(sketch.getStrokes().get(0).getX()[1] < 1);
        assertTrue(sketch.getStrokes().get(0).getY()[0] < 1);
        assertEquals(1.f, sketch.getStrokes().get(1).getY()[1]);
    }
}
