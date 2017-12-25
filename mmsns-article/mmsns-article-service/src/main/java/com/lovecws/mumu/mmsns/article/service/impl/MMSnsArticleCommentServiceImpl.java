package com.lovecws.mumu.mmsns.article.service.impl;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.page.PageBean;
import com.lovecws.mumu.core.page.PageParam;
import com.lovecws.mumu.mmsns.article.dao.MMSnsArticleCommentDao;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCommentEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCommentService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章评论服务实现
 * @date 2017-12-18 17:25:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsArticleCommentServiceImpl implements MMSnsArticleCommentService {

    @Autowired
    private MMSnsArticleCommentDao articleCommentDao;
    @Autowired
    private MMSnsArticleService articleService;

    @Override
    public List<MMSnsArticleCommentEntity> listArticleCommentPage(int articleId, int page, int limit) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("articleId", articleId);
        PageParam pageParam = new PageParam(page, limit);
        paramMap.put("beginIndex", pageParam.getBeginIndex());
        paramMap.put("numPerPage", pageParam.getNumPerPage());
        return articleCommentDao.selectList("listPage", paramMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public MMSnsArticleCommentEntity publishArticleComment(MMSnsArticleCommentEntity articleCommentEntity) {
        //添加文章评论
        articleCommentEntity = articleCommentDao.insert(articleCommentEntity);

        //增加文章评论数量
        MMSnsArticleEntity articleEntity = new MMSnsArticleEntity();
        articleEntity.setArticleId(articleCommentEntity.getArticleId());
        articleEntity.setCommentCount(1);
        articleService.updateArticle(articleEntity);
        return articleCommentEntity;
    }

    @Override
    public MMSnsArticleCommentEntity getArticleCommentInfo(int commentId) {
        return articleCommentDao.getById(String.valueOf(commentId));
    }
}
