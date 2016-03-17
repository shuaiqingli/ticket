<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<title>线路排班</title>
	<style type="text/css">
/* 		.datetimepicker table tr td.active{ */
/* 			background: #fff; */
/* 		} */
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="javascript:void(0)" method="get">
			<div >
				<a id="main_page" href="${basePath}/user/linemanage.do" style="color: black;border: none;"></a>
				<h2 onclick="location.href='${basePath}/user/linemanage.do">
				<span style="color: #000;">
				</span>线路排班</h2>
			</div>
			<div class="row">
				<div class="span6" style="font: normal 18px '黑体';margin-top: 10px;">
					&nbsp;&nbsp;&nbsp;${lm.lineID }（ ${lm.cityStartName }${lm.STStartName } - ${lm.cityArriveName }${lm.STArriveName } ）
				</div>
				<div class="span6 pull-right">
					<button  class="btn pull-right btn_batch" style="margin-left: 10px;height: 35px;font-size: 14px;" data-toggle="modal" data-target="#myModal">批量审核</button>
					<c:if test="${user.isAdmin==1 or user.isapprove == 1 }">
						<button  class="btn pull-right approve" style="margin-left: 10px;height: 35px;font-size: 14px;">审核</button>
					</c:if>
					<button class="btn pull-right" style="margin-left: 10px;height: 35px;font-size: 14px;" onclick="location.href='${basePath}/user/lineScheduleRuleList?lmid=${lm.id}'">排班规则</button>
					<button class="btn pull-right distributeDriver" style="display: none;margin-left: 10px;height: 35px;font-size: 14px;">分配司机</button>
					<button class="btn pull-right btn_approvescheduleday" style="margin-left: 10px;height: 35px;
					font-size: 14px;" day="${lm.approvescheduleday}">设置</button>
					<span class="pull-right" style="margin-left: 10px;height: 20px;
					font-size: 14px;margin-top:0.5em;">自动审核: <span class="scheduleday">
					${empty lm.approvescheduleday ? 0:lm.approvescheduleday }</span>天
					</span>
				</div>
			</div>
			<div class="row" style="margin-top:30px;font: normal 17px '黑体';">
				<div class="span10" style="text-align: center;">
					<span class="imgprev"><img src="${basePath}/images/left.png" height="10px" width="10px"></span>
					<span class="date _date" style="font: normal 17px '黑体';outline:none;background: #fff;border:none;text-align: center;border-color: #fff;" >
					</span>
					<span class="weekday" style="font: normal 17px '黑体';outline:none;background: #fff;border:none;text-align: center;border-color: #fff;" >
					</span>
					<span class="imgnext"><img src="${basePath}/images/right.png" height="10px" width="10px"></span>
				</div>
				<div class="span2 pull-right">
					<span class="pull-right">状态：<span class="isapprove" style="color: red;"></span></span>
				</div>
			</div>
			<hr/>
			<table class="table table-striped shiftStartTable" style="font-size: 14px;">
				<thead>
					<tr>
						<th>班次号</th>
						<th>出发时间</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
<%-- 			<%@include file="../common/page.jsp"%> --%>
		</form>
	</div>


<!-- Modal -->
<div id="myModal"  class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 380px;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">批量审核</h3>
  </div>
  <div class="modal-body" style="font: normal 17px '黑体';">
  		<div class="row" style="margin-left: 20px;margin-top: 1px;">
  			<span style="color: red;">提示：审核必须是60天以内</span>
  		</div>
  		<div class="row" style="margin-left: 20px;margin-top: 30px;">
  			<span>起止日期：</span>
			<input class="date begindate " type="text" readonly="readonly" style="width: 150px;"/>
			<span>至</span>
			<input class="date enddate " type="text" readonly="readonly" style="width: 150px;"/>
  		</div>
  		<br/>
  		<div class="row" style="margin-left: 20px;">
  			<span>天数：</span>
  			<span class="approveday">0</span>
  			<span>天</span>
  		</div>
  		<br/>
  		<div class="row" style="margin-left: 20px;">
  			<span>班次总数：</span>
  			<span class="shiftnum">0</span>
  			<span>班</span>
  		</div>
  		<br/>
  		<br/>
  		<br/>
  		<br/>
  		<div class="row" style="text-align: center;">
  				<input value="确定"  type="button" class="btn btn-success batch_save" style="height:40px;width: 15%;font-size: 18px;"/>
  				<input value="取消" data-dismiss="modal"  type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;"/>
  		</div>
  </div>
<!--   <div class="modal-footer"> -->
<!--     <button class="btn  btn-primary chooseLineManage" data-dismiss="modal" aria-hidden="true">确定</button> -->
<!--   </div> -->
</div>

	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">
var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];

$('._date,.begindate,.enddate').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0,
});

$('._date').datetimepicker().on('changeDate', function(ev){
	var datestr = ev.date.toISOString().split("T")[0].toString();
	datestr = datestr.replace("-","/").replace("-","/").replace("-","/");
// 	console.debug(datestr);
	ev.date = new Date(datestr);
	changeDate(ev.date);
});

