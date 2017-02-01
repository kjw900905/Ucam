package com.example.Beans;

public class Variable {
    // php 주소 구성 : "http://(서버 주소)/(php 파일명 + 확장자명)"
    // php 주소 형식 : "http://xxx.xxx.xxx.xxx/xxxxx.php"
    // php 주소 예시 : "http://221.148.86.18/SelectAll.php"

<<<<<<< HEAD
    public final static String m_SERVER_URL = "http://192.168.0.63";
    public final static String m_PHP_SELECTONE_SEARCH_SCHOOL = "/SelectOne_search_school.php";
    public final static String m_PHP_INSERT_PERSONAL_INFORMATION = "/Insert_personal_information.php";
    public final static String m_PHP_CHECK_ID_PW = "/Check_ID_PW.php";
    public final static String m_PHP_UPDATE_SEARCH = "/Update_Search.php"; // MatchFragment에서 매치에 사용하는 php
=======
    public static final String m_SERVER_URL = "http://117.17.158.173";
    public static final String m_PHP_SELECTONE_SEARCH_SCHOOL = "/SelectOne_search_school.php";
    public static final String m_PHP_INSERT_PERSONAL_INFORMATION = "/Insert_personal_information.php";
    public static final String m_PHP_CHECK_ID_PW = "/Check_ID_PW.php";
    public static final String m_PHP_UPDATE_SEARCH = "/Update_Search.php"; // MatchFragment에서 매치에 사용하는 php
    public static final String m_PHP_UPDATE = "/Update.php";
>>>>>>> f28117f2ff5e04bd56666073a1f82add690827e3

    // java.com.example.Activity.DrawTimeTable 에서 사용하는 상수 ============================================================= //
    public final static String[] m_DAY = {"월", "화", "수", "목", "금"};
    public final static String[] m_TIME = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
}
