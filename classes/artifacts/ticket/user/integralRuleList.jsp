<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/integralRuleList.js"></script>
    <title>积分规则列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/integralRuleList.do" method="get">
        <div class="page-header">
            <h2>积分规则列表</h2>
        </div>
        <div class="row">
            <div class="btn-group pull-right" style="margin-right:20px;">
                <input type="text" name="keyword" value="${param.keyword}" placeholder="线路" style="height:30px;">
                <a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="<%=basePath%>/user/integralRuleEditPage.do">
                    <i class="icon-plus-sign"></i>新增积分规则
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>可选星期</th>
                <th>积分倍数</th>
                <th>应用线路</th>
                <th>创建日期</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${integralRuleList }" var="integralRule">
                <tr class="odd">
                    <td>${fn:substring(integralRule.begindate,0,10)}</td>
                    <td>${fn:substring(integralRule.enddate,0,10)}</td>
                    <td>
                        <c:if test='${fn:substring(integralRule.weekdayStr,0,1)==1}'>周一</c:if>
                        <c:if test='${fn:substring(integralRule.weekdayStr,1,2)==1}'>周二</c:if>
                        <c:if test='${fn:substring(integralRule.weekdayStr,2,3)==1}'>周三</c:if>
                        <c:if test='${fn:substring(integralRule.weekdayStr,3,4)==1}'>周四</c:if>
                        <c:if test='${fn:substring(integralRule.weekdayStr,4,5)==1}'>周五</c:if>
                        <c:if test='${fn:substring(integralRule.weekdayStr,5,6)==1}'>周六</c:if>
                        <c:if test='${fn:substring(integralRule.weekdayStr,6,7)==1}'>周日</c:if>
                    </td>
                    <td>${integralRule.multiple}</td>
                    <td>
                        <c:forEach items="${fn:split(integralRule.lineidlist,',')}" var="lineid" varStatus="status">
                            <a href="javascript:void(0)">${lineid}</a><c:if test="${!status.last }">|</c:if><c:if test="${status.count%8==0 }"><br></c:if>
                        </c:forEach>
                    </td>
                    <td>${integralRule.makedate}</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn"
                               href="<%=basePath%>/user/integralRuleEditPage.do?id=${integralRule.id}">
                                <i class="icon-pencil"></i> 编辑
                            </a>
                            <a class="btn btn-danger" href="javascript:void(0);" onclick="del(${integralRule.id})">
                                <i class="icon-remove icon-white"></i> 删除
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
