package com.lovecws.mumu.mmsns.article.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章实体对象
 * @date 2017-12-18 16:25:
 * mb_article
 */
public class MMSnsArticleEntity extends PersistentEntity {

    public static final String ARTICLE_TYPE_ORIGINAL = "original";
    public static final String ARTICLE_TYPE_REPRINT = "reprint";
    private int articleId;
    private String articleStatus;
    private Date articleDate;

    private String articleTitle;
    private String articleSumary;
    private String articleContent;
    private String articleLogo;
    private String articleLabels;
    private int wordCount;

    private int sysCategoryId;
    private int userCategoryId;

    private int readCount;
    private int collectCount;
    private int voteCount;
    private int relayCount;
    private int commentCount;

    private String articleType;//原创 original、转载 reprint
    private int userId;//用户id

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleSumary() {
        return articleSumary;
    }

    public void setArticleSumary(String articleSumary) {
        this.articleSumary = articleSumary;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleLogo() {
        return articleLogo;
    }

    public void setArticleLogo(String articleLogo) {
        this.articleLogo = articleLogo;
    }

    public String getArticleLabels() {
        return articleLabels;
    }

    public void setArticleLabels(String articleLabels) {
        this.articleLabels = articleLabels;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSysCategoryId() {
        return sysCategoryId;
    }

    public void setSysCategoryId(int sysCategoryId) {
        this.sysCategoryId = sysCategoryId;
    }

    public int getUserCategoryId() {
        return userCategoryId;
    }

    public void setUserCategoryId(int userCategoryId) {
        this.userCategoryId = userCategoryId;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getRelayCount() {
        return relayCount;
    }

    public void setRelayCount(int relayCount) {
        this.relayCount = relayCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int articleCount;
    private String categoryName;
    private String categoryId;

    private String userName;
    private String company;
    private String positional;
    private String individuation;
    private int userFansCount;
    private int userScore;
    private int userAccessCount;
    private String avator;

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPositional() {
        return positional;
    }

    public void setPositional(String positional) {
        this.positional = positional;
    }

    public String getIndividuation() {
        return individuation;
    }

    public void setIndividuation(String individuation) {
        this.individuation = individuation;
    }

    public int getUserFansCount() {
        return userFansCount;
    }

    public void setUserFansCount(int userFansCount) {
        this.userFansCount = userFansCount;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserAccessCount() {
        return userAccessCount;
    }

    public void setUserAccessCount(int userAccessCount) {
        this.userAccessCount = userAccessCount;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }
}
