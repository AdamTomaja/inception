package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Random;

public class Color {

    private static final Random random = new Random();

    private double r, g, b;

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public static Color random() {
        return new Color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("r", r)
                .add("g", g)
                .add("b", b)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Double.compare(color.r, r) == 0 &&
                Double.compare(color.g, g) == 0 &&
                Double.compare(color.b, b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(r, g, b);
    }
}
