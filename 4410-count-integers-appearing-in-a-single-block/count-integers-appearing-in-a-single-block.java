import java.util.*;

class Solution {

    public boolean check(int target, int[] nums) {

        int[] index = new int[nums.length];
        int j = 0;

        // Store all positions of target
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                index[j] = i;
                j++;
            }
        }

        // Target occurs only once
        if (j == 1) {
            return true;
        }

        // Check whether all occurrences are consecutive
        for (int k = 1; k < j; k++) {
            if (index[k - 1] + 1 != index[k]) {
                return false;
            }
        }

        return true;
    }

    public int countSpecialIntegers(int[] nums) {

        int res = 0;
        HashSet<Integer> visited = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {

            if (!visited.contains(nums[i])) {

                if (check(nums[i], nums)) {
                    res++;
                }

                visited.add(nums[i]);
            }
        }

        return res;
    }
}