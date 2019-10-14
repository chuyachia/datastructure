public class StaticArrayQueue<T> {

    private T[] array;
    private int size;
    private int head;
    private int tail;

    StaticArrayQueue(int maxSize) {
        array = (T[]) new Object[maxSize];
        head = tail = 0;
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        return array[head];
    }

    public void enqueue(T element) {
        if (size > 0 && tail == head) {
            throw new RuntimeException("Queue maximum size reached");
        }
        array[tail] = element;
        if (++tail == array.length) tail = 0;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue Empty");
        T element = array[head];
        if (++head == array.length) head = 0;
        size--;
        return element;
    }

    public static void main(String[] args) {
        StaticArrayQueue<Integer> q = new StaticArrayQueue(5);

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.println(q.size()); // 3
        q.enqueue(4);
        q.enqueue(5);

        System.out.println(q.dequeue()); // 1
        System.out.println(q.peek()); // 2
        System.out.println(q.dequeue()); // 2
        System.out.println(q.dequeue()); // 3
        System.out.println(q.size()); // 2
        System.out.println(q.dequeue()); // 4

        System.out.println(q.isEmpty()); // false

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        System.out.println(q.dequeue()); // 5
        System.out.println(q.dequeue()); // 1
        System.out.println(q.dequeue()); // 2
        System.out.println(q.dequeue()); // 3

        System.out.println(q.isEmpty()); // true
    }
}
