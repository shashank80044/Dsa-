import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSubarray(int[] nums) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        int maxLen = 0;
        int l = 0, r = 0;
        int n = nums.length;

        while (r < n) {
            int num = nums[r];
            
            if (isValid(freqMap, num)) {
                freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
                r++;
            } else {
                freqMap.put(nums[l], freqMap.get(nums[l]) - 1);
                if (freqMap.get(nums[l]) == 0) {
                    freqMap.remove(nums[l]);
                }
                l++;
            }
            maxLen = Math.max(maxLen, r - l);
        }

        return maxLen;
    }

    private boolean isValid(Map<Integer, Integer> freqMap, int num) {
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int ele = entry.getKey();
            int count = entry.getValue();

            // Case 1: ele + num = some existing element in freqMap
            if (freqMap.containsKey(ele + num)) {
                return false;
            }

            // Case 2: ele + another element = num
            int other = num - ele;
            if (other > 0 && freqMap.containsKey(other)) {
                // If ele == other, we need at least 2 occurrences of 'ele' in the map
                if (ele != other || count > 1) {
                    return false;
                }
            }
        }
        return true;
    }
}