package com.example.Beans;

public class TimeTableDetail {
    private String m_day;
    private String m_time;
    private String m_field;

    public TimeTableDetail() {
        // null
    }

    public void setday(String day) {
        m_day = day;
    }

    public void settime(String time) {
        m_time = time;
    }

    public void setfield(String field) {
        m_field = field;
    }

    public String getday() {
        return m_day;
    }

    public String gettime() {
        return m_time;
    }

    public String getfield() {
        return m_field;
    }
}