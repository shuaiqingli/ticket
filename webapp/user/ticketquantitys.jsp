<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>余票列表</title>
</head>

<body>
<%@include file="../common/header.jsp"%>
<div class="container main_container">
	<form action="<%=basePath%>/user/ticketquantitys" method="get">
		<div class="page-header">
			<h2>余票列表</h2>
		</div>
		<div class="row">
			<div class="pull-right" style="padding-right:10px;">
				<select style="width: 140px;" name="citystartid" class="beginCitys">
					<option value="">--- 出发城市 ---</option>
					<c:forEach var="city" items="${citys}">
						<option  value="${city.id }" <c:if test='${city.id == page.param.citystartid }'>selected="selected"</c:if> >${city.cityname }</option>
					</c:forEach>
				</select>
				<select style="width: 140px;" name="ststartid" class="beginStationSelect">
					<option value="">--- 出发站点 ---</option>
				</select>
				<select style="width: 140px;" name="cityarriveid" class="endCitys">
					<option value="">--- 到达城市 ---</option>
					<c:forEach var="city" items="${citys}">
						<option  value="${city.id }" <c:if test='${city.id == page.param.cityarriveid }'>selected="selected"</c:if> >${city.cityname }</option>
					</c:forEach>
				</select>
				<select style="width: 140px;" name="starriveid" class="endStationSelect">
					<option value="">--- 到达站点 ---</option>
				</select>
				<span>票面日期：</span>
				<input name="ticketDate" placeholder="票面日期" size="16" type="text" value="${page.param.ticketDate }" readonly="readonly" class="date _date"/>
				<input type="text" name="transcompany" value="${page.param.transcompany }"
					   placeholder="班次号/公司名称"  style="height:30px;">
				<a class="btn"
				   style="padding:5px 12px;margin:-8px 0 0 10px;"
				   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
			</div>
		</div>
		<br/>
		<table class="table table-striped" style="font-size: 14px;">
			<thead>
			<tr>
				<!-- 						<th>线路号</th> -->
				<th>班次号</th>
				<th>线路</th>
				<th>出发站</th>
				<th>到达站</th>
				<th>发车时间</th>
				<th>到达时间</th>
				<th>票面日期</th>
				<th>总票</th>
				<th>余票</th>
				<th>已售票</th>
				<th>单价</th>
				<th>运输公司</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.data }" var="o">
				<tr class="odd">
						<%-- 							<td class="lineid">${o.lineid}</td> --%>
					<td class="shiftcode">${o.shiftcode}</td>
					<td>${o.linename}</td>
					<td>${o.ststartname}</td>
					<td>${o.starrivename}</td>
					<td>${o.starttime}</td>
					<td>${o.arrivetime}</td>
					<td class="ticketdate">${o.ticketdate}</td>
					<td>${o.allquantity}</td>
					<td>${o.balancequantity}</td>
					<td>${o.allquantity - o.balancequantity}</td>
					<td>${o.ticketprice}</td>
					<td>${o.transcompany}</td>
					<td>
						<a href="#myModal" role="button" class="btn shift_detil" lmid="${o.lmid }" data-toggle="modal"><i class="icon-list"></i>班次详情</a>
							<%-- 								<a class="btn btn-danger" href="javascript:void(0)" onclick="del(${o.id});"> --%>
						<!-- 									<i class="icon-remove"></i> 取消 -->
						<!-- 								</a>  -->
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<%@include file="../common/page.jsp"%>
	</form>
</div>

<%@include file="../common/footer.jsp" %>

<!-- Modal -->
<div id="myModal"  class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  style="left:40%;width: 850px;height: 540px;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 id="myModalLabel">班次详情</h3>
	</div>
	<div class="modal-body" style="min-height:400px;">
		<div class="row">
			<div class="span2">
				<span>线路：</span><span class="linename"></span>
			</div>
			<!--   			<div class="span2"> -->
			<!-- 	  			<span >线路号：</span><span class="lineid"></span> -->
			<!--   			</div> -->
			<div class="span2">
				<span>班次号：</span><span class="shiftcode"></span>
			</div>
			<div class="span2">
				<span>票面日期：</span><span class="ticketdate"></span>
			</div>
		</div>
		<!--   		<hr/> -->
		<div class="row">
			<div class="span2">
				<span>总票数：</span><span class="allquantity"></span>
			</div>
			<div class="span2">
				<span >余票：</span><span class="balancequantity"></span>
			</div>
			<div class="span2">
				<span>已售：</span><span class="allquantity_balancequantity"></span>
			</div>
		</div>
		<hr/>
		<table class="table table-striped" style="font-size: 14px;">
			<thead>
			<tr>
				<!-- 						<th>线路号</th> -->
				<!-- 						<th>班次号</th> -->
				<!-- 						<th>线路</th> -->
				<th>出发站</th>
				<th>到达站</th>
				<th>发车时间</th>
				<th>到达时间</th>
				<!-- 						<th>票面日期</th> -->
				<th>单价</th>
				<th>运输公司</th>
			</tr>
			</thead>
			<tbody>
			<tr class="odd hide tr_shiftdetail">
				<!-- 						<td class="lineid"></td> -->
				<!-- 						<td class="shiftcode"></td> -->
				<!-- 						<td class="linename"></td> -->
				<td class="ststartname"></td>
				<td class="starrivename"></td>
				<td class="starttime"></td>
				<td class="arrivetime"></td>
				<!-- 						<td class="ticketdate"></td> -->
				<td class="">
					<!-- 							<input style="width: 70px;" type="text" class="ticketprice"/> -->
					<span class="ticketprice"></span>
					<input type="hidden" class="id"/>
				</td>
				<td class="transcompany"></td>
			</tr>
			</tbody>
		</table>
	</div>
	<div class="modal-footer">
		<!--     <button class="btn  calcel_shfit"  aria-hidden="true">取消班次</button> -->
		<!--     <button class="btn  update_shfit"  aria-hidden="true">修改</button> -->
		<button class="btn "  aria-hidden="true" data-dismiss="modal">关闭</button>
	</div>
