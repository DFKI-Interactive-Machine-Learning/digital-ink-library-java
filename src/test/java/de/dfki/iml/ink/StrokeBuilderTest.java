package de.dfki.iml.ink;

import org.junit.Test;

import java.util.List;

public class StrokeBuilderTest {

    @Test
    public void constructor() {
        StrokeBuilder sb = new StrokeBuilder();
        sb.toString();
    }

    @Test
    public void addPoint() {
        StrokeBuilder sb = new StrokeBuilder();
        Point p = new Point(1.f, 2.f, 12314, 2.4f);
        sb.addPoint(p);
    }

    @Test
    public void getPoints() {
        StrokeBuilder sb = new StrokeBuilder();
        Point p = new Point(1.f, 2.f, 12314, 2.4f);
        sb.addPoint(p);
        List<Point> points = sb.getPoints();
        assert points.size() == 1;
    }

    @Test
    public void get() {
        StrokeBuilder sb = new StrokeBuilder();
        Point p = new Point(1.f, 2.f, 12314, 2.4f);
        Point p1 = new Point(6.f, 8.f, 12314, 2.4f);
        sb.addPoint(p);
        sb.addPoint(p1);

        float xMin = sb.getXMin();
        float xMax = sb.getXMax();
        float yMin = sb.getYMin();
        float yMax = sb.getYMax();

        assert xMin == 1.f;
        assert xMax == 6.f;
        assert yMin == 2.f;
        assert yMax == 8.f;
    }

    @Test
    public void build() {
        StrokeBuilder sb = new StrokeBuilder();
        Point p = new Point(1.f, 2.f, 12314, 2.4f);
        Point p1 = new Point(6.f, 8.f, 12314, 2.4f);
        sb.addPoint(p);
        sb.addPoint(p1);

        Stroke stroke = sb.build();
        assert stroke.getSize() == 2;
    }
}