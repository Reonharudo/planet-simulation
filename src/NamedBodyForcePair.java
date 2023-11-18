import codedraw.CodeDraw;

import java.util.Vector;

// A body with a name and an associated force. The leaf node of
// a hierarchical cosmic system. This class implements 'CosmicSystem'.
//
public class NamedBodyForcePair implements CosmicSystem {

    private final String name;
    private final Body body;
    private Vector3 force;

    // Initializes this with name, mass, current position and movement. The associated force
    // is initialized with a zero vector.
    public NamedBodyForcePair(String name, double mass, Vector3 massCenter, Vector3 currentMovement) {
        this.name = name;
        this.body = new Body(mass, massCenter, currentMovement);
        this.force = new Vector3();
    }

    // Returns the name of the body.
    public String getName() {
        return name;
    }

    @Override
    public NamedBodyForcePair getHeaviest() {
        return this;
    }

    @Override
    public String toString(){
        return getName();
    }


    @Override
    public Vector3 getMassCenter() {
        return body.massCenter();
    }

    @Override
    public double getMass() {
        return body.mass();
    }

    @Override
    public int numberOfBodies() {
        return 1;
    }

    @Override
    public double distanceTo(CosmicSystem cs) {
        return body.massCenter().distanceTo(cs.getMassCenter());
    }

    @Override
    public void addForceFrom(Body b) {
        if(body != b){
            force = force.plus(body.gravitationalForce(b));
        }
    }

    @Override
    public void addForceTo(CosmicSystem cs) {
        cs.addForceFrom(body);
    }

    @Override
    public BodyLinkedList getBodies() {
        BodyLinkedList list = new BodyLinkedList();
        list.addFirst(body);
        return list;
    }

    @Override
    public void update() {
        body.move(force);
        force = new Vector3();
    }

    @Override
    public void draw(CodeDraw cd) {
        body.draw(cd);
    }
}
