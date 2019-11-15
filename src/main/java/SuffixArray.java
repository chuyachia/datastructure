import java.util.Iterator;

public class SuffixArray implements Iterable<Integer> {

    private String originalStr;
    private int[][] pairs;
    private int[] sortedIndices;
    private int[] ranks;
    private int[] sa;
    private boolean sorted;
    private int space;
    private int len;

    public SuffixArray(String str) {
        originalStr = str;
        sorted = false;
        space = 0;
        len = str.length();
        pairs = new int[len][2];
        sortedIndices = new int[len];
        ranks = new int[len];
        sa = new int[len];
        int[] strValues = new int[len];
        for (int i = 0; i < len; i++) {
            strValues[i] = str.charAt(i) - 'a' + 1;
        }
        constructPairs(strValues, (int) Math.pow(2, space));
        radixSort();
        constructRanks();
        while (!sorted) {
            constructPairs(ranks, (int) Math.pow(2, ++space));
            radixSort();
            constructRanks();
        }
        for (int i = 0; i < ranks.length; i++) {
            sa[ranks[i]] = i;
        }

    }

    public int size() {
        return len;
    }

    public int get(int index) {
        return sa[index];
    }

    public int[] getArray() {
        return sa;
    }

    public int[] getInversed() {
        return ranks;
    }

    public String getOriginalString() {
        return originalStr;
    }

    private void constructPairs(int[] arr, int space) {
        for (int i = 0; i < arr.length; i++) {
            pairs[i] = new int[]{arr[i], i + space < arr.length ? arr[i + space] : 0};
            sortedIndices[i] = i;
        }
    }

    private void radixSort() {
        for (int i = 1; i >= 0; i--) {
            countSort(i);
        }
    }

    private void countSort(int digit) {
        int[][] sortedPairs = new int[sortedIndices.length][2];
        int[] indices = new int[sortedIndices.length];
        int[] count = new int['z' - 'a' + 2];
        for (int i : sortedIndices) {
            int n = pairs[i][digit];
            ++count[n];
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        for (int i = sortedIndices.length - 1; i >= 0; i--) {
            int countIndex = count[pairs[i][digit]];
            sortedPairs[countIndex - 1] = pairs[i];
            indices[countIndex - 1] = sortedIndices[i];
            count[pairs[i][digit]]--;
        }


        sortedIndices = indices;
        pairs = sortedPairs;
    }

    private boolean pairEqual(int[] p1, int[] p2) {
        int i = 0;
        while (i < 2) {
            if (p1[i] != p2[i]) return false;
            i++;
        }
        return true;
    }

    private void constructRanks() {
        int rank = 0;
        for (int i = 0; i < pairs.length - 1; i++) {
            ranks[sortedIndices[i]] = rank;
            if (!pairEqual(pairs[i], pairs[i + 1])) {
                rank++;
            }
        }
        ranks[sortedIndices[pairs.length - 1]] = rank;
        if (rank == pairs.length - 1) sorted = true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public Integer next() {
                if (index >= len) return null;
                return sa[index++];
            }
        };
    }

    public static void main(String[] args) {
        SuffixArray sa = new SuffixArray("trollolol");
        for (int i : sa) {
            System.out.println(i);
        }
        System.out.println("Inversed SA");
        int[] inversedSA = sa.getInversed();
        for (int i : inversedSA) {
            System.out.println(i);
        }
    }
}
