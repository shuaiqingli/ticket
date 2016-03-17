<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<title>排班规则</title>
	<style type="text/css">
		.datetimepicker table tr td.disabled{
			color:red;
		}
		.datetimepicker table tr td.disabled:hover{
			background: #ccc;
		}
		.datetimepicker td, .datetimepicker th{
			color:green;
		}
		.shift{
			color:#000;
		}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="javascript:void(0)" method="get">
			<input type="hidden" name="lmid" value="${lm.id }"/>
			<div >
				<a id="main_page" href="${basePath}/user/approveLineScheduleList?id=${lm.id}" style="color: black;border: none;"></a>
				<h2 onclick="location.href='${basePath}/user/approveLineScheduleList?id=${lm.id}">
				<span style="color: #000;">
				</span>排班规则</h2>
			</div>
			<div class="row" style="margin-bottom:2em;line-height:4em;">
				<div class="span6" style="font: normal 20px '黑体';margin-top: 20px;">
					&nbsp;${lm.lineID }（ ${lm.cityStartName }${lm.STStartName } - ${lm.cityArriveName }${lm.STArriveName } ）
				</div>
				<div class="span6 pull-right">
					<button class="btn pull-right btn_addschedule" style="margin-right:8px;margin-left: 10px;height: 38px;font-size: 20px;font-family:黑体" data-toggle="modal" data-target="#myModal">新增排班</button>
				</div>
			</div>
		<hr/>
		<c:forEach var="o" items="${page.data }">
			<div class="schedulelist">
			<div class="row" style="margin-bottom:15px;">
				<div class="span5" style="font: normal 18px '黑体';margin-top: 0px;">
					<img alt="menu_icon" style="height:20px;float:left;margin-top:4px;" src="${basePath}/images/menu_icon.png">&nbsp;&nbsp;${o.scheduname }<span style="color: #ff681c;">（${o.today }至${o.enddate }）</span>
					<div class="" style="display:inline-block;margin-left:0px;vertical-align:middle;">
						<button class="btn pull-left updateDate" style="margin-left: 0px;height: 30px;font-size: 14px;">修改时间</button>
						<span style="display: none;">${o.scheduname }*${o.begindate }*${o.enddate}*${o.id}</span>
					</div>
				</div>
				<div class="span6 pull-right">
					<c:if test="${o.weekday == 0 }">
						<button class="btn pull-right" style="margin-right:8px;margin-left: 10px;height: 35px;font-size: 14px;font-family:黑体;vertical-align:middle;" disabled="disabled">新建规则</button>
					</c:if>
					<c:if test="${o.weekday != 0 }">
						<button class="btn pull-right newrule" style="margin-right:8px;margin-left: 10px;height: 35px;font-size: 14px;font-family:黑体;vertical-align:middle;">新建规则</button>
						<input class="id" type="hidden" value="${o.id}"/>
					</c:if>
					<c:if test="${not empty o.weekdaystr }">
						<div class="pull-right" style="color: red;margin-top: 7px;">[ 未设置 周${o.weekdaystr } ]</div>
					</c:if>
				</div>
			</div>
			<hr/>
			<c:if test="${empty o.ruleList or o.ruleList.size()==0}">
			</c:if>
		<c:forEach var="rule" items="${o.ruleList }">
			<table class="table table-striped" style="font-size: 14px;border-bottom:0px solid #ccc;">
				<thead>
					<tr>
					</tr>
				</thead>
				<tbody>
						<c:if test="${not empty  rule.weekdaystr  and rule.isshift == 1  and rule.weekday != 0}">
							<tr class="ood" style="border: none;">
									<td style="width: 10px;border: none;background: none;">&nbsp;</td>
									<td class="span12 p" style="border-bottom: solid 1px #ccc;border-top: none;" >
										<div class="info-box span9" style="display:inline-block;" iseffect="${o.iseffect }">
											<input type="hidden" class="ruleid" value="${rule.id }">
											<span class="info-box" style="text-indent:1em;line-height:2em;display:inline-block;padding-bottom:1em;float:left;">
												${rule.shiftnum}班 （应用于周 ${rule.weekdaystr}）
											</span>
											<div class="hided" style="float:left;"></div>
										</div>
										<i style="display:inline-block;float:right;">
											<button class="btn pull-right btn_editScheduleRule" id="${rule.id}" lsid="${o.id}" style="margin-left: 10px;height: 30px;font-size: 14px;">编辑规则</button>
										</i>
										<div class="clear"></div>
										<div class="l-info" style="display:none;border-top:1px solid #ccc;">
											<div class="fl num-list">
												<div>
													<div class="num">班次号</div>
													<div class="time">发车时间</div>
												</div>
												<div class="shift_template" style="display: none;">
													<div class="n-cell shiftcode"></div>
													<div class="t-cell starttime" style="padding-left: 15px;"></div>
												</div>
											</div>
											<div class="clear"></div>
										</div>
									</td>
							</tr>
						</c:if>
						<c:if test="${not empty  rule.weekdaystr and rule.isshift == 0 and rule.weekday != 0}">
							<tr style="border: none;">
							<td style="width: 10px;border: none;background: none;">&nbsp;</td>
							<td class="span12 p" style="border-bottom: solid 1px #ccc;border-top: none; ">
								<div class="info-box span9" style="display:inline-block;">
									<span class="info-box" style="text-indent:1em;line-height:2em;display:inline-block;padding-bottom:1em;float:left;">
										无排班 （应用于周 ${rule.weekdaystr}）
									</span>
								</div>
								<i style="display:inline-block;float:right;">
									<button class="btn pull-right btn_editScheduleRule" id="${rule.id}" lsid="${o.id}" style="margin-left: 10px;height: 30px;font-size: 14px;">编辑规则</button>
								</i>
								<div class="clear"></div>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</c:forEach>
