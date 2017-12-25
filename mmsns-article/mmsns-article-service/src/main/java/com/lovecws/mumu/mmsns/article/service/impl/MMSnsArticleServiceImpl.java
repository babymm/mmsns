package com.lovecws.mumu.mmsns.article.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.article.dao.MMSnsArticleDao;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCategoryEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
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
 * @Description: 文章服务实现
 * @date 2017-12-18 17:25:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsArticleServiceImpl implements MMSnsArticleService {

    @Autowired
    private MMSnsArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsArticleEntity addArticle(MMSnsArticleEntity articleEntity) {
        //保存文章
        articleEntity = articleDao.insert(articleEntity);
        return articleEntity;
    }

    @Override
    public List<MMSnsArticleEntity> groupArticleCountByUserId(String userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("articleStatus", PublicEnum.NORMAL.value());
        return articleDao.selectList("groupArticleCountByUserId", paramMap);
    }

    @Override
    public List<MMSnsArticleEntity> groupArticleCountByCategory(String sessionUserId, String articleType) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", sessionUserId);
        paramMap.put("articleType", articleType);
        paramMap.put("articleStatus", PublicEnum.NORMAL.value());
        paramMap.put("categoryUserId", sessionUserId);
        paramMap.put("categoryStatus", PublicEnum.NORMAL.value());
        return articleDao.selectList("groupArticleCountByCategory", paramMap);
    }

    @Override
    public PageBean<MMSnsArticleEntity> listArticlePage(String sessionUserId, String articleType, String userCategoryId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", sessionUserId);
        paramMap.put("articleType", articleType);
        paramMap.put("articleStatus", PublicEnum.NORMAL.value());
        paramMap.put("userCategoryId", userCategoryId);
        return articleDao.listPage(new PageParam(page, limit), paramMap);
    }

    @Override
    public MMSnsArticleEntity getArticleInfo(String articleId) {
        return articleDao.getById(articleId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsArticleEntity updateArticle(MMSnsArticleEntity articleEntity) {
        articleDao.update(articleEntity);
        return articleEntity;
    }
}
