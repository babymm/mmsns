package com.lovecws.mumu.mmsns.article.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.article.dao.MMSnsArticleCategoryDao;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCategoryEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCategoryService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章分类服务实现
 * @date 2017-12-18 17:25:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsArticleCategoryServiceImpl implements MMSnsArticleCategoryService {

    @Autowired
    private MMSnsArticleCategoryDao articleCategoryDao;
    @Autowired
    private MMSnsArticleService articleService;

    @Override
    public PageBean<MMSnsArticleCategoryEntity> getArticleCategoryPageBean(String categoryType, String userId, String systemUserId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("categoryType", categoryType);
        paramMap.put("userId", userId);
        paramMap.put("systemUserId", systemUserId);
        paramMap.put("categoryStatus", PublicEnum.NORMAL.value());
        return articleCategoryDao.listPage(new PageParam(page, limit), paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsArticleCategoryEntity addArticleCategory(MMSnsArticleCategoryEntity articleCategoryEntity) {
        return articleCategoryDao.insert(articleCategoryEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsArticleCategoryEntity updateArticleCategory(MMSnsArticleCategoryEntity articleCategoryEntity) {
        articleCategoryDao.update(articleCategoryEntity);
        return articleCategoryEntity;
    }

    @Override
    public MMSnsArticleCategoryEntity getArticleCategoryInfo(String categoryId) {
        return articleCategoryDao.getById(categoryId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteArticleCategory(String categoryId) {
        //将文章分类标记为已删除状态
        //articleCategoryDao.delete(categoryId);
        MMSnsArticleCategoryEntity articleCategoryEntity = new MMSnsArticleCategoryEntity();
        articleCategoryEntity.setCategoryId(Integer.parseInt(categoryId));
        articleCategoryEntity.setCategoryStatus(PublicEnum.DELETE.value());
        updateArticleCategory(articleCategoryEntity);

        //将用户文章分类下的所有文章标记为以删除状态
        MMSnsArticleEntity articleEntity = new MMSnsArticleEntity();
        articleEntity.setUserCategoryId(Integer.parseInt(categoryId));
        articleEntity.setArticleStatus(PublicEnum.DELETE.value());
        articleService.updateArticleByCategoryId(null,categoryId,PublicEnum.DELETE.value());
    }

    @Override
    public List<MMSnsArticleCategoryEntity> getArticleCategoryList(String categoryType, String userId, String systemUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("categoryType", categoryType);
        paramMap.put("userId", userId);
        paramMap.put("systemUserId", systemUserId);
        paramMap.put("categoryStatus", PublicEnum.NORMAL.value());
        return articleCategoryDao.selectList("listPage", paramMap);
    }
}
