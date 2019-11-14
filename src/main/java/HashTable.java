import java.util.Iterator;

public class HashTable<K, V> implements Iterable<K> {

    private static final int DEFAULT_CAPACITY = 7;
    private static final double DEFAULT_LOAD_FACTOR = 0.45;
    private static final int LINEAR_CONSTANT = 3;

    private final K TOMBSTONE = (K) new Object();

    // useBlocks count includes TOMBSTONE while usedIndices count does not
    private int capacity, threshold, usedBlocks, usedIndices;
    private double loadFactor;
    private K[] keys;
    private V[] values;

    HashTable(int capacity, double loadFactor) {
        this.usedBlocks = 0;
        this.usedIndices = 0;
        this.loadFactor = loadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.threshold = (int) Math.round(this.capacity * loadFactor);
        this.keys = (K[]) new Object[this.capacity];
        this.values = (V[]) new Object[this.capacity];
    }

    HashTable(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    HashTable(double loadFactor) {
        this(DEFAULT_CAPACITY, loadFactor);
    }

    HashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public void put(K key, V value) {
        if (usedBlocks >= threshold) resize();

        int hash = normalizedHash(key.hashCode());
        int probeOffset = 0;
        int index = probeNext(hash, probeOffset);
        boolean keyAlreadyExists = key.equals(keys[index]);
        if (!keyAlreadyExists) {
            while (keys[index] != null && keys[index] != TOMBSTONE) {
                index = probeNext(hash, ++probeOffset);
            }
            if (keys[index] != TOMBSTONE) {
                usedBlocks++;
            }
            usedIndices++;
        }
        keys[index] = key;
        values[index] = value;
    }

    public boolean remove(K key) {
        int hash = normalizedHash(key.hashCode());
        int probeOffset = 0;
        int index = probeNext(hash, probeOffset);
        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                keys[index] = TOMBSTONE;
                usedIndices--;
                return true;
            }
            index = probeNext(hash, ++probeOffset);
        }
        return false;
    }

    public V get(K key) {
        int hash = normalizedHash(key.hashCode());
        int probeOffset = 0;
        int index = probeNext(hash, probeOffset);
        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return values[index];
            }
            index = probeNext(hash, ++probeOffset);
        }
        return null;
    }

    private int probeNext(int hash, int offSet) {
        return (hash + offSet * LINEAR_CONSTANT) % capacity;
    }

    private void resize() {
        capacity = capacity * 2;
        // Adjust capacity so that it's relative prime to linear constant to avoid infinite loop in probing
        while (greatestCommonDelimiter(capacity, LINEAR_CONSTANT) > 1) {
            capacity++;
        }
        threshold = (int) Math.round(capacity * loadFactor);
        usedBlocks = usedIndices = 0;
        K[] oldKeys = keys;
        V[] oldValues = values;

        keys = (K[]) new Object[capacity];
        values = (V[]) new Object[capacity];
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != TOMBSTONE) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }

    private int normalizedHash(int hash) {
        return (hash & 0x7FFFFFFF) % capacity;
    }

    private int greatestCommonDelimiter(int a, int b) {
        if (b == 0) return a;
        return greatestCommonDelimiter(b, a % b);
    }


    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int count = 0;
            int index = 0;

            @Override
            public boolean hasNext() {
                return count < usedIndices;
            }

            @Override
            public K next() {
                if (index >= capacity) return null;
                while (keys[index] == null || keys[index] == TOMBSTONE) {
                    index++;
                }
                count++;
                return keys[index++];
            }
        };
    }

    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);
        table.put("a", 2);
        table.put("b", 1);
        table.put("c", 4);
        table.put("d", 23);
        table.put("e", 76);
        table.put("f", 9);
        table.put("gggg", 2222);
        System.out.println(table.get("a"));
        System.out.println(table.get("gggg"));
        System.out.println(table.get("ggg"));
        System.out.println(table.get("e"));
        table.remove("gggg");
        System.out.println(table.get("gggg"));
        table.put("g", 234);
        Iterator<String> it = table.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
