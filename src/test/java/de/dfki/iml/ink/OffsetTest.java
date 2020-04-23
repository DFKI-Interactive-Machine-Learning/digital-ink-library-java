package de.dfki.iml.ink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class OffsetTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private static final float DELTA = 0.0000001f;

    @Test
    public void offsetStroke() {
        float[] x = new float[]{0.f, 0.f, 1.f, 1.f};
        float[] y = new float[]{0.f, 1.f, 1.f, 0.f};

        Stroke stroke = new Stroke(x, y, null, null);
        stroke.offset(4.2f, -1.f);

        assertArrayEquals(stroke.getX(), new float[]{4.2f, 4.2f, 5.2f, 5.2f}, DELTA);
        assertArrayEquals(stroke.getY(), new float[]{-1.f, 0.f, 0.f, -1.f}, DELTA);

        assertEquals(stroke.getBoundingBox().xMin , 4.2f, DELTA);
        assertEquals(stroke.getBoundingBox().xMax , 5.2f, DELTA);
        assertEquals(stroke.getBoundingBox().yMin , -1.f, DELTA);
        assertEquals(stroke.getBoundingBox().yMax , 0.f, DELTA);
    }

    @Test
    public void offsetSketch() {
        float[] x = new float[]{0.f, 0.f, 1.f, 1.f};
        float[] y = new float[]{0.f, 1.f, 1.f, 0.f};

        Stroke strokeOne = new Stroke(x, y, null, null);
        Stroke strokeTwo = new Stroke(x.clone(), y.clone(), null, null);
        List<Stroke> strokes = new LinkedList<>();
        strokes.add(strokeOne);
        strokes.add(strokeTwo);
        Sketch sketch = new Sketch(strokes);
        sketch.offset(4.2f, -1.f);


        assertArrayEquals(sketch.getStrokes().get(0).getX(), new float[]{4.2f, 4.2f, 5.2f, 5.2f}, DELTA);
        assertArrayEquals(sketch.getStrokes().get(0).getY(), new float[]{-1.f, 0.f, 0.f, -1.f},DELTA);
        assertArrayEquals(sketch.getStrokes().get(1).getX(), new float[]{4.2f, 4.2f, 5.2f, 5.2f},DELTA);
        assertArrayEquals(sketch.getStrokes().get(1).getY(), new float[]{-1.f, 0.f, 0.f, -1.f},DELTA);

        assertEquals(sketch.getBoundingBox().xMin, 4.2f, DELTA);
        assertEquals(sketch.getBoundingBox().xMax, 5.2f, DELTA);
        assertEquals(sketch.getBoundingBox().yMin, -1.f, DELTA);
        assertEquals(sketch.getBoundingBox().yMax, 0.f, DELTA);
    }
}
