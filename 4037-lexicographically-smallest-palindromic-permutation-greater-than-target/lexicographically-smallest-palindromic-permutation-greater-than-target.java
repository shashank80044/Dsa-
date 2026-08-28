class Solution {
    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                mid = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;

        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        /*
         * We build the first half.
         *
         * We want:
         *
         * palindrome > target
         *
         * Since the palindrome is determined by its first half
         * (and middle character for odd length), we can compare
         * while constructing it.
         */

        StringBuilder half = new StringBuilder();

        String result = dfs(
            0,
            halfLen,
            half,
            halfFreq,
            target,
            mid,
            n,
            false
        );

        return result == null ? "" : result;
    }

    private String dfs(
        int pos,
        int halfLen,
        StringBuilder half,
        int[] freq,
        String target,
        char mid,
        int n,
        boolean alreadyGreater
    ) {

        // First half completely constructed
        if (pos == halfLen) {

            // Build complete palindrome
            StringBuilder palindrome = new StringBuilder();

            palindrome.append(half);

            if (n % 2 == 1) {
                palindrome.append(mid);
            }

            palindrome.append(
                new StringBuilder(half).reverse()
            );

            String ans = palindrome.toString();

            if (ans.compareTo(target) > 0) {
                return ans;
            }

            return null;
        }

        /*
         * If we haven't become greater yet, we have to respect
         * the target character at this position.
         *
         * If alreadyGreater == true, choose the smallest available
         * character.
         */
        int start = 0;

        if (!alreadyGreater) {
            start = target.charAt(pos) - 'a';
        }

        for (int c = start; c < 26; c++) {

            if (freq[c] == 0) {
                continue;
            }

            boolean greater = alreadyGreater;

            if (!alreadyGreater) {

                if (c > target.charAt(pos) - 'a') {
                    greater = true;
                }
            }

            freq[c]--;
            half.append((char) ('a' + c));

            String result = dfs(
                pos + 1,
                halfLen,
                half,
                freq,
                target,
                mid,
                n,
                greater
            );

            if (result != null) {
                return result;
            }

            half.deleteCharAt(half.length() - 1);
            freq[c]++;
        }

        return null;
    }
}