package com.cydercode.inception.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Optional;
import java.util.Random;

public class Location {

    private static final Random random = new Random();

    private double x, y, z;

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0 &&
                Double.compare(location.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y, z);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .add("z", z)
                .toString();
    }

    public double distanceFrom(Location location) {
        return Math.sqrt(Math.pow(x - location.x, 2) + Math.pow(y - location.y, 2) + Math.pow(z - location.z, 2));
    }

    public static Optional<Location> tryParse(String location) {
        String[] splitted = location.split(",");
        if (splitted.length != 3) {
            return Optional.empty();
        }

        try {
            return Optional.of(new Location(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2])));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Location random() {
        return new Location(random.nextDouble() * 100,
                random.nextDouble() * 100,
                random.nextDouble() * 100);
    }
}
