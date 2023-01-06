package com.example.myapplication;

import java.util.ArrayList;

public class SlideShowData {
    public static ArrayList<ss_ImageData> list;

    public static void setList(ArrayList<ss_ImageData> data) {
        list = new ArrayList<>(data);
    }
    public static void clearList(){
        list.clear();
    }
}