<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<title>线路里程价格列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/editlineprice.do" method="post">
			<div class="page-header">
				<h2>(${lineManage.lineName })</h2>
			</div>
			<div class="page-header">
				<h4>${lineManage.STStartName } — ${lineManage.STArriveName }</h4>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>线路编号</th>
						<th>出发站</th>
						<th>到达站</th>
						<th>价格</th>
						<th>里程</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${lineManage.linePrices}" var="o" varStatus="vs">
						<tr class="odd">
							<td>${o.lineid }</td>
							<td>${o.ststartname }</td>
							<td>${o.starrivename }</td>
							<td>
								<input type="text" value="${o.price }" name="linePrices[${vs.index}].price" dataType="d" nullmsg="价格不能为空" />
								<input type="hidden" value="${o.id }" name="linePrices[${vs.index}].id"/>
							</td>
							<td>
								<input type="text" value="${empty o.mileage ? 0:o.mileage }" name="linePrices[${vs.index}].mileage" dataType="n" nullmsg="里程不能为空" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="btn-group pull-right ishide" style="margin-right:20px;">
				<a class="btn" href="javascript:void(0)" onclick="$('form').submit();">
					修改
				</a>
				<a class="btn" href="javascript:void(0)" onclick="back();">
					返回
				</a>
			</div>
		</form>
	</div>

	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">

function back(){
	if("${param.isNew}"=="1"){
		location.href = basePath+"/user/linemanage.do";
	}else{
		history.back();
	}
}

$(function(){
	//提交表单
	$("form").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			return true;
		},
		beforeSubmit:function(curform){
			
		},
		callback:function(data){
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/user/linemanage.do"
			}else if(data==-100){
				layer.msg('该记录已经存在');
			}else{
				layer.msg('操作失败');
			}
		}
	});
});

</script>
</html>
