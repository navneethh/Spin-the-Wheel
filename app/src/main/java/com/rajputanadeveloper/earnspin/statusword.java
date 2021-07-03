package com.rajputanadeveloper.earnspin;

/**
 * Created by user on 04-05-2018.
 */

public class statusword {
    private String mDefaultTranslatio;
    private String mSpanishTranslatio;
    private String mkstatuskey;

    public String getdesc() {
        return mkstatuskey;
    }

    public void setdesc(String mkstatuskey) {
        this.mkstatuskey = mkstatuskey;
    }

    public statusword(String time, String coin, String desc
    ) {
        this.mDefaultTranslatio = time;
        this.mSpanishTranslatio = coin;
        this.mkstatuskey = desc;

    }

    public String gettime() {
        return mDefaultTranslatio;
    }

    public void settime(String mDefaultTranslatio) {
        this.mDefaultTranslatio = mDefaultTranslatio;
    }

    public String getcoin() {
        return mSpanishTranslatio;
    }

    public void setcoin(String mSpanishTranslatio) {
        this.mSpanishTranslatio = mSpanishTranslatio;
    }
}
