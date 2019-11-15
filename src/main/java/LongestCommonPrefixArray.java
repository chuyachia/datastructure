import java.util.Iterator;

public class LongestCommonPrefixArray implements Iterable<Integer> {

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
            if (i == length - 1) {
                lcp[i] = 0;
                continue;
            }
            int currentSuffix = sa.get(i);
            int nextSuffix = sa.get(i + 1);
            while (currentSuffix + accumulatedCP < length && nextSuffix + accumulatedCP < length
                    && str.charAt(currentSuffix + accumulatedCP) == str.charAt(nextSuffix + accumulatedCP))
                accumulatedCP++;

            lcp[i] = accumulatedCP;

            if (accumulatedCP > 0)
                accumulatedCP--;
        }
    }

    public int[] getArray() {
        return lcp;
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
        // Longest repeated substrings
        String str1 = "abracadabra";
        SuffixArray sa1 = new SuffixArray(str1);
        LongestCommonPrefixArray lcp1 = new LongestCommonPrefixArray(sa1);
        int[] lcpArray1 = lcp1.getArray();
        String lrs = "";
        for (int i = 0; i < lcpArray1.length; i++) {
            int cp = lcpArray1[i];
            if (cp > lrs.length()) {
                lrs = str1.substring(sa1.get(i), sa1.get(i) + cp);
            }
        }

        System.out.println(lrs); // abra

        // TODO Longest common substring between at least n strings in m strings
        // Suffix array need to support special character to separate strings
    }
}
