package com.lovecws.mumu.mmsns.article.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章分类
 * @date 2017-12-18 16:24:
 * mb_article_category
 */
public class MMSnsArticleCategoryEntity extends PersistentEntity {

    public static final String ARTICLE_CATEGORY_TYPE_USER = "user";
    public static final String ARTICLE_CATEGORY_TYPE_SYSTEM = "system";

    private int categoryId;//分类id
    private String categoryStatus;//分类状态
    private Date categoryDate;//创建时间

    private String categoryType;//分类类型 user、system
    private int userId;//创建分类的用户id
    private int systemUserId;//如果是system分类类型，则是系统用户的用户id

    private String categoryName;//分类名称
    private String categoryIcon;//分类icon
    private String categoryDesc;//分类描述
    private int categoryOrder;//分类排序
    private int articleCount;//分类下文章数量

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public Date getCategoryDate() {
        return categoryDate;
    }

    public void setCategoryDate(Date categoryDate) {
        this.categoryDate = categoryDate;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(int systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }
}
