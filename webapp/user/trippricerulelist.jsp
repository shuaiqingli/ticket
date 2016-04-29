<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<title>行程价格</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	
		<div class="container main_container">
		<form action="javascript:void(0)" method="get">
			<div>
				<a id="main_page" href="${basePath}/user/ticketmanage?lmid=${lm.id}" style="color: black;border: none;"></a>
				<h2>
				<span style="color: #000;">
				</span>行程价格</h2>
			</div>
			<div class="row">
				<div class="span5" style="font: normal 18px '黑体';margin-top: 5px;">
					&nbsp;&nbsp;&nbsp;${lm.lineID }（ ${lm.cityStartName }${lm.STStartName } - ${lm.cityArriveName }${lm.STArriveName } ）
				</div>
			</div>
			<div class="row pull-right">
				<input type="button" value="新增行程价格" class="btn btn-large newrule" data-toggle="modal" data-target="#myModal"/>
			</div>
			<br/>
			<br/>
			<hr/>
			
			<ul class="nav nav-tabs" id="myTab" style="margin-top: 30px;"> 
				<c:forEach var="tp" items="${tps}" varStatus="status">
			      <li>
			      	<a href="#home${status.index}" onclick="javascript:void(0)">
				      	<span class="dates1">${tp.begindate}</span>
				      	至
				      	<span class="dates2">${empty tp.enddate ? '无限期' : tp.enddate }</span>
			      	</a>
			      </li>
				</c:forEach>
		    </ul> 
		    
		    <div class="tab-content"> 
		   		<c:forEach var="tp" items="${tps}" varStatus="status">
		      		<div class="tab-pane active" id="home${status.index}">
		      			<div style="text-align: right;margin-right: 3em;" >
		      				<c:if test="${not empty tp.weekdaystr }">
			      				<span style="color: red;margin-right: 20px;">[未设置 周${tp.weekdaystr}]</span>
		      				</c:if>
		      				<c:if test="${not empty tp.weekdaystr}">
			      				<input type="button" value="新增规则" class="btn editorupdaterule"/>
		      				</c:if>
		      				<c:if test="${empty tp.weekdaystr}">
			      				<input type="button" value="新增规则" class="btn" disabled="disabled"/>
		      				</c:if>
		      				<input type="button" value="修改时间" class="btn updatetripprice"/>
		      				<input type="hidden" class="id" value="${tp.id }"/>
		      				<input type="hidden" class="begindate" value="${tp.begindate}"/>
		      				<input type="hidden" class="enddate" value="${tp.enddate}"/>
		      			</div>
		      			<br/>
		      			<c:forEach var="rule" items="${tp.rules}">
			      			<div>
				      			<table class="table table-striped" style="text-align: center;cursor:pointer;">
				      			
			      				<tr>
				      					<td name="oods"><span>应用于周${rule.weekdaystr}</span> <i class="hided"></i></td>
				      					<td></td>
				      					<td style="text-align: right;">
				      						<button class="btn updaterule" style="margin-right: 3em;">
												编辑
											</button>
										</td>
										<input type="hidden" value="${rule.id}" class="id"/>
										<input type="hidden" value="${tp.id}" class="tplid"/>
		      						</tr>
		      						
									<tr name="oode" class="oo1" style="display: none;">
										<td>
											&nbsp;
										</td>
										<td style="text-align: center;">
											价格
										</td>
										<td style="text-align: center;">
											里程
										</td>
									</tr>
									
									<tr name="oode" class="oo2 ruledetail" style="display: none;">
										<td style="text-align: center;" class="">
											<span class="ststartname">0</span>
											<span class="">-</span>
											<span class="starrivename">0</span>
										</td>
										<td style="text-align: center;">
											<span class="price">0</span>元
										</td>
										<td style="text-align: center;">
											<span class="mileage">0</span>公里
										</td>
									</tr>
									
									<tr name="oode" class="oo3" style="display: none;">
										<td style="text-align: center;">
											票数
										</td>
										<td style="text-align: center;">
											${rule.ticketquantity}张
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									
			      			</table>
							</div>
		      			</c:forEach>
					</div> 
				</c:forEach>
					
		    	</div>
	</form>
	<form action="${basePath}/user/tripPriceRuleList">
		<input name="lmid" type="hidden" value="${lm.id}">
		<%@include file="../common/page.jsp"%>
	</form>
	</div>
	<%@include file="../common/footer.jsp" %>
	
	
	
