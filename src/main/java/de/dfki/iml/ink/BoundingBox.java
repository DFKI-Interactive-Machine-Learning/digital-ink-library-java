package de.dfki.iml.ink;

/**
 * A bounding box around an ink object (e.g., stroke).
 */
public class BoundingBox {

    public final float xMin;
    public final float xMax;
    public final float yMax;
    public final float yMin;

    public final float width;
    public final float height;

    /*
     *   y
     *   ^                   (xMax|yMax)
     *   |        +---------------+
     *   |        |               |
     *   |        |               |
     *   |        |               |
     *   |        +---------------+
     *   |   (xMin|yMin)
     *   |
     *   +------------------------> x
     * (0|0)
     */
    public BoundingBox(float xMin, float yMin, float xMax, float yMax) {
        if (xMin > xMax) throw new IllegalArgumentException(String.format("xMin value (%f) should be less than xMax value (%f)", xMin, xMax));
        if (yMin > yMax) throw new IllegalArgumentException(String.format("yMin value (%f) should be less than yMax value (%f)", yMin, yMax));

        this.xMin = xMin;
        this.yMax = yMax;
        this.xMax = xMax;
        this.yMin = yMin;

        width = xMax - xMin;
        height = yMax - yMin;

        if(width < 0) throw new IllegalStateException("width cannot be negative");
        if(height < 0) throw new IllegalStateException("height cannot be negative");
    }

    public BoundingBox(BoundingBox bBox) {
        if (bBox.xMin > bBox.xMax)
            throw new IllegalArgumentException(String.format("param bBox: xMin value (%f) should be less than xMax value (%f)", bBox.xMin, bBox.xMax));
        if (bBox.yMin > bBox.yMax)
            throw new IllegalArgumentException(String.format("param bBox: yMin value (%f) should be less than yMax value (%f)", bBox.yMin, bBox.yMax));

        this.xMin = bBox.xMin;
        this.xMax = bBox.xMax;
        this.yMin = bBox.yMin;
        this.yMax = bBox.yMax;

        this.width = bBox.width;
        this.height = bBox.height;

        if(width < 0) throw new IllegalStateException("width cannot be negative");
        if(height < 0) throw new IllegalStateException("height cannot be negative");
    }

    public BoundingBox merge(BoundingBox box) {
        float xMin = Math.min(this.xMin, box.xMin);
        float xMax = Math.max(this.xMax, box.xMax);
        float yMin = Math.min(this.yMin, box.yMin);
        float yMax = Math.max(this.yMax, box.yMax);

        return new BoundingBox(xMin, yMin, xMax, yMax);
    }

    public boolean contains(float x, float y) {
        return x >= this.xMin && x <= this.xMax && y >= this.yMin && y <= this.yMax;
    }

    public boolean intersects(BoundingBox bBox) {
        return contains(bBox.xMin, bBox.yMax) || contains(bBox.xMin, bBox.yMin) || contains(bBox.xMax, bBox.yMax) || contains(bBox.xMax, bBox.yMin)
                || bBox.contains(this.xMin, this.yMax) || bBox.contains(this.xMin, this.yMin) || bBox.contains(this.xMax, this.yMax) ||bBox.contains(this.xMax, this.yMin);
    }

    public float getCoverage(BoundingBox bBox) {
        if (!intersects(bBox))
            return 0.0f;

        BoundingBox intersection = getIntersection(bBox);

        return intersection.getArea() / bBox.getArea();
    }

    public BoundingBox getIntersection(BoundingBox bBox) {
        if (!intersects(bBox))
            return null;

        float xMin = Math.max(this.xMin, bBox.xMin);
        float xMax = Math.min(this.xMax, bBox.xMax);
        float yMin = Math.max(this.yMin, bBox.yMin);
        float yMax = Math.min(this.yMax, bBox.yMax);

        return new BoundingBox(xMin, yMin, xMax, yMax);
    }

    public float getArea() {
        return width * height;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(yMin);
        result = prime * result + Float.floatToIntBits(xMin);
        result = prime * result + Float.floatToIntBits(xMax);
        result = prime * result + Float.floatToIntBits(yMax);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BoundingBox other = (BoundingBox) obj;
        if (Float.floatToIntBits(yMin) != Float.floatToIntBits(other.yMin))
            return false;
        if (Float.floatToIntBits(xMin) != Float.floatToIntBits(other.xMin))
            return false;
        if (Float.floatToIntBits(xMax) != Float.floatToIntBits(other.xMax))
            return false;
        if (Float.floatToIntBits(yMax) != Float.floatToIntBits(other.yMax))
            return false;
        return true;
    }
}

