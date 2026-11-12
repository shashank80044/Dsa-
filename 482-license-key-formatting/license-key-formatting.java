
class Solution {
    public String licenseKeyFormatting(String s, int k) {

        StringBuilder sb = new StringBuilder();

        int count = 0;

        for (int i = s.length() - 1; i >= 0; i--) {

            char ch = s.charAt(i);

            // Ignore existing dashes
            if (ch == '-') {
                continue;
            }

            // Convert lowercase to uppercase
            sb.append(Character.toUpperCase(ch));

            count++;

            // Insert dash after every k characters
            if (count == k) {
                sb.append('-');
                count = 0;
            }
        }

        // Remove the extra dash at the end
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '-') {
            sb.deleteCharAt(sb.length() - 1);
        }

        // Reverse to get the correct order
        return sb.reverse().toString();
    }
}
