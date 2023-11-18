import codedraw.CodeDraw;

import java.util.Vector;

public class NamedBody implements Massive
{

    private String name;
    private double mass;
    private Vector3 massCenter;
    private Vector3 currentMovement;

    // Initializes this with name, mass, current position and movement. The associated force
    // is initialized with a zero vector.
    public NamedBody(String name, double mass, Vector3 massCenter, Vector3 currentMovement) {
        this.name = name;
        this.mass = mass;
        this.massCenter = massCenter;
        this.currentMovement = currentMovement;
    }

    // Compares `this` with the specified object. Returns `true` if the specified `o` is not
    // `null` and is of type `NamedBody` and both `this` and `o` have equal names.
    // Otherwise `false` is returned.
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o instanceof NamedBody && this.name.equals( ((NamedBody) o).getName() )){
            return true;
        }else{
            return false;
        }
    }

    // Returns the hashCode of `this`.
    public int hashCode() {
        return name.hashCode();
    }

    protected String getName(){
        return name;
    }

    // Returns a readable representation including the name of this body.
    public String toString() {
        return name+" "+mass+"kg"+massCenter.toString()+currentMovement.toString();
    }

    @Override
    public double mass() {
        return mass;
    }

    @Override
    public Vector3 massCenter() {
        return massCenter;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public Vector3 getMassCenter() {
        return massCenter;
    }

    @Override
    public double getRadius() {
        return Massive.super.getRadius();
    }

    @Override
    public double radius() {
        return Massive.super.radius();
    }

    @Override
    public Vector3 gravitationalForce(Massive b) {
        return Massive.super.gravitationalForce(b);
    }

    @Override
    public void move(Vector3 force) {
        Vector3 newPosition =
                force.times(1/this.getMass()).plus(this.getMassCenter()).plus(this.currentMovement);

        // new minus old position.
        Vector3 newMovement = newPosition.minus(this.getMassCenter());

        // update body state
        this.massCenter = newPosition;
        this.currentMovement = newMovement;
    }

    @Override
    public void draw(CodeDraw cd) {
        cd.setColor(SpaceDraw.massToColor(this.getMass()));
        this.getMassCenter().drawAsFilledCircle(cd, SpaceDraw.massToRadius(this.getMass()));
    }
}
