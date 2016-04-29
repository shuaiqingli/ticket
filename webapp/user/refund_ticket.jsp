<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>退款订单列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/refundTicketList" method="get">
			<div class="page-header">
				<h2>退款订单列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<select style="width: 120px;" name="rank">
						<option value="-1" <c:if test="${page.param.rank==-1 }">selected</c:if>>--- 会员级别 ---</option>
						<option value="0" <c:if test="${page.param.rank==0 }">selected</c:if>>普通</option>
						<option value="1" <c:if test="${page.param.rank==1 }">selected</c:if>>高级</option>
						<option value="2" <c:if test="${page.param.rank==2 }">selected</c:if>>站务</option>
					</select>
					<select style="width: 120px;" name="status">
						<option value="-1" <c:if test="${page.param.status==0 }">selected</c:if>>--- 退款状态 ---</option>
						<option value="0" <c:if test="${page.param.status==0 }">selected</c:if>>申请中</option>
						<option value="1" <c:if test="${page.param.status==1 }">selected</c:if>>已完成</option>
						<option value="2" <c:if test="${page.param.status==2 }">selected</c:if>>已拒绝</option>
					</select>
                    <span>开始时间：</span><input style="width: 120px;" name="begindate" placeholder="开始时间" size="16" type="text" value="${page.param.begindate }" readonly="readonly" class="date _date"/>
                    <span>结束时间：</span><input style="width: 120px;" name="enddate" placeholder="结束时间" size="16" type="text" value="${page.param.enddate }" readonly="readonly" class="date _date"/>
                    <span>出行日期：</span><input style="width: 120px;" name="ridedate" placeholder="出行日期" size="16" type="text" value="${page.param.ridedate }" readonly="readonly" class="date _date"/>
					<input type="text" name="mobile" value="${page.param.mobile }"
						placeholder="订单号/手机号码"  style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$('[type=text]').val('')">清空</a>
				</div>
			</div>
			<br/>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
<!-- 						<th>退款单号</th> -->
						<th>订单号</th>
						<th>客户名称</th>
						<th>手机号码</th>
						<th>数量（张）</th>
						<th>总金额</th>
						<th>手续费</th>
						<th>应退金额</th>
						<th>实际退款金额</th>
<!-- 						<th>退款详情</th> -->
<!-- 						<th>出发日期</th> -->
						<th>退款日期</th>
						<th class="hide">备注</th>
						<th>退款状态</th>
						<th class="pull-left">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td class="id hide">${o.id }</td>
							<td class="soid">${o.soid }</td>
							<td class="cname">${o.cname }</td>
							<td class="mobile">${o.mobile }</td>
							<td class="quantity">${empty o.quantity ? '-':o.quantity }</td>
							<td class="originalprice">&yen;<span>${empty o.originalprice ?o.totalprice:o.originalprice }</span></td>
							<td class="rpercent"><span>${empty o.rpercent?0:o.rpercent }</span>%</td> 
							<td class="totalprice">&yen;<span>${o.totalprice }</span></td>
							<td class="actualprice">&yen;<span>${o.actualprice }</span></td> 
<!-- 							<td> -->
<%-- 								<c:forEach items="${o.details}" var="detail"> --%>
<%-- 									验票码：${detail.checkcode }  --%>
<%-- 									退款金额：${detail.price} <br/> --%>
<%-- 								</c:forEach> --%>
<!-- 							</td> -->
							<td class="ridedate hide">${o.ridedate }</td>
							<td class="makedate">${o.makedate }</td>
							<td class="memo hide">${o.memo }</td>
							<td>${o.rtstatus ==0?'申请中':o.rtstatus==1?'已完成':'已关闭' }(${o.isaffirm==0?'确认中':'已确认'})</td>
							<td>
								<div class="btn-group pull-left">
									<a class="btn refundMoney <c:if test="${o.rtstatus != 0 or o.isaffirm==0}">disabled</c:if> "  href="javascript:;;">
										 退款
									</a> 
