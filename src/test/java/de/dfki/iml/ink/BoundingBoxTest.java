package de.dfki.iml.ink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BoundingBoxTest {

    private static final float DELTA = 0.000001f;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void constructorSimple() {
        new BoundingBox(0, 0, 1, 1);
    }

    @Test
    public void constructorSwitchedBounds() {
        try {
            new BoundingBox(2, 1, 1, 0);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new BoundingBox(0, 1, 0, -1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new BoundingBox(0, 2, 1, 1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new BoundingBox(-1, 1, 0, 0);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }

        try {
            new BoundingBox(1, 0, 0, 1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void merge() {
        BoundingBox bb1 = new BoundingBox(0, 0, 1, 1);
        BoundingBox bb1a = new BoundingBox(0.5f, 0.5f, 1.5f, 1.5f);
        BoundingBox bb2 = new BoundingBox(-2, -2, -1, -1);

        BoundingBox bb1_bb1a = bb1.merge(bb1a);

        assertEquals(bb1_bb1a, bb1a.merge(bb1));
        assertEquals(bb1_bb1a.xMin, 0f, DELTA);
        assertEquals(bb1_bb1a.xMax, 1.5f, DELTA);
        assertEquals(bb1_bb1a.yMin, 0f, DELTA);
        assertEquals(bb1_bb1a.yMax, 1.5f, DELTA);
        assertEquals(bb1_bb1a.width, 1.5f, DELTA);
        assertEquals(bb1_bb1a.height, 1.5f, DELTA);

        BoundingBox bb2_bb1 = bb1.merge(bb2);

        assertEquals(bb2_bb1, bb2.merge(bb1));
        assertEquals(bb2_bb1.xMin, -2f, DELTA);
        assertEquals(bb2_bb1.xMax, 1f, DELTA);
        assertEquals(bb2_bb1.yMin, -2f, DELTA);
        assertEquals(bb2_bb1.yMax, 1f, DELTA);
        assertEquals(bb2_bb1.width, 3f, DELTA);
        assertEquals(bb2_bb1.height, 3f, DELTA);
    }

    @Test
    public void isIntersecting() {
        BoundingBox bb1 = new BoundingBox(0, 0, 1, 1);
        BoundingBox bb1a = new BoundingBox(0.5f, 0.5f, 1.5f, 1.5f);
        BoundingBox bb2 = new BoundingBox(-2, -2, -1, -1);

        boolean bb1IntersectsBb2 = bb1.intersects(bb2);
        boolean bb2IntersectsBb1 = bb2.intersects(bb1);

        assertEquals(bb2IntersectsBb1, bb1IntersectsBb2);
        assertFalse(bb2IntersectsBb1);

        BoundingBox bb3 = new BoundingBox(0.25f, 0.25f, 0.75f, 0.75f);
        boolean bb1IntersectsBb3 = bb1.intersects(bb3);
        boolean bb3IntersectsBb1 = bb3.intersects(bb1);

        assertEquals(bb1IntersectsBb3, bb3IntersectsBb1);
        assertTrue(bb1IntersectsBb3);

        assertFalse(bb2.intersects(bb3));

        assertTrue(bb1.intersects(bb1a));
        assertTrue(bb1a.intersects(bb1));
    }

    @Test
    public void getCoverage() {
    }

    @Test
    public void getIntersection() {
        BoundingBox a = new BoundingBox(0,0,2,2);
        BoundingBox b = new BoundingBox(1,1,3,3);

        BoundingBox a_b = a.getIntersection(b);
        assertEquals(a_b, b.getIntersection(a));
        assertEquals(a_b.xMin, 1f, DELTA);
        assertEquals(a_b.xMax, 2f, DELTA);
        assertEquals(a_b.yMin, 1f, DELTA);
        assertEquals(a_b.yMax, 2f, DELTA);
        assertEquals(a_b.width, 1f, DELTA);
        assertEquals(a_b.height, 1f, DELTA);


        BoundingBox c = new BoundingBox(0,0,2,1);
        BoundingBox d = new BoundingBox(1, -1, 3, 2);

        BoundingBox c_d = c.getIntersection(d);
        assertEquals(c_d, d.getIntersection(c));
        assertEquals(c_d.xMin, 1f, DELTA);
        assertEquals(c_d.yMin, 0f, DELTA);
        assertEquals(c_d.xMax, 2f, DELTA);
        assertEquals(c_d.yMax, 1f, DELTA);
        assertEquals(c_d.width, 1f, DELTA);
        assertEquals(c_d.height, 1f, DELTA);

        BoundingBox e = new BoundingBox(0,0,1,1);
        BoundingBox f = new BoundingBox(2,2,3,3);

        BoundingBox e_f = e.getIntersection(f);
        assertEquals(e_f, f.getIntersection(e));
        assertNull(e_f);

        BoundingBox g = new BoundingBox(0,0,3,3);
        BoundingBox h = new BoundingBox(1,1,2,2);

        BoundingBox g_h = g.getIntersection(h);
        assertEquals(g_h, h.getIntersection(g));
        assertEquals(g_h.xMin, 1f, DELTA);
        assertEquals(g_h.yMin, 1f, DELTA);
        assertEquals(g_h.xMax, 2f, DELTA);
        assertEquals(g_h.yMax, 2f, DELTA);
        assertEquals(g_h.width, 1f, DELTA);
        assertEquals(g_h.height, 1f, DELTA);

        BoundingBox i = new BoundingBox(0,0,2,1);
        BoundingBox j = new BoundingBox(1,0,3,1);

        BoundingBox i_j = i.getIntersection(j);
        assertEquals(i_j, j.getIntersection(i));
        assertEquals(i_j.xMin, 1f, DELTA);
        assertEquals(i_j.yMin, 0f, DELTA);
        assertEquals(i_j.xMax, 2f, DELTA);
        assertEquals(i_j.yMax, 1f, DELTA);
        assertEquals(i_j.width, 1f, DELTA);
        assertEquals(i_j.height, 1f, DELTA);
    }

    @Test
    public void getArea() {
    }
}