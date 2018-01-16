package com.threabba.myapplication;

import java.util.IllegalFormatException;

/**
 * Created by ETRI LSAR Project Team on 2018-01-16.
 */

public class Memo {
    private int id;
    private String title;
    private String note;
    private String date;

    public Memo() {
    }

    public Memo(int id, String title, String note, String date) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Memo fromString(String string){
        String[] strList = string.split(",");
        if(strList.length !=4){
            return null;
            // or
            // throw new Exception("wrong string");
        }
        Memo memo = new Memo();
        try {
            memo.setId(Integer.parseInt(strList[0].trim()));
        }catch (NumberFormatException e){
            return null;
            // or
            // throw e;
        }
        memo.setTitle(strList[1].trim());
        memo.setNote(strList[2].trim());
        memo.setDate(strList[3].trim());
        return memo;
    }

    public static Memo fromStringWithoutNumber(String string){
        String[] strList = string.split(",");
        if(strList.length !=3){
            return null;
            // or
            // throw new Exception("wrong string");
        }
        Memo memo = new Memo();
        memo.setTitle(strList[0].trim());
        memo.setNote(strList[1].trim());
        memo.setDate(strList[2].trim());
        return memo;
    }

    @Override
    public String toString() {
        return id+", "+title+", "+note+", "+date;
    }
}