<!-- 									disabled -->
									<a class="btn refundCalcel <c:if test="${o.rtstatus != 0}">disabled</c:if> " href="javascript:;;">
										 关闭
									</a> 
<%-- 									<a class="btn delete <c:if test="${o.makesort == 0 or o.rtstatus != 0}">disabled</c:if>" href="javascript:;;"> --%>
<!-- 										删除 -->
<!-- 									</a>  -->
									<a class="btn detail" href="javascript:;;">
										详情
									</a> 
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@include file="../common/page.jsp"%>
		</form>
	</div>
	<div class="refund_comfirm" style="padding-top: 5%;padding-left: 5%;display: none;">
		<div class="row" style="margin-left: 2px;">
            <div class="input-prepend input-append pull-left">
		      <span class="add-on">总金额</span>
		      <input class="span2 originalprice" type="text" maxlength="3" readonly="readonly">
		      <span class="add-on">元</span>
	   	 	</div>
        </div>
		<div class="row" style="margin-left: 2px;">
            <div class="input-prepend input-append pull-left">
		      <span class="add-on">手续费</span>
		      <input class="span2 rpercent" type="text" maxlength="3">
		      <span class="add-on">%</span>
	   	 	</div>
        </div>
		<div class="row" style="margin-left: 2px;">
            <div class="input-prepend input-append pull-left">
		      <span class="add-on">应退款</span>
		      <input class="span2 actualprice" type="text" maxlength="3">
		      <span class="add-on">元</span>
	   	 	</div>
        </div>
		<textarea rows="3" cols="10" class="ticket_memo" placeholder="请输入备注" style="width: 90%;margin: 15px 5% 0px 0%;"></textarea>
	</div>
	
	<div class="refund_detail" style="padding-left: 20px;padding-top: 20px;display: none;">
		<div class="row" style="margin-left: 2px;">
            <div class="input-prepend input-append">
		      <span class="add-on">订单号</span>
		      <input class="span3 soid" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">客户名称</span>
		      <input class="span3 cname" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">手机号码</span>
		      <input class="span3 mobile" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">总金额</span>
		      <input class="span3 originalprice" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">应退金额</span>
		      <input class="span3 totalprice" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">实际退款金额</span>
		      <input class="span3 actualprice" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">出发日期</span>
		      <input class="span3 ridedate" type="text" readonly="readonly">
	   	 	</div>
            <div class="input-prepend input-append">
		      <span class="add-on">退款日期</span>
		      <input class="span3 makedate" type="text" readonly="readonly">
	   	 	</div>
            <div class="">
		      <span class="add-on">备注</span>
		      <textarea rows="2" cols="10" readonly="readonly"></textarea>
	   	 	</div>
        </div>
	</div>
	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">

