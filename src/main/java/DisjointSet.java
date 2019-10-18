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
        // Kruskal's minimum spanning tree
        int[][] weightSortedEdges = {{7, 6}, {8, 2}, {6, 5}, {0, 1}, {2, 5}, {8, 6}, {2, 3}, {7, 8}, {0, 7}, {1, 2}, {3, 4}, {5, 4}, {1, 7}, {3, 5}};
        int[][] selectedEdges = new int[8][2];
        DisjointSet ds = new DisjointSet(9);
        int i = 0;
        int connectedVertices = 0;
        while (i < weightSortedEdges.length && connectedVertices < 9) {
            int[] pair = weightSortedEdges[i];
            if (!ds.connected(pair[0], pair[1])) {
                selectedEdges[connectedVertices] = pair;
                ds.union(pair[0], pair[1]);
                connectedVertices++;
            }
            i++;
        }
        for (int[] pair : selectedEdges) {
            System.out.println(String.format("%s --- %s", pair[0], pair[1])); // MST
        }
    }

}
