package com.kerimov.adee.transtalorbyadil;

import com.google.gson.annotations.SerializedName;

public class Word {
    private int code;
    private String lang;

    @SerializedName("text")
    private String[] text;

    public String[] getText() {
        return text;
    }
}
