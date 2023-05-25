package com.example.d;

public class Item {
    String em;
    String pass;
    String fn;
    String bdy;
    String ms;


    public Item(String em, String pass, String fn, String bdy, String ms) {
        this.em = em;
        this.pass = pass;
        this.fn = fn;
        this.bdy = bdy;
        this.ms = ms;



    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getBdy() {
        return bdy;
    }

    public void setBdy(String bdy) {
        this.bdy = bdy;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }
}


