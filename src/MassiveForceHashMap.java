// A hash map that associates a 'Massive'-object with a Vector3 (typically this is the force
// exerted on the object). The number of key-value pairs is not limited.
//

import java.util.LinkedList;

/**
 *
 * Kollisionsbehandlung durch Verkettung der Überläufer.
 * Einträge sind somit einer eigenen MyVector3LinkedList gespeichert.
 */
public class MassiveForceHashMap {

    // TODO: define missing parts of this class.
    private MassiveLinkedList[] keys;
    private MyVector3LinkedList[] values;

    private static final int INIT_CAPACITY = 100;

    // Initializes 'this' as an empty map.
    public MassiveForceHashMap() {
        keys = new MassiveLinkedList[INIT_CAPACITY];
        values = new MyVector3LinkedList[INIT_CAPACITY];
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Massive key, Vector3 value) {
        int hashIndex = Math.abs(key.hashCode() % INIT_CAPACITY);

        //Case 1: is null
        if(keys[hashIndex] == null){
            keys[hashIndex] = new MassiveLinkedList();
            values[hashIndex] = new MyVector3LinkedList();

            keys[hashIndex].addFirst(key);
            values[hashIndex].addFirst(value);
            return null;
        }
        //Case 2: is not null
        else{
            //if key already exists, value is overwritten
            for(int i = 0; i < keys[hashIndex].size(); i++){
                if(keys[hashIndex].get(i).equals(key)){
                    Vector3 oldVal = values[hashIndex].get(i);
                    values[hashIndex].add(i, value);
                    return oldVal;
                }
            }
            //key does not exist
            keys[hashIndex].addFirst(key);
            values[hashIndex].addFirst(value);
            return null;
        }
    }

    // Returns the value associated with the specified key, i.e. the method returns the force vector
    // associated with the specified key. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Massive key) {
        int hashIndex = Math.abs(key.hashCode() % INIT_CAPACITY);
        if(keys[hashIndex] != null){
            for(int i = 0; i < keys[hashIndex].size(); i++){
                if(keys[hashIndex].get(i).equals(key)){
                    return values[hashIndex].get(i);
                }
            }
        }
        return null;
    }

    // Returns 'true' if this map contains a mapping for the specified key.
    public boolean containsKey(Massive key) {
        int hashIndex = Math.abs(key.hashCode() % INIT_CAPACITY);
        if(keys[hashIndex] != null){
            for(int i = 0; i < keys[hashIndex].size(); i++){
                if(keys[hashIndex].get(i).equals(key)){
                    return true;
                }
            }
        }
        return false;
    }

    // Returns a readable representation of this map, with all key-value pairs. Their order is not
    // defined.
    public String toString() {
        String erg = "";
        for(int i = 0; i < keys.length; i++){
           if(keys[i] != null){
               for(int j = 0; j < keys[i].size(); j++){
                   erg += "(key:"+keys[i].get(j)+",value:"+values[i].get(j)+")";
               }
           }
        }
        return erg;
    }

    // Compares `this` with the specified object for equality. Returns `true` if the specified
    // `o` is not `null` and is of type `MassiveForceHashMap` and both `this` and `o` have equal
    // key-value pairs, i.e. the number of key-value pairs is the same in both maps and every
    // key-value pair in `this` equals one key-value pair in `o`. Two key-value pairs are
    // equal if the two keys are equal and the two values are equal. Otherwise `false` is returned.
    public boolean equals(Object o) {
        if(o instanceof MassiveForceHashMap){
            for(int i = 0; i < keys.length; i++){
                if(keys[i] != null){
                    for(int j = 0; j < keys[i].size(); j++){
                        //check wether keys are equal
                        if(! ((MassiveForceHashMap)o).containsKey(this.keys[i].get(j)) && //keys must be equal...
                                ((MassiveForceHashMap)o).get(this.keys[i].get(j)) == this.values[i].get(j) //... and values must be equal
                        ){
                            //if keys or values are different, they are not equal
                            return false;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }

    // Returns the hashCode of `this`.
    public int hashCode() {
        return this.hashCode();
    }

    // Returns a list of all the keys in no specified order.
    public MassiveLinkedList keyList() {
        MassiveLinkedList keyList = new MassiveLinkedList();
        for(int i = 0; i < keys.length; i++){
            if(keys[i] != null){
                for(int j = 0; j < keys[i].size(); j++){
                    keyList.addFirst(keys[i].get(j));
                }
            }
        }
        return keyList;
    }

}