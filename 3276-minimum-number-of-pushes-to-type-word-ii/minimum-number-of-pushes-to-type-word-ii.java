import java.util.Arrays;

class Solution {
    public int minimumPushes(String word) {
        int[] mp = new int[26];
        
        
        for (char ch : word.toCharArray()) {
            mp[ch - 'a']++;
        }
        
       
        Arrays.sort(mp);
        
        int result = 0;
        
        
        for (int i = 0; i < 26; i++) {
            int freq = mp[25 - i]; 
            int press = i / 8 + 1;
            result += press * freq;
        }
        
        return result;
    }
}