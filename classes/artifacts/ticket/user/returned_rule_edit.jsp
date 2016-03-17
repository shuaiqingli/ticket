<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<title>退票规则</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
		<div class="container main_container">
		<div class="page-header">
        	<h2>
            <a id="main_page" href="${basePath}/user/returnedRuleList" style="color: black"></a>
	                规则列表
	        </h2>
    	</div>
			<ul class="nav nav-tabs" id="myTab" style="margin-top: 30px;"> 
			      <li class="active show">
			      	<a data-toggle="tab" href="#home1">
				      	基本信息
			      	</a>
			      </li>
			      <c:if test="${not empty rule.id }">
				      <li class="show">
				      	<a data-toggle="tab" href="#home2">
					      	线路列表
				      	</a>
				      </li>
			      </c:if>
		    </ul> 
		    
		    <div class="tab-content"> 
	      		<div class="tab-pane active" id="home1">
					<form action="${basePath}/user/editReturnedRule" method="post" class="ruleform">
						<input type="hidden" name="id" value="${rule.id }">
						 <div class="row">
		                    <div style="margin-left: 30px;" class="">
		                        <label>开始时间</label>
		                        <input readonly="readonly" type="text" name="begindate"
		                               value="${rule.begindate }" class="span3 date" datatype="*" nullmsg="开始时间不能为空">
		                    </div>
		                </div>
						 <div class="row">
		                    <div style="margin-left: 30px;" class="">
		                        <label>结束时间</label>
		                        <input readonly="readonly"  type="text" name="enddate"
		                               value="${rule.enddate }" class="span3 date"  datatype="*" nullmsg="结束时间不能为空">
		                    </div>
		                </div>
		                <div class="row">
		                    <div style="margin-left: 30px;" >
		                        <label>会员级别</label>
		                       <select name="rank" datatype="*" class="span3">
					                <option value="0" <c:if test="${rule.rank==0 }">selected</c:if> >普通</option>
					                <option value="1" <c:if test="${rule.rank==1 }">selected</c:if> >高级</option>
					                <option value="2"<c:if test="${rule.rank==2 }">selected</c:if> >站务</option>
					            </select>
		                    </div>
		                </div>
		                <div class="row">
		                    <div style="margin-left: 30px;" >
		                        <label>退票时间段:&nbsp;&nbsp;&nbsp;  <input type="button" class="btn add_time" value="添加"></label>
		                        <br/>
		                        <div class="times">
		                        	<div class="time row" style="display: none;margin-left: 2px;">
		                        		<input class="id" type="hidden">
		                        		<input class="isdel" type="hidden">
			                           <div class="input-prepend input-append pull-left">
									      <input class="span2 advancetime" type="text" maxlength="4">
									      <span class="add-on">小时</span>
									    </div>
			                           <div class="input-prepend input-append pull-left">
									      <input class="span2 deduction" type="text" maxlength="3">
									      <span class="add-on">%</span>
									      <span class="add-on btn time_remove">删除</span>
									    </div>
		                        	</div>
		                        	<c:forEach var="time" items="${times}" >
			                        	<div class="time row" style="display: block;margin-left: 2px;">
			                        		<input class="id" type="hidden" value="${time.id}">
			                        		<input class="isdel" type="hidden">
				                           <div class="input-prepend input-append pull-left">
										      <input class="span2 advancetime" type="text" maxlength="4" value="${time.advancetime}">
										      <span class="add-on">小时</span>
										    </div>
				                           <div class="input-prepend input-append pull-left">
										      <input class="span2 deduction" type="text" maxlength="3" value="${time.deduction}">
										      <span class="add-on">%</span>
										      <span class="add-on btn time_remove">删除</span>
										    </div>
			                        	</div>
		                        	</c:forEach>
		                        </div>
		                    </div>
		                </div>
		               <div class="row">
		                    <div class="span2" style="margin-top:8px;">
		                        <input type="submit" class="btn" value="保存">
		                    </div>
		                    <div class="span2" style="margin-top:8px;">
		                        <input type="button" onclick="history.go(-1)" class="btn" value="返回">
		                    </div>
		                </div>
					</form>
				</div> 
			    <c:if test="${not empty rule.id }">
					      	<div class="tab-pane" id="home2">
							      <div class="btn-group pull-right ishide" style="margin-right:20px;">
								<a class="btn selectlines" href="javascript:;;">
									<i class="icon-plus-sign"></i>选择线路
								</a>
							</div>
				     		<table class="table table-striped" style="font-size: 14px;">
							<thead>
								<tr>
									<th>线路编号</th>
									<th>线路</th>
									<th>运输公司</th>
									<th>始发站</th>
									<th>终点站</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${lines}" var="o">
									<tr class="odd">
										<td >${o.lineid }</td>
										<td >${o.linename }</td>
										<td>${o.transcompany }</td>
										<td>${o.ststartname }</td>
										<td>${o.starrivename }</td>
										<td>
											<a class="btn btn-danger pull-right btn-mini deleteline" href="javascript:void(0)" lmid="${o.id}">
		                                        <i class="icon-trash icon-white"></i>
		                                    </a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				      </div>
			    </c:if>
	    	</div>
	</div>
	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript" src="${basePath}/js/returned_rule.js"></script>
<script type="text/javascript">
var $id="${rule.id}";
var $show="${param.show}";
</script>
</html>