package com.company.Numeric_Expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("введите выражение для построения сднф и скнф");

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        ArrayList<Lexeme> lexemes = BinExpression.toLexemes(s);
        if (BinExpression.isValid(lexemes)){
        System.out.println(Pdnf.makePdnf(s));
        System.out.println(Pknf.makePknf(s));
        }
        else {
            BinExpression.getInfo(lexemes);
        }


        /*
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        lexemes = NumExpression.toLexemes(sc.nextLine());
        NumExpression.getInfo(lexemes);
        //System.out.println(NumExpression.toString(lexemes));
        if (NumExpression.isValid(lexemes)) {
            LexemesBuffer exp = new LexemesBuffer(lexemes);
            System.out.println(NumExpression.calculateExpression(exp));
        }

        //задание: линейная эл таблица

        String[] a = sc.nextLine().split("\\|");
        Task.insertExpressions(a);
        for (int i = 0; i<a.length; i++){
            ArrayList<Lexeme> lexemes = NumExpression.toLexemes(a[i]);
            if (NumExpression.isValid(lexemes)) {
                LexemesBuffer exp = new LexemesBuffer(lexemes);
                System.out.print(NumExpression.calculateExpression(exp));
                if (i<a.length-1){
                    System.out.print(" | ");
                }
            }
            else {
                System.out.println();
                NumExpression.getInfo(lexemes);
            }
        }

        вычисление бинарного выражения
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        lexemes = BinExpression.toLexemes(sc.nextLine());
        BinExpression.getInfo(lexemes);
        if (BinExpression.isValid(lexemes)) {
            BinExpression.calcBinExp(lexemes);
            System.out.println(BinExpression.toString(lexemes));
        }
         */
    }
}
