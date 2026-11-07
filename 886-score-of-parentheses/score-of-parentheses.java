class Solution {
    public int scoreOfParentheses(String s) {
        int len = s.length();
        Stack<Integer> st = new Stack<>();
        int score = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '(') {
                //Fresh 
                st.push(score);
                score = 0;
            } else {
                if (s.charAt(i - 1) == '(') {
                    score = st.pop() + 1;
                } else {
                    score = st.pop() + 2 * score;
                }
            }
            
        }
 return score;
    }
}