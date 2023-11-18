// A map that associates a body with a force exerted on it. The number of
// key-value pairs is not limited.
//
public class BodyForceMap {

    //TODO: declare variables.
    private int initialCapacity;
    private Body[] keys;
    private Vector3[] values;

    private int currentElemCount;

    // Initializes this map with an initial capacity.
    // Precondition: initialCapacity > 0.
    public BodyForceMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.currentElemCount = 0;
        this.keys = new Body[initialCapacity];
        this.values = new Vector3[initialCapacity];
    }

    // Adds a new key-value association to this map. If the key already exists in this map,
    // the value is replaced and the old value is returned. Otherwise 'null' is returned.
    // Precondition: key != null.
    public Vector3 put(Body key, Vector3 force) {
        int foundIndex = binarySearchFindKey(key);
        //Does the key already exist?
        if(foundIndex == -1){
            for(int i = 0; i < keys.length; i++){
                if(keys[i] != null){
                    if(keys[i].mass() < key.mass()){
                        //We need to right shift
                        if(keys.length == currentElemCount){enlarge(keys,1); enlarge(values, 1);} //Array is enlarged by 1 to make place
                        rightRotatebyOne(keys); //make place for new key
                        keys[i] = key; //key is inserted into the new freed index by rotatingRight
                        rightRotatebyOne(values);
                        values[i] = force;
                        currentElemCount++;
                        return null;
                    }
                }
                else{
                    keys[i] = key;
                    values[i] = force;
                    currentElemCount++;
                    return null;
                }
            }
        }else{ //if key does exist
            Vector3 oldVal = values[foundIndex];
            values[foundIndex] = force; //replace associated new value
            return oldVal;
        }
        return null;
    }

    /**
     * Modifies and returns the same array which was increased in size
     * by enlargeBy
     * @param toEnlarge array to be enlarged
     * @param enlargeBy size to be enlarged
     * @return the specified array which was given as a parameter
     */
    private Object[] enlarge(Object[] toEnlarge, int enlargeBy){
        Object[] result = new Object[toEnlarge.length + enlargeBy];
        for(int i = 0; i < toEnlarge.length; i++){
            result[i] = toEnlarge[i];
        }
        return result;
    }

    private void rightRotatebyOne(Object toRotateByOne[])
    {
        Object tempLast = toRotateByOne[toRotateByOne.length-1];

        for(int i = toRotateByOne.length - 2; i >= 0; i--){
            toRotateByOne[i+1] = toRotateByOne[i];
        }
        toRotateByOne[0] = tempLast;
    }

    // Returns the value associated with the specified key, i.e. the returns the force vector
    // associated with the specified body. Returns 'null' if the key is not contained in this map.
    // Precondition: key != null.
    public Vector3 get(Body key) {
        int foundIndex = binarySearchFindKey(key);
        if(foundIndex != -1){
            return values[foundIndex];
        }
        return null;
    }

    private int binarySearchFindKey(Body search) {
        int left = 0;
        int right = keys.length - 1;

        while (left <= right) {
            int middle = left + ((right - left) / 2);
            if(keys[middle] == search) {
                return middle;
            }else if(keys[middle] == null){
                right--;
            }else if (keys[middle].mass() < search.mass()) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }
}
