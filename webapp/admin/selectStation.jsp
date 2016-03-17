<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/selectStation.js"></script>
    <title>选取站点</title>
</head>

<body>
<div class="container main_container">
    <form action="<%=basePath%>/admin/selectStation.do" method="get">
        <div class="row">
            <div class="pull-left" style="padding-right:10px;margin-left: 20px;">
                <input type="hidden" name="userid" value="${param.userid}">
                <select name="cityid" class="span2">
                    <option value="">--选取城市--</option>
                    <c:forEach items="${cityList}" var="city" >
                        <option value="${city.id }" <c:if test="${param.cityid == city.id }">selected="selected"</c:if> >${city.cityname }</option>
                    </c:forEach>
                </select>
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="编号/名称/拼音" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th><input type="checkbox" class="check_all"></th>
                <th>编号</th>
                <th>名称</th>
                <th>拼音</th>
                <th>是否热门</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.data }" var="station">
                <tr class="odd">
                    <td><input type="checkbox" class="check_single" value="${station.id }"></td>
                    <td>${station.id }</td>
                    <td>${station.cityname }</td>
                    <td>${station.stpinyin }</td>
                    <td>${station.ishot == 1 ? '是':'否' }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
</body>
</html>
