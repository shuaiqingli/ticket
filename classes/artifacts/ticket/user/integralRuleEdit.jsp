<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/integralRuleEdit.js"></script>
    <title>
        <c:if test="${empty integralRule}">
            新增积分规则
        </c:if>
        <c:if test="${not empty integralRule}">
            编辑积分规则
        </c:if>
    </title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/user/integralRuleList.do" style="color: black"></a>
            <c:if test="${empty integralRule}">
                新增积分规则
            </c:if>
            <c:if test="${not empty integralRule}">
                编辑积分规则
            </c:if>
        </h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#information" data-toggle="tab">基本信息</a></li>
        <c:if test="${not empty integralRule}">
            <li><a href="#lineList" data-toggle="tab">线路列表</a></li>
        </c:if>
    </ul>
    <div class="tab-content">
        <!-- 基本信息 -->
        <div class="tab-pane active" id="information">
            <form method="post" id="integralRuleForm" action="${basePath}/user/integralRuleEdit.do">
                <input type="hidden" name="id" value="${integralRule.id}">
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label><span class="red">*</span> 积分倍数</label>
                        <input type="text" name="multiple" class="span3"
                               value="${integralRule.multiple}" onkeyup="value=value.replace(/[^0-9\.]/g,'')"
                               datatype="money" nullmsg="积分倍数不能为空" errormsg="积分倍数格式错误">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label><span class="red">*</span> 开始时间</label>
                        <input type="text" name="begindate" class="span3" readonly="readonly"
                               value="${fn:substring(integralRule.begindate,0,10)}"
                               datatype="*" nullmsg="开始时间不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label><span class="red">*</span> 结束时间</label>
                        <input type="text" name="enddate" class="span3" readonly="readonly"
                               value="${fn:substring(integralRule.enddate,0,10)}"
                               datatype="*" nullmsg="结束时间不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <input type="hidden" name="weekdays" value="${integralRule.weekdays }">
                        <label>可选星期 <span style="color: gray;font-size: 12px;">(选中表示生效)</span></label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="1"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,0,1)==1}'>checked</c:if> >
                            <span>周一</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="2"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,1,2)==1}'>checked</c:if> >
                            <span>周二</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="4"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,2,3)==1}'>checked</c:if> >
                            <span>周三</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="8"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,3,4)==1}'>checked</c:if> >
                            <span>周四</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="16"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,4,5)==1}'>checked</c:if> >
                            <span>周五</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="32"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,5,6)==1}'>checked</c:if> >
                            <span>周六</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="64"
                                   <c:if test='${fn:substring(integralRule.weekdayStr,6,7)==1}'>checked</c:if> >
                            <span>周日</span>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <div class="span2" style="margin-top:8px;">
                        <input type="submit" class="btn" value="保存">
                    </div>
                </div>
            </form>
        </div>
        <!-- 线路列表 -->
        <div class="tab-pane" id="lineList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="javascript:void(0)" onclick="chooseLine();">
                        <i class="icon-plus-sign"></i>新增线路
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>线路名称</th>
                            <th>始发城市</th>
                            <th>到达城市</th>
                            <th>所属公司</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${integralRule.lineMapList }" var="lineMap">
                            <tr class="odd">
                                <td>${lineMap.LineID }</td>
                                <td>${lineMap.LineName }</td>
                                <td>${lineMap.CityStartName }</td>
                                <td>${lineMap.CityArriveName }</td>
                                <td>${lineMap.TransCompany }</td>
                                <td>
                                    <a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
                                       onclick="delLine(${integralRule.id}, ${lineMap.ID});">
                                        <i class="icon-trash icon-white"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
