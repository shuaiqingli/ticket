<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/prohibitEdit.js"></script>
    <title>
        <c:if test="${empty prohibit}">
            新增禁售规则
        </c:if>
        <c:if test="${not empty prohibit}">
            编辑禁售规则
        </c:if>
    </title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/admin/prohibitList.do?stid=${station.id}" style="color: black"></a>
            <c:if test="${empty prohibit}">
                新增禁售规则(${station.cityname})
            </c:if>
            <c:if test="${not empty prohibit}">
                编辑禁售规则(${station.cityname})
            </c:if>
        </h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#information" data-toggle="tab">基本信息</a></li>
        <c:if test="${not empty prohibit}">
            <li><a href="#lineList" data-toggle="tab">线路列表</a></li>
            <li><a href="#timeList" data-toggle="tab">时间列表</a></li>
        </c:if>
    </ul>
    <div class="tab-content">
        <!-- 基本信息 -->
        <div class="tab-pane active" id="information">
            <form method="post" id="prohibitForm" action="${basePath}/admin/prohibitEdit.do">
                <input type="hidden" name="id" value="${prohibit.id}">
                <input type="hidden" name="stid" value="${param.stid}">

                <div class="row">
                    <div style="margin-left: 30px;">
                        <label><span class="red">*</span> 开始时间</label>
                        <input type="text" name="begindate" class="span3" readonly="readonly"
                               value="${fn:substring(prohibit.begindate,0,10)}"
                               datatype="*" nullmsg="开始时间不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label><span class="red">*</span> 结束时间</label>
                        <input type="text" name="enddate" class="span3" readonly="readonly"
                               value="${fn:substring(prohibit.enddate,0,10)}"
                               datatype="*" nullmsg="结束时间不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <input type="hidden" name="weekdays" value="${prohibit.weekdays }">
                        <label>可选星期 <span style="color: gray;font-size: 12px;">(选中表示禁售)</span></label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="1"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,0,1)==1}'>checked</c:if> >
                            <span>周一</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="2"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,1,2)==1}'>checked</c:if> >
                            <span>周二</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="4"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,2,3)==1}'>checked</c:if> >
                            <span>周三</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="8"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,3,4)==1}'>checked</c:if> >
                            <span>周四</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="16"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,4,5)==1}'>checked</c:if> >
                            <span>周五</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="32"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,5,6)==1}'>checked</c:if> >
                            <span>周六</span>
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" value="64"
                                   <c:if test='${fn:substring(prohibit.weekdayStr,6,7)==1}'>checked</c:if> >
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
                        <c:forEach items="${prohibit.prohibitLineList }" var="line">
                            <tr class="odd">
                                <td>${line.LineID }</td>
                                <td>${line.LineName }</td>
                                <td>${line.CityStartName }</td>
                                <td>${line.CityArriveName }</td>
                                <td>${line.TransCompany }</td>
                                <td>
                                    <a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
                                       onclick="delLine('${line.RelationID}');">
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
        <!-- 时间列表 -->
        <div class="tab-pane" id="timeList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="javascript:void(0)" onclick="addTime();">
                        <i class="icon-plus-sign"></i>新增时间
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${prohibit.prohibitTimeList }" var="time">
                            <tr class="odd">
                                <td>${time.BeginTime }</td>
                                <td>${time.EndTime }</td>
                                <td>
                                    <a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
                                       onclick="delTime('${time.ID}');">
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

<div class="addTime" style="display:none">
    <div class="row">
        <div style="margin-left: 50px;">
            <label>开始时间</label>
            <input name="begintime" type="text" class="span3" value="00:00" readonly="readonly"/>
        </div>
    </div>
    <div class="row">
        <div style="margin-left: 50px;">
            <label>结束时间</label>
            <input name="endtime" type="text" class="span3" value="23:59" readonly="readonly"/>
        </div>
    </div>
</div>
</body>
</html>
