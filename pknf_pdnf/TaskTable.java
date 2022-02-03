package com.company.Numeric_Expressions;

import java.util.Scanner;

public class TaskTable {

    public static String[] insertExpressions (String[] a){
        for (int i = 0; i<a.length; i++){
            for (int j = 0; j<a[i].length(); j++){

                if (a[i].charAt(j) == '$'){
                    int b = j;
                    j++;
                    StringBuilder sb = new StringBuilder();
                    while (j<a[i].length() && Character.isDigit(a[i].charAt(j))){
                        sb.append(a[i].charAt(j));
                        j++;
                    }
                    int ind = Integer.parseInt(sb.toString()) -1;
                    a[i] = cutSubstring(a[i], b, j);
                    a[i] = insertString(a[i], a[ind], b);
                    toString(a);
                    insertExpressions(a);
                }
            }
        }
        return a;
    }

    private static String insertString (String d, String f, int q){
        d = d.substring(0, q) + f + d.substring(q, d.length());
        return d;
    }
    private static String cutSubstring (String d, int y, int u){
        d = d.substring(0, y) + d.substring(u, d.length());
        return d;
    }
    public static String toString (String[] a){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<a.length; i++){
            sb.append(a[i]).append("|");
        }
        return sb.toString();
    }

}
