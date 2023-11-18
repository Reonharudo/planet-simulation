import java.util.Queue;

// A queue of bodies. A collection designed for holding bodies prior to processing.
// The bodies of the queue can be accessed in a FIFO (first-in-first-out) manner,
// i.e., the body that was first inserted by 'add' is retrieved first by 'poll'.
// The number of elements of the queue is not limited.
//
public class BodyQueue {

    //TODO: declare variables.
    private Body[] queue;
    private int childQuantity;
    private int initialCapacity;

    // Initializes this queue with an initial capacity.
    // Precondition: initialCapacity > 0.
    public BodyQueue(int initialCapacity) {
        this.queue = new Body[initialCapacity];
        this.childQuantity = 0;
        this.initialCapacity = initialCapacity;
    }

    // Initializes this queue as an independent copy of the specified queue.
    // Calling methods of this queue will not affect the specified queue
    // and vice versa.
    // Precondition: q != null.
    public BodyQueue(BodyQueue q) {
        initialCapacity = q.size();
        queue = new Body[initialCapacity];

        Body[] tempQ = new Body[q.size()];
        int tempQIndex = q.size() - 1;

        while(q.size() > 0){
            Body b = q.poll();

            tempQ[tempQIndex] = b;
            tempQIndex--;

            this.add(b);
        }

        for(int i = tempQ.length-1; i >= 0; i--){
            q.add(tempQ[i]);
        }
    }

    // Adds the specified body 'b' to this queue.
    public void add(Body b) {
        //If queue is full, a new queue doubles the previous length is created
        if(queue.length == childQuantity){
            Body[] restoreBodyArr = queue;
            queue = new Body[2 * initialCapacity];
            for(int i = 0; i < restoreBodyArr.length; i++){
                    queue[i] = restoreBodyArr[i];
            }
        }

        queue[childQuantity] = b;
        childQuantity++;
    }

    // Retrieves and removes the head of this queue, or returns 'null'
    // if this queue is empty.
    public Body poll() {
        if(childQuantity > 0){
            Body removedBody = queue[0];
            queue[0] = null;
            childQuantity--;

            for(int i = 0; i < queue.length - 1; i++){
                queue[i] = queue[i + 1];
            }
            return removedBody;
        }
        return null;
    }

    // Returns the number of bodies in this queue.
    public int size() {
        return childQuantity;
    }

    @Override
    public String toString(){
        String erg = "";
        for(Body b : queue){
            erg += b != null ? "   "+ b.mass() : "";
        }
        return erg;
    }
}
