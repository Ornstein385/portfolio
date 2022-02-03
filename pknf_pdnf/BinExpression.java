package com.company.Numeric_Expressions;

import java.util.ArrayList;
import java.util.HashMap;

public class BinExpression {
    public static ArrayList<Lexeme> toLexemes(String exp) {
        //если нужно считывать лексемы из нескольких символов в каждой,
        // то нужно требовать пробел между ними, а этот метод переделать
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        for (int i = 0; i < exp.length(); i++) {
            if (!Character.isWhitespace(exp.charAt(i))) {
                if (Character.isLetter(exp.charAt(i))) {
                    lexemes.add(new Lexeme(Character.toString(exp.charAt(i)), Type.VAR));
                    continue;
                }
                switch (exp.charAt(i)) {
                    case '(':
                        lexemes.add(new Lexeme("(", Type.LB));
                        continue;
                    case ')':
                        lexemes.add(new Lexeme(")", Type.RB));
                        continue;
                    case '&':
                        lexemes.add(new Lexeme("&", Type.BIN_OPER));
                        continue;
                    case '|':
                        lexemes.add(new Lexeme("|", Type.BIN_OPER));
                        continue;
                    case '~':
                        lexemes.add(new Lexeme("~", Type.UN_OPER));
                        continue;
                    case '0':
                        lexemes.add(new Lexeme("0", Type.CONST));
                        continue;
                    case '1':
                        lexemes.add(new Lexeme("1", Type.CONST));
                        continue;
                    default:
                        lexemes.add(new Lexeme(Character.toString(exp.charAt(i)), Type.NOT_DEFINED));
                }
            }
        }
        //lexemes.add(new Lexeme("", Type.EOI));
        return lexemes;
    }

    public static void getInfo(ArrayList<Lexeme> lexemes) {
        if (!isValid(lexemes)) {
            System.out.println("выражение некорректно: ");
            System.out.println(toString(lexemes));
            if (!isCorrectBracketSequence(lexemes)) {
                System.out.println("неправильная скобочная последовательность. ");
            }
            if (!isDefined(lexemes)) {
                System.out.println("выражение содержит неизвестные символы или были были пропущены пробелы. ");
            }
            if (!isCorrectActions(lexemes)) {
                System.out.println("некоторые символы операций расставлены неправильно или отсутствуют. ");
            }
        } else {
            System.out.println("выражение корректно.");
        }
    }

    public static String toString(ArrayList<Lexeme> lexemes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lexemes.size(); i++) {
            sb.append(lexemes.get(i).value).append(" ");
        }
        return sb.toString();
    }

    public static boolean isValid(ArrayList<Lexeme> lexemes) {
        if (isDefined(lexemes) && isCorrectBracketSequence(lexemes) && isCorrectActions(lexemes)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCorrectBracketSequence(ArrayList<Lexeme> lexemes) {
        int balance = 0;
        for (int i = 0; i < lexemes.size(); i++) {
            if (lexemes.get(i).value.equals("(")) {
                balance++;
            }
            if (lexemes.get(i).value.equals(")")) {
                balance--;
            }
            if (balance < 0) {
                return false;
            }
        }
        if (balance != 0) {
            return false;
        }
        return true;
    }

    public static boolean isDefined(ArrayList<Lexeme> lexemes) {
        for (int i = 0; i < lexemes.size(); i++) {
            if (lexemes.get(i).type == Type.NOT_DEFINED) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCorrectActions(ArrayList<Lexeme> lexemes) {
        try {
            for (int i = 0; i < lexemes.size(); i++) {
                switch (lexemes.get(i).type) {
                    case BIN_OPER:
                        if (!((lexemes.get(i - 1).type == Type.CONST || lexemes.get(i - 1).type == Type.VAR || lexemes.get(i - 1).value.equals(")")) &&
                                (lexemes.get(i + 1).type == Type.CONST || lexemes.get(i + 1).type == Type.VAR || lexemes.get(i + 1).type == Type.UN_OPER
                                        || lexemes.get(i + 1).value.equals("(")))) {
                            return false;
                        }
                        continue;

                    case UN_OPER://несколько унарных операций подряд не поддерживаются
                        if (lexemes.get(i + 1).type == Type.UN_OPER) {
                            return false;
                        }
                        continue;

                    case CONST, VAR:
                        if (!(i > 0 && (lexemes.get(i - 1).type == Type.UN_OPER || lexemes.get(i - 1).type == Type.BIN_OPER
                                || lexemes.get(i - 1).value.equals("(")) || i < lexemes.size() && (lexemes.get(i + 1).type == Type.BIN_OPER
                                || lexemes.get(i + 1).value.equals(")")))) {
                            return false;
                        }
                        continue;
                    case LB:// " ( ) " не поддерживается
                        if (lexemes.get(i + 1).type == Type.RB) {
                            return false;
                        }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public static void calcBinExp(ArrayList<Lexeme> lexemes) {
        System.out.println(toString(lexemes));
        for (int i = 0; i < lexemes.size(); i++) {
            switch (lexemes.get(i).value) {
                case "~":
                    if (lexemes.get(i + 1).type == Type.CONST) {
                        if (lexemes.get(i + 1).value.equals("0")) {
                            lexemes.get(i + 1).setValue("1");
                        } else {
                            lexemes.get(i + 1).setValue("0");
                        }
                        lexemes.remove(i);
                        calcBinExp(lexemes);
                        break;
                    }
                    continue;
                case "0", "1"://удалить скобки
                    if (i > 0 && i < lexemes.size() - 1 && lexemes.get(i - 1).type == Type.LB && lexemes.get(i + 1).type == Type.RB) {
                        lexemes.remove(i + 1);
                        lexemes.remove(i - 1);
                        calcBinExp(lexemes);
                        break;
                    }
                    continue;
                case "&":
                    if (lexemes.get(i-1).type == Type.CONST && lexemes.get(i+1).type == Type.CONST){
                        int x = Math.min(Integer.parseInt(lexemes.get(i-1).value), Integer.parseInt(lexemes.get(i+1).value));
                        lexemes.get(i-1).setValue(Integer.toString(x));
                        lexemes.remove(i+1);
                        lexemes.remove(i);
                        calcBinExp(lexemes);
                        break;
                    }
                    continue;
                case "|":
                    if (lexemes.get(i-1).type == Type.CONST && lexemes.get(i+1).type == Type.CONST){
                        int x = Math.max(Integer.parseInt(lexemes.get(i-1).value), Integer.parseInt(lexemes.get(i+1).value));
                        lexemes.get(i-1).setValue(Integer.toString(x));
                        lexemes.remove(i+1);
                        lexemes.remove(i);
                        calcBinExp(lexemes);
                        break;
                    }
                    continue;
            }
        }
    }
    public static HashMap<Integer, Character> markVar(String s){
        HashMap<Integer, Character> m = new HashMap<>();
        int x = 0;
        for (int i = 0; i<s.length(); i++){
            if (Character.isLetter(s.charAt(i))){
                if (!m.containsValue(s.charAt(i))){
                    m.put(x, s.charAt(i));
                    x++;
                }
            }
        }
        return m;
    }
    public static String insertValues(String s, HashMap<Character, String> m){
        for (int i = 0; i<s.length(); i++){
            if (m.containsKey(s.charAt(i))){
                s = changeSymbol(s, i, m.get(s.charAt(i)));
            }
        }
        return s;
    }
    private static String changeSymbol(String s, int i, String d){
        s = s.substring(0, i) + d + s.substring(i+1, s.length());
        return s;
    }
}
