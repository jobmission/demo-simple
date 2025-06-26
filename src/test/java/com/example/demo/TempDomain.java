package com.example.demo;

import java.io.Serializable;


public class TempDomain implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4096506988865524998L;
    private int a;
    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "{"
            + "\"a\":"
            + a
            + ",\"b\":"
            + b
            + "}";
    }
}