<!-- 		<div style="text-align: center;"> -->
<%-- 			<input type="button" value="保存" class="btn span3 btn-success effect" style="height: 35px;" id="${o.id}"/> --%>
<!-- 		</div> -->
<!-- 		<hr/> -->
		</div>
		</c:forEach>
	</form>
	<form action="${basePath }/user/lineScheduleRuleList" method="get">
		<input type="hidden" name="lmid" value="${lm.id }"/>
		<%@include file="../common/page.jsp"%>
	</form>
	</div>
	<%@include file="../common/footer.jsp" %>
	
	<!-- Modal -->
<div id="myModal"  class="modal hide fade addschedule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 300px;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">排班</h3>
  </div>
  <div class="modal-body schedule_params" style="font: normal 17px '黑体';">
		<input  name="id" style="width: 150px;" type="hidden"/>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
  			<span>排班名称：</span>
			<input class="notnull" type="text" name="scheduname" style="width: 150px;" placeholder="排班名称"/>

  		</div>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
  			<span>生效日期：</span>
			<input class="date begindate notnull" type="text" name="begindate" readonly="readonly" style="width: 150px;" placeholder="生效日期"/>
			<span>失效效日期：</span>
			<input class="date enddate notnull" type="text" name="enddate" readonly="readonly" style="width: 150px;" placeholder="失效日期"/>
  		</div>
  		<br/>
  		<div class="row" style="text-align: center;">
  				<input value="确定"  type="button" class="btn btn-success save addschedule_save" style="height:40px;width: 15%;font-size: 18px;"/>
  				<input value="取消" data-dismiss="modal"  type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;"/>
  		</div>
  </div>
<!--   <div class="modal-footer"> -->
<!--     <button class="btn  btn-primary chooseLineManage" data-dismiss="modal" aria-hidden="true">确定</button> -->
<!--   </div> -->
</div>


	<!-- Modal -->
<div id="addScheduleRule"  class="addschedule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 100%;height: 100%;display: none;">
  <div class="modal-header">
<!--     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
    <h3 id="myModalLabel">规则</h3>
  </div>
  <div class="addScheduleRule" style="font: normal 17px '黑体';">
		
		<div class="row shift" style="margin-top: 15px;margin-left: 10px;">
			<span class="span1">应用于：</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="1" class="w1"><span> 周一</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="2" class="w2"><span> 周二</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="4" class="w4"><span> 周三</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="8" class="w8"><span> 周四</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="16" class="w16"><span> 周五</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="32" class="w32"><span> 周六</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="64" class="w64"><span> 周日</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" class="allchoose" class="w1"><span> 全选</span>
		</div>
		<hr/>
		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
			<input  name="lsid" type="hidden" class="lsid"/>
			<input  name="id"  type="hidden" class="id"/>
			<div class="span2">
	  			<span>首班发车时间：</span>
			</div>
			<div class="span3">
				<input class="notnull first_time" type="text" name="firststarttime" readonly="readonly" style="width: 150px;" placeholder="首班发车时间" value="00:00"/>
			</div>

  		</div>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
			<div class="span2">
	  			<span>末班发车时间：</span>
			</div>
			<div class="span3">
				<input class="last_time" type="text" name="lasttime" readonly="readonly" style="width: 150px;" placeholder="末班发车时间" value="00:00"/>
			</div>
  		</div>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
			<div class="span2">
	  			<span>间隔时间：</span>
			</div>
			<div class="span3">
				<input class="notnull" onblur="changeTimeShifNum(this)" onchange="changeTimeShifNum(this)" type="text" data-pattern="n" name="intevalminute" maxlength="4" style="width: 150px;" placeholder="间隔时间"/>
			</div>
  		</div>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
			<div class="span2">
	  			<span>班次数：</span>
			</div>
			<div class="span3">
				<input class="notnull" onblur="changeTimeShifNum(this)" onchange="changeTimeShifNum(this)"  type="text" maxlength="2" data-pattern="n" name="shiftnum" style="width: 150px;" placeholder="班次数"/>
			</div>
  		</div>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
			<div class="span2">
	  			<span>无排班：</span>
			</div>
			<div class="span3">
				<input  name="isshift" value="0" type="checkbox" style="margin-top: 3px;"/>
			</div>
  		</div>
		<br/>
  		<div class="row" style="text-align: center;">
  				<input value="确定"  type="button" class="btn btn-success save_shcedule_rule" style="height:40px;width: 15%;font-size: 18px;"/>
  				<input value="取消" data-dismiss="modal"  type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;"/>
  		</div>
		<br/>
  </div>
