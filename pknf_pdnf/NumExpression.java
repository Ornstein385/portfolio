package com.company.Numeric_Expressions;

import java.util.ArrayList;
import java.util.Scanner;

public class NumExpression {
    public static ArrayList<Lexeme> toLexemes(String exp) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        Scanner sc = new Scanner(exp);
        while (sc.hasNext()) {
            String buf = sc.next();
            if (isWord(buf)) {
                lexemes.add(new Lexeme(buf, Type.VAR));
                continue;
            }
            if (isNumber(buf)) {
                lexemes.add(new Lexeme(buf, Type.CONST));
                continue;
            }
            switch (buf) {
                case "+":
                    lexemes.add(new Lexeme("+", Type.BIN_OPER));
                    continue;
                case "-":
                    lexemes.add(new Lexeme("-", Type.BIN_OPER));
                    continue;
                case "*":
                    lexemes.add(new Lexeme("*", Type.BIN_OPER));
                    continue;
                case "/":
                    lexemes.add(new Lexeme("/", Type.BIN_OPER));
                    continue;
                case "(":
                    lexemes.add(new Lexeme("(", Type.LB));
                    continue;
                case ")":
                    lexemes.add(new Lexeme(")", Type.RB));
                    continue;
                default:
                    lexemes.add(new Lexeme(buf, Type.NOT_DEFINED));
            }
        }
        lexemes.add(new Lexeme("", Type.EOI));
        return lexemes;
    }

    private static boolean isWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void getInfo (ArrayList<Lexeme> lexemes){
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
        }
        else {
            System.out.println("выражение корректно.");
        }
    }

    public static boolean isValid(ArrayList<Lexeme> lexemes) {
        if (isDefined(lexemes) && isCorrectBracketSequence(lexemes) && isCorrectActions(lexemes)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDefined(ArrayList<Lexeme> lexemes) {
        for (int i = 0; i < lexemes.size(); i++) {
            if (lexemes.get(i).type == Type.NOT_DEFINED) {
                return false;
            }
        }
        return true;
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

    public static boolean isCorrectActions(ArrayList<Lexeme> lexemes) {
        try {
            for (int i = 0; i < lexemes.size(); i++) {
                switch (lexemes.get(i).type) {
                    case BIN_OPER:
                        if (!((lexemes.get(i - 1).type == Type.CONST || lexemes.get(i - 1).type == Type.VAR
                                || lexemes.get(i - 1).type == Type.RB) && (lexemes.get(i + 1).type == Type.CONST
                                || lexemes.get(i + 1).type == Type.VAR || lexemes.get(i + 1).type == Type.LB))) {
                            return false;
                        }
                        continue;
                    case CONST, VAR:
                        if (!(i == 0 || (i > 0 && (lexemes.get(i - 1).type == Type.BIN_OPER || lexemes.get(i - 1).type == Type.LB)) &&
                                (lexemes.get(i + 1).type == Type.BIN_OPER || lexemes.get(i + 1).type == Type.RB || lexemes.get(i + 1).type == Type.EOI))) {
                            return false;
                        }
                        continue;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public static String toString(ArrayList<Lexeme> lexemes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lexemes.size(); i++) {
            sb.append(lexemes.get(i).value).append(" ");
        }
        return sb.toString();
    }

    public static int calculateExpression(LexemesBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == Type.EOI) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    private static int plusminus(LexemesBuffer lexemes) {
        int value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.value) {
                case "+":
                    value += multdiv(lexemes);
                    break;
                case "-":
                    value -= multdiv(lexemes);
                    break;
                case "" , ")":
                    lexemes.back();
                    return value;
                default:
                    return 0;
            }
        }
    }

    private static int multdiv(LexemesBuffer lexemes) {
        int value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.value) {
                case "*":
                    value *= factor(lexemes);
                    break;
                case "/":
                    value /= factor(lexemes);
                    break;
                case "", ")", "+", "-":
                    lexemes.back();
                    return value;
                default:
                    return 0;
            }
        }
    }

    private static int factor(LexemesBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case CONST:
                return Integer.parseInt(lexeme.value);
            case LB:
                int value = plusminus(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != Type.RB) {
                    return 0;
                }
                return value;
            default:
                return 0;
        }
    }
}
