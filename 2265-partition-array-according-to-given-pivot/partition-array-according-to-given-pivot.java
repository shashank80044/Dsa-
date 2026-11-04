class Solution {
    public int[] pivotArray(int[] nums, int pivot) {

        int n = nums.length;

        int[] ans = new int[n];

        int index = 0;

        // Step 1: Add elements smaller than pivot
        for (int i = 0; i < n; i++) {
            if (nums[i] < pivot) {
                ans[index] = nums[i];
                index++;
            }
        }

        // Step 2: Add elements equal to pivot
        for (int i = 0; i < n; i++) {
            if (nums[i] == pivot) {
                ans[index] = nums[i];
                index++;
            }
        }

        // Step 3: Add elements greater than pivot
        for (int i = 0; i < n; i++) {
            if (nums[i] > pivot) {
                ans[index] = nums[i];
                index++;
            }
        }

        return ans;
    }
}