</div>

	<!-- Modal -->
<div id="editScheduleRule"  class=" " tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display:none;left:40%;width: 100%;height: auto;">
  <div class="modal-header">
<!--     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
    <h3 id="myModalLabel">编辑规则</h3>
  </div>
  <div class="editScheduleRule" style="font: normal 17px '黑体';">
		<div class="row editshift weeks" style="margin-top: 15px;">
			<span class="span0" style="margin-left: 50px;">应用于：</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="1" class="w1"><span> 周一</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="2" class="w2"><span> 周二</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="4" class="w4"><span> 周三</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="8" class="w8"><span> 周四</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="16" class="w16"><span> 周五</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="32" class="w32"><span> 周六</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" value="64" class="w64"><span> 周日</span>
			<input type="checkbox" style="margin-top: -3px;margin-right: 7px;margin-left: 7px;" class="allchoose" class="w1"><span> 全选</span>
			<div class="pull-right" style="margin-right: 20px;"><span class="shiftnum">0</span>班</div>
		</div>
		<hr/>
		<div class="rule">
			<input type="hidden" class="id"/>
			<input type="hidden" class="isshift"/>
			<input type="hidden" class="lsid"/>
		</div>
		<div class="shiftdetail">
			<div  style="margin-left: 30px;">
				间隔时间 ：<input type="text" class="intevalminute">	<input type="button" class="btn btn_changestarttime" value="确定" style="margin-top: -7px;" />		
			</div>
			<br/>
			<div class="row" style="margin-left: 5px;">
				<div class="span1" style="width: 10px;"> 
					<input type="checkbox" class="chooseAllShif"/>
				</div>
				<div class="span1">班次号</div>
				<div class="span1">发车时间</div>
				<div class="span"></div>
				<div class="span"></div>
			</div>
			<div class="row scheduleDetail" style="margin-left: 5px;padding-top: 10px;display: none;">
				<div class="span1" style="width: 10px;">
					<input type="checkbox" class="updatetime"/>
				</div>
				<div class="span1 shiftcode">
					&nbsp;
				</div>
				<div class="span1">
					<input type="text" value="00:00" readonly="readonly" style="width:85px;" class="starttime"/>
				</div>
				<div class="span1">
					<input type="button" class="btn btn_adddetail" value="新增" />
				</div>
				<div class="span1">
					<input type="button" class="btn btn_deldetail" value="删除" />
					<input type="hidden" class="isdel" value="0"/>
					<input type="hidden" class="id"/>
				</div>
			</div>
		</div>
		<br/>
		<br/>
  		<div class="row" style="text-align: center;">
  				<input value="确定"  type="button" class="btn btn-success edit_shcedule_rule" style="height:40px;width: 15%;font-size: 18px;"/>
  				<input value="取消" data-dismiss="modal"  type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;"/>
  		</div>
  		<br/>
		<br/>
  </div>
</div>
	
</body>
<style>
	.showed{
		width: 0;
		height: 0;
		border-left: 5px solid transparent;
		border-right: 5px solid transparent;
		border-top: 5px solid #000;
/* 		display:inline-block; */
		margin-top:0.8em;
		cursor:pointer;
	}
	.hided{
		width: 0;
	    height: 0;
	    border-left: 5px solid transparent;
	    border-right: 5px solid transparent;
	    border-bottom: 5px solid #000;
		display:inline-block;
		margin-top:0.8em;
		cursor:pointer;
	}
	.fl{
		float:left;
	}
	.fr{
		float:right;
	}
	.clear{
		clear:both;
	}
	.time{
		margin-bottom:1em;
		display:inline-block;
		margin-left:5em;
	}
	.num{
		margin-bottom:1em;
		display:inline-block;
	}
	.num-list{
		margin-left:1em;
		margin-top:1em;
		margin-bottom:1em;
	}
	.time-list{
		margin-left:6em;
		margin-top:1em;
		margin-bottom:1em;
	}
	.t-cell{
		display:inline-block;
		margin-left:4em;
	}
	.n-cell{
		display:inline-block;
	}
	.info-box{
		cursor:pointer;
	}
