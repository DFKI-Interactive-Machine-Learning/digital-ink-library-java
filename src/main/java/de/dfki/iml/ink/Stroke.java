package de.dfki.iml.ink;

import com.google.gson.annotations.Expose;
import de.dfki.iml.ink.serialization.SerializableInkObject;

import java.util.*;

/**
 * Represents a collection of Points, together forming a stroke.
 */
public class Stroke extends SerializableInkObject<Stroke> {

    @Expose
    private float[] x;
    @Expose
    private float[] y;
    @Expose
    private long[] timestamp;
    @Expose
    private float[] pressure;
    @Expose
    private final HashMap<String, Object> meta = new HashMap<>();
    private BoundingBox bbox;

    Stroke(StrokeBuilder strokeBuilder) {
        super(Type.stroke);

        List<Point> points = strokeBuilder.getPoints();

        int size = points.size();
        x = new float[size];
        y = new float[size];
        timestamp = new long[size];
        pressure = new float[size];

        int i = 0;
        for (Point p : strokeBuilder.getPoints()) {
            x[i] = p.x;
            y[i] = p.y;
            timestamp[i] = p.timestamp;
            pressure[i] = p.pressure;

            i++;
        }

        bbox = createBoundingBox();
    }

    public Stroke(float[] x, float[] y, long[] timestamp, float[] pressure) {
        super(Type.stroke);

        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.pressure = pressure;

        bbox = createBoundingBox();
    }

    public float[] getX() {
        return x;
    }

    public float[] getY() {
        return y;
    }

    public long[] getTimestamp() {
        return timestamp;
    }

    public float[] getPressure() {
        return pressure;
    }

    public int getSize() {
        return x.length;
    }

    private BoundingBox createBoundingBox() {
        float xMin = Float.MAX_VALUE;
        float xMax = Float.MIN_VALUE;
        float yMin = Float.MAX_VALUE;
        float yMax = Float.MIN_VALUE;

        for (int i = 0; i < x.length; i++) {
            xMin = Math.min(x[i], xMin);
            xMax = Math.max(x[i], xMax);
            yMin = Math.min(y[i], yMin);
            yMax = Math.max(y[i], yMax);
        }

        return new BoundingBox(xMin, yMin, xMax, yMax);
    }

    public BoundingBox getBoundingBox() {
        if (bbox == null) {
            bbox = createBoundingBox();
        }
        return bbox;
    }

    public Object getMeta(String metaKey) {
        return meta.get(metaKey);
    }

    public HashMap<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(String key, Object value) {
        meta.put(key, value);
    }

    public void offset(float x_offset, float y_offset) {
        assert x.length == y.length;
        for (int i = 0; i < x.length; i++) {
            x[i] += x_offset;
            y[i] += y_offset;
        }
        bbox = createBoundingBox();
    }

    public void scale(float x_factor, float y_factor) {
        assert x.length == y.length;
        for (int i = 0; i < x.length; i++) {
            x[i] *= x_factor;
            y[i] *= y_factor;
        }
        bbox = createBoundingBox();
    }

    public void removeDuplicateDots() {
        ArrayList<Float> retainedX = new ArrayList<>(getSize());
        ArrayList<Float> retainedY = new ArrayList<>(getSize());
        ArrayList<Long> retainedTimestamps = new ArrayList<>(getSize());
        ArrayList<Float> retainedPressures = new ArrayList<>(getSize());

        retainedX.add(x[0]);
        retainedY.add(y[0]);
        retainedTimestamps.add(timestamp[0]);
        retainedPressures.add(pressure[0]);

        for (int i = 1; i < getSize(); i++) {
            float currentX = x[i];
            float currentY = y[i];
            float lastX = x[i - 1];
            float lastY = y[i - 1];
            if (currentX != lastX || currentY != lastY) {
                retainedX.add(currentX);
                retainedY.add(currentY);
                retainedTimestamps.add(timestamp[i]);
                retainedPressures.add(pressure[i]);
            }
        }

        x = new float[retainedX.size()];
        y = new float[retainedY.size()];
        timestamp = new long[retainedTimestamps.size()];
        pressure = new float[retainedPressures.size()];

        for (int i = 0; i < x.length; i++) {
            x[i] = retainedX.get(i);
            y[i] = retainedY.get(i);
            timestamp[i] = retainedTimestamps.get(i);
            pressure[i] = retainedPressures.get(i);
        }

        bbox = createBoundingBox();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stroke stroke = (Stroke) o;
        return type == stroke.type && Arrays.equals(x, stroke.x) &&
                Arrays.equals(y, stroke.y) &&
                Arrays.equals(timestamp, stroke.timestamp) &&
                Arrays.equals(pressure, stroke.pressure) &&
                Objects.equals(meta, stroke.meta);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(meta);
        result = 31 * result + Arrays.hashCode(x);
        result = 31 * result + Arrays.hashCode(y);
        result = 31 * result + Arrays.hashCode(timestamp);
        result = 31 * result + Arrays.hashCode(pressure);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toJson() {
        return super.toJson(this);
    }

    @Override
    public Stroke fromJson(String jsonString) {
        return super.fromJson(jsonString, Stroke.class);
    }
}

