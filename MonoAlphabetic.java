import java.util.*;
public class MonoAlphabetic {
    public static String convert(String ip, HashMap<Character, Character> m) {
        String res = "";
        for(int i = 0; i < ip.length(); i++) {
            Character ch = m.get(ip.charAt(i));
            res += ch;
        }
        return res;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap <Character, Character> encMap = new HashMap<>();
        HashMap <Character, Character> decMap = new HashMap<>();
        String inp = sc.next();
        String key = sc.next();
        inp = inp.toLowerCase();
        String ref = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < ref.length(); i++) {
            encMap.put(ref.charAt(i), key.charAt(i));
            decMap.put(key.charAt(i), ref.charAt(i));
        }
        
        String encrypted = convert(inp, encMap);
        String decrypted = convert(encrypted, decMap);
        System.out.println("Input String: " + inp);
        System.out.println("Encrypted String: " + encrypted);
        System.out.println("Decrypted String: " + decrypted);
        sc.close();
    }
}