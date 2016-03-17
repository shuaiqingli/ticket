<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/feedbackDetail.js"></script>
    <title>咨询详情</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/admin/feedbackList.do" style="color: black"></a>
            咨询详情
        </h2>
    </div>
    <div style="border: 1px solid #c4d6ec;padding: 0 1px;">
        <div style="height: 36px;line-height: 38px;background: #e5eef8;border-top: 1px solid #c4d6ec;border-bottom: 0;padding: 0 0 0 19px;margin: 0;">
            <strong>备注： ${fn:length(feedback.remark) > 0?feedback.remark:'无'}</strong>
            <span style="color: #666;line-height: 18px;">
                <c:if test="${feedback.type == 1}">(支付问题)</c:if>
                <c:if test="${feedback.type == 2}">(账户问题)</c:if>
                <c:if test="${feedback.type == 3}">(订单问题)</c:if>
                <c:if test="${feedback.type == 4}">(评论问题)</c:if>
                <c:if test="${feedback.type == 5}">(促销问题)</c:if>
                <c:if test="${feedback.type == 6}">(其它问题)</c:if>
                <c:if test="${feedback.type == 7}">(退票问题)</c:if>
                <c:if test="${feedback.type == 8}">(投诉建议)</c:if>
            </span>
        </div>
        <div>
            <c:forEach items="${feedback.feedbackDetailList}" var="feedbackDetail" varStatus="i">
                <div style="text-align: left;padding: 16px 0 18px 0;margin: 0 20px 0 19px;color: #000;${i.last?'':'border-bottom: 1px dotted #afafb0;'}">
                    <a href="javascript:void(0)">${feedbackDetail.replyname}</a>
                    <span style="color: #666;line-height: 18px;">[${feedbackDetail.type==1?'顾客':'客服'}]</span>
                    <span style="color: #666;line-height: 18px;">[${feedbackDetail.makedate}]</span>
                    <c:if test="${feedbackDetail.type == 2}">
                        <a class="btn btn-danger" href="javascript:void(0);" onclick="del(${feedbackDetail.id})" style="float: right;">
                            <i class="icon-remove icon-white"></i> 删除
                        </a>
                    </c:if>
                    <div style="font-size: 14px;padding: 12px 40px;clear: both;zoom: 1;white-space: normal;word-break: break-all;">
                        <div style="line-height: 21px; margin-bottom: 3px; zoom: 1; word-wrap: break-word;overflow:hidden;">
                                ${feedbackDetail.content}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <c:if test="${feedback.status == 1}">
        <form action="<%=basePath%>/admin/feedbackReply.do" method="get" id="feedbackForm">
            <input type="hidden" name="feedbackid" value="${feedback.id}">
        <textarea name="content" rows="4" cols="80"
                  style="width: 100%;margin-top:8px;padding: 0 1px;border: 1px solid #c4d6ec;box-shadow: 0 1px 3px #e3e3e3 inset;overflow: auto;"
                  placeholder="请输入回复内容"></textarea>

            <div class="row">
                <div class="span2">
                    <input type="submit" class="btn" value="提交">
                </div>
            </div>
        </form>
    </c:if>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
