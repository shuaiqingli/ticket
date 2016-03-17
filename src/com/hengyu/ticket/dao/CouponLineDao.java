package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.CouponLine;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface CouponLineDao {

    int saveCouponLine(CouponLine couponLine);

    List<CouponLine> findCouponLineList(@Param("keyword") String keyword, @Param("page") Page page);

    long findCouponLineTotal(@Param("keyword") String keyword);

    BigDecimal findCouponPriceTotalForAdmin(@Param("userid") String userid);

    BigDecimal findCouponPriceTotalForCustomer(@Param("cid") String cid);
}
