package com.example.Beans;

public class RoomInfo {

    private String m_roomTitle;
    private String m_roomInterest;
    private String m_roomMemberNumber;
    private long m_roomTime;

    public RoomInfo(String roomTitle, String roomInterest, String roomMemberNumber, long roomTime) {
        m_roomTitle = roomTitle;
        m_roomInterest = roomInterest;
        m_roomMemberNumber = roomMemberNumber;
        m_roomTime = roomTime;
    }

    public RoomInfo() {

    }

    public void setM_roomTime(long m_roomTime) {
        this.m_roomTime = m_roomTime;
    }

    public String getM_roomTitle() {
        return m_roomTitle;
    }

    public void setM_roomTitle(String m_roomTitle) {
        this.m_roomTitle = m_roomTitle;
    }

    public String getM_roomInterest() {
        return m_roomInterest;
    }

    public void setM_roomInterest(String m_roomInterest) {
        this.m_roomInterest = m_roomInterest;
    }

    public String getM_roomMemberNumber() {
        return m_roomMemberNumber;
    }

    public void setM_roomMemberNumber(String m_roomMemberNumber) {
        this.m_roomMemberNumber = m_roomMemberNumber;
    }

    public long getM_roomTime() {
        return m_roomTime;
    }

}
