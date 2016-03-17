<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="navbar navbar-fixed-top ishide">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
			</a>
			
			<div class="nav-collapse">
				<ul class="nav">
					<li><a href="${basePath}/user/main.do">主页</a></li>
					<li><a href="${basePath}/data_statistic.jsp" target="_bank">数据统计</a></li>
					<c:forEach items="${user.menus}" var="funcMode">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">${funcMode.menuName }<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<c:forEach items="${funcMode.children }" var="subFuncMode">
									<li><a href="${basePath}/${subFuncMode.menuUrl}">${subFuncMode.menuName }</a></li>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</ul>

				<ul class="nav pull-right">
					<li>
						<a href="javascript:void(0)" style="cursor: default;">欢迎您,${user.realName }</a>
					</li>
					<li>
						<a href="${basePath}/user/logout.do"><img src="${basePath}/images/logout.png" style="width: 20px;height: 20px;">注销</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
