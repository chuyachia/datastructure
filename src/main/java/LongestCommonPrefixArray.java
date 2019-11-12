import java.util.Iterator;

public class LongestCommonPrefixArray implements Iterable<Integer>{

    private int length;
    private int[] inversedSA;
    private int[] lcp;

    public LongestCommonPrefixArray(SuffixArray sa) {
        length = sa.size();
        String str = sa.getOriginalString();
        lcp = new int[length];
        inversedSA = sa.getInversed();
        int accumulatedCP = 0;
        for (int i : inversedSA) {
            if (i == length-1) {
                lcp[i] = 0;
                continue;
            }
            int currentSuffix =sa.get(i);
            int nextSuffix = sa.get(i + 1);
            while (currentSuffix + accumulatedCP < length && nextSuffix + accumulatedCP < length
                    && str.charAt(currentSuffix+accumulatedCP) == str.charAt(nextSuffix+accumulatedCP))
                accumulatedCP++;

            lcp[i] = accumulatedCP;

            if (accumulatedCP > 0)
                accumulatedCP--;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public Integer next() {
                if (index >= length) return null;
                return lcp[index++];
            }
        };
    }


    public static void main(String[] args) {
        SuffixArray sa = new SuffixArray("banana");
        LongestCommonPrefixArray lcp = new LongestCommonPrefixArray(sa);
        for (int i : lcp) {
            System.out.println(i);
        }
    }
}
