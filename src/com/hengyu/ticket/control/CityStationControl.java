package com.hengyu.ticket.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hengyu.ticket.util.CollectionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.CityStationService;

@Controller
@RequestMapping("/admin")
public class CityStationControl {
	
	//城市站点管理
	@Autowired
	private CityStationService ccs;
	
	@ModelAttribute
	public void before(Model m,CityStation cs) throws Exception{
		if(cs!=null&&cs.getId()!=null){
			CityStation c = ccs.find(cs.getId());
			if(c!=null){
				m.addAttribute(c);
			}else{
				m.addAttribute("isnull", true);
			}
		}
	}
	
	//城市列表
	@RequestMapping("citystationlist.do")
	public String cityStationList(CityStation cs,Page page,Model m,Integer isstation) throws Exception{
		page.setParam(cs);
		ccs.findList(page);
		m.addAttribute(page);
		editStation(m);
		if(isstation==null){
			cs.setIshot(null);
			return "admin/citystationlist";
		}else{
			return "admin/stationlist";
		}
	}
	
	//编辑城市页面
	@RequestMapping(value={"cityedit.do"})
	public String editCity(Model m){
		Assert.isTrue(!m.containsAttribute("isnull"), Const.HTTP_MSG_NOT_FOUND);
		return "admin/cityedit";
	}
	
	//编辑站点页面
	@RequestMapping(value={"stationedit.do"})
	public String editStation(Model m) throws Exception{
		Assert.isTrue(!m.containsAttribute("isnull"), Const.HTTP_MSG_NOT_FOUND);
		List<CityStation> list = ccs.findAllCity();
		if(list!=null){
			m.addAttribute("citys",list);
		}
		return "admin/stationedit";
	}
	
	//添加或修改城市/站点 -1 城市存在 400 操作失败 200 成功
	@RequestMapping(value={"citystationedit.do"})
	@ResponseBody
	public Object editCityStation(CityStation cityStation,Model m) throws Exception{
		cityStation.setStpinyin(PinyinHelper.getShortPinyin(cityStation.getCityname())+PinyinHelper.convertToPinyinString(cityStation.getCityname(), "", PinyinFormat.WITHOUT_TONE));
		Assert.isTrue(cityStation!=null,"服务器异常！");
		if(StringUtils.isNotEmpty(cityStation.getParentid())){
			Assert.isTrue(cityStation.getTicketpercent()>=0&&cityStation.getTicketpercent()<=100,"百分比填写不正确！");
			Assert.isTrue(cityStation.getCouponticketpercent()>=0&&cityStation.getCouponticketpercent()<=100,"百分比填写不正确！");
			Assert.isTrue(cityStation.getCouponpercent()>=0&&cityStation.getCouponpercent()<=100,"百分比填写不正确！");
		}
		
		//更新操作
		if(cityStation!=null&&StringUtils.isNotEmpty(cityStation.getId())){
			ccs.update(cityStation);
		}
		//添加操作
		else if(cityStation!=null&&!StringUtils.isNotEmpty(cityStation.getId())){
			int ret = ccs.save(cityStation);
			if(ret==-1){
				return "-1";
			}
		}else{
			return "400";
		}
		return "200";
	}

	//站点选取页
	@RequestMapping(value = "selectStation.do")
	public String selectStation(Page page, String userid, String cityid, String keyword, Model m) throws Exception {
		Assert.hasText(userid, "管理员不能为空");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("cityid", StringUtils.isBlank(cityid) ? null : cityid);
		params.put("keyword", StringUtils.isBlank(keyword) ? null : keyword);
		page.setParam(params);
		page.setData(ccs.findListForBindAdmin(page));
		m.addAttribute("cityList", ccs.findAllCity());
		m.addAttribute(page);
		return "admin/selectStation";
	}
}
