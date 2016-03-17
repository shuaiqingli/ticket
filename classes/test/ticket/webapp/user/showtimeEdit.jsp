<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/showtimeEdit.js"></script>
    <title>
        <c:if test="${empty showtime}">
            新增线路提示
        </c:if>
        <c:if test="${not empty showtime}">
            编辑线路提示
        </c:if>
    </title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/user/showTimeList.do" style="color: black"></a>
            <c:if test="${empty showtime}">
                新增线路提示
            </c:if>
            <c:if test="${not empty showtime}">
                编辑线路提示
            </c:if>
        </h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#information" data-toggle="tab">基本信息</a></li>
        <c:if test="${not empty showtime}">
            <li><a href="#lineList" data-toggle="tab">线路列表</a></li>
        </c:if>
    </ul>
    <div class="tab-content">
        <!-- 基本信息 -->
        <div class="tab-pane active" id="information">
            <form method="post" id="showtimeForm" action="<%=basePath%>/user/showTimeEdit.do">
                <input type="hidden" name="id" value="${showtime.id}">

                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>提示内容</label>
                        <input type="text" name="showcontent" value="${showtime.showcontent }" class="span3"
                               datatype="*" nullmsg="提示内容不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>开始时间</label>
                        <input type="text" name="begindate" value="${fn:substring(showtime.begindate,0,10)}"
                               class="span3" datatype="*" nullmsg="开始时间不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>结束时间</label>
                        <input type="text" name="enddate" value="${fn:substring(showtime.enddate,0,10)}" class="span3"
                               datatype="*" nullmsg="结束时间不能为空">
                    </div>
                </div>
                <div class="row">
                    <div class="span2" style="margin-top:8px;">
                        <input type="submit" class="btn" value="保存">
                    </div>
                </div>
            </form>
        </div>
        <!-- 线路列表 -->
        <div class="tab-pane" id="lineList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="javascript:void(0)" onclick="chooseLine();">
                        <i class="icon-plus-sign"></i>新增线路
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>线路名称</th>
                            <th>始发站</th>
                            <th>终点站</th>
                            <th>所属公司</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${showtime.lineManageList }" var="line">
                            <tr class="odd">
                                <td>${line.LineID }</td>
                                <td>${line.LineName }</td>
                                <td>${line.STStartName }</td>
                                <td>${line.STArriveName }</td>
                                <td>${line.TransCompany }</td>
                                <td>
                                    <a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
                                       onclick="delLine(${showtime.id},${line.ID });">
                                        <i class="icon-trash icon-white"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>
</body>
</html>