<!-- 新增行程价格 -->
<div id="myModal"  class="modal hide fade addschedule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 300px;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">行程价格</h3>
  </div>
  <div class="modal-body schedule_params" style="font: normal 17px '黑体';">
		<input  name="id" style="width: 150px;" type="hidden"/>
<!--   		<div class="row params" style="margin-left: 20px;margin-top: 30px;"> -->
<!--   			<span>行程价格名称：</span> -->
<!-- 			<input class="notnull" name="tprname" style="width: 150px;" placeholder="排班名称"/> -->
<!--   		</div> -->
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
  			<span>生效日期：</span>
			<input class="date begindate notnull" type="text" name="begindate" readonly="readonly" style="width: 150px;" placeholder="生效日期"/>
			<span>失效效日期：</span>
			<input class="date enddate notnull" type="text" name="enddate" readonly="readonly" style="width: 150px;" placeholder="失效日期"/>
			<input type="hidden" name="id" class="id"/>
  		</div>
  		<div class="row params" style="margin-left: 20px;margin-top: 30px;">
  			<span>是否永久有效：</span>
			<input name="isforever" type="checkbox" value="1" />
  		</div>
  		<br/>
  		<div class="row" style="text-align: center;">
  				<input value="确定"  type="button" class="btn btn-success save editTripPrice" style="height:40px;width: 15%;font-size: 18px;"/>
  				<input value="取消" data-dismiss="modal"  type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;"/>
  		</div>
  </div>
<!--   <div class="modal-footer"> -->
<!--     <button class="btn  btn-primary chooseLineManage" data-dismiss="modal" aria-hidden="true">确定</button> -->
<!--   </div> -->
</div>


	<!-- Modal -->
