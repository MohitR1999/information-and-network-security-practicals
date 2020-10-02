import java.util.*;

class CaesarCipher {
    
    public static String encrypt(String input, int k) {
        String res = "";
        for (int i = 0; i < input.length(); i++) {
            int ch = input.charAt(i);
            if (Character.isUpperCase(ch)) {
                ch = (ch + k - 65) % 26 + 65; 
            }
            else {
                ch = (ch + k - 97) % 26 + 97;
            }
            res += (char)ch;
        }
        return res;
    }
    
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String inp = sc.next();
        int key = sc.nextInt();
        String enc = encrypt(inp, key);
        String dec = encrypt(enc, 26 - key);
        System.out.println("Original text: " + inp);
        System.out.println("Encrypted text: " + enc);
        System.out.println("Decrypted text: " + dec);
        sc.close();
    }
}