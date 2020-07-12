package com.fred.bean;

/**
 * @auther fred
 * @create 2020-05-29 10:15
 */
public class UserDetail {
    private int userId;
    private String nickname;
    private String introduction;
    private int blogNum;
    private int getLikeNum;
    private int getMarkNum;


    public UserDetail() {
    }

    public UserDetail(int userId, int blogNum, String nickname) {
        this.userId = userId;
        this.blogNum = blogNum;
        this.nickname = nickname;
    }

    public UserDetail(int userId, String nickname, String introduction, int blogNum, int getLikeNum, int getMarkNum) {
        this.userId = userId;
        this.nickname = nickname;
        this.introduction = introduction;
        this.blogNum = blogNum;
        this.getLikeNum = getLikeNum;
        this.getMarkNum = getMarkNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(int blogNum) {
        this.blogNum = blogNum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getGetLikeNum() {
        return getLikeNum;
    }

    public void setGetLikeNum(int getLikeNum) {
        this.getLikeNum = getLikeNum;
    }

    public int getGetMarkNum() {
        return getMarkNum;
    }

    public void setGetMarkNum(int getMarkNum) {
        this.getMarkNum = getMarkNum;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "userId=" + userId +
                ", blogNum=" + blogNum +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
