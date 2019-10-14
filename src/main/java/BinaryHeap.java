public class BinaryHeap<T extends Comparable> {
    private int last;
    private T[] array;

    BinaryHeap(int size) {
        array = (T[]) new Comparable[size];
        last = -1;
    }

    public void insert(T element) {
        if (++last >= array.length) throw new RuntimeException("Heap max size reached");
        array[last] = element;
        shiftUp();
    }

    public T delete() {
        if (last == -1) throw new RuntimeException("Heap is empty");
        T element = array[0];
        array[0] = array[last--];
        shiftDown();
        return element;
    }

    public void print() {
        int current = 0;
        while (current <= last) {
            System.out.println(array[current]);
            current++;
        }
    }

    private void shiftUp() {
        int current = last;
        int parent = findParent(current);
        while (parent >= 0) {
            if (array[current].compareTo(array[parent]) <= 0) {
                break;
            }
            swap(current, parent);
            current = parent;
            parent = findParent(parent);
        }
    }

    private void shiftDown() {
        int current = 0;
        int maxChild = findMaxChild(current);
        while (maxChild != -1) {
            if (array[current].compareTo(array[maxChild]) >= 0) {
                break;
            }
            swap(current, maxChild);
            current = maxChild;
            maxChild = findMaxChild(current);
        }
    }

    public void swap(int indexA, int indexB) {
        T tmp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = tmp;
    }

    private int findParent(int index) {
        return (index - 1) / 2;
    }

    private int findLeftChild(int index) {
        return (2 * index) + 1;
    }

    private int findRightChild(int index) {
        return (2 * index) + 2;
    }

    private int findMaxChild(int index) {
        int leftChild = findLeftChild(index);
        int rightChild= findRightChild(index);
        if (rightChild <= last) {
            return array[leftChild].compareTo(array[rightChild]) < 0 ? rightChild : leftChild;
        } else if (leftChild <= last) {
            return leftChild;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        BinaryHeap bh = new BinaryHeap(15);
        bh.insert(new Integer(5));
        bh.insert(new Integer(3));
        bh.insert(new Integer(17));
        bh.insert(new Integer(10));
        bh.insert(new Integer(84));
        bh.insert(new Integer(19));
        bh.insert(new Integer(6));
        bh.insert(new Integer(22));
        bh.insert(new Integer(9));
        bh.print();
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
        System.out.println(bh.delete());
    }
}