<div id="editRule"  class="" tabindex="-1" style="display: none;">
  <div class="modal-header">
    <h3>规则</h3>
  </div>
  <div class="editRule" style="font: normal 17px '黑体';">
		<div class="rule">
			<input type="hidden"  class="id"/>
		</div>
		<div class="details" style="margin-top: 10px;">
			<div class="row" style="margin-left: 10px;">
				<div class="span4" >
					<span>价格：</span>
					<input class="price" type="text" style="margin-top: 8px;"/>
					<button class="btn batch_update_price" type="button"   value="">确定</button>
				</div>
			</div>
			<div class="row" style="margin-left: 5px;">
				<div class="span1"><input type="checkbox" class="allchoosetrip" style="margin-top: -3px;"/>全选</div>
				<div class="span3">&nbsp;</div>
				<div class="span1">价格</div>
				<div class="span1">里程</div>
			</div>
			<div class="row detail" style="margin-left: 5px;padding-top: 10px;display: none;">
				<div class="span1">
					<input type="checkbox" class="cktrip" style="margin-top: -3px;"/>
				</div>
				<div class="span3">
					<span class="ststartname"></span>
					<span> - </span>
					<span class="starrivename"></span>
				</div>
				<div class="span1">
					<input type="text" style="width:65px;" name="price" class="price"/>
				</div>
				<div class="span1">
					<input type="text"  style="width:65px;" name="mileage" class="mileage"/>
				</div>
				<input type="hidden" name="ststartname"  class="ststartname"/>  
				<input type="hidden" name="id"  class="id"/>  
				<input type="hidden" name="starrivename"  class="starrivename"/>
				<input type="hidden" name="lmid"  class="lmid"/>
				<input type="hidden" name="linename"  class="linename"/>
				<input type="hidden" name="pricesort"  class="pricesort"/>
				<input type="hidden" name="ststartid"  class="ststartid"/> 
				<input type="hidden" name="starriveid"  class="starriveid"/>
			</div>
			<hr/>
			<div class="row ticketnum" style="margin-left: 35px;padding-top: 10px;display: block;">
				<span>总票数:</span>
				<input type="text" name="ticketquantity"  style="width:65px;" maxlength="3" placeholder="总票数" class="notnull ticketquantity"/>(张)
				<span>锁票数量:</span>
				<input type="text" name="lockquantity"  style="width:85px;" maxlength="3" placeholder="锁票数量" class="notnull lockquantity"/>(张)
				<span style="margin-left: 30px;">开始座位:</span>
				<input type="text" name="isstart"  style="width:105px;" maxlength="3" value="0" placeholder="开始座位" class="notnull isstart"/>
				<span style="color:red;">(0默认不排座位)</span>
				<input type="hidden" name="id"  style="width:65px;" class="id"/>
				<input type="hidden" name="tplid"  style="width:65px;" class="tplid"/>
			</div>
			<div class="row ticketnum" style="margin-left: 35px;padding-top: 10px;display: block;">
				<span style="margin-left: 0px;">卖票开始座位:</span>
				<input type="text" name="startseat"  style="width:105px;" maxlength="3" value="0" placeholder="卖票座位" class="notnull startseat"/>
			</div>
			<hr/>
			<div class="row" style="margin-left: 35px;">
				<div style="float: left;">
					<input type="checkbox" class="allchoosepercent" style="margin-top: -7px;" /><span> 全选</span>
					<span style="margin-left: 30px;">票数百分比:</span>
					<input type="text"  maxlength="3"  style="width:65px;" class="defaultpercent"/>%
					<span style="margin-left: 10px;">优惠票数百分比:</span>
					<input type="text" maxlength="3"  style="width:65px;" class="defaultCouponpercent"/>%
					<button class="btn batch_update_percent" type="button"   value="" style="margin-top: -7px;">确定</button>
				</div>
			</div>
			<hr/>
			<div class="row startstation" style="margin-left: 35px;padding-top: 10px;display: block;">
				<input type="checkbox" class="ckpercent" style="margin-top: -7px;" />
				<span class="stationname"></span>
				<input class="stationname" type="hidden"></input>
				<input class="stationid" type="hidden"></input>
				<input class="id" type="hidden"></input>
				<span style="margin-left: 30px;">出票百分比:</span>
				<input type="text" style="width:65px;" class="notnull ticketpercent" value="100"/>%
				<input type="text" style="width:65px;" class="salequantity" readonly="readonly" value="0"/>(张)
				<span style="margin-left: 30px;">优惠票百分比:</span>
				<input type="text" maxlength="3" style="width:65px;" class="notnull couponticketpercent" value="100"/>%
				<input type="text" maxlength="3"  style="width:65px;" class="couponsalequantity" readonly="readonly" value="0"/>(张)
			</div>
		</div>
		<hr/>
		<div class="row  weeks" style="margin-top: 15px;">
			<span class="span0" style="margin-left: 50px;">应用于：</span>
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
		<br/>
  		<div class="row" style="text-align: center;">
  				<input value="确定"  type="button" class="btn btn-success edit_tripprice_rule" style="height:40px;width: 15%;font-size: 18px;"/>
  				<input value="取消"   type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;"/>
  		</div>
  		<br/>
		<br/>
  </div>
</div>
	
