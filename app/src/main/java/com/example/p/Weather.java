package com.example.p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Weather {
    private String date;
    private String temp;
    private String we;
    public Weather(String date, String temp, String we) {
        this.date = date;
        this.temp = temp;
        this.we = we;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return this.we;
    }

    public String getTemp() {
        return this.temp;
    }
}
