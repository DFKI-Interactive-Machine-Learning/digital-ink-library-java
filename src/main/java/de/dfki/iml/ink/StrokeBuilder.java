package de.dfki.iml.ink;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Builder pattern for incremental creation of immutable Stroke
 * objects.
 */
public class StrokeBuilder {

    private LinkedList<Point> points;

    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;

    public StrokeBuilder() {
        points = new LinkedList<>();
        xMin = Float.MAX_VALUE;
        xMax = Float.MIN_VALUE;
        yMin = Float.MAX_VALUE;
        yMax = Float.MIN_VALUE;
    }

    public void addPoint(Point p) {
        xMin = Math.min(p.x, xMin);
        xMax = Math.max(p.x, xMax);
        yMin = Math.min(p.y, yMin);
        yMax = Math.max(p.y, yMax);

        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    public float getXMin() {
        return xMin;
    }

    public float getXMax() {
        return xMax;
    }

    public float getYMin() {
        return yMin;
    }

    public float getYMax() {
        return yMax;
    }

    public int getSize() {
        return points.size();
    }

    /**
     * Creates an immutable Stroke object based on previously added Point data.
     * @return immutable Stroke object
     */
    public Stroke build() {
        return new Stroke(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrokeBuilder that = (StrokeBuilder) o;
        return Float.compare(that.xMin, xMin) == 0 &&
                Float.compare(that.xMax, xMax) == 0 &&
                Float.compare(that.yMin, yMin) == 0 &&
                Float.compare(that.yMax, yMax) == 0 &&
                Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points, xMin, xMax, yMin, yMax);
    }
}
