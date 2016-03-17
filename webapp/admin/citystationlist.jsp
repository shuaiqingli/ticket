<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>城市列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/admin/citystationlist.do" method="get">
			<div class="page-header">
				<h2>城市列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
<!-- 					<select name="parentID" class="span2"> -->
<!-- 						<option value="">全部</option> -->
<!-- 						<c:forEach var="city" items="${citys}"> -->
<!-- 							<option value="${city.id }" <c:if test="${page.param.parentid == city.id }">selected="selected"</c:if> >${city.cityname }</option> -->
<!-- 						</c:forEach> -->
<!-- 					</select> -->
<!-- 					<input name="ishot" value="1" type="hidden"/> -->
					<input type="text" name="cityName" value="${page.param.cityname}"
						placeholder="城市名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right cls_hide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/admin/cityedit.do">
						<i class="icon-plus-sign"></i>新增城市
					</a>
					<a class="btn" href="<%=basePath%>/admin/stationedit.do">
						<i class="icon-plus-sign"></i>新增站点
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th class="choose_title" style="display: none;">选择</th>
						<th>编号</th>
						<th>城市</th>
						<th>拼音</th>
						<th>是否热门</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td class="choose_station" style="display: none;">
								<c:if test="${not empty o.parentid }">
									<input type="radio" name="chooseCity"/>
								</c:if>
								<c:if test="${empty o.parentid }">
									-
								</c:if>
							</td>
							<td>${o.id}</td>
							<td>${o.cityname}</td>
							<td>${o.stpinyin}</td>
<!-- 							<td>${o.parent.cityname}</td> -->
<!-- 							<td>${o.rank}</td> -->
							<td>${o.ishot ==1 ? '是':'否'}</td>
							<td class="cls_hide">
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/admin/${empty o.parentid ? 'cityedit.do':'stationedit.do' }?id=${o.id}">
										<i class="icon-pencil "></i> 编辑
									</a> 
									<a class="btn" href="<%=basePath%>/admin/citystationlist.do?parentid=${o.id}&isstation=1&ishot=1">
										<i class="icon-list "></i> 站点列表
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
<script type="text/javascript">
//删除城市信息
function delCityStation(id){
	layer.confirm('是否删除？', function(index){
	    	layer.msg('yes');
	});    
}
</script>
</html>
