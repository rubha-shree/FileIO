package com.company;

public class mapValue {

    private String lines = "";
    private int numberOfLines = 0;

    public mapValue(String line, int numberOfLines) {
        this.lines += line;
        this.numberOfLines += numberOfLines;
    }

    public void reset() {
        this.lines = "";
        this.numberOfLines = 0;
    }

    public boolean appendAndCheckLimit(String line, int numberOfLines) {
        this.lines += line;
        this.numberOfLines += numberOfLines;
        return this.isLimitReached();
    }

    public boolean isLimitReached() {
        if(this.numberOfLines == 100)  {
            return true;
        }

        return false;
    }

    public String toString() {
        return this.lines;
    }

    public boolean isEmpty() {
        if (this.numberOfLines > 0) {
            return false;
        }

        return true;
    }
}
