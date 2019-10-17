public class DisjointSet {
    private int[] sets;
    private int[] setsSize;
    private int setsCount;

    DisjointSet(int size) {
        sets = new int[size];
        setsSize = new int[size];
        for (int i = 0; i < size; i++) {
            sets[i] = i;
            setsSize[i] = 1;
        }
        setsCount = size;
    }

    public int find(int i) {
        int root = i;
        while (sets[root] != root) {
            root = sets[root];
        }
        // path compression
        while (i != root) {
            int next = sets[i];
            sets[i] = root;
            i = next;
        }

        return root;
    }

    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

    public int size(int i) {
        return setsSize[i];
    }

    public int count() {
        return setsCount;
    }

    public void union(int i, int j) {
        int iRoot = find(i);
        int jRoot = find(j);
        if (iRoot == jRoot) return;

        if (size(i) < size(j)) {
            sets[iRoot] = jRoot;
            setsSize[jRoot] += setsSize[iRoot];
        } else {
            sets[jRoot] = iRoot;
            setsSize[iRoot] += setsSize[jRoot];
        }
        setsCount--;
    }

    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(5);
        ds.union(0, 2);
        ds.union(4, 2);
        ds.union(3, 1);
        System.out.println(ds.connected(4,0)); // true
        System.out.println(ds.connected(1,0)); // false
        System.out.println(ds.count()); // 2
        System.out.println(ds.size(ds.find(4))); // 3
        System.out.println(ds.size(ds.find(1))); // 2
    }

}
