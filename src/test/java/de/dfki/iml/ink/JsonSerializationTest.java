package de.dfki.iml.ink;

import de.dfki.iml.ink.serialization.JsonSerializer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertNotNull;

public class JsonSerializationTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void constructorSimple() {
        new JsonSerializer();
        new JsonSerializer(true);
        new JsonSerializer(false);
    }

    @Test
    public void serializeStroke() {
        StrokeBuilder sb = new StrokeBuilder();
        sb.addPoint(new Point(1, 2,3,4));
        sb.addPoint(new Point(2, 3,4,5));
        Stroke s = sb.build();

        new JsonSerializer().dumps(s);
    }

    @Test
    public void deserializeStroke() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        StrokeBuilder sb = new StrokeBuilder();
        sb.addPoint(new Point(1, 2,3,4));
        sb.addPoint(new Point(2, 3,4,5));
        Stroke stroke = sb.build();

        stroke.setMeta("float", 1.);

        String serializedString = jsonSerializer.dumps(stroke);
        Stroke deserializedStroke = (Stroke) jsonSerializer.loads(serializedString, Stroke.class);

        assert stroke.equals(deserializedStroke);
    }

    @Test
    public void serializeSketch() {
        StrokeBuilder sb = new StrokeBuilder();
        sb.addPoint(new Point(1, 2,3,4));
        sb.addPoint(new Point(2, 3,4,5));
        Stroke stroke1 = sb.build();

        sb = new StrokeBuilder();
        sb.addPoint(new Point(6, 7,8,9));
        sb.addPoint(new Point(7, 8,9,10));
        Stroke stroke2 = sb.build();

        Sketch sketch = new Sketch(2);
        sketch.addStroke(stroke1);
        sketch.addStroke(stroke2);

        new JsonSerializer().dumps(sketch);
    }

    @Test
    public void deserializeSketch() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        StrokeBuilder sb = new StrokeBuilder();
        sb.addPoint(new Point(1, 2,3,4));
        sb.addPoint(new Point(2, 3,4,5));
        Stroke stroke1 = sb.build();

        sb = new StrokeBuilder();
        sb.addPoint(new Point(6, 7,8,9));
        sb.addPoint(new Point(7, 8,9,10));
        Stroke stroke2 = sb.build();

        Sketch sketch = new Sketch(2);
        sketch.addStroke(stroke1);
        sketch.addStroke(stroke2);

        String serializedString = jsonSerializer.dumps(sketch);
        Sketch deserializedSketch = (Sketch) jsonSerializer.loads(serializedString, Sketch.class);

        assert sketch.equals(deserializedSketch);
    }

    @Test
    public void deserializeSketchBoundingBox() {
        JsonSerializer jsonSerializer = new JsonSerializer();
        StrokeBuilder sb = new StrokeBuilder();
        sb.addPoint(new Point(1, 2,3,4));
        sb.addPoint(new Point(2, 3,4,5));
        Stroke stroke1 = sb.build();

        sb = new StrokeBuilder();
        sb.addPoint(new Point(6, 7,8,9));
        sb.addPoint(new Point(7, 8,9,10));
        Stroke stroke2 = sb.build();

        Sketch sketch = new Sketch(2);
        sketch.addStroke(stroke1);
        sketch.addStroke(stroke2);

        String serializedString = jsonSerializer.dumps(sketch);
        Sketch deserializedSketch = (Sketch) jsonSerializer.loads(serializedString, Sketch.class);

        assertNotNull(deserializedSketch.getBoundingBox());
    }
}
