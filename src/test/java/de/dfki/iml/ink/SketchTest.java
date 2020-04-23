package de.dfki.iml.ink;

import de.dfki.iml.ink.serialization.JsonSerializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SketchTest {

    @Test
    public void constructor() {

    }

    @Test
    public void meta() {
        StrokeBuilder sb = new StrokeBuilder();
        sb.addPoint(new Point(1, 2,3,4));
        sb.addPoint(new Point(2, 3,4,5));
        Stroke stroke1 = sb.build();
        stroke1.setMeta("s1", "Hello World");

        sb = new StrokeBuilder();
        sb.addPoint(new Point(6, 7,8,9));
        sb.addPoint(new Point(7, 8,9,10));
        Stroke stroke2 = sb.build();
        stroke2.setMeta("s2", 6.7);

        Sketch sketch = new Sketch(2);
        sketch.addStroke(stroke1);
        sketch.addStroke(stroke2);
        List<Double> doubleArrayList = Arrays.asList(1.,2.,3.,4.);
        sketch.setMeta("sketchArray", doubleArrayList);

        JsonSerializer jsonSerializer = new JsonSerializer();
        String serializedString = jsonSerializer.dumps(sketch);
        Sketch deserializedSketch = (Sketch) jsonSerializer.loads(serializedString, Sketch.class);

        assert sketch.equals(deserializedSketch);
    }
}