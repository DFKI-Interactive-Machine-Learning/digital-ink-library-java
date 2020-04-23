package de.dfki.iml.ink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ProcessingTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void processingFunctions() {
        float[] x1 = new float[]{1, 2, 3, 4};
        float[] y1 = new float[]{1, 2, 3, 4};
        long[] t1 = new long[]{1, 2, 3, 4};
        float[] p1 = new float[]{1, 1, 1, 1};
        Stroke s1 = new Stroke(x1, y1, t1, p1);

        float[] x2 = new float[]{2, 3, 3, 5};
        float[] y2 = new float[]{5, 3, 3, 1};
        long[] t2 = new long[]{1, 2, 3, 4};
        float[] p2 = new float[]{1, 1, 1, 1};
        Stroke s2 = new Stroke(x2, y2, t2, p2);

        float[] x3 = new float[]{3, 3};
        float[] y3 = new float[]{3, 3};
        long[] t3 = new long[]{1, 2};
        float[] p3 = new float[]{1, 1};
        Stroke s3 = new Stroke(x3, y3, t3, p3);

        ArrayList<Stroke> strokes = new ArrayList<>();
        strokes.add(s1);
        strokes.add(s2);
        strokes.add(s3);

        Sketch sketch = new Sketch(strokes);

        sketch.removeDuplicateDots();
        assertEquals(4, s1.getSize());
        assertEquals(3, s2.getSize());

        sketch.removeSingleDotStrokes();
        assertEquals(2, sketch.getSize());
    }
}
