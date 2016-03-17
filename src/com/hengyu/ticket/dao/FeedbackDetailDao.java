package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.FeedbackDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface FeedbackDetailDao {

    List<FeedbackDetail> findFeedbackDetailList(@Param("feedbackid") Integer feedbackid);

    int findFeedbackDetailMaxIndex(@Param("feedbackid") Integer feedbackid);

    int saveFeedbackDetail(FeedbackDetail feedbackDetail);

    int delAllFeedbackDetail(@Param("feedbackid") Integer feedbackid);

    int delFeedbackDetail(@Param("id") Integer id);
}
