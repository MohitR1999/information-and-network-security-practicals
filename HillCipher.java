import java.util.*;

public class HillCipher {

    private static HashMap<Character, Integer> table;
    private static HashMap<Integer, Character> revTable;

    public static ArrayList<ArrayList<Integer>> copyMatrix(ArrayList<ArrayList<Integer>> mat) {
        Integer size = mat.size();
        ArrayList<ArrayList<Integer>> resMatrix = new ArrayList<ArrayList<Integer>>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                Integer x = mat.get(i).get(j);
                list.add(x);
            }
            resMatrix.add(list);
        }
        return resMatrix;
    }

    public static ArrayList<ArrayList<Integer>> getMinor(ArrayList<ArrayList<Integer>> matrix, int i, int j) {
        ArrayList<ArrayList<Integer>> res = copyMatrix(matrix);
        Integer size = res.size();
        res.remove(i);
        for (int k = 0; k < size - 1; k++) {
            res.get(k).remove(j);
        }
        return res;
    }

    public static Integer getCoFactor(ArrayList<ArrayList<Integer>> matrix, int i, int j) {
        ArrayList<ArrayList<Integer>> res = getMinor(matrix, i, j);
        Integer det = determinant(res, res.size());
        if ((i + j) % 2 == 0)
            return det;
        else
            return -det;
    }

    public static Integer determinant(ArrayList<ArrayList<Integer>> mat, int size) {
        if (size == 1) {
            return mat.get(size - 1).get(size - 1);
        } else {
            Integer res = 0;
            for (int j = 0; j < size; j++) {
                Integer cof = getCoFactor(mat, 0, j);
                res = res + mat.get(0).get(j) * cof;
            }
            return res;
        }
    }

    public static ArrayList<ArrayList<Integer>> adjoint(ArrayList<ArrayList<Integer>> mat, int size) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                Integer x = getCoFactor(mat, i, j);
                list.add(x);
            }
            res.add(list);
        }
        ArrayList<ArrayList<Integer>> transpose = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                Integer x = res.get(j).get(i);
                list.add(x);
            }
            transpose.add(list);
        }
        return transpose;
    }

    public static int modInverse(int a, int m) {
        if (a < 0) a += m;
        int m0 = m;
        int y = 0, x = 1;
        if (m == 1)
            return 0;

        while (a > 1) {
            if (m == 0)
                return -1;
            int q = a / m;
            int t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if (x < 0)
            x += m0;
        return x;
    }

    public static ArrayList<ArrayList<Integer>> multiply(ArrayList<ArrayList<Integer>> first, ArrayList<ArrayList<Integer>> second) {
        int fn = first.size();
        int fm = first.get(0).size();
        int sn = second.size();
        int sm = second.get(0).size();
        if(fm != sn) {
            return null;
        }
        ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < fn; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int j = 0; j < sm; j++) {
                Integer x = 0;
                for(int k = 0; k < sn; k++) {
                    Integer f = first.get(i).get(k);
                    Integer s = second.get(k).get(j);
                    x = x + f*s;
                }
                list.add(x);
            }
            arr.add(list);
        }
        return arr; 
    }

    public static ArrayList<ArrayList<Integer>> inverse(ArrayList<ArrayList<Integer>> mat, int size, int inverse) {
        Integer det = determinant(mat, size);
        if (det == 0) {
            return null;
        } else {
            ArrayList<ArrayList<Integer>> adj = adjoint(mat, size);
            ArrayList<ArrayList<Integer>> inv = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < size; i++) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int j = 0; j < size; j++) {
                    Integer x = adj.get(i).get(j) + 26;
                    x = (x*inverse) % 26;
                    list.add(x);
                }
                inv.add(list);
            }
            return inv;
        }
    }

    public static String encrypt(String plaintext, ArrayList<ArrayList<Integer>> matrix) {
        int n = matrix.size();
        String res = "";
        ArrayList<Integer> encrArrayList = new ArrayList<Integer>();
        if (plaintext.length() % n != 0) {
            while(plaintext.length() % n != 0) {
                plaintext += 'x';
            }
        }
        
        for(int i = 0; i < plaintext.length(); i+=n) {
            ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
            for(int j = i; j < i + n; j++) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                Character ch = plaintext.charAt(j);
                Integer val = table.get(ch);
                list.add(val);
                vector.add(list);
            }
            ArrayList<ArrayList<Integer>> result = multiply(matrix, vector);
            for(int l = 0; l < result.size(); l++) {
                for(int m = 0; m < result.get(0).size(); m++) {
                    Integer x = result.get(l).get(m);
                    encrArrayList.add(x);
                }
            }
        }
        for(int i = 0; i < encrArrayList.size(); i++) {
            Integer x = encrArrayList.get(i);
            x = x % 26;
            res = res + revTable.get(x);
        }
        return res;
    }

    public static void main(String[] args) {
        table = new HashMap<Character, Integer>();
        revTable = new HashMap<Integer, Character>();
        String t = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < t.length(); i++) {
            table.put(t.charAt(i), i);
            revTable.put(i, t.charAt(i));
        }

        Scanner scanner = new Scanner(System.in);
        Integer n = scanner.nextInt();
        ArrayList<ArrayList<Integer>> mat1 = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            for (int j = 0; j < n; j++) {
                Integer x = scanner.nextInt();
                a.add(x);
            }
            mat1.add(a);
        }
        String plaintext = scanner.next();
        plaintext.toLowerCase();
        Integer det = determinant(mat1, mat1.size());
        Integer inv = modInverse(det, 26);
        if (inv == -1) {
            System.out.println("Matrix cannot be inverted. Aborting!!!");
            scanner.close();
            System.exit(1);
        }
        String enc = encrypt(plaintext, mat1);
        System.out.println(enc);
        ArrayList<ArrayList<Integer>> inverseMat = inverse(mat1, mat1.size(), inv);
        String dec = encrypt(enc, inverseMat);
        if (dec.length() > plaintext.length()) {
            dec = dec.substring(0, plaintext.length());
        }
        System.out.println(dec);
        scanner.close();
    }
}
