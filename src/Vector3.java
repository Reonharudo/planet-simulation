import codedraw.CodeDraw;

import java.awt.*;

// This class represents vectors in a 3D vector space.
public class Vector3 {

    //TODO: change modifiers.
    private double x;
    private double y;
    private double z;

    //TODO: define constructor.
    public Vector3(double x, double y, double z){
        setX(x);
        setY(y);
        setZ(z);
    }

    public Vector3(){ }

    public Vector3(Vector3 vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
    }

    // Returns the sum of this vector and vector 'v'.
    public Vector3 plus(Vector3 v) {
        Vector3 result = new Vector3();
        result.setX(this.getX() + v.getX());
        result.setY(this.getY() + v.getY());
        result.setZ(this.getZ() + v.getZ());

        return result;
    }

    // Returns the product of this vector and 'd'.
    public Vector3 times(double d) {
        Vector3 result = new Vector3();
        result.setX(this.getX() * d);
        result.setY(this.getY() * d);
        result.setZ(this.getZ() * d);

        return result;
    }

    // Returns the sum of this vector and -1*v.
    public Vector3 minus(Vector3 v) {
        Vector3 result = new Vector3();
        result.setX(this.getX() - v.getX());
        result.setY(this.getY() - v.getY());
        result.setZ(this.getZ() - v.getZ());

        return result;
    }

    // Returns the Euclidean distance of this vector
    // to the specified vector 'v'.
    public double distanceTo(Vector3 v) {
        double dX = this.getX() - v.getX();
        double dY = this.getY() - v.getY();
        double dZ = this.getZ() - v.getZ();

        return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    // Returns the length (norm) of this vector.
    public double length() {
        return distanceTo(new Vector3()); // distance to origin.
    }

    // Normalizes this vector: changes the length of this vector such that it becomes 1.
    // The direction and orientation of the vector is not affected.
    public void normalize() {
        double length = length();
        setX(getX()/length);
        setY(getY()/length);
        setZ(getZ()/length);
    }

    // Draws a filled circle with a specified radius centered at the (x,y) coordinates of this vector
    // in the canvas associated with 'cd'. The z-coordinate is not used.
    public void drawAsFilledCircle(CodeDraw cd, double radius) {
        double x = cd.getWidth() * (this.getX() + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
        double y = cd.getWidth() * (this.getY() + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
        radius = cd.getWidth() * radius / Simulation.SECTION_SIZE;
        cd.fillCircle(x, y, Math.max(radius, 1.5));
    }

    // Returns the coordinates of this vector in brackets as a string
    // in the form "[x,y,z]", e.g., "[1.48E11,0.0,0.0]".
    public String toString() {
        return "["+getX()+","+getY()+","+getZ()+"]";
    }

    /**
     * GETTER AND SETTER
     */

    public double getX() {
        return x;
    }

    private void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    private void setY(double y) {
        this.y = y;
    }

    private double getZ() {
        return z;
    }

    private void setZ(double z) {
        this.z = z;
    }
}