$('._date,.begindate,.enddate').datetimepicker().on('changeMonth', function(ev){
	getdatas(ev);
});

$('._date,.begindate,.enddate').datetimepicker().on('next:month', function(ev){
	getdatas(ev);
});

$('._date,.begindate,.enddate').datetimepicker().on('prev:month', function(ev){
	getdatas(ev);
});
$('._date,.begindate,.enddate').datetimepicker().on('next:year', function(ev){
	getdatas(ev);
});

$('._date,.begindate,.enddate').datetimepicker().on('prev:year', function(ev){
	getdatas(ev);
	
});

$('._date,.begindate,.enddate').on('show',function(ev){
	getdatas(ev);
});

$lmid = "${lm.id}";

//查询审核过的日期
function getdatas(ev){
	
	var month = ev.date.getMonth();
	var year = ev.date.getFullYear();
	var begindate = new Date(year+'/'+(month+1)+'/'+'/01');
// 	var enddate = new Date(begindate.getTime()-1000*60*60*24);
	var enddate = new Date(year+'/'+(month+2)+'/'+'/0');
	enddate.setMonth(begindate.getMonth());
	
	begindate = formatDate(begindate);
	enddate = formatDate(enddate);
	var params = {};
	
	
	params.lmid = "${lm.id}";
	params.begindate = begindate;
	params.enddate = enddate;
// 	console.debug(params);
	ajax(params, basePath+"/user/findApprovedShiftDates",function(json){
		approvedhanlder(ev,json)
	});
}


var date = new Date();
$(function(){
// 	changeDate(date);

	$('.btn_approvescheduleday').bind('click',function(){
		var self = $(this);
		var val = self.attr('day')||0;
		layer.open({
			type:1,
			content:'<div style="margin:25px;"><span>自动审核天数</span><input type="text" value="'+val+'" class="approvescheduleday" /></div>',
			btn:['确定','取消'],
			title:'设置自动发布天数',
			offset: ['25%', '40%'],
			area: ['300px', '210px'],
			yes:function(){
				var v = $('.approvescheduleday').val();
				if(isNaN(v)){
					layer.msg('输入天数不正确！',{time:1000});
					throw 'err';
				}
				if(parseInt(v)>30){
					layer.msg('审核天数不能大于30天！',{time:1000});
					throw 'err';
				}
				var params = {};
				params.id = $lmid;
				params.approvescheduleday = v;
				ajax(params, basePath+"/user/updateLineManange",function(json){
					if(json==1){
						layer.msg('操作成功！');
						self.attr('day',v);
						$('.scheduleday').text(v);
					}else{
						layer.alert('操作失败！')
					}
				});
			},
			success:function(){
				
			}
		});
	});
	
	if("${param.date}"!=''){
		try{
			var pdate = new Date("${param.date}".replace(/-/g,"/"))
			changeDate(pdate);
		}catch(e){
			changeDate(date);
		}
	}else{
		changeDate(date);
	}
	
	$('.imgnext').click(function(){
		var d = $('._date').text();
		if(d){
			d =  new Date(new Date(d).getTime()+1000*60*60*24);
			changeDate(d);
		}
	});
	$('.imgprev').click(function(){
		var d = $('._date').text();
		if(d){
			d =  new Date(new Date(d).getTime()-1000*60*60*24);
			changeDate(d);
		}
	});
	
	$(".weekday").click(function(){
		$('._date').datetimepicker('show');
	});
	
	
	$('.approve').bind('click',function(json){
		if($('.isapprove').attr('approve')=='1'){
			layer.msg('班次已经审核！');
			return;
		}
		var approve = $(this).attr('approve')
		if(approve=='1'){
			layer.alert('后台正在处理...')
			return;
		}else{
			aprove();
		}
	})
	
	$('.btn_batch').click(function(){
		changApproveDate();
	});
	
	$('.batch_save').click(function(){
		if($(this).data('flag')==1){
			layer.msg('正在处理....');
			return;
		}
		var $this =$(this);
		var ad = $('.approveday').text();
		var sn = $('.shiftnum').text();
		ad = parseInt(ad);
		if(ad<=0||isNaN(ad)){
			layer.msg('审核天数不能小于等于0 ！');
			return;
		}
		if(ad>60){
			layer.msg('审核天数必须是60天以内！');
			return;
		}
		sn = parseInt(sn);
		if(sn<=0||isNaN(sn)){
			layer.msg('没有可以审核的班次！');
			return;
		}
		var params = {};
		params.lmid = "${lm.id}";
		params.begindate = $('.begindate').val();
		params.enddate = $('.enddate').val();
// 		console.debug(params)
		$(this).data('flag',1);
		ajax(params, basePath+"/user/batchsaveApproveShiftStart",{success:function(json){
			if(json==1){
				layer.msg('操作成功！');
				location.href = basePath+"/user/approveLineScheduleList?id=${lm.id}&date="+params.begindate;
			}else{
				layer.alert('操作失败！')
			}
			$this.data('flag',0);
		},error:function(){
			$this.data('flag',0);
		}});
	});
	
	$('.begindate,.enddate').datetimepicker().on('changeDate', function(ev){
		changApproveDate();
	});
	
});

