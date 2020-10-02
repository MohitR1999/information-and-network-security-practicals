import java.util.*;
public class PolyAlphabeticCipher {
    
    private static HashMap<Character, Integer> table;
    private static HashMap<Integer, Character> revTable;

    public static String keyGenerator(String keyString, String pString) {
        String reString = "";
        while(reString.length() < pString.length()) {
            reString = reString + keyString;
        }
        reString = reString.substring(0, pString.length());
        return reString;
    }

    public static String encrypString(String key, String plainText) {
        String result = "";
        for(int i = 0; i < plainText.length(); i++) {
            Character pCharacter = plainText.charAt(i);
            Character kCharacter = key.charAt(i);
            Integer pos = (table.get(pCharacter) + table.get(kCharacter)) % 26;
            result = result + revTable.get(pos);
        }
        return result;
    }

    public static String decrypString(String key, String cipher) {
        String result = "";
        for(int i = 0; i < cipher.length(); i++) {
            Character cCharacter = cipher.charAt(i);
            Character kCharacter = key.charAt(i);
            Integer pos = (table.get(cCharacter) - table.get(kCharacter) + 26) % 26;
            result += revTable.get(pos);
        }
        return result;
    }

    public static void main(String[] args) {
        
        table = new HashMap<Character, Integer>();
        revTable = new HashMap<Integer, Character>();
        
        String t = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < t.length(); i++) {
            table.put(t.charAt(i), i);
            revTable.put(i, t.charAt(i));
        }
        
        Scanner sc = new Scanner(System.in);
        String keyString = sc.next();
        String plainTextString = sc.next();
        plainTextString = plainTextString.toLowerCase();
        keyString = keyString.toLowerCase();
        String key = keyGenerator(keyString, plainTextString);
        String enc = encrypString(key, plainTextString);
        String dec = decrypString(key, enc);
        System.out.println(enc);
        System.out.println(dec);
        sc.close();
    }
}