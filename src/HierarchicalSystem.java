import codedraw.CodeDraw;

// A cosmic system that is composed of a central named body (of type 'NamedBodyForcePair')
// and an arbitrary number of subsystems (of type 'HierarchicalSystem') in its orbit.
// This class implements 'CosmicSystem'.
//
public class HierarchicalSystem implements CosmicSystem {

    private double mass;
    private Vector3 massCenter;

    private NamedBodyForcePair central;
    private CosmicSystem[] inOrbit;

    // Initializes this system with a name and a central body.
    public HierarchicalSystem(NamedBodyForcePair central, CosmicSystem... inOrbit) {
        this.central = central;
        this.inOrbit = inOrbit;

        mass = central.getMass();

        massCenter = new Vector3(central.getMassCenter());

        //Compute total mass
        for (CosmicSystem cs : this.inOrbit) {
            mass += cs.getMass();
        }
    }


    @Override
    public NamedBodyForcePair getHeaviest() {
        double highestMass = inOrbit[0].getMass();
        int highestIndex = 0;
        for(int i = 0; i < inOrbit.length; i++){
            if(inOrbit[i].getMass() > highestMass){
                highestMass = inOrbit[i].getMass();
                highestIndex = i;
            }
        }
        return inOrbit[highestIndex].getHeaviest();
    }

    @Override
    public String toString(){
        if(inOrbit.length > 0){
            String desc = "";
            for(int i = 0; i < inOrbit.length - 1; i++){
                desc += inOrbit[i] + ", ";
            }
            //Last element will be added to desc but without a comma
            desc += inOrbit[inOrbit.length-1];

            return central + " {"+desc+"} ";
        }
        return central.toString();
    }

    @Override
    public Vector3 getMassCenter() {
        return massCenter;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public int numberOfBodies() {
        int bodyCount = 1;
        for(CosmicSystem cs : inOrbit){
            bodyCount += cs.numberOfBodies();
        }
        return bodyCount;
    }

    @Override
    public double distanceTo(CosmicSystem cs) {
        return massCenter.distanceTo(cs.getMassCenter());
    }

    @Override
    public void addForceFrom(Body b) {
        central.addForceFrom(b);
        for (CosmicSystem cs : inOrbit) {
            cs.addForceFrom(b);
        }
    }

    @Override
    public void addForceTo(CosmicSystem cs) {
        BodyLinkedList bodies = getBodies();
        while (bodies.size() != 0) {
            cs.addForceFrom(bodies.pollFirst());
        }
    }

    @Override
    public BodyLinkedList getBodies() {
        Body innerCentral = central.getBodies().pollFirst();
        BodyLinkedList allBodies = new BodyLinkedList();
        allBodies.addFirst(innerCentral);

        for (CosmicSystem cs : inOrbit) {
            BodyLinkedList bodies = cs.getBodies();
            while (bodies.size() != 0) {
                allBodies.addLast(bodies.pollFirst());
            }
        }
        return allBodies;
    }

    @Override
    public void update() {
        central.update();

        //Move all the other Systems
        for(CosmicSystem system : inOrbit){
            system.update();
        }
    }

    @Override
    public void draw(CodeDraw cd) {
        central.draw(cd);
        for (CosmicSystem system : inOrbit) {
            system.draw(cd);
        }
    }
}
