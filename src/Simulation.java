import codedraw.CodeDraw;

import java.awt.*;
import java.util.Random;

/*
## Was versteht man unter Datenkapselung? Geben Sie ein Beispiel, wo dieses Konzept in dieser Aufgabenstellung angewendet wird.
In der objektorientierten Programmierung wird darunter die Struktur verstanden, Daten zusammenzufassen
und in einer Datenstruktur (Klasse) zu speichern. Eine Klasse kann mit einer anderen Klasse nur iteragieren durch geeignte
Schnittstellen. In der Aufgabenstellung wird das durch die Unterteilung in Body und Vector3 + den jeweiligen Konstruktoren
mit Parametern und Methoden sichtbar.

## Was versteht man unter Data Hiding? Geben Sie ein Beispiel, wo dieses Konzept in dieser Aufgabenstellung angewendet wird.
Data Hiding ist die Kombination von Datenkapselung und das Verstecken von Implementierungsdetails. In der
Aufgabenstellung kommt dies vor durch zb private SET und GET methoden da damit die private Instanzvariablen nicht
exposed werden.

## Was steht bei einem Methodenaufruf links vom . (z.B. bei SpaceDraw.massToColor(1e30) oder
body.radius())? Woran erkennt man dabei Objektmethoden?
Nach der Konvention beginnen Klassennamen in Java mit einem großen Buchstaben und Objekte mit einem kleinen
Buchstaben. In dem Beispiel ist somit SpaceDraw eine Klasse und body ein Obekt ("body" = variable name for object.


 */

// Simulates the formation of a massive solar system.
public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9; // meters

    // one light year
    public static final double LY = 9.461e15; // meters

    // some further constants needed in the simulation
    public static final double SUN_MASS = 1.989e30; // kilograms
    public static final double SUN_RADIUS = 696340e3; // meters
    public static final double EARTH_MASS = 5.972e24; // kilograms
    public static final double EARTH_RADIUS = 6371e3; // meters

    // set some system parameters
    public static final double SECTION_SIZE = 2 * AU; // the size of the square region in space
    public static final int NUMBER_OF_BODIES = 22;
    public static final double OVERALL_SYSTEM_MASS = 20 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {
        // simulation
        CodeDraw cd = new CodeDraw();
        BodyQueue bodies = new BodyQueue(NUMBER_OF_BODIES);
        BodyForceMap forceOnBody = new BodyForceMap(NUMBER_OF_BODIES);

        Random random = new Random(2022);
        double masse = Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / NUMBER_OF_BODIES; //kg
        for (int i = 0; i < NUMBER_OF_BODIES; i++) {
            Vector3 massCenter = new Vector3(0.2 * random.nextGaussian() * AU,
                    0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU);
            Vector3 currentMovement = new Vector3(0.2 * random.nextGaussian() * 5e3,
                    0.2 * random.nextGaussian() * 5e3, 0.2 * random.nextGaussian() * 5e3);
            bodies.add(new Body(masse, massCenter, currentMovement));
        }

        double seconds = 0;

        // simulation loop
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

             /*
            for (int i = 0; i < bodies.length; i++) {
                for (int j = i + 1; j < bodies.length; j++) {
                    if (bodies[i].distanceTo(bodies[j]) < bodies[j].radius() + bodies[i].radius()) {
                        bodies[i] = bodies[i].merge(bodies[j]);
                        Body[] bodiesOneRemoved = new Body[bodies.length - 1];
                        for (int k = 0; k < bodiesOneRemoved.length; k++) {
                            bodiesOneRemoved[k] = bodies[k < j ? k : k + 1];
                        }
                        bodies = bodiesOneRemoved;

                        // since the body index i changed size there might be new collisions
                        // at all positions of bodies, so start all over again
                        i = -1;
                        j = bodies.length;
                    }
                }
            }
            */

            // merge bodies that have collided
            /*
            BodyQueue bq = new BodyQueue(bodies);
            BodyQueue nextLowerBody = new BodyQueue(bodies);
            nextLowerBody.poll();

            // bodies.poll().distanceTo(nextLowerBody.poll())
            while(bq.size() > 0){
                Body iBody = bq.poll();
                Body jBody = nextLowerBody.poll();
                if(iBody != null && jBody != null){
                    if(iBody.distanceTo(jBody) < jBody.radius() + iBody.radius()){
                        iBody.merge(jBody);
                        BodyQueue removedBodies = new BodyQueue(NUMBER_OF_BODIES-1);
                        while (removedBodies.size() > 0){
                            if (bq.size() < nextLowerBody.size()) {
                                removedBodies.add(iBody);
                            } else {
                                removedBodies.add(jBody);
                            }
                        }
                        bodies = removedBodies;
                    }
                }
            }

             */

            // for each body (with index i): compute the total force exerted on it.
                        /*
            for (int i = 0; i < bodies.length; i++) {
                forceOnBody[i] = new Vector3(); // begin with zero
                for (int j = 0; j < bodies.length; j++) {
                    if (i != j) {
                        Vector3 forceToAdd = bodies[i].gravitationalForce(bodies[j]);
                        forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                    }
                }
            }
             */
            BodyQueue copiedQueue = new BodyQueue(bodies);
            BodyQueue restore = new BodyQueue(bodies);
            for(int i = 0; i < copiedQueue.size(); i++){
                Vector3 forceOnBodyVec = new Vector3(0,0,0);
                Body currentBody = copiedQueue.poll();
                for(int j = 0; j < bodies.size(); j++){
                    Body secCurrentBody = bodies.poll();
                    if(i != j){
                        Vector3 forceToAdd = currentBody.gravitationalForce(secCurrentBody);
                        forceOnBodyVec = forceOnBodyVec.plus(forceToAdd);
                    }
                    bodies.add(secCurrentBody);
                }
                forceOnBody.put(currentBody, forceOnBodyVec);
            }
            bodies = restore;

            // now forceOnBody[i] holds the force vector exerted on body with index i.


            // for each body (with index i): move it according to the total force exerted on it.
            /*
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].move(forceOnBody[i]);
            }
            */
            BodyQueue copy = new BodyQueue(bodies);
            while(bodies.size() > 0){
                Body iterateBody = bodies.poll();
                if(iterateBody != null && forceOnBody.get(iterateBody) != null){
                    iterateBody.move(forceOnBody.get(iterateBody));
                }
            }
            bodies = copy;
            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                copy = new BodyQueue(bodies);
                while(bodies.size() > 0){
                    Body movedBody = bodies.poll();
                    movedBody.drawMovement(cd);
                    movedBody.draw(cd);
                }
                bodies = copy;

                // show new positions
                cd.show();
            }

        }

    }
}
