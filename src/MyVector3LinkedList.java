// A list of massive objects implemented as a linked list.
// The number of elements of the list is not limited.
public class MyVector3LinkedList {

    private MyNode head;
    private int size;

    // Initializes 'this' as an empty list.
    public MyVector3LinkedList() {
        this.size = 0;
    }

    // Inserts the specified element 'body' at the beginning of this list.
    public void addFirst(Vector3 body) {
        MyNode node = new MyNode(body);
        if(head == null){
            head = node;
        }else{
            MyNode currentHead = head;
            MyNode lastFoundNode = head;
            //set head to first node
            while(head.prev != null){
                head = head.prev;
                lastFoundNode = head;
            }
            //add node before found first Node
            head.prev = node;

            //add node
            head = node;
            head.next = lastFoundNode;

            //restore pointer to current head
            head = currentHead;
        }
        size++;
    }

    // Appends the specified element 'body' to the end of this list.
    public void addLast(Vector3 body) {
        MyNode node = new MyNode(body);
        if(head == null){
            head = node;
        }else{
            MyNode oldHead = head;
            head.next = node; //Old node needs to reference new node

            head = node;
            head.prev = oldHead;
            head.next = null;
        }
        size++;
    }

    // Returns the last element in this list.
    // Returns 'null' if the list is empty.
    public Vector3 getLast() {
        return head.value; //head always points to last node
    }

    // Returns the first element in this list.
    // Returns 'null' if the list is empty.
    public Vector3 getFirst() {
        if(head != null){
            MyNode currentHead = head;
            MyNode result = head;
            //set head to first node
            while(head.prev != null){
                head = head.prev;
                result = head; //save first node
            }
            head = currentHead; //restore pointer to current head
            return result.value; //return first node
        }
        return null;
    }

    // Retrieves and removes the first element in this list.
    // Returns 'null' if the list is empty.
    public Vector3 pollFirst() {
        if(head != null){
            MyNode currentHead = head;
            MyNode firstElement = head;
            if(head.prev != null){ //if previous nodes exist first node is set
                //set head to first node
                while(head.prev != null){
                    head = head.prev;
                    firstElement = head;
                }
            }
            if(head.next != null){
                //remove reference to removed node from next node
                head = head.next;
                head.prev = null;
                size--;
                //restore pointer to current head
                head = currentHead;
                return firstElement.value;
            }else{
                Vector3 removedBody = head.value;
                head = null;
                size--;
                return removedBody;
            }
        }
        return null;
    }

    // Retrieves and removes the last element in this list.
    // Returns 'null' if the list is empty.
    public Vector3 pollLast() {
        if(head != null){
            if(head.prev != null){
                Vector3 removedBody = head.value;
                head = head.prev;
                head.next = null;
                size--;
                return removedBody;
            }else{
                Vector3 removedBody = head.value;
                this.head = null;
                size--;
                return removedBody;
            }
        }
        return null;
    }

    // Inserts the specified element at the specified position in this list.
    // Precondition: i >= 0 && i <= size().
    public void add(int i, Vector3 m) {
        MyNode nodeAtIndex = getNode(i);
        if(i == 0){ addFirst(m); return; }

        if(nodeAtIndex != null){ //node already exists, need to shift
            MyNode toAdd = new MyNode(m);
            //re-reference adjacent node of left if present
            if(nodeAtIndex.prev != null){
                MyNode leftNode = nodeAtIndex.prev;
                leftNode.next = toAdd;
                toAdd.prev = leftNode;
            }
            toAdd.next = nodeAtIndex;
            nodeAtIndex.prev = toAdd;
            size++;
        }else{
            addLast(m);
        }
    }

    // Returns the element at the specified position in this list.
    // Precondition: i >= 0 && i < size().
    public Vector3 get(int i) {
        MyNode nodeAtIndex = getNode(i);
        if(nodeAtIndex != null) { return nodeAtIndex.value; };
        return null;
    }

    /**
     * returns the node at index i
     * @param i index
     * @return null if list is empty or node found at index i
     */
    private MyNode getNode(int i){
        MyNode currentHead = head;
        if(i == size-1) { return head; }
        for(int z = size - 2; z >= i; z--){
            head = head.prev;
            if(z == i){
                //temporary save found node
                MyNode found = head;
                //restore pointer to current head
                head = currentHead;

                return found;
            }
        }
        return null;
    }

    // Returns the index of the first occurrence of the specified element in this list, or -1 if
    // this list does not contain the element.
    public int indexOf(Massive m) {
        MyNode currentHead = head;
        int count = size-2;

        if(head.value == m) { return size-1; }
        while(head.prev != null){
            head = head.prev;
            if(head.value == m){
                //restore pointer to current head
                head = currentHead;
                return count;
            }
            count--;
        }
        return -1;
    }

    // Returns the number of elements in this list.
    public int size() {
        return size;
    }

    private class MyNode {
        private Vector3 value;
        private MyNode prev;
        private MyNode next;

        private MyNode(Vector3 value){
            this.value = value;
        }
    }

}