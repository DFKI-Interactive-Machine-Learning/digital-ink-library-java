package de.dfki.iml.ink;

import com.google.gson.annotations.Expose;
import de.dfki.iml.ink.serialization.SerializableInkObject;

import java.util.*;

/**
 * A collection of Strokes, together forming a figure/group of
 * strokes.
 */
public class Sketch extends SerializableInkObject<Sketch> {

    @Expose
    private final ArrayList<Stroke> strokes;
    private BoundingBox bbox;
    @Expose
    private final HashMap<String, Object> meta = new HashMap<>();

    public Sketch(int size) {
        super(Type.sketch);

        strokes = new ArrayList<>(size);
    }

    public Sketch(List<Stroke> strokes) {
        super(Type.sketch);

        this.strokes = new ArrayList<>();
        addAll(strokes);
        bbox = createBoundingBox();
    }

    public void addAll(Collection<Stroke> strokes) {
        for (Stroke stroke : strokes) {
            addStroke(stroke);
        }
    }

    public boolean addStroke(Stroke stroke) {
        if (strokes.isEmpty()) {
            bbox = new BoundingBox(stroke.getBoundingBox());
        } else {
            bbox = getBoundingBox().merge(stroke.getBoundingBox());
        }

        return strokes.add(stroke);
    }

    public boolean removeStroke(Stroke stroke) {
        boolean removed = strokes.remove(stroke);

        if (!removed) {
            return false;
        }

        bbox = null;
        for (Stroke s : strokes) {
            if (bbox == null)
                bbox = s.getBoundingBox();
            else
                bbox = bbox.merge(s.getBoundingBox());
        }

        return true;
    }

    private BoundingBox createBoundingBox() {
        float xMin = Float.MAX_VALUE;
        float xMax = Float.MIN_VALUE;
        float yMin = Float.MAX_VALUE;
        float yMax = Float.MIN_VALUE;

        for (Stroke stroke : strokes) {
            BoundingBox boundingBox = stroke.getBoundingBox();

            xMin = Math.min(boundingBox.xMin, xMin);
            xMax = Math.max(boundingBox.xMax, xMax);
            yMin = Math.min(boundingBox.yMin, yMin);
            yMax = Math.max(boundingBox.yMax, yMax);
        }

        return new BoundingBox(xMin, yMin, xMax, yMax);
    }

    public BoundingBox getBoundingBox() {
        if(bbox == null) {
            bbox = createBoundingBox();
        }
        return bbox;
    }

    public List<Stroke> getStrokes() {
        return strokes;
    }

    public int getSize() {
        return strokes.size();
    }

    public HashMap<String, Object> getMeta() {
        return meta;
    }

    public Object getMeta(String metaKey) {
        return meta.get(metaKey);
    }

    public void setMeta(String key, Object value) {
        meta.put(key, value);
    }

    public void offset(float x_offset, float y_offset) {
        for (Stroke stroke: strokes) {
            stroke.offset(x_offset, y_offset);
        }
        bbox = createBoundingBox();
    }

    public void scale(float x_factor, float y_factor) {
        for (Stroke stroke: strokes) {
            stroke.scale(x_factor, y_factor);
        }
        bbox = createBoundingBox();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void normalize(float newSize, boolean keepAspectRatio) {
        offset(-bbox.xMin, -bbox.yMin);
        float xFactor = newSize / bbox.xMax;
        float yFactor = newSize / bbox.yMax;
        if (keepAspectRatio && bbox.xMax >= bbox.yMax) {
            scale(xFactor, xFactor);
        } else if (keepAspectRatio && bbox.xMax < bbox.yMax) {
            scale(yFactor, yFactor);
        } else {
            scale(xFactor, yFactor);
        }
    }

    public void removeDuplicateDots() {
        for (Stroke stroke : strokes) {
            stroke.removeDuplicateDots();
        }
        bbox = createBoundingBox();
    }

    public void removeSingleDotStrokes() {
        strokes.removeIf(p -> p.getSize() < 2);
        bbox = createBoundingBox();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sketch sketch = (Sketch) o;
        return type == sketch.type && Objects.equals(strokes, sketch.strokes) &&
                Objects.equals(meta, sketch.meta);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(strokes, meta);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toJson() {
        return super.toJson(this);
    }

    @Override
    public Sketch fromJson(String jsonString) {
        return super.fromJson(jsonString, Sketch.class);
    }
}
