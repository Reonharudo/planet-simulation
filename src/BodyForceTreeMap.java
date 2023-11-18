// A map that associates a Body with a Vector3 (typically this is the force exerted on the body).
// The number of key-value pairs is not limited.
public class BodyForceTreeMap {

    private MyTreeMapNodeBody node;


    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Body key, Vector3 value) {
        if(node == null){
            node = new MyTreeMapNodeBody(key, value);
            return null;
        }
        return node.put(key, value);
    }

    // Returns the value associated with the specified key, i.e. the method returns the force vector
    // associated with the specified key. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Body key) {
        return node.findValueByKey(key);
    }

    // Returns 'true' if this map contains a mapping for the specified key.
    public boolean containsKey(Body key) {
        return node.findValueByKey(key) != null;
    }

    // Returns a readable representation of this map, in which key-value pairs are ordered
    // descending according to the mass of the bodies.
    public String toString() {
        return node.toString();
    }
}

class MyTreeMapNodeBody {
    private Body key;
    private Vector3 value;

    private MyTreeMapNodeBody left;
    private MyTreeMapNodeBody right;

    protected MyTreeMapNodeBody(Body key, Vector3 value){
        this.key = key;
        this.value = value;
    }

    protected Vector3 put(Body key, Vector3 value){
        if(key.mass() == this.key.mass()){
            Vector3 oldVal = this.value;
            this.value = value;
            return oldVal;
        }else{
            if(key.mass() < this.key.mass()){
                if(left != null){
                    return left.put(key, value);
                }else{
                    left = new MyTreeMapNodeBody(key, value);
                    return null;
                }
            }else{
                if(right != null){
                    return right.put(key, value);
                }else{
                    right = new MyTreeMapNodeBody(key, value);
                    return null;
                }
            }
        }
    }

    protected Vector3 findValueByKey(Body key){
        if(key.mass() == this.key.mass()){
            return this.value;
        }else if(key.mass() < this.key.mass()){
            if(left != null){
                return left.findValueByKey(key);
            }else{
                return null;
            }
        }else{
            if(right != null){
                return right.findValueByKey(key);
            }else{
                return null;
            }
        }
    }

    @Override
    public String toString(){
        String erg = "";
        if(left != null){
            erg += left.toString();
        }
        erg += key.toString() + value.toString() + System.lineSeparator();
        if(right != null){
            erg += right.toString();
        }
        return erg;
    }
}
