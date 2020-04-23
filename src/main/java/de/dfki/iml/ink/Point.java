package de.dfki.iml.ink;

import de.dfki.iml.ink.serialization.SerializableInkObject;

/**
 * Represents a single dot of ink, containing x/y coordinates, pressure
 * and a timestamp
 */
public class Point {

    public final float x;
    public final float y;
    public final long timestamp;
    public final float pressure;

    /**
     * Default constructor for Point
     *
     * @param x         the x coordinate of the point
     * @param y         the y coordinate of the point
     * @param timestamp the timestamp (long milis)
     * @param pressure  the pressure value of the point
     */
    public Point(float x, float y, long timestamp, float pressure) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.pressure = pressure;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(pressure);
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
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
        Point other = (Point) obj;
        if (Float.floatToIntBits(pressure) != Float.floatToIntBits(other.pressure))
            return false;
        if (timestamp != other.timestamp)
            return false;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }
}
