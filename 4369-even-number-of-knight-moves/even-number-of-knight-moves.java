class Solution {
    public boolean canReach(int[] start, int[] target) {
    int s1 = start[0];
    int s2 = start[1];
    int t1 = target[0];
    int t2 = target[1];
        int count1=0;
        int count2=0;
        int total;
        
    
            count1 = s1-t1;
            count2 = s2-t2;
            if(count1<0)
            {
                count1 = t1-s1; 
            }
            else if(count2<0)
            {
                count2 = t2-s2;
            }
           
       
        total = count1 + count2;
        return(total%2==0) ? true:false;

    }
}