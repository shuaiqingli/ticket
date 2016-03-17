<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/feedbackList.js"></script>
    <title>咨询列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/feedbackList.do" method="get">
        <div class="page-header">
            <h2>咨询列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <select name="type" class="span2">
                    <option value="">--请选择类型--</option>
                    <option value="1" <c:if test="${param.type==1}">selected</c:if>>支付问题</option>
                    <option value="2" <c:if test="${param.type==2}">selected</c:if>>帐户问题</option>
                    <option value="3" <c:if test="${param.type==3}">selected</c:if>>订单问题</option>
                    <option value="7" <c:if test="${param.type==7}">selected</c:if>>退票问题</option>
                    <option value="8" <c:if test="${param.type==8}">selected</c:if>>投诉建议</option>
                    <option value="9" <c:if test="${param.type==9}">selected</c:if>>积分及代金券</option>
                    <option value="6" <c:if test="${param.type==6}">selected</c:if>>其它问题</option>
                </select>
                <input type="text" name="content" value="${param.content}"
                       placeholder="编号/顾客/内容" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                <%--<a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="<%=basePath%>/admin/couponAddPage.do">手动送券</a>--%>
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="<%=basePath%>/admin/couponLineList.do">送券列表</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>编号</th>
                <th>顾客名称</th>
                <th>类型</th>
                <th>内容</th>
                <th>备注</th>
                <th>状态</th>
                <th>创建日期</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${feedbackList }" var="feedback">
                <tr class="odd">
                    <td>${feedback.id}</td>
                    <td>
                        ${feedback.cname }
                        <c:if test="${feedback.latestRecordsForCustomer > 0}">
                            <img src="<%=basePath%>/wx/images/news_red.png" style="height:25px;vertical-align: middle;padding-left: 6px;">
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${feedback.type == 1}">支付问题</c:if>
                        <c:if test="${feedback.type == 2}">帐户问题</c:if>
                        <c:if test="${feedback.type == 3}">订单问题</c:if>
                        <c:if test="${feedback.type == 4}">评论问题</c:if>
                        <c:if test="${feedback.type == 5}">促销问题</c:if>
                        <c:if test="${feedback.type == 6}">其它问题</c:if>
                        <c:if test="${feedback.type == 7}">退票问题</c:if>
                        <c:if test="${feedback.type == 8}">投诉建议</c:if>
                        <c:if test="${feedback.type == 9}">积分及代金券</c:if>
                    </td>
                    <td title="${feedback.content}">
                        <c:if test="${fn:length(feedback.content)>20}">${fn:substring(feedback.content,0,20)}...</c:if>
                        <c:if test="${fn:length(feedback.content)<=20}">${feedback.content}</c:if>
                    </td>
                    <td title="${feedback.remark}">
                        <c:if test="${fn:length(feedback.remark)>20}">${fn:substring(feedback.remark,0,20)}...</c:if>
                        <c:if test="${fn:length(feedback.remark)<=20}">${feedback.remark}</c:if>
                    </td>
                    <td>
                        <c:if test="${feedback.status == 1}">处理中</c:if>
                        <c:if test="${feedback.status == 2}">已关闭</c:if>
                    </td>
                    <td>${fn:substring(feedback.makedate,0,16)}</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="<%=basePath%>/admin/feedbackDetail.do?id=${feedback.id}">
                                <i class="icon-pencil"></i> 详情
                            </a>
                            <c:if test="${feedback.status == 1}">
                                <a class="btn" href="javascript:void(0);" onclick="complete(${feedback.id})">
                                    <i class="icon-pencil"></i> 关闭
                                </a>
                            </c:if>
                            <a class="btn btn-danger" href="javascript:void(0);" onclick="del(${feedback.id})">
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
