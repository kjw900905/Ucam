package com.example.Beans;

public class Variable {
    // php 주소 구성 : "http://(서버 주소)/(php 파일명 + 확장자명)"
    // php 주소 형식 : "http://xxx.xxx.xxx.xxx/xxxxx.php"
    // php 주소 예시 : "http://221.148.86.18/SelectAll.php"
    private static final String m_SERVER_URL = "http://117.17.158.173";
    private static final String m_SELECTONE_SEARCH_SCHOOL_PHP = "/SelectOne_search_school.php";
    private static final String m_INSERT_PERSONAL_INFORMATION_PHP = "/Insert_personal_information.php";
    private static final String m_UPDATE_SEARCH_PHP = "/Update_Search.php"; // MatchFragment에서 매치에 사용하는 php
    
    public Variable() {
        // null
    }

    public String getSERVER_URL() {
        return m_SERVER_URL;
    }

    public String getSELECTONE_SEARCH_SCHOOL_PHP() {
        return m_SELECTONE_SEARCH_SCHOOL_PHP;
    }

    public String getINSERT_PERSONAL_INFORMATION_PHP() {
        return m_INSERT_PERSONAL_INFORMATION_PHP;
    }

    public String getUPDATE_SEARCH_PHP() {
        return m_UPDATE_SEARCH_PHP;
    }
}
