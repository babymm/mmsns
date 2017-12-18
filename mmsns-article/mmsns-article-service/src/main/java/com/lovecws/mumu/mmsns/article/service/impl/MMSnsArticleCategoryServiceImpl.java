package com.lovecws.mumu.mmsns.article.service.impl;

import com.lovecws.mumu.mmsns.article.service.MMSnsArticleCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章分类服务实现
 * @date 2017-12-18 17:25:
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, readOnly = true)
public class MMSnsArticleCategoryServiceImpl implements MMSnsArticleCategoryService {
}
