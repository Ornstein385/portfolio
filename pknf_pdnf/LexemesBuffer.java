package com.company.Numeric_Expressions;

import java.util.ArrayList;

public class LexemesBuffer {
    public int pos;

    public ArrayList<Lexeme> lexemes;

    public LexemesBuffer(ArrayList<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next() {
        return lexemes.get(pos++);
    }

    public void back() {
        pos--;
    }

}
