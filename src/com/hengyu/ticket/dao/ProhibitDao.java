package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Prohibit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface ProhibitDao {

    Prohibit findProhibit(@Param("id") Integer id);

    List<Prohibit> findProhibitList(@Param("stid") String stid, @Param("page") Page page);

    long findProhibitTotal(@Param("stid") String stid);

    int saveProhibit(Prohibit prohibit);

    int updateProhibit(Prohibit prohibit);

    int delProhibit(@Param("id") Integer id);

    List<Map> findProhibitTimeList(@Param("ptid") Integer ptid);

    int bindTimeToProhibit(@Param("ptid") Integer ptid, @Param("begintime") String begintime, @Param("endtime") String endtime);

    int unbindTimeToProhibit(@Param("id") Integer id);

    int unbindAllTimeToProhibit(@Param("ptid") Integer ptid);

    List<Map> findProhibitLineList(@Param("ptid") Integer ptid);

    int checkForBindLineToProhibit(@Param("ptid") Integer ptid, @Param("lmid") Integer lmid);

    int bindLineToProhibit(@Param("ptid") Integer ptid, @Param("lmid") Integer lmid);

    int unbindLineToProhibit(@Param("id") Integer id);

    int unbindAllLineToProhibit(@Param("ptid") Integer ptid);

    List<Prohibit> findProhibitListForApply(@Param("id") Integer id);

    int updateForApplyProhibit(@Param("stid") String stid, @Param("timeList") List<Map> timeList, @Param("lmidList") List<Integer> lmidList, @Param("dateList") List<String> dateList);

    List<Map> findProhibitTicketLineList(@Param("startStationIDList") List<String> startStationIDList, @Param("arriveStationIDList") List<String> arriveStationIDList, @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("keywordList") List<String> keywordList, @Param("page") Page page);

    long findProhibitTicketLineTotal(@Param("startStationIDList") List<String> startStationIDList, @Param("arriveStationIDList") List<String> arriveStationIDList, @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("keywordList") List<String> keywordList);

    int openTicketLineList(@Param("ticketLineIDList") List<Integer> ticketLineIDList);
}
