package com.lovecws.mumu.mmsns.article.service.impl;

import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.article.dao.MMSnsArticleVoteDao;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleVoteEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
        return articleVoteDao.listPage(new PageParam(page, limit), paramMap);
    }
}
