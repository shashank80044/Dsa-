class Solution {
    public int maxEqualAdjacentPairs(int[] nums) {

        int base = 0;

        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 1; i < nums.length; i++) {

            int a = nums[i - 1];
            int b = nums[i];

            if (a == b) {
                base++;
            } 
            else {
                int min = Math.min(a, b);
                int max = Math.max(a, b);

                String key = min + "#" + max;

                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }

        int best = 0;

        for (int count : map.values()) {
            best = Math.max(best, count);
        }

        return base + best;
    }
}