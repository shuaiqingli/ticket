<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/lineEdit.js"></script>
    <title>
        <c:if test="${empty lineManage.id}">
            新增线路
        </c:if>
        <c:if test="${not empty lineManage.id}">
            编辑线路
        </c:if>
    </title>
    <link type="text/css" href="${basePath}/common/css/jquery.ui.css">
    <style type="text/css">
        ul, li {
            list-style: none;
            padding: 0px;
            margin: 0px;
        }
    </style>
    <style type="text/css">
        .stationNode li {
            display: inline-block;
            margin-top: 10px;
            margin-bottom: 30px;
            font-family: 黑体;
        }

        /*.point {*/
        /*width: 10px;*/
        /*height: 10px;*/
        /*border: 4px solid #555;*/
        /*border-radius: 100%;*/
        /*float: left;*/
        /*position: relative;*/

        /*}*/

        .line {
            width: 50px;
            height: 1.0px;
            border-top: 6px solid #fff;
            border-bottom: 7px solid #fff;
            background-color: #555;
            float: left;
        }
    </style>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/user/linemanage.do" style="color: black"></a>
            <c:if test="${empty lineManage.id}">
                新增线路
            </c:if>
            <c:if test="${not empty lineManage.id}">
                编辑线路
            </c:if>
        </h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#information" data-toggle="tab">基本信息</a></li>
        <c:if test="${not empty lineManage.id}">
            <li><a href="#driverList" data-toggle="tab">司机列表</a></li>
            <li><a href="#plateList" data-toggle="tab">车牌列表</a></li>
            <li><a href="#lineRuleList" data-toggle="tab">线路规则</a></li>
            <li><a href="#scheduleRuleList" data-toggle="tab">排班规则</a></li>
            <li><a href="#saleRuleList" data-toggle="tab">售票规则</a></li>
            <li><a href="#promotionRuleList" data-toggle="tab">调价规则</a></li>
        </c:if>
    </ul>
    <div class="tab-content">
        <!-- 基本信息 -->
        <div class="tab-pane active" id="information">
            <form method="post" id="adminForm" action="${basePath}/user/lineEdit.do">
                <input type="hidden" name="id" value="${lm.id}">
                <input type="hidden" name="groupid" value="${lm.groupid}">
                <input type="hidden" name="tcid" class="tcid" value="${empty lm.tcid ? user.tcid : lm.tcid }">
                <div class="row">
                    <div style="margin-left: 30px;" class="">
                        <label>运输公司</label>
                        <input readonly="readonly" id="transcompany" type="text" name="transcompany"
                               value="${empty lm.transcompany ? user.companyname : lm.transcompany }" class="span3"
                               datatype="*"
                               nullmsg="运输公司不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>线路编号<span style="color: red;"> (三位字母)</span>
                        </label>
                        <input type="text" name="lineid" value="${lm.lineid }" maxlength="3" class="span3 lineid"
                               datatype="/^[A-Za-z]{3}?.*$/" nullmsg="编号不能为空">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;" class="">
                        <label>车型</label>
                        <select class="span2 carmodelid" datatype="*" nullmsg="车型不能为空" name="carmodelid">
                            <c:forEach var="car" items="${cars}">
                                <option value="${car.id }"
                                        <c:if test='${car.id == lm.carmodelid }'>selected="selected"</c:if> >${car.tagsubvalue }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;" class="">
                        <label>司机数量</label>
                        <select class="span2 driverquantity" datatype="*" nullmsg="司机数量不能为空" name="driverquantity">
                            <option value="1"
                                    <c:if test='${1 == lm.driverquantity }'>selected="selected"</c:if> >1
                            </option>
                            <option value="2"
                                    <c:if test='${2 == lm.driverquantity }'>selected="selected"</c:if> >2
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>始发城市</label>
                        <select class="span2 begincitys" datatype="*" nullmsg="始发城市不能为空"
                                <c:if test="${empty lm.id}">name="citystartid"</c:if>
                                <c:if test="${not empty lm.id}">disabled</c:if>    >
                            <option value="">--选择城市--</option>
                            <c:forEach var="city" items="${citys}">
                                <option value="${city.id }"
                                        <c:if test='${city.id == lm.citystartid }'>selected="selected"</c:if> >${city.cityname }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>到达城市</label>
                        <select class="span2 endcitys" datatype="*" nullmsg="到达城市不能为空"
                                <c:if test="${empty lm.id}">name="cityarriveid"</c:if>
                                <c:if test="${not empty lm.id}">disabled</c:if>  >
                            <option value="">--选择城市--</option>
                            <c:forEach var="city" items="${citys}">
                                <option value="${city.id }"
                                        <c:if test='${city.id == lm.cityarriveid }'>selected="selected"</c:if> >${city.cityname }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>余票告警
                        </label>
                        <input type="text" name="balanceticketwarn" datatype="n"
                               value="${empty lm.balanceticketwarn ? 0:lm.balanceticketwarn}" maxlength="32"
                               class="span3 ">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>
                            是否可以退票
                        </label>
                        <input name="refundstatus" value="0" ${empty lm.id or lm.refundstatus==0?'checked':''} maxlength="50" type="radio">是
                        &nbsp;&nbsp;&nbsp;
                        <input name="refundstatus" value="1" ${lm.refundstatus==1?'checked':''}  maxlength="50" type="radio">否
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>
                            不能退票备注
                        </label>
                        <input name="refundremark" value="${lm.refundremark}" maxlength="50" class="span3 " type="text">
                    </div>
                </div>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>
                            是否可以使用代金券
                        </label>
                        <input name="couponflag" value="1" ${empty lm.couponflag or lm.couponflag==1?'checked':''} maxlength="50" type="radio">是
                        &nbsp;&nbsp;&nbsp;
                        <input name="couponflag" value="2" ${lm.couponflag==2?'checked':''}  maxlength="50" type="radio">否
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div style="margin-left: 30px;">
                        <label>车票详情页备注
                        </label>
                        <input type="text" name="memo" value="${lm.memo }" maxlength="32" class="span3 ">
                    </div>
                </div>

                <div class="row">
                    <div class="span2" style="margin-top:8px;">
                        <input type="submit" class="btn" value="保存">
                    </div>
                    <div class="span2" style="margin-top:8px;">
                        <input type="button" onclick="history.go(-1)" class="btn" value="返回">
                    </div>
                </div>

                <div class="transCompanylist" style="display: none">
                    <br/>

                    <div class="pull-right" style="padding-right:10px;">
                        <input type="text"
                               placeholder="公司名称/拼音" value="" style="height:30px;" class="tagsubvalue">
                        <a class="btn search"
                           style="padding:5px 12px;margin:-8px 0 0 10px;"
                           href="javascript:void(0)">搜索</a>
                    </div>
                    <table class="table table-striped" style="font-size: 14px;">
                        <thead>
                        <tr>
                            <th>公司名称</th>
                            <th>公司简称</th>
                        </tr>
                        </thead>
                        <tr class="template" style="display: none;">
                            <td>
                                <input type="hidden" class="id"/>
                                <input type="radio" name="cp" class="cp" style="margin-top: -3px;">
                                <span class="companyname"></span>
                            </td>
                            <td>
                                <span class="shortname"></span>
                            </td>
                        </tr>
                        <tbody>
                        </tbody>
                    </table>
                </div>

            </form>
        </div>


        <!-- 司机列表 -->
        <div class="tab-pane" id="driverList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="javascript:void(0)" onclick="chooseDriver();">
                        <i class="icon-plus-sign"></i>新增司机
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>手机号码</th>
                            <th>身份证号码</th>
                            <th>是否停用</th>
                            <th>类型</th>
                            <c:if test="${lm.driverquantity == 1}">
                                <th>默认车牌</th>
                            </c:if>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${driverList }" var="driver">
                            <tr class="odd">
                                <td>${driver.DriverID }</td>
                                <td>${driver.DName }</td>
                                <td>${driver.DMobile }</td>
                                <td>${driver.IDCard }</td>
                                <td>${driver.IsStop == 1 ? '是':'否' }</td>
                                <td>${driver.Type == 1 ? '固定司机':'机动司机' }</td>
                                <c:if test="${lm.driverquantity == 1}">
                                    <td>${driver.PlateNum }</td>
                                </c:if>
                                <td>
                                    <a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
                                       onclick="delDriver('${lm.groupid}','${driver.DriverID }');">
                                        <i class="icon-trash icon-white"></i>
                                    </a>
                                    <c:if test="${lm.driverquantity == 1 && driver.Type == 1}">
                                        <a class="btn pull-right btn-mini" href="javascript:void(0)"
                                           onclick="editDriver('${driver.DriverID }');">
                                            <i class="icon-pencil"></i>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <!-- 车牌列表 -->
        <div class="tab-pane" id="plateList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="javascript:void(0)" onclick="choosePlate();">
                        <i class="icon-plus-sign"></i>新增车牌
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>车牌</th>
                            <th>核载</th>
                            <th>车型</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${plateList }" var="plate">
                            <tr class="odd">
                                <td>${plate.id }</td>
                                <td>${plate.platenum }</td>
                                <td>${plate.nuclearload }</td>
                                <td>${plate.carmodelname }</td>
                                <td>
                                    <a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
                                       onclick="delPlate('${lm.groupid}','${plate.id }');">
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

        <!-- 线路规则列表 -->
        <div class="tab-pane" id="lineRuleList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="javascript:void(0)"
                       onclick="location.href = '${basePath}/user/stationTimeRule?isnew=1&lmid=${lm.id}'">
                        <i class="icon-plus-sign"></i>新增线路规则
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>规则名称</th>
                            <th>状态</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="o" items="${rules}">
                            <tr class="odd">
                                <td style="color:green;">
                                        ${o.rulename}
                                    <c:if test="${o.isdefault==1}">
                                        （默认规则）
                                    </c:if>
                                </td>
                                <td style="color: red;">${o.status==0?'未发布':o.status>=1?'已发布':'未排班'}</td>
                                <td>
                                    <div class="btn-group pull-right">
                                        <a class="btn"
                                           href="<%=basePath%>/user/stationTimeRule?id=${o.id}&lmid=${lm.id}">
                                            编辑
                                        </a>
                                        <a class="btn" href="<%=basePath%>/user/copyStationTimeRule?id=${o.id}">
                                            复制
                                        </a>
                                        <a onclick="layer.confirm('你确定要删除该规则吗?',{offset:'40%'},function(){
                                                location.href=basePath+'/user/delStationTimeRule?lmid=${lm.id}&id=${o.id}'
                                                });"
                                           class="btn btn-danger" href="javascript:void(0)" id="${o.id}"
                                           groupid="${o.groupid}">
                                            删除
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <c:forEach var="rule" items="${o.rules}">
                                <tr>
                                    <td>${rule.begintime} 至 ${rule.endtime}</td>
                                    <td colspan="2" style="">
                                        <c:forEach items="${rule.stations}" var="station" varStatus="status">
                                            <c:if test="${status.index!=0 and (status.index)%4 ==0}">
                                                <div style="clear: both"></div>
                                            </c:if>
                                            <span style="float: left;margin-bottom: 15px;margin-top: 3px;">${station.stname}</span>
                                            <div style="position: relative;float: left;padding-top: 5px;">
                                                <c:if test="${status.index!=fn:length(rule.stations)-1}">
                                                        <span style="position:absolute;top: -8px;left: 20%;color: #0c80fe;">
                                                                ${rule.stations[status.index+1].requiretime}
                                                        </span>
                                                    <span class="point" style=""></span>
                                                    <span class="line" style=""></span>
                                                    <span class="point"></span>
                                                </c:if>
                                            </div>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div class="tab-pane" id="scheduleRuleList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn newScheduleRule" href="javascript:void(0)">
                        <i class="icon-plus-sign"></i>新增排班规则
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>规则名称</th>
                            <th>内容</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="schedule" items="${schedules}" varStatus="status">
                            <tr class="odd">
                                <td style="color:green;">
                                        ${schedule.scheduname}
                                    <c:if test="${schedule.isdefault==1}">
                                        （默认规则）
                                    </c:if>
                                </td>
                                <td style="color:green;">
                                    <c:if test="${fn:length(schedule.schedules)>0}">
                                        始发时间：${schedule.schedules[0].starttime} &nbsp;&nbsp;${fn:length(schedule.schedules)}班
                                    </c:if>
                                </td>
                                <td>
                                    <div class="btn-group pull-right">
                                        <a class="btn"
                                           href="javascript:void(0)" onclick="editScheduleRule(basePath+'/user/lineScheduleRule?id=${schedule.id}&lineid='+$lineid)">
                                            编辑
                                        </a>
                                        <a class="btn" href="<%=basePath%>/user/copyLineSchedule.do?id=${schedule.id}&lineid=${lm.lineid}">
                                            复制
                                        </a>
                                        <a onclick="layer.confirm('你确定要删除该规则吗?',{offset:'40%'},function(){ location.href=basePath+'/user/delschedule.do?id=${schedule.id}&lmid=${lm.id}' });"
                                           class="btn btn-danger" href="javascript:void(0)" id="${schedule.id}"
                                           groupid="${o.groupid}">
                                            删除
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <c:forEach var="scheduleDetail" items="${schedule.schedules}" >
                                <tr class="schedules schedules${status.index}" index="${status.index}" style="display: none;">
                                    <td></td>
                                    <td>${scheduleDetail.starttime}</td>
                                    <td>${scheduleDetail.shiftcode}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <!-- 售票规则 -->
        <div class="tab-pane" id="saleRuleList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn newSaleRule" href="javascript:void(0)" onclick="">
                        <i class="icon-plus-sign"></i>新增售票规则
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>规则名称</th>
                            <th>总票数（张）</th>
                            <th>锁票数（张）</th>
                            <th>线路规则</th>
                            <th class="pull-right">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${tplRule}" var="tp">
                                <tr>
                                    <td>
                                        ${tp.tplname}${tp.isdefault==1?'（默认规则）':''}
                                        <c:if test="${fn:length(tp.differStations)!=0}">
                                            <div style="font-size: 12px;padding-top: 5px;">
                                                <span>缺少站点：</span>
                                                <c:forEach items="${tp.differStations}" var="station" varStatus="status">
                                                   <span style="color: red;">${station.stname}</span>
                                                    ${status.index!=fn:length(tp.differStations)-1?'、':''}
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </td>
                                    <td>${tp.ticketquantity}</td>
                                    <td>${tp.lockquantity}</td>
                                    <td>${tp.strname}</td>
                                    <td>
                                        <div class="btn-group pull-right">
                                            <a class="btn"
                                               href="javascript:void(0)" onclick=" newSaleRule(basePath+'/user/lineSaleRule?id=${tp.id}&lmid='+lmid);">
                                                编辑
                                            </a>
                                            <a class="btn" href="<%=basePath%>/user/copyLineSaleRule?id=${tp.id}">
                                                复制
                                            </a>
                                            <a onclick="layer.confirm('你确定要删除该规则吗?',{offset:'40%'},function(){ location.href=basePath+'/user/delSaleRule?id=${tp.id}&lmid=${lm.id}' });" class="btn btn-danger" href="javascript:void(0)"> 删除 </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- 调价规则 -->
        <div class="tab-pane" id="promotionRuleList">
            <div class="row">
                <div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
                    <a class="btn" href="${basePath}/user/promotiontoadd.do?lmid=${lm.id}&type=1" onclick="">
                        <i class="icon-plus-sign"></i>新增调价规则
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <table class="table table-striped" style="font-size: 14px;">
                        <thead>
                        <tr>
                            <th>规则名称</th>
                            <th>适用时间段</th>
                            <th>调价幅度</th>
                            <%--<th>优惠票百分比</th>--%>
                            <%--<th>是否启用</th>--%>
                            <th class="pull-right">操作&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${promotions}" var="p">
                            <tr class="odd">
                                <td>${p.promotionname }${p.isdefault==1?'(默认规则)':''}</td>
                                <td>
                                    <c:forEach var="time" items="${p.times}">
                                        ${time.begintime}-${time.endtime}
                                        <br/>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach var="time" items="${p.times}">
                                        ${time.reducesum}元
                                        <br/>
                                    </c:forEach>
                                </td>
                                <%--<td>--%>
                                    <%--<c:forEach var="time" items="${p.times}">--%>
                                        <%--${time.couponpercent}%--%>
                                        <%--<br/>--%>
                                    <%--</c:forEach>--%>
                                <%--</td>--%>
                                <%--<td>${p.isenablename}</td>--%>
                                <td>
                                    <div class="btn-group pull-right">
                                        <a class="btn" href="<%=basePath%>/user/promotiontoedit.do?id=${p.id}&lmid=${lm.id}&type=1">
                                            <i class="icon-pencil"></i> 编辑
                                        </a>
                                        <a class="btn btn-danger deleteLine" href="<%=basePath%>/user/promotiondel.do?id=${p.id}&lmid=${lm.id}&type=1">
                                            <i class="icon-remove icon-white"></i> 删除
                                        </a>
                                    </div>
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

