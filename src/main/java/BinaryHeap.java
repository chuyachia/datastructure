// TODO use hash table to track element indices for faster remove
public class BinaryHeap<T extends Comparable<T>> {
    private int last;
    private T[] array;

    BinaryHeap(int size) {
        array = (T[]) new Comparable[size];
        last = -1;
    }

    public T poll() {
        if (last == -1) throw new RuntimeException("Heap is empty");
        T element = array[0];
        array[0] = array[last--];
        shiftDown(0);
        return element;
    }

    public boolean remove(T element) {
        int current = 0;
        boolean found = false;
        while (current <= last) {
            if (array[current].equals(element)) {
                found = true;
                break;
            }
            current++;
        }
        if (found) {
            array[current] = array[last];
            last--;
            int parent = findParent(current);
            int leftChild = findLeftChild(current);
            int rightChild = findRightChild(current);
            if (array[current].compareTo(array[parent]) > 0) {
                shiftUp(current);
            } else if (
                    (leftChild <= last && array[current].compareTo(array[leftChild]) < 0) ||
                            (rightChild <=last && array[current].compareTo(array[rightChild]) < 0)
            ) {
                shiftDown(current);
            }
        }
        return found;
    }

    public void insert(T element) {
        if (++last >= array.length) throw new RuntimeException("Heap max size reached");
        array[last] = element;
        shiftUp(last);
    }

    private void shiftUp(int index) {
        int current = index;
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

    public void print() {
        int current = 0;
        while (current <= last) {
            System.out.println(array[current]);
            current++;
        }
    }

    private void shiftDown(int index) {
        int current = index;
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
        int rightChild = findRightChild(index);
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
        System.out.println(bh.remove(new Integer(3)));
        bh.insert(new Integer(10));
        bh.insert(new Integer(84));
        bh.insert(new Integer(19));
        bh.insert(new Integer(6));
        bh.insert(new Integer(22));
        bh.insert(new Integer(9));
        bh.print();
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
        System.out.println(bh.poll());
    }
}
