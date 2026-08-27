class Solution {

    public String lexGreaterPermutation(String s, String target) {

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder ans = new StringBuilder();

        if (solve(0, target, freq, ans, false)) {
            return ans.toString();
        }

        return "";
    }

    private boolean solve(
        int pos,
        String target,
        int[] freq,
        StringBuilder ans,
        boolean greater
    ) {

        if (pos == target.length()) {
            return greater;
        }

        int start = 0;

        if (!greater) {
            start = target.charAt(pos) - 'a';
        }

        for (int c = start; c < 26; c++) {

            if (freq[c] == 0) {
                continue;
            }

            // If we are still equal to target,
            // choosing the same character keeps us equal.
            boolean newGreater = greater || c > target.charAt(pos) - 'a';

            // If c is smaller than target[pos], invalid.
            if (!greater && c < target.charAt(pos) - 'a') {
                continue;
            }

            freq[c]--;
            ans.append((char) ('a' + c));

            if (solve(pos + 1, target, freq, ans, newGreater)) {
                return true;
            }

            ans.deleteCharAt(ans.length() - 1);
            freq[c]++;
        }

        return false;
    }
}