<div class="layer_dialog" style="display:none;" id="driverEditDialog">
    <input type="hidden" name="driverid">
    <div style="margin: 10px 10px 10px 10px;">
        <label>
            <span class="title">车牌号 :</span>
            <select></select>
        </label>
    </div>
    <div class="row" style="margin-top: 20px;margin-bottom: 20px;text-align: center;">
        <input type="submit" value="保存" onclick="bindPlateToDriver();">
        <input type="reset" value="取消" onclick="layer.closeAll();">
    </div>
</div>

</body>
<script type="text/javascript" src="${basePath}/common/js/jquery.ui.min.js"></script>
<script type="text/javascript">
    var $isallowupdate = "${lm.isallowupdate}";
    var $id = "${lm.id}";
    var $lineid = "${lm.lineid}";
    $(".sel-line-list .check-btn").live("click", function () {
        var item = $(".show-line-list");
        var checks = $(this).attr("checks");
        var citysName = $(this).attr("name");
        if (checks == "0") {
            btnCheck($(this));
            showStarts(citysName, item);

        } else {
            btnUncheck($(this));
            hideStarts(citysName, item);
            btnUncheck($(".chooseAll"));
        }
    });

    $(".sel-line-endlist .check-btn").live("click", function () {
        var item = $(".show-line-endlist");
        var checks = $(this).attr("checks");
        var citysName = $(this).attr("name");
        if (checks == "0") {
            btnCheck($(this));
            showStarts(citysName, item);

        } else {
            btnUncheck($(this));
            hideStarts(citysName, item);
            btnUncheck($(".chooseAllEnd"));
        }
    });

    function btnCheck(item) {
        item.attr("checks", "1");
        item.css("background-color", "black");
    }
    function btnUncheck(item) {
        item.attr("checks", "0");
        item.css("background-color", "white");
    }