</body>
<script type="text/javascript">
var islock = false;
$(function(){
	
	$('.allchoose').click(function(){
		var isck = $(this).prop('checked');
		$(this).parent().find('input').prop('checked',isck);
	});
	
	$('.newrule').bind('click',function(){
		$('#myModal').find('.notnull,.id').val('');
	});
	
	$('.updatetripprice').bind('click',function(){
		$('#myModal').modal('show');
		var p = $('#myModal');
		var u = $(this).parent();
		var id = u.find('.id').val();
		ajax({id:id},basePath+"/user/findTripPrice",function(json){
			jeach(json,function(){
				return {$:$('#myModal')};
			})
			if(json.isforever!=undefined&&json.isforever==1){
				$('#myModal').find('[name=isforever]').prop('checked',true);
			}
		})
	});
	
	
	$('.editorupdaterule').click(function(){
		open_layer();
		
		$('#editRule').find('[type=checkbox]').prop('checked',false).show();
		$('#editRule').find('.weeks').find('span').show();
		
		var tplid = $(this).parent().find('.id').val();
		$('#editRule').find('.tplid').val(tplid)
		getTripPriceTicketLineData({tplid:tplid});
		getTripPriceListWeekDay({tplid:tplid})
	});
	
	$('.updaterule').click(function(){
		open_layer();
		$('#editRule').find('[type=checkbox]').prop('checked',false).show();
		$('#editRule').find('.weeks').find('span').show();
		
		$('#editRule').modal('show');
		var id = $(this).parents('tr').find('.id').val();
		var tplid = $(this).parents('tr').find('.tplid').val();
		getTripPriceTicketLineData({id:id});
		getTripPriceListWeekDay({tplid:tplid,id:id})
	});
	
	$('.editTripPrice').click(function(){
		editTripPriceRule();
	});
	
	$('.edit_tripprice_rule').click(function(){
		var items = $('#editRule').find('.detail:visible');
		var pre = "tps";
		var issubmit = true;
		var msg = "";
		if(!validate($('#editRule').find('.notnull'))){
			console.debug('......')
			return;
		}
		if(parseInt($('.ticketquantity').val())<0){
			issubmit = false;
			msg = "总票不能为负数！";
		}
		if(parseInt($('.conponticketquantity').val())<0){
			issubmit = false;
			msg = "总优惠票不能为负数！";
		}
		$.each(items,function(i,v){
			if(issubmit){
				//ststartname starrivename lmid linename pricesort price mileage ststartid starriveid
				$(v).find('.id').attr('name',"tps["+i+"].id");
				$(v).find('.lmid').attr('name',"tps["+i+"].lmid");
				$(v).find('.linename').attr('name',"tps["+i+"].linename");
				$(v).find('.pricesort').attr('name',"tps["+i+"].pricesort").val(i+1);
				$(v).find('.starriveid').attr('name',"tps["+i+"].starriveid");
				$(v).find('.ststartid').attr('name',"tps["+i+"].ststartid");
				var m = $(v).find('.mileage').attr('name',"tps["+i+"].mileage");
				var begin = $(v).find('input.ststartname').attr('name',"tps["+i+"].ststartname");
				var end = $(v).find('input.starrivename').attr('name',"tps["+i+"].starrivename");
				var price = $(v).find('.price').attr('name',"tps["+i+"].price");
				if(isNaN(price.val())||price.val()==''||price.val()=='0'){
// 					console.debug($(v).html())
					msg = begin.val()+"-"+end.val()+" 填写价格不正确，请检查！";
					issubmit = false;
					return false;
				}else if(isNaN(m.val())||m.val()==''){
					msg = begin.val()+"-"+end.val()+" 填写里程不正确，请检查！";
					issubmit = false;
					return false;
				}
			}
		});
		
		items = $('#editRule').find('.startstation:visible');
		$.each(items,function(i,v){
			if(issubmit){
				var prefix = "stationlines";
				$(v).find('.ticketpercent').attr('name',prefix+"["+i+"].ticketpercent");
				$(v).find('.stationid').attr('name',prefix+"["+i+"].stationid");
				$(v).find('.stationname').attr('name',prefix+"["+i+"].stationname");
				$(v).find('.couponticketpercent').attr('name',prefix+"["+i+"].couponticketpercent");
				$(v).find('.id').attr('name',prefix+"["+i+"].id");
			}
		});
		
		
		if(issubmit==false){
			layer.alert(msg);
			return;
		}
		
		var visiblebox = $('.weeks').find('[type=checkbox]:visible:checked').not('.allchoose');
		var allbox = $('.weeks').find('[type=checkbox]:checked').not('.allchoose');
		var sum = 0;
		var weekday = 0;
		$.each(allbox,function(i,v){
			sum += parseInt($(v).val());
		})
		$.each(visiblebox,function(i,v){
			weekday += parseInt($(v).val());
		})
		var params = getparams($('#editRule').find('input').not($('#editRule').find('.detail:first').find('input')));
		params.weekday = weekday;
// 		console.debug(params);
// 		return;
		if(sum!=127){
			var arr = getweekstring(127-sum);
			var msg = '未设置周'+arr+',请继续设置规则！';
			if(weekday==0&&params.id!=''&&params.id!=undefined){
				layer.confirm('没有选择星期，该行程价格规则将会删除，是否继续？',function(){
					saveOrUpdateTripPriceRule(params);
				});
			}else{
				layer.alert(msg,{offset: '40%'}, function(index){
				    layer.close(index);
				    saveOrUpdateTripPriceRule(params)
				}); 
			}
		}else{
			 saveOrUpdateTripPriceRule(params)
		}
	});
	
	$('.allchoosetrip').click(function(){
		$('.cktrip').prop('checked',$(this).prop('checked'));
	});
	
	
	$('.batch_update_price').click(function(){
		var items  = $('.cktrip:checked');
		var price = $(this).prev().val();
		if(isNaN(price)||price==false){
			layer.msg('填写行程价格不正确，请检查！');
			return false;
		}
		$.each(items,function(i,v){
			$(v).parents('.detail').find('.price').val(price);
		});
	});
	
	$('.ticketquantity,.ticketpercent,.Couponticketpercent').keyup(function(){
		autoCalculateTicket();
	});
	
	$('.allchoosepercent').click(function(){
		$('.ckpercent').prop('checked',$(this).prop('checked'));
	})
	
	$('.batch_update_percent').click(function(){
		var dp = $('.defaultpercent');
		var dcp = $('.defaultCouponpercent');
// 		if(dp.val()==''||isNaN(dp.val())||parseInt(dp.val())>100||parseInt(dp.val())<0){
// 			layer.msg('填写出票百分比不正确！');
// 			dp.focus();
// 			return;
// 		}
// 		if(dcp.val()==''||isNaN(dcp.val())||parseInt(dcp.val())>100||parseInt(dcp.val())<0){
// 			layer.msg('填写优惠票百分比不正确！');
// 			dcp.focus();
// 			return;
// 		}
		var items = $('.ckpercent:checked');
		$.each(items,function(k,v){
			var s = $(v).parents('.startstation');
			if(dp.val()!=''&&!isNaN(dp.val())&&parseInt(dp.val())<=100&&parseInt(dp.val())>=0){
				$(s).find('.ticketpercent').val(dp.val());
			}
			if(dcp.val()!=''&&!isNaN(dcp.val())&&parseInt(dcp.val())<=100&&parseInt(dcp.val())>=0){
				$(s).find('.couponticketpercent').val(dcp.val());
			}
		});
		autoCalculateTicket();
		
	});
	
})

