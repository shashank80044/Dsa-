class Solution {
     private int[]tree;
    public int maxActiveSectionsAfterTrade(String s) {
        int activeCount = 0;
        int n = s.length();
       
        for(int i=0;i<n;i++)
        {
      if(s.charAt(i)=='1')
      {
        activeCount+=1;
      }
        }
        List <Integer> blockstart = new ArrayList<>();
        List <Integer> blockend =   new ArrayList<>();
        List <Integer> blocksize =   new ArrayList<>();
    

        int i =0;
        while(i<n){
            if(s.charAt(i)=='0'){
                int start = i;
                while(i<n && s.charAt(i)=='0')i++;
                blockstart.add(start);
                blockend.add(i-1);
            }
            else{
                i++;
            }
        }
         if(blockstart.size()<2)
         {
            return activeCount;
         }

         int [] pairsum =new  int [blockstart.size()-1];      
        for(int j = 0; j< blockstart.size(); j++)
        {
    blocksize.add(blockend.get(j) - blockstart.get(j) + 1);
        }
             
      
   
      if (blocksize.size() < 2) {
    return activeCount; 
}


for (int k = 0; k < blocksize.size() - 1; k++) {
    pairsum[k] = blocksize.get(k) + blocksize.get(k + 1);
}
      tree = new int[4 * pairsum.length];
      buildSegmentTree(0,0,pairsum.length-1,pairsum,tree);
   return tree[0]+activeCount;
    }
  private void  buildSegmentTree(int index,int l,int r,int[]arr,int[]tree)
  {

 if(l==r)
 {
    tree[index] = arr[l];
    return;
 }
 int mid = l + (r-l)/2;
 buildSegmentTree(2*index+1,l,mid,arr,tree);
 buildSegmentTree(2*index+2,mid+1,r,arr,tree);
 tree[index]=Math.max(tree[2*index+1],tree[2*index+2]); 
  }
}