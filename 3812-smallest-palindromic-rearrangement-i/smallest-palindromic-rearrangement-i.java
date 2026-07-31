class Solution {
    public String smallestPalindrome(String s) {
     int n = s.length();
     int mid = n/2;
     char []arr=s.toCharArray();
     Arrays.sort(arr,0,mid);
     for(int i=0;i<mid;i++)
     {
    arr[n-1-i]=arr[i];
     } 
     String res = new String(arr);
     return res;
    }
}