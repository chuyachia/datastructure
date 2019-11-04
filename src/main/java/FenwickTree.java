public class FenwickTree {

    private int[] tree;

    public FenwickTree(int[] arr) {
        // arr needs to be 1 indexed
        tree = arr.clone();
        for (int i = 1; i < arr.length; i++) {
            int parent = i + leastSignificantBit(i);
            if (parent < tree.length) tree[parent] += tree[i];
        }
    }

    private int leastSignificantBit(int n) {
        return n & -n;
    }

    private int getPrefixSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += tree[n];
            n -= leastSignificantBit(n);
        }
        return sum;
    }

    public void set(int index, int value) {
        int oldValue = tree[index];
        while (index < tree.length) {
            tree[index] -= oldValue;
            tree[index] += value;
            index += leastSignificantBit(index);
        }
    }

    public int sum(int i, int j) {
        return getPrefixSum(j) - getPrefixSum(i - 1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{0, 3, 4, -2, 7, 3, 11, 5, -8, -9, 2, 4, -8};
        FenwickTree ft = new FenwickTree(arr);
        System.out.println(ft.sum(2,4)); //9
        System.out.println(ft.sum(5,10)); //4
        ft.set(3,5);
        System.out.println(ft.sum(2,4)); //16
    }
}
