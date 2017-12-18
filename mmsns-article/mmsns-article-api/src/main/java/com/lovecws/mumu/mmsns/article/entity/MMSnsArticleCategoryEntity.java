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

    private int categoryType;//分类类型 user、system
    private int userId;//创建分类的用户id
    private int systemUserId;//如果是system分类类型，则是系统用户的用户id

    private String categoryName;//分类名称
    private String categoryDesc;//分类描述
    private int categoryOrder;//分类排序
    private int articleCount;//分类下文章数量
}
