<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/activityMsgList.js"></script>
    <title>资讯列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/activityMsgList.do" method="get">
        <div class="page-header">
            <h2>资讯列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <select name="amsort" class="span2" title="类型">
                    <option value="">--请选择类型--</option>
                    <option value="0"
                            <c:if test="${param.amsort == '0'}">selected</c:if> >活动
                    </option>
                    <option value="1"
                            <c:if test="${param.amsort == '1'}">selected</c:if> >消息
                    </option>
                    <option value="2"
                            <c:if test="${param.amsort == '2'}">selected</c:if> >首页消息
                    </option>
                </select>
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="标题/内容" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
            <div class="btn-group pull-right" style="margin-right:20px;">
                <a class="btn" href="<%=basePath%>/user/activityMsgEditPage.do">
                    <i class="icon-plus-sign"></i>新增资讯
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>类型</th>
                <th>标题</th>
                <th>生效日期</th>
                <th>失效日期</th>
                <th>创建人</th>
                <th>创建日期</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${activityMsgList }" var="activityMsg">
                <tr class="odd">
                    <td>
                        <c:if test="${activityMsg.amsort == '0'}">活动</c:if>
                        <c:if test="${activityMsg.amsort == '1'}">消息</c:if>
                        <c:if test="${activityMsg.amsort == '2'}">首页消息</c:if>
                    </td>
                    <td>
                        <span title="${activityMsg.amtitle}">
                            <c:choose>
                                <c:when test="${fn:length(activityMsg.amtitle) > 25}">${fn:substring(activityMsg.amtitle,0,25) }...</c:when>
                                <c:otherwise>${activityMsg.amtitle }</c:otherwise>
                            </c:choose>
                        </span>
                    </td>
                    <td>${fn:substring(activityMsg.begindate, 0, 10)}</td>
                    <td>${fn:substring(activityMsg.enddate, 0, 10)}</td>
                    <td>${activityMsg.makename}</td>
                    <td>${fn:substring(activityMsg.makedate, 0, 16)}</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="<%=basePath%>/user/activityMsgEditPage.do?id=${activityMsg.id}">
                                <i class="icon-pencil"></i> 编辑
                            </a>
                            <a class="btn btn-danger" href="javascript:void(0);" onclick="del(${activityMsg.id})">
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