</style>
<script type="text/javascript">
var lmid = "${lm.id}";
$(function(){
	
	var schedulelist = $('.schedulelist');
	$.each(schedulelist,function(i,v){
		$(v).find('table').css({'margin-top':'-19px'})
	});
	
	
	$('.table-striped').find(".info-box").bind('click',function(){
		if($(this).attr('iseffect')!="1"){
			return;
		}
		if($(this).find("div").eq(0).attr("class")=="showed"){
			$(this).parent().find('.l-info').hide();
			$(this).find("div").eq(0).attr("class","hided");
		}else if($(this).find("div").eq(0).attr("class")=="hided"){
			$(this).parent().find('.l-info').show();
			$(this).find("div").eq(0).attr("class","showed");
			var id = $(this).find('.ruleid').val();
			var template = $(this).parents('tbody').find('.shift_template');
			var $this = $(this);
			if(template.length==1){
				ajax({'lsrid':id},basePath+"/user/findScheduleDetail",function(json){
					jeach(json,function(k,v){
						var parent = template.clone(true).show();
						var params = {};
						params.$ = parent;
						jeach(v,function(k,v){
							return params;
						})
						$this.parents('tbody').find('.shift_template').last().after(parent);
					});
				});
			}
		}
	});
	
	
	rule = {};
	$('.btn_editScheduleRule').click(function(){
		$("#editScheduleRule").find(":input").show().prop('checked',false);
		$("#editScheduleRule").find("span").show();
		var lsid = $(this).attr('lsid');
		var id = $(this).attr('id');
// 		$("#editScheduleRule").modal('show');
		 var index_editScheduleRule = layer.open({
			            type: 1,
			            closeBtn: false,
			            scrollbar: true,
			            closeBtn: 2,
			            zIndex:999,
// 			            btn: ['确定', '取消'],
			            offset: '15%',
			            area: ['1024px', '700px'],
			            title: '',
			            content: $("#editScheduleRule"),
			            move: false,
			            yes: function (index, dom) {
			            	$('.edit_shcedule_rule').click();
			            	return false;
			            },
			            btn2: function (index) {
			            },
			            success: function () {
			            }
			        });
		$('#editScheduleRule').find('.cancel').bind('click',function(){
			 layer.close(index_editScheduleRule);
		});
		ajax({lsid:lsid,id:id},basePath+"/user/findweekScheduleRule",function(json){
			if(json&&json.allshift){
				var wks = getweeks(json.allshift);
				$.each(wks,function(i,v){
					var cls = '.w'+v;
					$("#editScheduleRule").find('.weeks').find(cls).prop('checked',true).hide().next().hide();
				});
			}
		});
		$(".scheduleDetail").not($(".scheduleDetail").eq(0)).remove();
		ajax({ruleid:id}, basePath+"/user/findScheduleRule",function(json){
			rule = json;
			var wks = getweeks(json.weekday);
			$.each(wks,function(i,v){
				var cls = '.w'+v;
				$("#editScheduleRule").find(cls).prop('checked',true).show().next().show();
			});
			var shiftnum = 0;
			if(json&&json.linescheduledetail&&json.linescheduledetail.length){
				shiftnum = json.linescheduledetail.length;
			}
			$(".scheduleDetail").not($(".scheduleDetail").eq(0)).remove();
			$("#editScheduleRule").find('.shiftnum').text(shiftnum)
			var rule = $("#editScheduleRule").find('.rule');
			rule.find('.id').val(json.id);
			rule.find('.isshift').val(json.isshift);
			rule.find('.lsid').val(lsid);
			if(json.isshift==0){
// 				$(".shiftdetail").hide();
				return;
			}
			jeach(json.linescheduledetail,function(k,v){
				var detail = $(".scheduleDetail").eq(0).clone(true).show();
				var params = {};
				params.$ = detail;
				jeach(v,function(k,v){
					return params;
				})
				starttime(detail.find('.starttime'));
				$(".scheduleDetail").last().after(detail);
			});
		});
	});
	

	$('.btn_adddetail').bind('click',function(){
		if($('.starttime').length==100){
			layer.msg('添加班次已经到上限！');
			return;
		}
		var p = $(this).parents('.scheduleDetail');
		var subdetail = $(".scheduleDetail").eq(0).clone(true).show();
		subdetail.find('.starttime').val(p.find('.starttime').val());
		p.after(subdetail);
		updateShiftCode();
		starttime(subdetail.find('.starttime'));
	});
	
	$('.btn_deldetail').click(function(){
		var p = $(this).parents('.scheduleDetail');
		var id = p.find('.id').val();
		var msg = "您确定要删除该班次码？";
		layer.confirm(msg,function(){
			if(id==null||id==''){
				p.remove();
			}else{
				p.hide();
				p.find('.isdel').val(1);
			}
			updateShiftCode();
		})
	});
	
	$('.btn_changestarttime').click(function(){
		var intevalminute = $(this).parent().find('.intevalminute').val();
		var cks = $('.scheduleDetail').find('input:checked:visible');
		if(cks.length==0){
			layer.msg('请选择班次后操作！');
			return;
		}
		if(intevalminute==''||intevalminute==null||isNaN(intevalminute)){
			layer.msg('请输入正确的间隔时间！');
			return;
		}
		var time = 0;
		var intevalminute = parseInt(intevalminute);
		$.each(cks,function(i,v){
			var p = $(v).parents('.scheduleDetail');
			if(i>=1){
				var temptime = time+ intevalminute*1000*60*i;
				var currdate = new Date('2015/01/01');
				var sdate = new Date(temptime+currdate.getTime());
				var hour = sdate.getHours();
				var minute = sdate.getMinutes();
				if(hour<=9){
					hour = '0'+hour;
				}
				if(minute<=9){
					minute = '0'+minute;
				}
				var st = hour+":"+minute;
				p.find('.starttime').val(st);
				console.debug(st);
			}else if(i==0){
				var val = p.find('.starttime').val();
				var arr = val.split(':')
				if(val==''||arr.length>2){
					layer.msg('数据错误！');
				}else{
					time += parseInt(arr[0])*1000*60*60; 
					time += parseInt(arr[1])*1000*60; 
				}
// 				console.debug('*******'+time+'*****'+val+'******');
			}
		})
	});
	
	
	$('.edit_shcedule_rule').click(function(){
		var issubmit = 1;
		var params = {}
		var items = $('.scheduleDetail').not($('.scheduleDetail').eq(0));
		var p = $(this).parents('#editScheduleRule');
		params.id = p.find('.rule').find('.id').val();
		params.isshift = p.find('.rule').find('.isshift').val();
		params.lsid = p.find('.rule').find('.lsid').val();
		if(params.id==''||params.isshift==''){
			issubmit = -1;
		}
		var weekday = 0
		var sum = 0
		var cks = p.find('.weeks').find('input:checked').not('.allchoose');
		$.each(cks,function(i,v){
			sum += parseInt($(v).val());
		});
		var hcks = p.find('.weeks').find('input:hidden')
		var hidecks = cks.not(hcks);
		$.each(hidecks,function(i,v){
			weekday += parseInt($(v).val());
		});
// 		console.debug('sum '+sum)
// 		console.debug('weekday '+weekday)
// 		return;
		params.weekday = weekday;
		var pre = "linescheduledetail";
		var showitems = $('.scheduleDetail:visible')
		$.each(showitems,function(i,v){
			if(issubmit==1&&weekday!=0){
				if(i>=1){
					var time1 = $(v).find('.starttime').val();
					var time2 = showitems.eq(i-1).find('.starttime').val();
					if(time1==''||time2==''){
						issubmit = -1;
					}else{
						time1 = convertTime(time1);
						time2 = convertTime(time2);
						if(time1<=time2){
							issubmit = -2;
							var c1 = $(v).find('.shiftcode').text();
							var c2 = showitems.eq(i-1).find('.shiftcode').text();
							var msg = "班车号："+c1+"的发车时间不能小于等于，班车号："+c2+"发车时间！";
							layer.alert(msg,function(){})
						}
					}
				}
			}
		});
		if(issubmit!=1){
			return;
		}
		if(issubmit==-1){
			layer.msg('数据传输错误，请稍后再试！');
			return;
		}
		$.each(items,function(i,v){
			var id = $(v).find('.id').val();
			var shiftcode = $(v).find('.shiftcode').text();
			var starttime = $(v).find('.starttime').val();
			var isdel = $(v).find('.isdel').val();
			var key = pre+"["+i+"]";
			params[key+'.id'] = id;
			params[key+'.shiftcode']= shiftcode;
			params[key+'.starttime']= starttime;
			params[key+'.isdel']= isdel;
			params[key+'.lsuid']= params.id;
		});
// 		console.debug(params);
		if(params.isshift==1&&weekday==0){
			layer.confirm('没有选择排班星期，该排班将会删除，是否继续？',function(){
				if(sum!=127){
					var arr = getweekstring(127-sum);
					var msg = '未设置周'+arr+',请继续设置规则！';
					layer.alert(msg,{offset: '40%'}, function(index){
					    layer.close(index);
						updateScheduleRule(params);
					}); 
				}
			})
		}else{
			if(sum!=127){
				var arr = getweekstring(127-sum);
				var msg = '未设置周'+arr+',请继续设置规则！';
				layer.alert(msg,{offset: '40%'}, function(index){
				    layer.close(index);
					updateScheduleRule(params);
				}); 
			}else{
					updateScheduleRule(params);
			}
		}
	});
	
	$('.addschedule_save').click(function(){
		if (validate($('.schedule_params .params').find('input'))) {
			var params = getparams($('.schedule_params').find('input'));
			params.lmid = lmid;
// 			console.debug(params);
// 			return;
			if(params.id!=''&&params.id!=undefined){
				layer.confirm('你确定要修改排班吗？修改后将会删除'+params.enddate+'以及后续日期的未审核未发布的车票，修改后您需要重新审核排班，是否继续？'
						,{offset:['30%','40%']},function(){
					ajax(params, basePath+"/user/editlineschedule.do", function(json){
						if(json>=1){
							location.reload(true);
						}else if(json<=0){
							layer.msg('操作失败');
						}else{
							layer.alert(json);
						}
					});
				});
			}else{
				
				ajax(params, basePath+"/user/editlineschedule.do", function(json){
					if(json>=1){
						location.reload(true);
					}else if(json<=0){
						layer.msg('操作失败');
					}else{
						layer.alert(json);
					}
				});
			}
		}
	});
	
	
	$('.updateDate').click(function(){
		var arr = $(this).next().text().split('*');
		$('#myModal').modal({show:true})
		var m = $('#myModal');
		m.find('[name=scheduname]').val(arr[0]);
		m.find('[name=begindate]').val(arr[1]);
		m.find('[name=enddate]').val(arr[2]);
		m.find('[name=id]').val(arr[3]);
	});
	
	
	$('.btn_addschedule').click(function(){
		var m = $('#myModal');
		m.find('[name=scheduname]').val('');
		m.find('[name=begindate]').val('');
		m.find('[name=enddate]').val('');
		m.find('[name=id]').val('');
	});
	
	$('.newrule').click(function(){
		
		 var index_editScheduleRule = layer.open({
	            type: 1,
	            closeBtn: false,
	            scrollbar: true,
	            closeBtn: 2,
	            zIndex:999,
//	            btn: ['确定', '取消'],
	            offset: '15%',
	            area: ['1024px', '700px'],
	            title: '',
	            content: $("#addScheduleRule"),
	            move: false,
	            yes: function (index, dom) {
	            	return false;
	            },
	            btn2: function (index) {
	            },
	            success: function () {
	            }
	        });
			$('#addScheduleRule').find('.cancel').bind('click',function(){
				 layer.close(index_editScheduleRule);
			});
		
// 		$('#addScheduleRule').modal({show:true});
		var ls = $('#addScheduleRule');
		var lsid = $(this).parent().find('.id').val();
		ls.find('[name=lsid]').val(lsid);
		
		//查询排班
		$('.shift').find(':input').prop('checked',false).removeAttr('disabled').show();
		$('.shift').find('span').show();
		ajax({lsid:lsid},basePath+"/user/findweekScheduleRule",function(json){
			if(json&&json.allshift){
				var weeks = getweeks(json.allshift);
				if(json.allshift==127){
					$('.allchoose').hide().next().hide();
				}else{
					
				}
				if(weeks.length>0){
					$.each(weeks,function(i,v){
						var s = $('.shift');
						var cls = '.w'+v;
						s.find(cls).prop('checked',true);
						s.find(cls).hide().next().hide();
					})
				}
			}
		});
		
	});
	
	$('.allchoose').click(function(){
		var ck = $(this).prop('checked');
		$(this).parent().find('input').removeAttr('disabled').not($(this).parent().find('input:hidden')).prop('checked',ck);
	});
	
// 	$('[type=checkbox]').not('.allchoose').bind('click',function(){
// 		var ck = $(this).prop('checked');
// 		if(ck){
// 			$('.allchoose').parent().find('[value='+$(this).val()+']').not($(this)).prop('checked',!ck).attr('disabled','disabled');
// 		}else{
// 			$('.allchoose').parent().find('[value='+$(this).val()+']').not($(this)).removeAttr('disabled','disabled').prop('checked',true);
// 		}
// 	});
	
	$('.save_shcedule_rule').click(function(){
		var isshift = $('.addScheduleRule .params [name=isshift]');
		if(isshift.prop('checked')==false){
			if(validate($('.addScheduleRule .notnull'))==false){
				return;
			}
		}
		var intevalminute = $("[name=intevalminute]").val();
		var shiftnum = $("[name=shiftnum]").val();
		if((intevalminute*shiftnum) > 24*60){
			layer.msg('排班不能超过24小时，请检查排班间隔时间，排班数量！');
			return;
		}
		var item = $('.addScheduleRule input:checked').not('.allchoose');
		var sum = 0;
		var issubmit = true;
		$.each(item,function(i,v){
			var num = $(v).val();	
			if(issubmit==false||isNaN(num)||num==''){
				issubmit = false;
				return;
			}
			sum+=parseInt(num);
		});
		if(issubmit==false){
			layer.msg('数据错误刷新后重试...');
			return;
		}else{
			try{
				if(sum!=127){
					var arr = getweekstring(127-sum);
					var msg = '未设置周'+arr+',请继续设置规则！';
					layer.alert(msg,{offset: '40%'}, function(index){
					    layer.close(index);
						editScheduleRule();
					}); 
				}else{
						editScheduleRule();
				}
			}catch(e){
				console.debug(e);
			}
			
		}
	});
	
	$('.effect').click(function(){
		var id = $(this).attr("id");
		if(id!=""){
			ajax({id:id},basePath+"/user/effectSchedule",function(json){
				layer.alert(json,function(){
				});
			})
		}
	});
});