//自动计算票数
function autoCalculateTicket(){
	var tq = $('.ticketquantity');
	var val = parseInt(tq.val());
	if(tq.val()==""){
		val = 0;
	}
	if(isNaN(tq.val())){
		tq.val(0);
		val = 0;
	}
	var items = $('#editRule').find('.startstation');
	items = items.not(items.eq(0));
	$.each(items,function(k,v){
		var tpval = $(v).find('.ticketpercent').val();
		var ctpval = $(v).find('.couponticketpercent').val();
		
		if(tpval==""){
			tpval = 0;
		}
		if(ctpval==""){
			ctpval = 0;
		}
		if(isNaN($(v).find('.ticketpercent').val())){
			$(v).find('.ticketpercent').val(0);
			tpval = 0;
		}
		if(isNaN($(v).find('.couponticketpercent').val())){
			$(v).find('.couponticketpercent').val(0);
			ctpval = 0;
		}
		if(tpval<0){
			tpval = 0;
		}else if(tpval>100){
			$(v).find('.ticketpercent').val(100);
			tpval = 100;
		}
		if(ctpval<0){
			ctpval = 0;
		}else if(ctpval>100){
			$(v).find('.couponticketpercent').val(100);
			ctpval = 100;
		}
		var q = parseInt(tpval/100*val);
		$(v).find('.salequantity').val(q);
		$(v).find('.couponsalequantity').val(parseInt(ctpval/100*val));
		
		
	});
}