</div>

</body>
<script type="text/javascript">
	$('._date').datetimepicker({
		language:  'zh-CN',
		format:'yyyy-mm-dd',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
// 	endDate:new Date(new Date().getTime()+1000*60*60*24*8),
// 	startDate:new Date(),
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});

	//获取城市列表
	function getStationList(parentid,parent,fn){
		var data = {parentid:parentid,ishot:1};
		$.ajax({
			type: "POST",
			dataType:"JSON",
			url: basePath+"/public/stationlist.do",
			data: data,
			success: function(json){
				if(json){
					parent.children().not(parent.children().eq(0)).remove();
					fn(json,parent);
				}
			}
		});
	}

	//获取站点列表
	function fn_getStationList(parent,cityId,stationId,type){
		getStationList(cityId,parent,function(json,parent){
			$.each(json,function(i,v){
				if(type==0){
					if(v.iD == "${page.param.ststartid}"){
						parent.append('<option selected="selected" value="'+v.id+'">'+v.cityname+'</option>');
					}else{
						parent.append('<option value="'+v.id+'">'+v.cityname+'</option>');
					}
				}else{
					if(v.iD == "${page.param.starriveid}"){
						parent.append('<option selected="selected" value="'+v.id+'">'+v.cityname+'</option>');
					}else{
						parent.append('<option value="'+v.id+'">'+v.cityname+'</option>');
					}
				}
			});
		});
	}

	$(function(){

		fn_getStationList($('.beginStationSelect'), "${page.param.citystartid}", $(".beginStations").val(),0);
		fn_getStationList($('.endStationSelect'), "${page.param.cityarriveid}", $(".endStations").val(),1);

		//选择城市
		$(".beginCitys").change(function(){
			var parentid = $(this).val();
			if(parentid==""){
				return;
			}
			fn_getStationList($('.beginStationSelect'), parentid, $(".beginStations").val(),0);
		});
		$(".endCitys").change(function(){
			var parentid = $(this).val();
			if(parentid==""){
				return;
			}
			fn_getStationList($('.endStationSelect'), parentid, $(".endStations").val(),1);
		});


		$('.shift_detil').click(function(){
			var p = $(this).parents('tr');
			var params = {};
			params.ticketdate = p.find('.ticketdate').text().trim();
			params.lmid = $(this).attr('lmid');
			params.shiftcode = p.find('.shiftcode').text().trim();
			$('.tr_shiftdetail').not($('.tr_shiftdetail').first()).remove();
			ajax(params, basePath+"/user/shiftdetail", function(json){
				jeach(json.data,function(k,v,i){
					if(i==0){
						var ret = v.allquantity-v.balancequantity;
						$('.allquantity_balancequantity').text(ret);
						jeach(v,function(k,v){
							return {$:$('#myModal')}
						});
					}
					var p = $('.tr_shiftdetail').eq(0).clone(true);
					p.removeClass('hide');
					var params = {};
					params.$ = p;
					jeach(v,function(k,v){
						return params;
					});
					$('.tr_shiftdetail').last().after(p);
				});
			});
		});

		//更新价格
		$('.update_shfit').click(function(){
			var items = $('#myModal').find('[type=text]');
			var iserr = false;
			//车票线路id数组
			var ticklineids = [];
			//车票线路价格数组
			var ticklineprices = [];
			$.each(items,function(i,v){
				if(isNaN($(v).val())||$(v).val()==''||$(v).val().trim()==''){
					iserr = true;
				}else{
					ticklineids.push($(v).next().val());
					ticklineprices.push($(v).val());
				}
			});
			if(iserr){
				layer.msg('检查填写价格是否正确？');
				return;
			}
			layer.confirm("您确定要修改价格 ？ <br/> 审核后的车票价格将会改变<br/>对已售出的车票无效", {
				btn: ['确定', '取消']
			}, function(index, layero){
				var params = {};
				params.ids = ticklineids;
				params.prices = ticklineprices;
				//将参数转为字符串
				var json = JSON.stringify(params);

				ajax({"json":json}, basePath+"/user/updateTicketLinePrice",function(data){
					if(data>=1){
						layer.msg('操作成功');
					}else{
						layer.msg('操作失败');
					}
				});
			});
		});

		//取消班次
		$('.calcel_shfit').click(function(){
			layer.confirm("您确定要取消班次吗 ？ <br/> 该班次审核过的车票将会无效。<br/> 如果已经有售出的车票请跟客户协商。", {
				btn: ['确定', '取消']
			}, function(index, layero){
				//lineid shiftcode ticketdate
				//
				var p = $('#myModal');
				var params = {};
				params.lineid=p.find('.lineid').text();
				params.shiftcode=p.find('.shiftcode').text();
				params.ticketdate=p.find('.ticketdate').text();
				ajax(params, basePath+"/user/cancelShiftTicketLine",function(data){
					if(data>=1){
						layer.msg('操作成功');
					}else{
						layer.msg('操作失败');
					}
				});
			});
		});

	});
</script>
</html>