$(function(){
	$('[name=begindate]').datetimepicker().on('changeDate',function(ev){
		$('[name=enddate]').datetimepicker('setStartDate', ev.date);
	});
	
	$('.refund_comfirm').find('.actualprice').keyup(function(){
		var val = this.value;
		var total = $('.refund_comfirm').find('.originalprice').val();
		if(val==''){
			val = 0;
		}
		val = parseFloat(val);
		if(val>total){
			val = total;
			this.value = total;
			$('.refund_comfirm').find('.rpercent').val(0);
			return;
		}
		total = parseFloat(total);
		var temp = 100-(val/total)*100;
		$('.refund_comfirm').find('.rpercent').val(temp.toFixed(0));
		
	});
	
	
	$('.refund_comfirm').find('.rpercent').keyup(function(){
		var val = this.value;
		var total = $('.refund_comfirm').find('.originalprice').val();
		if(val==''){
			val = 0;
		}
		val = parseFloat(val);
		if(val>100){
			val = 100;
		}else if(val<0){
			val = 0;
		}
		total = parseFloat(total);
		var temp = (total-(total*parseFloat(val)/100)).toFixed(1);
		$('.refund_comfirm').find('.actualprice').val(temp);
		
	});
	
	$('.delete').not($('.disabled')).bind('click',function(){
		return;
		var refundno = $(this).parents('tr').find('.id').text();
		layer.confirm('您确定要删除该退款订单吗？删除后不可以恢复，是否继续？',function(){
			var params = {};
			params.refundno = refundno;
			ajax(params,basePath+"/user/deleteRefundTicket",function(json){
				if(json>=1){
					layer.msg('操作成功！');
					location.reload();
				}else{
					layer.msg('操作失败！');
				}
			});
		});
	});
	
	$('.refundMoney').not($('.disabled')).bind('click',function(){
		$('.ticket_memo').val('');
		//originalprice,totalprice,actualprice
		var tr = $(this).parents('tr');
		var originalprice = tr.find('.originalprice').find('span').text();
		var totalprice = tr.find('.totalprice').find('span').text();
		var actualprice = tr.find('.actualprice').find('span').text();
		var rpercent = tr.find('.rpercent').find('span').text();
		
		var c = $('.refund_comfirm');
		c.find('.originalprice').val(originalprice);
		c.find('.actualprice').val(actualprice);
		c.find('.rpercent').val(rpercent==''?0:rpercent);
		var refundno = $(this).parents('tr').find('.id').text();
		layer.open({
			type:1,
			title:'退款确认',
			area: ['400px', '350px'],
			content:$('.refund_comfirm'),
			btn: ['确定', '取消'],
			yes:function(i,dom){
				var params = {};
				params.status = 1;
				params.refundno = refundno;
				params.actualprice = c.find('.actualprice').val();
				params.rpercent = c.find('.rpercent').val();
				params.memo = $('.ticket_memo').val();
				if(params.actualprice==undefined||params.actualprice==''){
					layer.msg('实际退款价格不能空！');
					throw 'error';
				}
				if(params.rpercent==undefined||params.rpercent==''){
					layer.msg('手续费不能为空！');
					throw 'error';
				}
// 				console.debug(params);
				refund(params);
			}
		});
	});
	
	
	$('.detail').click(function(){
		var tr = $(this).parents('tr');
		layer.open({
			type:1,
			area: ['700px', '510px'],
			title:'退款详情',
			content:$('.refund_detail'),
			btn: ['关闭'],
			success:function(i,dom){
				$('.refund_detail').find('textarea,input').val('');
				var items = tr.find('td');
				$.each(items,function(i,v){
					try{
						var cls = '.'+$(v).attr('class').split(' ')[0];
						$('.refund_detail').find(cls).val($(v).text());
					}catch (e) {
					}
				});
				$('.refund_detail').find('textarea').val(tr.find('.memo').text());
			},
		});
	});
	
	$('.refundCalcel').not($('.disabled')).bind('click',function(){
		$('.memo').val('');
		var refundno = $(this).parents('tr').find('.id').text();
		layer.confirm('您确定要关闭该退款订单吗？关闭后不可以恢复，是否继续？',function(){
			var params = {};
			params.refundno = refundno;
			params.status = 3;
			ajax(params,basePath+"/user/cancelRefundTicket",function(json){
				if(json>=1){
					layer.msg('操作成功！');
					location.reload();
				}else{
					layer.msg('操作失败！');
				}
			});
		});
	});
	
})

function refund(params){
	 ajax(params,basePath+"/user/refundTicket",function(json){
		var resultType = json.resultType;
		var rtid = json.refund_nos;
		/* var urls = basePath+"/web/public/alipay/fast_refund";
		var datas = '{rtid:"'+rtid+'"}'; */
		if(resultType==2){
			window.location.href=basepath+"/web/public/alipay/fast_refund.do?rtid="+rtid;
			//syncAjax(urls,datas);
		}else if(resultType==1){
			layer.msg('操作成功！');
			location.reload();
		}else{
			layer.msg('操作失败！');
		}
	});
}
function syncAjax(urls,datas){
	 $.post(
			 urls,
             datas,
             function(data) {
				 alert(data);
             },
         'html'
         );
}

$('._date').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});
</script>
</html>