function open_layer(){
	 var index_edit_triprule = layer.open({
         type: 1,
         closeBtn: true,
         scrollbar: true,
//          closeBtn: 2,
//         zIndex:999,
//         btn: ['确定', '取消'],
         offset: '10%',
         area: ['1024px', '80%'],
         title: '',
         content: $("#editRule"),
         move: true,
         cancel:function(){
			 $('.modal-backdrop').remove();
         },
         success: function () {
         }
     });
	$('#editRule').find('.cancel').bind('click',function(){
		 layer.close(index_edit_triprule);
		 $('.modal-backdrop').remove();
	});
}

function saveOrUpdateTripPriceRule(params){
	if(islock){
		layer.msg('正在处理，请稍后...');
		return;
	}
	islock = true;
	ajax(params, basePath+"/user/saveOrUpdateTripPriceRule", function(json){
		islock = false;
		if(json==1){
			layer.msg('操作成功！');
			location.reload(true);
		}else{
			layer.alert('操作失败！')
		}
	});
}

function getTripPriceListWeekDay(params){
	ajax(params, basePath+"/user/getTripPriceListWeekDay",function(json){
		if(json!=null&&json.weekday){
			var arr = getweeks(json.weekday);
			$.each(arr,function(i,v){
				var cls = '.w'+v;
				$(cls).prop('checked',true).hide().next().hide();
			});
		}
	});
}

function getTripPriceTicketLineData(params){
	params.lmid = "${lm.id}";
	$('#editRule').find('.detail').not($('#editRule').find('.detail').eq(0)).remove();
	$('#editRule').find('.startstation').not($('#editRule').find('.startstation').eq(0)).remove();
	ajax(params,basePath+"/user/getTripPriceTicketLineData",function(json){
		jeach(json,function(){
			return {$:$('#editRule').find('.ticketnum')};
		})
		if(json.weekday&&json.weekday!=0){
			var arr = getweeks(json.weekday);
			$.each(arr,function(i,v){
				var cls = '.w'+v;
				$(cls).prop('checked',true).show().next().show();
			});
			console.debug('arr ' + arr)
		}
		jeach(json.tps,function(k,v,i){
			var dt = $('#editRule').find('.detail').eq(0).clone(true);
			jeach(v,function(k,v,i){
				return {$:dt};
			})
			$('#editRule').find('.detail').last().after(dt.show());
		})
		jeach(json.stationlines,function(k,v){
			var p = $('.startstation').eq(0).hide().clone(true);
			jeach(v,function(k,v){
				return {$:p};
			});
			$('.startstation').last().after(p.show());
		});
		autoCalculateTicket();
	})
	
	
}


function editTripPriceRule(){
	if(islock){
		layer.msg('正在处理，请稍后...');
		return;
	}
	islock = true;
	var v = $('#myModal').find('.notnull');
	if($('[name=isforever]').prop('checked')){
		v = v.not( $('#myModal').find('.enddate'));
	}
	if(validate(v)){
		var params = getparams($('#myModal').find('input'));
		params.lmid = "${lm.id}";
		if($('[name=isforever]').prop('checked')==false){
			delete params.isforever;
		}else{
			delete params.enddate;
		}
		ajax(params, basePath+"/user/editTripPriceRule",function(json){
			islock = false;
			if(json>=1){
				layer.msg('操作成功！')
				location.reload(true);
			}else if(json<=-1){
				layer.alert('操作失败！');
			}else{
				layer.alert(json);
			}
		});
	}
}
</script>
<script type="text/javascript">
var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];