function aprove(){
	//offset: ['100px', '200px']
	layer.confirm('您确定要审核吗？',{offset: ['25%', '40%']},function(){
		$('.approve').attr('approve','1');
		ajax({date:$('._date').text().trim(),lmid:"${lm.id}"},basePath+"/user/saveApproveShiftStart",function(json){
			$('.approve').attr('approve','0');
			if(json>=1){
				layer.alert('操作成功！',{offset:'30%'},function(){
					$('.isapprove').text('已审核').attr('approve',1).css('color','green');
				});
			}else if(json<=0){
				layer.alert('服务器异常，请稍后再试！',{offset:'30%'});
			}else{
				layer.alert(json,{offset:'30%'});
			}
		});
	})
}

function changeDate(d){
	$('._date').text(formatDate(d));
	$('.weekday').text(weeks[d.getDay()]);
	findschedulebydate($('._date').text());
}


function changApproveDate(){
	if($('.begindate').val()==''||$('.enddate').val()==''){
		$('.begindate,.enddate').val($('._date').text());
	}
	var day = differDay($('.begindate').val(),$('.enddate').val());
	if(day<0){
		layer.msg('结束日期不能小于开始日期！')
		$('.approveday').text(0)
		$('.shiftnum').text(0)
		return;
	}
	$('.approveday').text(day+1)
	day = day+1;
	$('.approveday').text(day); 
	$('.shiftnum').text(day*shiftnum);
}


var shiftnum = 0;

function findschedulebydate(date){
	shiftnum = 0;
	$('.isapprove').text('未审核').css('color','red');
	$('.shiftStartTable').find('thead').text('');
	$('.shiftStartTable').find('tbody').text('');
	ajax({date:date,lmid:"${lm.id}"},basePath+"/user/findRuleByLineSchedue",function(json){
		if(json.lineSchedueRule.linescheduledetail){
			shiftnum = json.lineSchedueRule.linescheduledetail.length;
			$('.isapprove').text(json.lineSchedueRule.isapprove==0?'未审核':'已审核').attr('approve',json.lineSchedueRule.isapprove).css('color',json.lineSchedueRule.isapprove==0?'red':'green');

			if(json.distributeFlag != '2'){
				var head = [];
				head.push('<tr>');
				head.push('<th>班次号</th>');
				head.push('<th>发车时间</th>');
				head.push('</tr>');
				$('.shiftStartTable').find('thead').text('').append(head.join(''));
				var body = [];
				jeach(json.lineSchedueRule.linescheduledetail,function(k,v){
					body.push('<tr>');
					body.push('<td>' + v.shiftcode + '</td>');
					body.push('<td>' + v.starttime + '</td>');
					body.push('<tr>');
				});
				$('.shiftStartTable').find('tbody').text('').append(body.join(''));
			}else{
				var head = [];
				head.push('<tr>');
				head.push('<th>班次号</th>');
				head.push('<th>发车时间</th>');
				head.push('<th>司机编号</th>');
				head.push('<th>司机名称</th>');
				if (json.line.driverquantity == 2) {
					head.push('<th>司机II编号</th>');
					head.push('<th>司机II名称</th>');
				}
				head.push('<th>车牌</th>');
				head.push('<th>核载人数</th>');
				head.push('</tr>');
				$('.shiftStartTable').find('thead').text('').append(head.join(''));
				var body = [];
				for (var i = 0; i < json.shiftStartList.length; i++) {
					var shiftStart = json.shiftStartList[i];
					body.push('<tr>');
					body.push('<td>' + shiftStart.ShiftCode + '</td>');
					body.push('<td>' + shiftStart.OriginStartTime + '</td>');
					body.push('<td>' + (typeof(shiftStart.DriverID)=='undefined'?'':shiftStart.DriverID) + '</td>');
					body.push('<td>' + (typeof(shiftStart.DriverName)=='undefined'?'':shiftStart.DriverName) + '</td>');
					if (json.line.driverquantity == 2) {
						body.push('<td>' + (typeof(shiftStart.DriverIDII)=='undefined'?'':shiftStart.DriverIDII) + '</td>');
						body.push('<td>' + (typeof(shiftStart.DriverNameII)=='undefined'?'':shiftStart.DriverNameII) + '</td>');
					}
					body.push('<td>' + (typeof(shiftStart.PlateNum)=='undefined'?'':shiftStart.PlateNum) + '</td>');
					body.push('<td>' + (typeof(shiftStart.NuclearLoad)=='undefined'?'':shiftStart.NuclearLoad) + '</td>');
					body.push('<tr>');
				}
				$('.shiftStartTable').find('tbody').text('').append(body.join(''));
			}

			if(json.distributeFlag == '1'){
				$('button.distributeDriver').show().bind('click', function(){
					location.href = basePath+"/user/distributeDriverAndPlatePage.do?groupid="+json.groupid+"&date="+json.date+"&id=${lm.id}";
				});
			}else{
				$('button.distributeDriver').hide().unbind('click');
			}
		}else{
// 			layer.alert('没有匹配到任何排班记录...',{offset:'30%'})
		}
	});
}

</script>
</html>
