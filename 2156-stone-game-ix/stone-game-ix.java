class Solution {
    public boolean stoneGameIX(int[] stones) {
     int co = 0;
     int c1 = 0;
     int c2 = 0;

     for(int i =0; i<stones.length ;i++)
     {
     if(stones[i] %3==0)
     {
        co++;
     }
     else if(stones[i] % 3 ==1)
     {
        c1++;
     }
     else{
        c2++;
     }

     }
     if(co % 2 == 0)
     {
            return ((c1>=1 && c2>=1) && (c2>=c1 || c1>=c2));
     }

     else{
        return Math.abs(c1-c2)>=3;
     }

    }
}