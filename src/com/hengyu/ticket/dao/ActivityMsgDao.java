package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.ActivityMsg;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface ActivityMsgDao {
    ActivityMsg findActivityMsg(@Param("id") Integer id);

    List<ActivityMsg> findActivityMsgList(@Param("keyword") String keyword, @Param("amsort") Integer amsort, @Param("page") Page page);

    long findActivityMsgTotal(@Param("keyword") String keyword, @Param("amsort") Integer amsort);

    int saveActivityMsg(ActivityMsg activityMsg);

    int updateActivityMsg(ActivityMsg activityMsg);

    int delActivityMsg(@Param("id") Integer id);

    List<ActivityMsg> findAvailableActivityMsgList(@Param("type") Integer type);

    ActivityMsg findAvailableActivityMsg(@Param("id") Integer id);

    Integer findActivityMsgStatus(@Param("amsort") Integer amsort, @Param("idList") String[] idList);
}
