package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.IntegralProduct;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface IntegralProductDao {
    IntegralProduct findIntegralProduct(@Param("id") Integer id, @Param("available") boolean available);

    List<IntegralProduct> findIntegralProductList(@Param("keyword") String keyword, @Param("page") Page page, @Param("available") boolean available);

    long findIntegralProductTotal(@Param("keyword") String keyword, @Param("available") boolean available);

    int saveIntegralProduct(IntegralProduct integralProduct);

    int updateIntegralProduct(IntegralProduct integralProduct);

    int updateIntegralProductStock(@Param("count") Integer count, @Param("id") Integer id);

    int delIntegralProduct(@Param("id") Integer id);
}
