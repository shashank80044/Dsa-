import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minimumPushes(String word) {
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int assign_key = 2;

        for (char ch : word.toCharArray()) {
            if (assign_key > 9) {
                assign_key = 2;
            }
            map.put(assign_key, map.getOrDefault(assign_key, 0) + 1);
            result += map.get(assign_key);
            assign_key++;
        }

        return result;
    }
}