$('.begindate,.enddate').datetimepicker({
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

$('.enddate').datetimepicker().on('changeDate', function(ev){
	if($("[name=tprname]").val()==''){
		var str = "行程价格"+"${page.totalCount+1}";
		$("[name=tprname]").val(str)
	}
});

$(function(){
	$('td[name="oods"]').bind('click',function(){
		if($(this).find('i').attr('class')=="hided"){
			$(this).find('i').attr('class','showed');
			$(this).parent().parent().find('tr[name="oode"]').show();
			$(this).parents('table').find('.ruledetail').eq(0).hide();
			var rd = $(this).parents('table').find('.ruledetail');
			if(rd.length!=1){
				return;
			}
			var t = $(this);
			var params = {};
			params.id = $(this).parent().find('.id').val();
			//ruledetail
			ajax(params, basePath+"/user/findTripPriceSubByTripPriceRule",function(json){
				jeach(json,function(k,v,i){
					var rd = $('.ruledetail').eq(0).clone(true);
					jeach(v, function(){
						return {$:rd};
					});
					t.parents('table').find('.ruledetail').last().after(rd.show());
				})
			});
		}else{
			$(this).find('i').attr('class','hided');
			$(this).parent().parent().find('tr[name="oode"]').hide();
		}
	});
	$('#myTab a:first').tab('show');//初始化显示哪个tab 
	      
    $('#myTab a').click(function (e) { 
      e.preventDefault();//阻止a链接的跳转行为 
      $(this).tab('show');//显示当前选中的链接及关联的content 
    }); 

});


</script>
<style type="text/css">
			.title{
				font-size: 30px;
				line-height: 2em;
			}
			.info{
				display: inline-block;
				line-height: 2em;
			}
			.btns{
				display: inline-block;
			}
			.days{
				display: inline-block;
				line-height: 2em;
			}
			.fl{
				float: left;
			}
			.fr{
				float: right;
			}
			.clear{
				clear: both;
			}
			.dates{
				line-height: 4em;
				text-align: center;
			}
			.list-cell{
				border-top: 1px solid #ccc;
			}
			.check-box{
				width: 10px;
				height: 10px;
				border: 1px solid #ccc;
				display: inline-block;
				line-height: 2em;
			}
			.check-box-all{
				width: 10px;
				height: 10px;
				border: 1px solid #ccc;
				display: inline-block;
				line-height: 2em;
			}
			.title-row div{
				display: inline-block;
			}
			.title-row{
				margin-left: 2em;
				margin-top: 1em;
				padding-bottom: 0.5em;

			}
			.line-box{
				border-bottom: 1px solid #ccc;
				padding-bottom: 0.5em;
			}
			.infos-row div{
				display: inline-block;
				float: left;
			}
			.detail-cell div{
				display: inline-block;
			}
			.detail-cell{
				margin-left: 13em;
			}
			.infos-row{
				margin-left: 5em;
			}
			.info-row div{
				display: inline-block;
				line-height: 2em;
			}
			.info-row .check-box{
				margin-top: 10px;
			}
			.ofh{
				overflow: hidden;
			}
			.pdl05{
				padding-left: 1em;
			}
			.pdl1{
				padding-left: 1em;
			}
			.pdl12{
				padding-left: 1.2em;
			}
			.pdl2{
				padding-left: 2em;
			}
			.pdl18{
				padding-left: 1.8em;
			}
			.pdl25{
				padding-left: 2.5em;
			}
			.pdl22{
				padding-left: 2.2em;
			}
			.pdl3{
				padding-left: 3em;
			}
			.pdl33{
				padding-left: 3.3em;
			}
			.pdl4{
				padding-left: 4em;
			}
			.pdl43{
				padding-left: 4.3em;
			}
			.pdl5{
				padding-left: 5em;
			}
			.pdr1{
				padding-right: 1em;
			}
			.pdr2{
				padding-right: 2em;
			}
			.mrl5{
				margin-left: 5em;
			}
			.mrl4{
				margin-left: 4em;
			}
			.td-cell{
				margin-left: 2em;
			}
			.checked{
				background-color: #ccc;
			}
			.showed{
				width: 0;
				height: 0;
				border-left: 5px solid transparent;
				border-right: 5px solid transparent;
				border-top: 5px solid #000;
				display:inline-block;
				cursor:pointer;
				vertical-align: top;
				margin-top: 6px;
			}
			.showedate{
				width: 0;
				height: 0;
				border-left: 5px solid transparent;
				border-right: 5px solid transparent;
				border-top: 5px solid #000;
				display:inline-block;
				margin-top:0.8em;
				cursor:pointer;
				vertical-align: top;
				margin-top: 9px;
			}
			.hided{
				width: 0;
			    height: 0;
			    border-left: 5px solid transparent;
			    border-right: 5px solid transparent;
			    border-bottom: 5px solid #000;
				display:inline-block;
				cursor:pointer;
				vertical-align: top;
				margin-top: 6px;
			}
			.info-detail{
				font-size: 17px;
			}
</style>
</html>