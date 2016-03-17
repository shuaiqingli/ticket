package com.hengyu.ticket.control;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineScheduDetail;
import com.hengyu.ticket.entity.LineSchedueRule;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.LineScheduDetailService;
import com.hengyu.ticket.service.LineSchedueRuleService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;

@Controller
@RequestMapping("/user")
public class LineScheduleRuleControl {

	//排班规则
	@Autowired
	private LineSchedueRuleService lsrs;
	//线路管理
	@Autowired
	private LineManageService lms;
	@Autowired
	//排班详情
	private LineScheduDetailService lsds;
	
	
	
	@ModelAttribute
	public void before(Integer id,Model m) throws Exception{
		if(id!=null){
			LineSchedueRule lsr = lsrs.find(id);
			if(lsr!=null){
				m.addAttribute(m);
			}
		}
		
	}
	
	//查询排班规则，星期
	@RequestMapping("findweekScheduleRule")
	public void findweekScheduleRule(LineSchedueRule lsr,Writer w) throws Exception{
		 Map<String,Integer> map = lsrs.findweekScheduleRule(lsr);
		if(lsr!=null){
			APIUtil.toJSONString(map, w);
		}
	}
	
	
	//保存或修改班次规则
	@RequestMapping("editScheduleRule")
	public void editScheduleRule(@Validated LineSchedueRule lsr,BindingResult br,Integer lmid,Writer w) throws Exception{
		if(br.hasErrors()&&lsr.getId()==null){
			APIUtil.toJSONString("参数传输错误，请稍后再试！", w);
			return;
		}
		int result = -1;
		//save
		if(lsr.getId()==null){
			if(lmid==null){
				APIUtil.toJSONString("参数传输错误，请稍后再试！", w);
				return;
			}
			LineManage lm = lms.find(lmid);
			result = lsrs.save(lsr,lm);
		}else{
			//----------------- 时间验证
			List<LineScheduDetail> lsd = lsr.getLinescheduledetail();
			if(lsd!=null&&lsr.getWeekday()!=0){
				lsr.getLinescheduledetail();
				for (int i = 0; i < lsd.size(); i++) {
					if(i>=1){
						LineScheduDetail d1 = lsd.get(i-1);
						LineScheduDetail d2 = lsd.get(i);
						if((d1.getIsdel()!=null&&d1.getIsdel()==1)||(d2.getIsdel()!=null&&d2.getIsdel()==1)){
							continue;
						}
						SimpleDateFormat tf = DateHanlder.getTimeFromat();
						long time1 = tf.parse(d1.getStarttime()).getTime();
						long time2 = tf.parse(d2.getStarttime()).getTime();
						if(time2<=time1){
							String msg = "班车号："+d2.getShiftcode()+"的发车时间不能小于等于，班车号："+d1.getShiftcode()+"发车时间！";
							APIUtil.toJSONString(msg, w);
							return;
						}
					}
				}
			}
			//----------------------------------
			result = lsrs.update(lsr);
		}
		APIUtil.toJSONString(result, w);
	}
	
	//查询排班详情
	@RequestMapping("findScheduleDetail")
	public void findScheduleDetail(Integer lsrid,Writer w) throws Exception{
		List<LineScheduDetail> list = lsds.findByLineScheduleRule(lsrid);
		APIUtil.toJSONString(list, w);
	}
	
	//查询排班规则
	@RequestMapping("findScheduleRule")
	public void findScheduleRule(Integer ruleid ,Writer w) throws Exception{
		LineSchedueRule ls = lsrs.find(ruleid);
		List<LineScheduDetail> linescheduledetail = lsds.findByLineScheduleRule(ruleid);
		ls.setLinescheduledetail(linescheduledetail );
		APIUtil.toJSONString(ls, w);
	}
	
	
	
}
