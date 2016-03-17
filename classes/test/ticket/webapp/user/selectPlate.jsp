<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/selectPlate.js"></script>
    <title>选取车牌</title>
</head>

<body>
<div class="container main_container">
    <form action="<%=basePath%>/user/selectPlate.do" method="get">
        <div class="row">
            <div class="pull-left" style="padding-right:10px;margin-left: 20px;">
                <input type="text" name="platenum" value="${param.platenum }"
                       placeholder="车牌号码" style="height:30px;">
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
                <th>车牌</th>
                <th>核载</th>
                <th>车型</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.data }" var="plate">
                <tr class="odd">
                    <td><input type="checkbox" class="check_single" value="${plate.id }"></td>
                    <td>${plate.id }</td>
                    <td>${plate.platenum }</td>
                    <td>${plate.nuclearload }</td>
                    <td>${plate.carmodelname }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
</body>
</html>
