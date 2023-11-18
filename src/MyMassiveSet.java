import codedraw.CodeDraw;

public class MyMassiveSet implements MassiveSet{
    private MassiveForceTreeMap map;

    public MyMassiveSet(){}

    public MyMassiveSet(MassiveForceTreeMap map){
        if(map != null){
            this.map = map;
        }else{
            throw new RuntimeException("Map may not be null");
        }
    }

    @Override
    public void draw(CodeDraw cd) {
        MassiveLinkedList list = toList();
        while(list.getLast() != null) {
            list.pollFirst().draw(cd);
        }
    }

    @Override
    public MassiveIterator iterator() {
        return map.iterator();
    }

    @Override
    public boolean contains(Massive element) {
        return map.containsKey(element);
    }

    @Override
    public void remove(Massive element) {
        map.remove(element);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public MassiveLinkedList toList() {
        return map.toList();
    }
}

class MyMassiveSetIterator implements MassiveIterator{
    private MyMassiveSetIterator parent;
    private MyTreeMapNode next;

    public MyMassiveSetIterator(){}

    public MyMassiveSetIterator(MyTreeMapNode treeNode, MyMassiveSetIterator iterator) {
        this.next = treeNode;
        iterator.next = treeNode;

        this.parent = iterator.parent;
        iterator.parent = this;
    }

    @Override
    public Massive next() {
        if( next == null) {
            return null;
        }
        MyTreeMapNode treeNode = next;
        next = parent.next;
        parent = parent.parent;
        return treeNode.iterator(this,true);
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }
}