</script>
<script type="text/javascript">
    startCityId = "${lm.citystartid}";
    startStationId = "${lm.ststartid}";
    endCityId = "${lm.cityarriveid}";
    endStationId = "${lm.starriveid}";
    endStations = null;
    startStations = null;
    beginStationSelect = null;
    endStationSelect = null;
    lineManageStationList = null;
    isChange = true;
    isUpdate = false;
    lmid = "${lm.id}";

    $(function (e) {
        //提交表单
        $("form").Validform({
            tiptype: 4,
            postonce: true,
            ajaxPost: true,
            beforeCheck: function (curform) {
                return true;
            },
            beforeSubmit: function (curform) {

            },
            callback: function (data) {
                if (data >= 1) {
                    layer.msg('操作成功');
                    location.href = basePath + "/user/linemanage.do"
                } else if (data == -100) {
                    layer.msg('该线路编号已经存在');
                } else {
                    layer.msg('操作失败');
                }
            }
        });


    });
</script>
<script type="text/javascript" src="${basePath}/common/js/jquery.ui.js"></script>
<script type="text/javascript">
    $(function () {

        $('#transcompany').click(function () {
            if ("${user.isAdmin}" != "1") {
                return;
            }
            layer.open({
                type: 1,
                closeBtn: false,
                scrollbar: true,
                closeBtn: 1,
                btn: ['确定'],
                offset: '15%',
                area: ['500', '500px'],
                title: '运输公司',
                content: $('.transCompanylist'),
                yes: function (index, dom) {
                    console.debug(dom);
                    var transCompany = dom.find("input:checked").parents("tr").find(".shortname").text();
                    console.debug(transCompany);
                    if (transCompany == '' || transCompany == undefined) {
                        return;
                    }
                    $("#transcompany").val(transCompany).next().hide();
                    $('.tcid').val(dom.find("input:checked").prev().val());
                },
                success: function () {
                    findreslist();
                }
            });
        });

        $('.transCompanylist').find('.search').click(function () {
            findreslist();
        });

    });

    function findreslist() {
        var params = {};
        params.type = "json";
        params.companyname = $('.transCompanylist').find(".tagsubvalue").val();
        $('.transCompanylist').find('.template').not($('.transCompanylist').find('.template').eq(0)).remove();
        ajax(params, basePath + "/admin/companylist", function (json) {
            jeach(json.data, function (k, v) {
                var t = $('.transCompanylist').find('.template').eq(0).clone();
                var params = {};
                params.$ = t;
                t.find('.id').val(v.id);
                t.find('.companyname').text(v.companyname);
                t.find('.shortname').text(v.shortname);
// 			jeach(v,function(){
// 				return params;
// 			});
                $('.transCompanylist').find('.template').last().after(t.show());
            });
        })
    }

</script>
</html>