function updateShiftCode(){
	var items = $(".scheduleDetail:visible");
	var pre = null;
	var index = 1;
	$.each(items,function(i,v){
		if(i==0){
			pre = $(v).find('.shiftcode').text().substring(0,3);
		}
		var suffix = i;
		if(index<10){
			suffix = '0'+index;
		}else{
			suffix = index;
		}
		index++;
		$(v).find('.shiftcode').text(pre+suffix)
	});
}

function editScheduleRule(){
	var items = $('.shift').find('input:visible').not('.allchoose');
	var sum = 0;
	$.each(items,function(i,v){
		if($(v).prop('checked')==true){
			var num = $(v).val();	
			sum+=parseInt(num);
		}
	});
	if(sum==0){
// 		$('#addScheduleRule').modal('hide');
		return;
	}
	var params = getparams($('.addScheduleRule .params :input'));
	var isshift = $('.addScheduleRule .params [name=isshift]');
	params.lmid = lmid;
	params.weekday = sum;
	if(isshift.prop('checked')){
		params.isshift = 0;
		params.intevalminute = 0;
		params.shiftnum = 0;
		params.firststarttime = '00:00';
	}else{
		params.isshift = 1;
	}
	ajax(params,basePath+"/user/editScheduleRule",function(json){
		if(json>=1){
			layer.msg('操作成功！');
			location.reload(true);
		}else if(json<=0){
			layer.msg('操作失败');
		}else{
			layer.alert(json);
		}
	});
}

