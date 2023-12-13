package com.example.buaaexercise.blog.sensitive_detect;

public class Word implements Comparable<Word>{
    public char c;
    public List next = null;

    public Word(char c){
        this.c = c;
    }

    @Override
    public int compareTo(Word word) {
        return c - word.c;
    }

    public String toString(){
        return c + "(" + (next == null ? null : next.size()) + ")";
    }
}
