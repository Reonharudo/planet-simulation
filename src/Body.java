import codedraw.CodeDraw;

import java.awt.*;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class Body implements Massive{

    //TODO: change modifiers.
    private double mass;
    private Vector3 massCenter; // position of the mass center.
    private Vector3 currentMovement;

    //TODO: define constructor.
    public Body(double mass, Vector3 massCenter, Vector3 currentMovement){
        setMass(mass);
        setMassCenter(massCenter);
        setCurrentMovement(currentMovement);
    }

    public Body(){ }

    @Override
    public double mass(){
        return mass;
    }

    @Override
    public Vector3 massCenter() {
        return massCenter;
    }


    public void drawMovement(CodeDraw cd){
        Vector3 linie = massCenter.plus(currentMovement);
        linie.normalize();
        double laenge = linie.length() * 20;
        Vector3 laenger = new Vector3(linie.length() * 20, linie.length() * 20, linie.length() * 20);
        Vector3 finaleLaenge = linie.plus(laenger);
        cd.drawLine(massCenter.getX(), massCenter.getY(), finaleLaenge.getX(), finaleLaenge.getY());
    }

    // Returns the distance between the mass centers of this body and the specified body 'b'.
    public double distanceTo(Body b) {
        return this.getMassCenter().distanceTo(b.getMassCenter());
    }

    // Returns a vector representing the gravitational force exerted by 'b' on this body.
    // The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the
    // masses of the objects interacting, r being the distance between the centers of the masses
    // and G being the gravitational constant.
    // Hint: see simulation loop in Simulation.java to find out how this is done.
    public Vector3 gravitationalForce(Body b) {
        Vector3 massCenter1 = this.getMassCenter();
        Vector3 massCenter2 = b.getMassCenter();

        Vector3 direction = massCenter2.minus(massCenter1);
        double distance = direction.length();
        direction.normalize();


        double force = Simulation.G * this.getMass() * b.getMass() / (distance * distance);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force.)
    // Hint: see simulation loop in Simulation.java to find out how this is done.
    @Override
    public void move(Vector3 force) {
        Vector3 newPosition =
                force.times(1/this.getMass()).plus(this.getMassCenter()).plus(this.getCurrentMovement());

        // new minus old position.
        Vector3 newMovement = newPosition.minus(this.getMassCenter());

        // update body state
        this.setMassCenter(newPosition);
        this.setCurrentMovement(newMovement);
    }

    // Returns the approximate radius of this body.
    // (It is assumed that the radius r is related to the mass m of the body by r = m ^ 0.5,
    // where m and r measured in solar units.)
    @Override
    public double radius() {
        return SpaceDraw.massToRadius(mass);
    }

    // Returns a new body that is formed by the collision of this body and 'b'. The impulse
    // of the returned body is the sum of the impulses of 'this' and 'b'.
    public Body merge(Body b) {
        Body result = new Body();

        result.setMass(this.getMass() + b.getMass());

        //Quasi an den Anfang hinzugefügt und vom innersten Methodenaufruf reverse engineered.

        Vector3 massCenter1 = this.getMassCenter();
        Vector3 massCenter2 = b.getMassCenter();

        result.setMassCenter(
                massCenter1.times(this.getMass()).plus(massCenter2.times(b.getMass()).times(1.0/result.getMass()))
        );

        Vector3 currentCenter1 = this.getCurrentMovement();
        Vector3 currentCenter2 = b.getCurrentMovement();

        result.setCurrentMovement(
                currentCenter1.times(this.getMass()).plus(currentCenter2.times(b.getMass()).times(1.0/result.getMass()))
        );
        return result;
    }

    // Draws the body to the specified canvas as a filled circle.
    // The radius of the circle corresponds to the radius of the body
    // (use a conversion of the real scale to the scale of the canvas as
    // in 'Simulation.java').
    // Hint: call the method 'drawAsFilledCircle' implemented in 'Vector3'.
    @Override
    public void draw(CodeDraw cd) {
        cd.setColor(SpaceDraw.massToColor(this.getMass()));
       this.getMassCenter().drawAsFilledCircle(cd, SpaceDraw.massToRadius(this.getMass()));
    }

    // Returns a string with the information about this body including
    // mass, position (mass center) and current movement. Example:
    // "5.972E24 kg, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {
        return getMass()+"kg"+getMassCenter()+getCurrentMovement();
    }

    /**
     * GETTER AND SETTER
     *
     */
    @Override
    public double getMass() {
        return mass;
    }

    private void setMass(double mass) {
        this.mass = mass;
    }

    public
    @Override
    Vector3 getMassCenter() {
        return massCenter;
    }

    private void setMassCenter(Vector3 massCenter) {
        this.massCenter = massCenter;
    }

    private Vector3 getCurrentMovement() {
        return currentMovement;
    }

    private void setCurrentMovement(Vector3 currentMovement) {
        this.currentMovement = currentMovement;
    }
}