function updateScheduleRule(params){
	if(params.isshift==0){
		params.intevalminute = 0;
		params.shiftnum = 0;
		params.firststarttime = '00:00';
	}
	ajax(params,basePath+"/user/editScheduleRule",function(json){
		if(json>=1){
			layer.msg('操作成功！');
			location.reload(true);
		}else if(json<=0){
			layer.msg('操作失败');
		}else{
			layer.alert(json);
		}
	});
}

$('.begindate').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  0,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	startDate:new Date(),
	endDate:new Date(new Date().getTime()*9999999),
	forceParse: 0,
});
$('.enddate').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  0,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	startDate:new Date(new Date().getTime()+1000*60*60*24),
	forceParse: 0,
});

$('.enddate,.begindate').datetimepicker().on('show',function(e){
	datetimepickerHanlder($(this));
});

$('.enddate').datetimepicker().on('changeDate', function(ev){
// 	console.debug(ev)
	if($("[name=scheduname]").val()==''){
		var str = "排班"+"${page.totalCount+1}";
		$("[name=scheduname]").val(str)
}
	
	
});

$('.first_time,.last_time').timepicker({
	showMeridian : false,
	showInputs : true,
	defaultTime : false,
	format:'H:i',
	minuteStep : 1
});

function convertTime(str){
	var time = 0;
	var arr = str.split(':');
	time += parseInt(arr[0])*1000*60*60;
	time += parseInt(arr[1])*1000*60;
	return time;
}

