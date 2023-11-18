// A map that associates an object of 'Massive' with a Vector3. The number of key-value pairs
// is not limited.
//
// TODO: define further classes and methods for the binary search tree and the implementation
//  of MassiveSet, if needed.
//
public class MassiveForceTreeMap {

    private MyTreeMapNode root;
    public MassiveForceTreeMap(){}

    public MassiveForceTreeMap(MyTreeMapNode root) {
        this.root = root;
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Massive key, Vector3 value) {
        if(root == null){
            root = new MyTreeMapNode(key, value);
            return null;
        }
        return root.put(key, value);
    }

    // Returns the value associated with the specified key, i.e. the method returns the force vector
    // associated with the specified key. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Massive key) {
        return root.findValueByKey(key);
    }

    // Returns 'true' if this map contains a mapping for the specified key.
    //Precondition: key != null
    public boolean containsKey(Massive key) {
        return root.findValueByKey(key) != null;
    }

    // Returns a readable representation of this map, in which key-value pairs are ordered
    // descending according to 'key.getMass()'.
    public String toString() {
        return root.toString();
    }

    public Massive remove(Massive element){
        return null;
    }

    public MassiveLinkedList toList() {
        MassiveLinkedList list = new MassiveLinkedList();
        root.toList(list);
        return list;
    }

    public void clear(){
        this.root = null;
    }

    public int size() {
        if(root == null) {
            return 0;
        }
        return root.size();
    }

    // Returns a `MassiveSet` view of the keys contained in this tree map. Changing the
    // elements of the returned `MassiveSet` object also affects the keys in this tree map.
    public MassiveSet getKeys() {
        return new MyMassiveSet(this);
    }

    public MassiveIterator iterator() {
       MyMassiveSetIterator iterator = new MyMassiveSetIterator();

        if(root != null) {
            return null;
        }
        return iterator;
    }
}


class MyTreeMapNode{
    private Massive key;
    private Vector3 value;

    private MyTreeMapNode left;
    private MyTreeMapNode right;

    protected MyTreeMapNode(Massive key, Vector3 value){
        this.key = key;
        this.value = value;
    }

    protected Vector3 put(Massive key, Vector3 value){
        if(key.mass() == this.key.mass()){
            Vector3 oldVal = this.value;
            this.value = value;
            return oldVal;
        }else{
            if(key.mass() < this.key.mass()){
                if(left != null){
                    return left.put(key, value);
                }else{
                    left = new MyTreeMapNode(key, value);
                    return null;
                }
            }else{
                if(right != null){
                    return right.put(key, value);
                }else{
                    right = new MyTreeMapNode(key, value);
                    return null;
                }
            }
        }
    }

    protected Vector3 findValueByKey(Massive key){
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

    public void toList(MassiveLinkedList list) {
        if(this.key != null) { //current ?
            list.addFirst(this.key);
        }else if(left != null){ //left ?
            left.toList(list);
        }else { //right ?
            right.toList(list);
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

    public int size(){
        int size = 1;
        if(left != null){
            size += left.size();
        }
        if(right != null){
            size += right.size();
        }
        return size;
    }

    public Massive min(){
        Massive min = key;
        if(left != null){
            min = left.min();
        }
        return min;
    }

    public MyTreeMapNode remove(MyTreeMapNode node, Massive element){
        if(node == null){
            return node;
        }
        return null;
    }

    public Massive iterator(MyMassiveSetIterator iterator, boolean isSubtreeFromRight) {
        MyTreeMapNode start = isSubtreeFromRight ? right : this;
        while(start != null) {
            new MyMassiveSetIterator(start, iterator);
            start = isSubtreeFromRight ? start.right : start.left;
        }
        return this.key;
    }

}

//TODO: Define additional class(es) implementing the binary search tree and the implementation
// of MassiveSet (either here or in a separate file).
