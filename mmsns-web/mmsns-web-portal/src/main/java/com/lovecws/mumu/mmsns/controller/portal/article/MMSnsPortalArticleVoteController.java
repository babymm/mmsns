package com.lovecws.mumu.mmsns.controller.portal.article;

import com.lovecws.mumu.core.enums.PublicEnum;
import com.lovecws.mumu.core.response.ResponseEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleCollectEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleEntity;
import com.lovecws.mumu.mmsns.article.entity.MMSnsArticleVoteEntity;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCollectService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleService;
import com.lovecws.mumu.mmsns.article.service.MMSnsArticleVoteService;
import com.lovecws.mumu.mmsns.common.user.entity.MMSnsCommonUserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章点赞
 * @date 2017-12-25 11:17:
 */
@Controller
@RequestMapping("/article/vote")
public class MMSnsPortalArticleVoteController {

    @Autowired(required = false)
    private MMSnsArticleVoteService articleVoteService;
    @Autowired(required = false)
    private MMSnsArticleService articleService;

    /**
     * 用户对文章进行点赞
     *
     * @param articleId 文章id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity articleVote(int articleId) {
        MMSnsArticleEntity articleInfo = articleService.getArticleSimpleInfo(String.valueOf(articleId));
        if (articleInfo == null) {
            return new ResponseEntity(404, "parameter error", "文章不存在");
        }
        MMSnsCommonUserEntity sessionCommonUser = (MMSnsCommonUserEntity) SecurityUtils.getSubject().getSession().getAttribute(MMSnsCommonUserEntity.MMSNS_COMMON_USER);
        Integer sessionCommonUserUserId = sessionCommonUser.getUserId();
        if (articleInfo.getUserId() == sessionCommonUserUserId.intValue()) {
            return new ResponseEntity(404, "parameter error", "不能点赞自己的文章");
        }
        List<MMSnsArticleVoteEntity> articleVotes = articleVoteService.getArticleVoteByCondition(articleId, sessionCommonUserUserId);
        if (articleVotes != null && articleVotes.size() > 0) {
            return new ResponseEntity(200, "success", "已点赞");
        }
        MMSnsArticleVoteEntity articleVoteEntity = new MMSnsArticleVoteEntity();
        articleVoteEntity.setArticleId(articleId);
        articleVoteEntity.setVoteUserId(sessionCommonUserUserId);
        articleVoteEntity.setVoteStatus(PublicEnum.NORMAL.value());
        articleVoteEntity.setVoteDate(new Date());
        articleVoteEntity.setArticleUserId(articleInfo.getUserId());
        articleVoteEntity = articleVoteService.voteArticle(articleVoteEntity);
        return new ResponseEntity(200, "success", "点赞成功");
    }
}
