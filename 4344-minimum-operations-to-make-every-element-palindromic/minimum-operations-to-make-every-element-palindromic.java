import java.util.*;

class Solution {

    public long minOperations(int[] nums) {

        long ans = 0;

        for (int num : nums) {
            ans += findMinimumOperations(num);
        }

        return ans;
    }

    private long findMinimumOperations(long num) {

        if (isPalindrome(num)) {
            return 0;
        }

        long palindrome = findClosestPalindrome(num);

        return Math.abs(num - palindrome) / 2;
    }

    private boolean isPalindrome(long num) {

        long original = num;
        long reverse = 0;

        while (num > 0) {
            reverse = reverse * 10 + num % 10;
            num /= 10;
        }

        return original == reverse;
    }

    private long findClosestPalindrome(long num) {

        String s = Long.toString(num);

        int length = s.length();
        int halfLength = (length + 1) / 2;

        long prefix = Long.parseLong(
                s.substring(0, halfLength)
        );

        int requiredParity = (int) (num % 2);

        long closest = Long.MAX_VALUE;
        long minDistance = Long.MAX_VALUE;

        /*
         * --------------------------------------------------
         * 1. Check prefix - 1, prefix, prefix + 1
         * --------------------------------------------------
         *
         * These handle the closest palindrome around
         * the current mirrored value.
         */
        for (long p = prefix - 1; p <= prefix + 1; p++) {

            if (p <= 0) {
                continue;
            }

            // Must still have the same number of prefix digits
            if (Long.toString(p).length() != halfLength) {
                continue;
            }

            long candidate = makePalindrome(p, length);

            if (candidate <= 0) {
                continue;
            }

            if (candidate % 2 != requiredParity) {
                continue;
            }

            long distance = Math.abs(num - candidate);

            if (distance < minDistance) {
                minDistance = distance;
                closest = candidate;
            }
        }

        /*
         * --------------------------------------------------
         * 2. Check nearest prefix for every possible
         *    first digit having the required parity.
         * --------------------------------------------------
         *
         * Why?
         * The parity of a palindrome is determined by its
         * first digit (= last digit).
         */
        long power = 1;

        for (int i = 0; i < halfLength - 1; i++) {
            power *= 10;
        }

        for (int firstDigit = 1; firstDigit <= 9; firstDigit++) {

            if (firstDigit % 2 != requiredParity) {
                continue;
            }

            long low = firstDigit * power;
            long high = (firstDigit + 1) * power - 1;

            /*
             * Find the prefix inside this block that is
             * closest to the original prefix.
             */
            long candidatePrefix;

            if (prefix < low) {
                candidatePrefix = low;
            } else if (prefix > high) {
                candidatePrefix = high;
            } else {
                candidatePrefix = prefix;
            }

            long candidate =
                    makePalindrome(candidatePrefix, length);

            long distance =
                    Math.abs(num - candidate);

            if (distance < minDistance) {
                minDistance = distance;
                closest = candidate;
            }
        }

        /*
         * --------------------------------------------------
         * 3. Check one fewer digit
         * --------------------------------------------------
         */
        if (length > 1) {

            long candidate =
                    largestPalindromeWithParity(
                            length - 1,
                            requiredParity
                    );

            if (candidate > 0) {

                long distance =
                        Math.abs(num - candidate);

                if (distance < minDistance) {
                    minDistance = distance;
                    closest = candidate;
                }
            }
        }

        /*
         * --------------------------------------------------
         * 4. Check one extra digit
         * --------------------------------------------------
         */
        long largerCandidate =
                smallestPalindromeWithParity(
                        length + 1,
                        requiredParity
                );

        long distance =
                Math.abs(num - largerCandidate);

        if (distance < minDistance) {
            closest = largerCandidate;
        }

        return closest;
    }

    private long makePalindrome(long prefix, int length) {

        String left = Long.toString(prefix);

        StringBuilder result =
                new StringBuilder(left);

        int start;

        if (length % 2 == 0) {
            start = left.length() - 1;
        } else {
            start = left.length() - 2;
        }

        for (int i = start; i >= 0; i--) {
            result.append(left.charAt(i));
        }

        return Long.parseLong(result.toString());
    }

    private long largestPalindromeWithParity(
            int length,
            int parity) {

        int halfLength = (length + 1) / 2;

        long power = 1;

        for (int i = 0; i < halfLength - 1; i++) {
            power *= 10;
        }

        /*
         * Largest possible first digit having required parity:
         *
         * odd  -> 9
         * even -> 8
         */
        int firstDigit = (parity == 0) ? 8 : 9;

        /*
         * Remaining prefix digits should be 9.
         */
        long prefix = firstDigit * power;

        for (long p = power / 10; p >= 1; p /= 10) {
            prefix += 9 * p;
        }

        return makePalindrome(prefix, length);
    }

    private long smallestPalindromeWithParity(
            int length,
            int parity) {

        int halfLength = (length + 1) / 2;

        long power = 1;

        for (int i = 0; i < halfLength - 1; i++) {
            power *= 10;
        }

        /*
         * Smallest positive first digit:
         *
         * odd  -> 1
         * even -> 2
         */
        int firstDigit = (parity == 0) ? 2 : 1;

        long prefix = firstDigit * power;

        return makePalindrome(prefix, length);
    }
}