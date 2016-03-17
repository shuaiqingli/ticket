<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/roleAdd.js"></script>
	<title>
		<c:if test="${empty role.id }">
			新增角色
		</c:if>
		<c:if test="${not empty role.id}">
			编辑角色
		</c:if>
	</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/admin/rolelist.do" style="color: black"></a>
				<c:if test="${empty role.id }">
					新增角色
				</c:if>
				<c:if test="${not empty role.id}">
					编辑角色
				</c:if>
			</h2>
		</div>
		<form method="post" id="roleForm" action="${basePath}/admin/roleedit.do">
			<input type="hidden" name="id" value="${role.id }">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>角色名称</label>
					<input type="text" name="rolename" value="${role.rolename }" class="span3" datatype="*" nullmsg="角色名称不能为空">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>包含权限</label>
					<div class="well">
						<ul style="list-style:none;">
						
							<c:forEach items="${funcModes }" var="funcMode">
								<li>
									<c:set var="checkAll" value="0"/>
									<c:forEach items="${roleFuncModes }" var="checkedFuncMode">
										<c:if test="${checkedFuncMode.ID == funcMode.ID }">
											<c:set var="checkAll" value="1"/>
										</c:if>
									</c:forEach>
									<label class="checkbox inline">
										<input class="oneLevel" type="checkbox" value="${funcMode.ID }" 
										<c:if test="${checkAll == 1 }">checked</c:if>  >
										${funcMode.menuName }
										<input type="hidden" class="isdel"/>
										<input type="hidden" value="${funcMode.ID }" class="id"/>
									</label>
									
									<table style="margin-left: 30px;">
										<tbody>
											<c:set var="tagFlag" value="1"/>
											
											<c:forEach items="${funcMode.children }" var="subFuncMode" varStatus="status">
												<c:set var="checkSingle" value="0"/>
												<c:forEach items="${roleFuncModes}" var="oneCheckedFuncMode">
													<c:forEach items="${oneCheckedFuncMode.children}" var="towCheckedFuncMode">
														<c:if test="${towCheckedFuncMode.ID == subFuncMode.ID }">
															<c:set var="checkSingle" value="1"/>
														</c:if>
													</c:forEach>
												</c:forEach>
												
												<c:if test='${tagFlag=="1" }'><tr><c:set var="tagFlag" value="2"/></c:if>
												<td style="width: 120px; vertical-align: top;">
													<label class="checkbox inline">
														<input class="twoLevel" type="checkbox" 
														value="${subFuncMode.ID }" <c:if test="${checkSingle == 1 }">checked</c:if> >
														${subFuncMode.menuName }
														<input type="hidden" class="isdel"/>
														<input type="hidden" value="${subFuncMode.ID}" class="id"/>
													</label>
												</td>
												<c:if test='${tagFlag=="2"&&status.count%4==0 }'></tr><c:set var="tagFlag" value="1"/></c:if>
											</c:forEach>
											
											<c:if test='${tagFlag=="2" }'></tr><c:set var="tagFlag" value="1"/></c:if>
										</tbody>
									</table>
									
								</li>
							</c:forEach>
							
							
						</ul>
					</div>	
				</div>
			</div>
			<div class="row">
				<div class="span2" style="margin-top:8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top:8px;">
					<input type="button" class="btn" value="返回" onclick="history.back()">
				</div>
			</div>
		</form>
	</div>

	<%@include file="../common/footer.jsp" %>
</body>
</html>
