// A list of bodies implemented as a linked list.
// The number of elements of the list is not limited.
public class BodyLinkedList {
    private MyNode head;
    private int size;

    // Initializes 'this' as an empty list.
    public BodyLinkedList() {
        size = 0;
    }

    // Initializes 'this' as an independent copy of the specified list 'list'.
    // Calling methods of this list will not affect the specified list 'list'
    // and vice versa.
    // Precondition: list != null.
    public BodyLinkedList(BodyLinkedList list) {
        Body iteratorBody ;
        Body[] restoreArr = new Body[list.size()];

        for(int i = 0; i < restoreArr.length; i++){
            if( (iteratorBody = list.pollFirst()) != null)
            {
                this.addFirst(iteratorBody);
                restoreArr[i] = iteratorBody;
            }
        }

        //restore parameter list
        for(Body b : restoreArr){
            list.addFirst(b);
        }
    }

    // Inserts the specified element 'body' at the beginning of this list.
    public void addFirst(Body body) {
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
    public void addLast(Body body) {
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
    public Body getLast() {
        return head.value; //head always points to last node
    }

    // Returns the first element in this list.
    // Returns 'null' if the list is empty.
    public Body getFirst() {
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
    public Body pollFirst() {
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
                Body removedBody = head.value;
                head = null;
                size--;
                return removedBody;
            }
        }
        return null;
    }

    // Retrieves and removes the last element in this list.
    // Returns 'null' if the list is empty.
    public Body pollLast() {
        if(head != null){
            if(head.prev != null){
                Body removedBody = head.value;
                head = head.prev;
                head.next = null;
                size--;
                return removedBody;
            }else{
                Body removedBody = head.value;
                this.head = null;
                size--;
                return removedBody;
            }
        }
        return null;
    }

    // Inserts the specified element 'body' at the specified position in this list.
    // Precondition: i >= 0 && i <= size().
    public void add(int i, Body body) {
        MyNode nodeAtIndex = getNode(i);
        if(i == 0){ addFirst(body); return; }

        if(nodeAtIndex != null){ //node already exists, need to shift
            MyNode toAdd = new MyNode(body);
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
            addLast(body);
        }
    }

    // Returns the element at the specified position in this list.
    // Precondition: i >= 0 && i < size().
    public Body get(int i) {
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
    public int indexOf(Body body) {
        MyNode currentHead = head;
        int count = size-2;

        if(head.value == body) { return size-1; }
        while(head.prev != null){
            head = head.prev;
            if(head.value == body){
                //restore pointer to current head
                head = currentHead;
                return count;
            }
            count--;
        }
        return -1;
    }

    // Removes all bodies of this list, which are colliding with the specified
    // body. Returns a list with all the removed bodies.
    public BodyLinkedList removeCollidingWith(Body body) {
        BodyLinkedList removedBodies = new BodyLinkedList();
        MyNode currentHead = head;
        while(head.prev != null){
            head = head.prev;
            if(head.value.distanceTo(body) == 0d){
                removedBodies.addFirst(head.value);

                //re-reference adjacent nodes e.g removes references from the to be deleted node and rewire the
                //references to each other N1<->N2<->N3 to N1<->N3 (N... Node)
                if(head.next != null && head.prev != null){
                    MyNode left = head.prev;
                    MyNode right = head.next;
                    left.next = right;
                    right.prev = left;
                }else if(head.next != null){ //remove reference if only next [var=prev] is present
                    MyNode right = head.next;
                    right.prev = null;
                }else if(head.prev != null){ //remove reference if only previous [var=next] is present
                    MyNode left = head.prev;
                    left.next = null;
                }
                size--;
            }
        }
        //restore pointer to current head
        head = currentHead;
        return removedBodies;
    }

    // Returns the number of bodies in this list.
    public int size() {
        return size;
    }
}

class MyNode {
    protected Body value;
    protected MyNode prev;
    protected MyNode next;

    protected MyNode(Body value){
        this.value = value;
    }
}
