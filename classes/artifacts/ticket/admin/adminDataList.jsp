<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>管理员概览</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/adminDataList.do" method="get">
        <div class="page-header">
            <h2>管理员概览</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="编号/姓名/手机/站点/线路" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>编号</th>
                <th>姓名</th>
                <th>手机</th>
                <th>站点</th>
                <th>线路</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adminDataList }" var="adminData">
                <tr class="odd">
                    <td>${adminData.UserID }</td>
                    <td>${adminData.RealName }</td>
                    <td>${adminData.Mobile }</td>
                    <td>
                        <c:set var="cityName" value="" />
                        <c:forEach items="${fn:split(adminData.StationNames,',')}" var="stationName" varStatus="status">
                            <c:if test="${status.count > 5 && status.count%5==1 }"><br></c:if>
                            <c:if test="${status.count%5!=1 && cityName==fn:split(stationName,'#')[1]}">|</c:if>
                            <c:if test="${cityName!=fn:split(stationName,'#')[1]}">
                                ${fn:split(stationName,'#')[1]}
                                <c:set var="cityName" value="${fn:split(stationName,'#')[1]}" />
                            </c:if>
                            <a href="javascript:void(0)">${fn:split(stationName,'#')[0]}</a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${fn:split(adminData.LineIDs,',')}" var="lineID" varStatus="status">
                            <a href="javascript:void(0)">${lineID}</a><c:if test="${!status.last }">|</c:if><c:if test="${status.count%8==0 }"><br></c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>

<%@include file="../common/footer.jsp" %>
</body>
</html>
