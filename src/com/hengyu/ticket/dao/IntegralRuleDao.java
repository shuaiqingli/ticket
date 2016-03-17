package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.IntegralRule;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2015-12-15.
 */
public interface IntegralRuleDao {

    Long findIntegralRuleTotal(@Param("keyword") String keyword);

    List<Map> findIntegralRuleList(@Param("keyword") String keyword, @Param("page") Page page);

    IntegralRule findIntegralRule(@Param("id") Integer id);

    List<Map> findLineListForIntegralRule(@Param("id") Integer id);

    int bindLineToIntegralRule(@Param("lmid") Integer lmid, @Param("id") Integer id);

    int unbindLineToIntegralRule(@Param("lmid") Integer lmid, @Param("id") Integer id);

    int unbindAllLineToIntegralRule(@Param("id") Integer id);

    int saveIntegralRule(IntegralRule integralRule);

    int updateIntegralRule(IntegralRule integralRule);

    int delIntegralRule(@Param("id") Integer id);

}
