package com.company.Numeric_Expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Pdnf {
    public static String makePdnf(String s) {
        HashMap<Integer, Character> v = BinExpression.markVar(s);
        int l = v.size();
        int k = (int) Math.pow(2, l);
        int[] a = createTruthTable(l, k, v, s);
        //System.out.println(Arrays.toString(a));
        //return Arrays.toString(a);
        StringBuilder sb = new StringBuilder("сднф: ");
        for (int i = 0; i < k; i++) {
            if (a[i] == 1) {
                sb.append("(");
                String bin = Integer.toBinaryString(i);
                while (bin.length() < l) {
                    bin = "0" + bin;
                }
                for (int j = 0; j < l; j++) {
                    if (bin.charAt(j)=='0'){
                        sb.append("~");
                    }
                    sb.append(v.get(j));
                    if (j<l-1){
                        sb.append("&");
                    }
                }
                sb.append(")|");
            }
        }
        String r = sb.toString();
        r = r.substring(0, r.length()-1);
        return r;
    }

    private static int[] createTruthTable(int n, int k, HashMap<Integer, Character> v, String s) {
        int[] a = new int[k];
        for (int i = 0; i < k; i++) {
            String d = s;
            HashMap<Character, String> m = new HashMap<>();
            String bin = Integer.toBinaryString(i);
            while (bin.length() < n) {
                bin = "0" + bin;
            }
            for (int j = 0; j < n; j++) {
                char ch = v.get(j);
                if (bin.charAt(j) == '0') {
                    m.put(ch, "0");
                } else {
                    m.put(ch, "1");
                }
            }
            d = BinExpression.insertValues(d, m);
            ArrayList<Lexeme> lexemes = BinExpression.toLexemes(d);
            BinExpression.calcBinExp(lexemes);
            a[i] = Integer.parseInt(lexemes.get(0).value);
        }
        return a;
    }
}
