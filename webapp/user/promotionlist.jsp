<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<title>调价规则列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/promotionlist.do" method="get">
			<input type="hidden" value="${lm.id}" name="lmid"/>
			<div>
				<a id="main_page" href="${basePath}/user/ticketmanage?lmid=${lm.id}" style="color: black;border: none;"></a>
				<h2>
				<span style="color: #000;">
				</span>调价规则</h2>
			</div>
			<div class="row">
				<div class="span5" style="font: normal 18px '黑体';margin-top: 5px;">
					&nbsp;&nbsp;&nbsp;${lm.lineid }（ ${lm.citystartname }${lm.ststartname } - ${lm.cityarrivename }${lm.starrivename } ）
				</div>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="promotionname" value="${page.param.promotionname}"
						placeholder="名称" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/promotiontoadd.do?lmid=${lm.id}">
						<i class="icon-plus-sign"></i>新增调价规则
				  </a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<%--<th>编号</th>--%>
						<th>规则名称</th>
						<%--<th>开始日期</th>--%>
						<%--<th>结束日期</th>--%>
						<%--<th>星期</th>--%>
						<th>适用时间段</th>
						<th>调价幅度</th>
						<th>优惠票百分比</th>
						<th>是否启用</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="p">
						<tr class="odd">
							<%--<td height="18">${p.id }</td>--%>
							<td>${p.promotionname }</td>
							<%--<td>${p.begindate }</td>--%>
							<%--<td>${p.enddate}</td>--%>
							<%--<td>周${p.weekdaystr}</td>--%>
							<td>
								<c:forEach var="time" items="${p.times}">
									${time.begintime}-${time.endtime}
									<br/>
								</c:forEach>
							</td>
							<td>
								<c:forEach var="time" items="${p.times}">
									${time.reducesum}元
									<br/>
								</c:forEach>
							</td>
							<td>
								<c:forEach var="time" items="${p.times}">
									${time.couponpercent}%
									<br/>
								</c:forEach>
							</td>
							<td>${p.isenablename}</td>
							<td>
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/user/promotiontoedit.do?id=${p.id}&lmid=${lm.id}">
										<i class="icon-pencil"></i> 编辑
									</a> 
<%--                                     <a class="btn" href="<%=basePath%>/admin/promotiondetail.do?id=${p.id}"> --%>
<!-- 										<i class="icon-search"></i> 详情 -->
<!-- 									</a> -->
                                    <a class="btn btn-danger deleteLine" href="<%=basePath%>/user/promotiondel.do?id=${p.id}&lmid=${lm.id}">
										<i class="icon-remove icon-white"></i> 删除
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

	<%@include file="../common/footer.jsp" %>
</body>
</html>