function starttime(e){
	e.timepicker({
		showMeridian : false,
		showInputs : true,
		defaultTime : false,
		format:'H:i',
		minuteStep : 1
	});
}

$(function(){


	$('.btn_addschedule,.updateDate').bind('click',function(){
		
		$('.begindate').datetimepicker('setStartDate', null);
		$('.begindate').datetimepicker('setEndDate', null);
		$('.enddate').datetimepicker('setEndDate', null);
		$('.enddate').datetimepicker('setStartDate', null);
		if($(this).is('.btn_addschedule')){
			//默认排班最后的日期
			ajax({lmid:"${lm.id}"}, basePath+"/user/findLineScheduleLastDate",function(json){
				if(json&&json.enddate){
					var date = json.enddate.replace(/-/g,"/");
					date = formatDate(new Date((new Date(date).getTime()+1000*60*60*24)));
// 					var enddate_min = formatDate(new Date((new Date(date).getTime()+1000*60*60*24)));
					var enddate_min = formatDate(new Date((new Date(date).getTime())));
//					console.debug(json);
//					console.debug("***************");
					if(json.enddate!=json.begindate){
						$('.begindate').datetimepicker('setStartDate', date);
						$('.enddate').datetimepicker('setStartDate', enddate_min);
					}
					$('.begindate').datetimepicker('setEndDate', null);
					$('.enddate').datetimepicker('setEndDate', null);
					$('.begindate').val(date);
				}
			});
		}else{
			var enddate = $('#myModal').find('.enddate').val();
			var lastdate = null;
			
			ajaxAsync({lmid:"${lm.id}",enddate:enddate}, basePath+"/user/findLineScheduleByEndDate",function(json){
				if(json&&json.begindate){
					var date = json.begindate.replace(/-/g,"/");
					date = formatDate(new Date((new Date(date).getTime()-1000*60*60*24)));
// 					var begindate_max = formatDate(new Date((new Date(date).getTime()-1000*60*60*24)));
					var begindate_max = formatDate(new Date((new Date(date).getTime())));
					if(json.enddate!=json.begindate){
						$('.enddate').datetimepicker('setEndDate', date);
						$('.begindate').datetimepicker('setEndDate', begindate_max);
					}
				}else{
					$('.begindate').datetimepicker('setStartDate', new Date());
					$('.enddate').datetimepicker('setStartDate',new Date(new Date().getTime()+1000*60*60*24));
				}
			});
			var begindate = $('#myModal').find('.begindate').val();
			
			ajaxAsync({lmid:"${lm.id}",begindate:begindate}, basePath+"/user/findLineScheduleByBeginDate",function(json){
				if(json&&json.enddate){
					var date = json.enddate.replace(/-/g,"/");
					date = formatDate(new Date((new Date(date).getTime()+1000*60*60*24)));
// 					var enddate_min = formatDate(new Date((new Date(date).getTime()+1000*60*60*24)));
					var enddate_min = formatDate(new Date((new Date(date).getTime())));
					if(json.enddate!=json.begindate){
						$('.begindate').datetimepicker('setStartDate', date);
						$('.enddate').datetimepicker('setStartDate', enddate_min);
					}
					lastdate = date;
				}else{
					$('.begindate').datetimepicker('setStartDate', new Date());
					$('.enddate').datetimepicker('setStartDate',new Date(new Date().getTime()+(0*1000*60*60*24)));
				}
			});
			
			ajaxAsync({lmid:"${lm.id}"},basePath+"/user/getMaxApproveTicketDate",function(json){
				if(lastdate==null){
					$('.begindate').datetimepicker('setStartDate', new Date((new Date(json).getTime()+1000*60*60*24)));
					$('.enddate').datetimepicker('setStartDate', new Date((new Date(json).getTime()+1000*60*60*24*1)));
				}else{
					if(new Date(lastdate).getTime()<new Date(json).getTime()){
						$('.begindate').datetimepicker('setStartDate', new Date((new Date(json).getTime()+1000*60*60*24)));
						$('.enddate').datetimepicker('setStartDate', new Date((new Date(json).getTime()+1000*60*60*24*1)));
					}else{
						// 
					}
				}
			});
			
		}
	})
	
	$('.chooseAllShif').bind('click',function(){
		var isck = $(this).prop('checked');
		$('.updatetime').prop('checked',isck);
	});
	$('.starttime').click(function(){
		$('a[data-action="incrementHour"]').text("+");
		$('a[data-action="decrementHour"]').text("-");
		$('a[data-action="incrementMinute"]').text("+");
		$('a[data-action="decrementMinute"]').text("-");
		$('a[data-action="incrementHour"],a[data-action="decrementHour"],a[data-action="incrementMinute"],a[data-action="decrementMinute"]').css("text-decoration","none");
	});
})

