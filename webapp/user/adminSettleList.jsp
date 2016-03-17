<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/adminSettleList.js"></script>
    <title>站务结算</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/adminSettleList.do" method="get">
        <div class="page-header">
            <h2>站务结算</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="编号/姓名" value="" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="<%=basePath%>/user/adminSettleLogList.do">结算日志</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>编号</th>
                <th>姓名</th>
                <th>订单数量</th>
                <th>车票数量</th>
                <th>待结算金额</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dataList }" var="data">
                <tr class="odd">
                    <td>${data.ReceiveID }</td>
                    <td>${data.ReceiveName }</td>
                    <td>${data.SaleOrderTotal }</td>
                    <td>${data.QuantityTotal }</td>
                    <td>¥${data.PriceTotal }</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="javascript:void(0);"
                               onclick="settle('${data.ReceiveID}',${data.PriceTotal });">
                                <i class="icon-pencil"></i> 结算
                            </a>
                            <a class="btn" href="<%=basePath%>/user/adminSettleDetailList.do?userid=${data.ReceiveID}">
                                <i class="icon-search"></i> 详情
                            </a>
                        </div>
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
