class Solution {
public:
    int minimumPushes(string word) {
      int result = 0;
      unordered_map<int,int>map;
      int assign_key = 2;
      for(char &ch : word)
      {
        if(assign_key>9)
        {
            assign_key = 2;

        }
        map[assign_key]++;
        result +=map[assign_key];
        assign_key++;
      }  
     return result ;
    }
};