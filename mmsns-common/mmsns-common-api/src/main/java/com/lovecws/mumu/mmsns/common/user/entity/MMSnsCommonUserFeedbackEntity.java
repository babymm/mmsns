package com.lovecws.mumu.mmsns.common.user.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 用户反馈
 * @date 2017-11-24 10:59
 * mc_user_feedback
 */
public class MMSnsCommonUserFeedbackEntity extends PersistentEntity {

    public static final String FEEDBACK_UNSOLVED = "unsolved";//未解决
    public static final String FEEDBACK_SOLVED = "solved";//已解决

    private Integer feedbackId;//反馈id
    private String feedbackStatus;//反馈状态
    private String createDate;//反馈时间
    private String resolveDate;//问题解决时间

    private String feedbackTitle;//反馈标题
    private String feedbackContent;//反馈内容

    private String feedbackImage;//反馈图片
    private Integer feedbackType;//用户反馈类型（1用户提交反馈 2系统反馈回复）
    private String feedbackDevice;//设备类型

    private Integer userId;//app用户id
}