function changeTimeShifNum(t){
	var lasttime = $('.last_time').val();
	var firsttime = $('.first_time').val();
	
	firsttime = parseInt(firsttime.split(':')[0])*60+parseInt(firsttime.split(':')[1]);
	lasttime = parseInt(lasttime.split(':')[0])*60+parseInt(lasttime.split(':')[1]);
	
	var temp = lasttime - firsttime;
	
	if($(t).is('[name=intevalminute]')){
		
		if($(t).val()==''){
// 			console.debug('time ------------ null');
			if($('[name=shiftnum]').val()){
// 				CalculationTime(t,lasttime,firsttime,temp);
			}
// 			layer.msg('输入间隔时间不合法，请检查！');
			return;
		}else{
// 			console.debug('time ------------ not null');
			CalculationShiftNum(t,lasttime,firsttime,temp);
		}
	}else if($(t).is('[name=shiftnum]')){
		if($(t).val()==''){
// 			CalculationShiftNum(t,lasttime,firsttime,temp);
//			layer.msg('输入班次数量不合法，请检查！');
			return;
		}else{
			CalculationTime(t,lasttime,firsttime,temp);
		}
	}
}

function CalculationTime(t,lasttime,firsttime,temp){
	var val = $('[name=shiftnum]').val();
	if($(t).val()==''&&val==''){
		return;
	}
	var time = parseInt($(t).val() == '' ? val:$(t).val());
	if(isNaN(lasttime)||isNaN(firsttime)||lasttime<=firsttime){
		layer.msg('结束时间不合法，不能自动计算班次数量！');
		return;
	}else{
		if($('[name=intevalminute]').val()==''){
			$('[name=intevalminute]').val(temp%time==0?temp/time:temp/time+1);
		}
	}
}
function CalculationShiftNum(t,lasttime,firsttime,temp){
	var val = $('[name=intevalminute]').val();
	if($(t).val()==''&&val==''){
		return;
	}
	var num =parseInt($(t).val() == '' ? val:$(t).val());
// 	console.debug('=========' + num)
	if($('[name=shiftnum]').val()==''){
		if(isNaN(lasttime)||isNaN(firsttime)||lasttime<=firsttime){
			layer.msg('结束时间不合法，不能自动计算间隔时间！');
			return;
		}else{
				$('[name=shiftnum]').val(temp%num==0?parseInt(temp/num):parseInt(temp/num)+1);
		}
	}
}


//日期处理
function datetimepickerHanlder($this){
	var datetimepicker = $('.datetimepicker:visible');
	var isedit = $('#myModal').find('[name=id]').val()=="" ? false:true;
	if($this.is('.enddate')){
		if(isedit){
			
		}else{
			
		}
	}else if($this.is('.begindate')){
		if(isedit){
			
		}else{
			
		}
	}
}

</script>
</html>
