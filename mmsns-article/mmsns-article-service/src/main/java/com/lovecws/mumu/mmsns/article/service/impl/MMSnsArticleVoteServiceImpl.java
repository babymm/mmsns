package com.lovecws.mumu.mmsns.article.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.article.dao.MMSnsArticleVoteDao;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCollectEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleVoteEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleVoteService;
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
 * @Description: 文章点赞服务实现
 * @date 2017-12-18 17:25:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsArticleVoteServiceImpl implements MMSnsArticleVoteService {

    @Autowired
    private MMSnsArticleVoteDao articleVoteDao;
    @Autowired
    private MMSnsArticleService articleService;

    @Override
    public int getVoteArticleCountByCondition(String sessionUserId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("voteUserId", sessionUserId);
        return articleVoteDao.listPageCount(paramMap);
    }

    @Override
    public PageBean<MMSnsArticleVoteEntity> listVoteArticlePage(String sessionUserId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("voteUserId", sessionUserId);
        paramMap.put("voteStatus", PublicEnum.NORMAL.value());
        return articleVoteDao.listPage(new PageParam(page, limit), paramMap);
    }

    @Override
    public List<MMSnsArticleVoteEntity> getArticleVoteByCondition(int articleId, Integer userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("articleId", articleId);
        paramMap.put("voteUserId", userId);
        paramMap.put("voteStatus", PublicEnum.NORMAL.value());
        return articleVoteDao.listByColumn(paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsArticleVoteEntity voteArticle(MMSnsArticleVoteEntity articleVoteEntity) {
        //保存文章点赞信息
        articleVoteEntity = articleVoteDao.insert(articleVoteEntity);

        //更新文章点赞量
        MMSnsArticleEntity articleEntity = new MMSnsArticleEntity();
        articleEntity.setArticleId(articleVoteEntity.getArticleId());
        articleEntity.setVoteCount(1);
        articleService.updateArticle(articleEntity);
        return articleVoteEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cancelArticleVote(MMSnsArticleVoteEntity articleVoteEntity) {
        //删除文章点赞信息
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("articleId", articleVoteEntity.getArticleId());
        paramMap.put("voteUserId", articleVoteEntity.getVoteUserId());
        articleVoteDao.delete(paramMap);

        //更新文章点赞量
        MMSnsArticleEntity articleEntity = new MMSnsArticleEntity();
        articleEntity.setArticleId(articleVoteEntity.getArticleId());
        articleEntity.setVoteCount(-1);
        articleService.updateArticle(articleEntity);
    }
}
