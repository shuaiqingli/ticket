package com.hengyu.ticket.control;

import com.hengyu.ticket.entity.IntegralProduct;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.IntegralService;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/admin")
public class IntegralControl {

    @Autowired
    private IntegralService integralService;

    @RequestMapping("integralProductList.do")
    public String integralProductList(Page page, String keyword, Model m) {
        m.addAttribute("integralProductList", integralService.findIntegralProductList(keyword, page, false));
        m.addAttribute("page", page);
        return "admin/integralProductList";
    }

    @RequestMapping("integralProductEditPage.do")
    public String integralProductEditPage(Integer id, Model m) {
        if (null != id) {
            m.addAttribute("integralProduct", integralService.findIntegralProduct(id, false));
        }
        return "admin/integralProductEdit";
    }

    @ResponseBody
    @RequestMapping("integralProductEdit.do")
    public String integralProductEdit(IntegralProduct integralProduct) {
        Assert.notNull(integralProduct, "商品信息不能为空");
        Assert.hasText(integralProduct.getName(), "商品名称不能为空");
        Assert.isTrue(integralProduct.getAmount() != null && integralProduct.getAmount() > 0, "无效积分");
        Assert.isTrue(CollectionUtil.contain(new Integer[]{1}, integralProduct.getType()), "无效商品类型");
        Assert.isTrue(integralProduct.getValidday() != null && integralProduct.getValidday() > 0, "无效有效天数");
        Assert.isTrue(integralProduct.getLowlimit() != null && integralProduct.getLowlimit().compareTo(new BigDecimal(0)) >= 0, "无效最低消费");
        Assert.isTrue(integralProduct.getVprice() != null && integralProduct.getVprice().compareTo(new BigDecimal(0)) > 0, "无效面值");
        Date startDate = DateUtil.stringToDate(integralProduct.getStartdate());
        Date endDate = DateUtil.stringToDate(integralProduct.getEnddate());
        Assert.isTrue(startDate != null, "无效生效时间");
        Assert.isTrue(endDate != null && !endDate.before(startDate), "无效失效时间");
        integralProduct.setStartdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getFirstDate(startDate)));
        integralProduct.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLatestDate(endDate)));
        Assert.isTrue(CollectionUtil.contain(new Integer[]{0, 1}, integralProduct.getStockflag()), "无效库存限制");
        Assert.isTrue(integralProduct.getStockflag().equals(0) || (integralProduct.getFixedstock() != null && integralProduct.getFixedstock() >= 0), "无效库存数量");
        /*Assert.hasText(integralProduct.getIconurl(), "商品图标不能为空");
        Assert.hasText(integralProduct.getMainurl(), "商品图片不能为空");
        Assert.hasText(integralProduct.getDesc(), "商品描述不能为空");*/
        if (integralProduct.getStockflag().equals(0)) {
            integralProduct.setFixedstock(0);
            integralProduct.setCurrentstock(0);
        } else {
            integralProduct.setCurrentstock(integralProduct.getFixedstock());
        }
        integralProduct.setStatus(1);
        if (null != integralProduct.getId()) {
            integralService.updateIntegralProduct(integralProduct);
        } else {
            integralProduct.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            integralService.addIntegralProduct(integralProduct);
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping("integralProductDel.do")
    public String integralProductDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        integralService.delIntegralProduct(id);
        return "1";
    }

    //商品上架
    @ResponseBody
    @RequestMapping("integralProductPuton.do")
    public String integralProductPuton(Integer id) {
        Assert.notNull(id, "ID不能为空");
        integralService.updateIntegralProductForPuton(id);
        return "1";
    }

    //商品下架
    @ResponseBody
    @RequestMapping("integralProductPutoff.do")
    public String integralProductPutoff(Integer id) {
        Assert.notNull(id, "ID不能为空");
        integralService.updateIntegralProductForPutoff(id);
        return "1";
    }
}
