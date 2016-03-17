<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/common/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="<%=basePath%>/common/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/activityMsgEdit.js"></script>
    <title>
        <c:if test="${empty activityMsg}">
            新增资讯
        </c:if>
        <c:if test="${not empty activityMsg}">
            编辑资讯
        </c:if>
    </title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/user/activityMsgList.do" style="color: black"></a>
            <c:if test="${empty activityMsg}">
                新增资讯
            </c:if>
            <c:if test="${not empty activityMsg}">
                编辑资讯
            </c:if>
        </h2>
    </div>
    <form method="post" id="activityMsgForm" action="<%=basePath%>/user/activityMsgEdit.do">
        <input type="hidden" name="id" value="${activityMsg.id}">

        <div class="row">
            <div style="margin-left: 30px;">
                <label>类型</label>
                <select name="amsort" class="span3" title="类型" onclick="selectAMSort()">
                    <option value="0"
                            <c:if test="${activityMsg.amsort=='0' }">selected</c:if> >活动
                    </option>
                    <option value="1"
                            <c:if test="${activityMsg.amsort=='1' }">selected</c:if> >消息
                    </option>
                    <option value="2"
                            <c:if test="${activityMsg.amsort=='2' }">selected</c:if> >首页消息
                    </option>
                </select>
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>标题</label>
                <input type="text" name="amtitle" value="${activityMsg.amtitle }" class="span3" datatype="*"
                       nullmsg="标题不能为空">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>生效时间</label>
                <input type="text" name="begindate" value="${fn:substring(activityMsg.begindate,0,10)}"
                       class="span3" datatype="*" nullmsg="生效时间不能为空">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>失效时间</label>
                <input type="text" name="enddate" value="${fn:substring(activityMsg.enddate,0,10)}" class="span3"
                       datatype="*" nullmsg="失效时间不能为空">
            </div>
        </div>
        <div class="row"
             <c:if test="${activityMsg.amsort=='1' || activityMsg.amsort=='2'}">style="display: none;" </c:if> >
            <div style="margin-left: 30px;">
                <label>图片<span style="color: gray;font-size: 12px;">仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB</span></label>
                <input type="file" id="ampictureurl">

                <div id="ampictureurlPreview" class="pull-left" style="margin-bottom: 10px;">
                    <c:if test="${not empty activityMsg.ampictureurl }">
                        <img style="height:150px;" src="${activityMsg.ampictureurl }" alt="图片">
                    </c:if>
                </div>
                <input type="hidden" name="ampictureurl" value="${activityMsg.ampictureurl }">
            </div>
        </div>
        <div class="row" <c:if test="${activityMsg.amsort=='1' || activityMsg.amsort=='2'}">style="display: none;" </c:if> >
            <div style="margin-left: 30px;">
                <label>内容</label>
                <input type="hidden" name="amcontent" value="${activityMsg.escapeAmcontent }" class="span3">
                <script id="contentEditor" name="content" type="text/plain"></script>
            </div>
        </div>
        <div class="row">
            <div class="span2" style="margin-top:8px;">
                <input type="submit" class="btn" value="保存">
            </div>
        </div>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
