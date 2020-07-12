package com.example.bean;

/**
 * @auther fred
 * @create 2020-05-29 10:15
 */
public class UserDetail {
    private int userId;
    private String nickname;
    private String introduction;
    private int blogNum;
    //因为时被点赞 和被收藏
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

    public void setAll(UserDetail userDetail){
        this.userId = userDetail.userId;
        this.nickname = userDetail.nickname;
        this.introduction = userDetail.introduction;
        this.blogNum = userDetail.blogNum;
        this.getLikeNum = userDetail.getLikeNum;
        this.getMarkNum = userDetail.getMarkNum;
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
