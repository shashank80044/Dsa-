class Solution {

    public List<List<Integer>> findDisappearedNumbers(
        int[] nums, int lower, int upper) {

        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();

        int next = lower;

        for (int num : nums) {

            // num is before our range
            if (num < next) {
                continue;
            }

            // num is beyond our range
            if (num > upper) {
                if (next <= upper) {
                    ans.add(Arrays.asList(next, upper));
                }
                return ans;
            }

            // There is a missing range before num
            if (num > next) {
                ans.add(Arrays.asList(next, num - 1));
            }

            // num exists
            next = num + 1;
        }

        // Missing numbers after the last element
        if (next <= upper) {
            ans.add(Arrays.asList(next, upper));
        }

        return ans;
    }
}