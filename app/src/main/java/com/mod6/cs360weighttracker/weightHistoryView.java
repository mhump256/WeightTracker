package com.mod6.cs360weighttracker;

public class weightHistoryView {

    private final int mImageResource;
    private final String mText1;
    private final String mText2;
    private final String mText3;

    public weightHistoryView(int imageResource, String text1, String text2, String text3){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getText1(){
        return mText1;
    }

    public String getText2(){
        return mText2;
    }

    public String getText3() {return mText3;}


}