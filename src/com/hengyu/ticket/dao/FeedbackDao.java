package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Feedback;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface FeedbackDao {
	Feedback findFeedback(@Param("id") Integer id);

	List<Feedback> findFeedbackList(@Param("feedback") Feedback feedback, @Param("page") Page page);

	long findFeedbackTotal(@Param("feedback") Feedback feedback);

	int saveFeedback(Feedback feedback);

	int updateFeedback(Feedback feedback);

	int delFeedback(@Param("id") Integer id);

	int findFeedbackStatusForCustomer(@Param("cid") String cid);
	
	int getAllFeedback();
}
