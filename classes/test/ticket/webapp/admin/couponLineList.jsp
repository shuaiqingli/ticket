<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/couponLineList.js"></script>
    <title>送券列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/couponLineList.do" method="get">
        <div class="page-header">
            <a id="main_page" href="<%=basePath%>/admin/feedbackList.do" style="color: black"></a>
            <h2>送券列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="客服名称/客户名称/券码" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="<%=basePath%>/admin/couponAddPage.do">手动送券</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>客服名称</th>
                <th>客户名称</th>
                <th>面值</th>
                <th>最低消费</th>
                <th>有效期</th>
                <th>券码</th>
                <th>备注</th>
                <th>创建日期</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${couponLineList }" var="couponLine">
                <tr class="odd">
                    <td>${couponLine.username }</td>
                    <td>${couponLine.cname }</td>
                    <td>${couponLine.vprice }</td>
                    <td>${couponLine.lowlimit }</td>
                    <td>${couponLine.begindate }到${couponLine.enddate }</td>
                    <td>${couponLine.vouchercode }</td>
                    <td>${empty couponLine.remark ? '无' :  couponLine.remark}</td>
                    <td>${couponLine.makedate}</td>
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

