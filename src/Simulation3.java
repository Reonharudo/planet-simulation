import codedraw.CodeDraw;

import java.awt.*;
import java.util.Random;

// Simulates the formation of a massive solar system.
//
public class Simulation3 {

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

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {
        // simulation
        CodeDraw cd = new CodeDraw();
        BodyLinkedList bodies = new BodyLinkedList();
        BodyForceTreeMap forceOnBody = new BodyForceTreeMap();
        Random random = new Random(2022);

        double masse = Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / NUMBER_OF_BODIES; //kg
        for (int i = 0; i < NUMBER_OF_BODIES; i++) {
            Vector3 massCenter = new Vector3(0.2 * random.nextGaussian() * AU,
                    0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU);
            Vector3 currentMovement = new Vector3(0.2 * random.nextGaussian() * 5e3,
                    0.2 * random.nextGaussian() * 5e3, 0.2 * random.nextGaussian() * 5e3);
            bodies.addLast(new Body(masse, massCenter, currentMovement));
        }

        double seconds = 0;

        // simulation loop
        while(true){
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //merge bodies that have collided
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
            for (int i = 0; i < bodies.size(); i++) {
                for (int j = i + 1; j < bodies.size(); j++) {
                    if (bodies.get(i).distanceTo(bodies.get(j)) < bodies.get(j).radius() + bodies.get(i).radius()) {
                        bodies.add(i, bodies.get(i).merge(bodies.get(j)));

                        BodyLinkedList bodiesOneRemoved = new BodyLinkedList();
                        for (int k = 0; k < bodiesOneRemoved.size(); k++) {
                            bodiesOneRemoved.add(k, bodies.get(k < j ? k : k + 1));
                        }
                        bodies = bodiesOneRemoved;

                        // since the body index i changed size there might be new collisions
                        // at all positions of bodies, so start all over again
                        i = -1;
                        j = bodies.size();
                    }
                }
            }

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

            for(int i = 0; i < bodies.size(); i++){
                Vector3 forceOnBodyVec = new Vector3(0,0,0);
                Body currentBody = bodies.get(i);

                for(int j = 0; j < bodies.size(); j++){
                    Body secCurrentBody = bodies.get(j);
                    if(i != j){
                        Vector3 forceToAdd = currentBody.gravitationalForce(secCurrentBody);
                        forceOnBodyVec = forceOnBodyVec.plus(forceToAdd);
                    }
                }
                forceOnBody.put(currentBody, forceOnBodyVec);
            }

            // for each body (with index i): move it according to the total force exerted on it.
            /*
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].move(forceOnBody[i]);
            }
            */
            for(int i = 0; i < bodies.size(); i++){
                Body keyBody = bodies.get(i);
                keyBody.move(forceOnBody.get(keyBody));
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                for (int i = 0; i < bodies.size(); i++) {
                    Body tempBody = bodies.get(i);
                    //tempBody.drawMovement(cd);
                    tempBody.draw(cd);
                }

                // show new positions
                cd.show();
            }
        }

    }
}
