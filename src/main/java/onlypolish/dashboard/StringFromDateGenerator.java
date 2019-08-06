package onlypolish.dashboard;

import java.util.Date;

public enum StringFromDateGenerator {

    INSTANCE;

    public String yearString(int year){
        year = 1900 + year;
        return String.valueOf(year);
    }

    public String getMonth(int month){
        month = month + 1;
        return getOtherTimeUnit(month);
    }

    public String getOtherTimeUnit(int timeUnit){
        if(timeUnit <= 9) return "0" + timeUnit;
        else return String.valueOf(timeUnit);
    }

    public String stringFromDateGenerator(Date date){
        String dateString = "";
        dateString += yearString(date.getYear());
        dateString += getMonth(date.getMonth());
        dateString += getOtherTimeUnit(date.getDate());
        dateString += getOtherTimeUnit(date.getHours());
        dateString += getOtherTimeUnit(date.getMinutes());
        dateString += getOtherTimeUnit(date.getSeconds());
        return dateString;
    }
}
