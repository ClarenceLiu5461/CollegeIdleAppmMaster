package com.leaf.collegeidleapp.bean;

/**
 * 評論實體類
 */
public class Review {

    private String stuId;//用戶賬號
    private String currentTime;//當前時間
    private String content;//評論內容
    private Integer position;//商品項編號

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}


