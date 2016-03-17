<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/lineScheduleRule.js"></script>
    <title>线路规则</title>
</head>

<body>
<div class="container main_container">

    <div
         style="display: block;width: 800px;">
        <div  style="font: normal 17px '黑体';">
            <input value="" name="id" style="width: 150px;" type="hidden">
            <div class="row params" style="margin-left: 20px;margin-top: 30px;">
                <span>排班名称：</span>
                <input class="notnull" name="scheduname" style="width: 350px;" placeholder="排班名称" type="text">

            </div>
            <hr/>
            <div class="row params" style="margin-left: 20px;margin-top: 30px;">
                <span>始发时间：</span>
                <input class="starttime first_time" value="07:00" readonly="readonly" style="width: 150px;" placeholder="始发时间" type="text">
                <span>结束时间：</span>
                <input class="endtime last_time" value="18:00"  readonly="readonly" style="width: 150px;" placeholder="结束时间" type="text">
                <br/>
                <span>间隔时间：</span>
                <input class="" style="width: 150px;" name="intevalminute" placeholder="间隔时间" type="text" maxlength="3" onkeyup="changeTimeShifNum(this)">
                <span>班次数量：</span>
                <input class="" style="width: 150px;" name="shiftnum"  placeholder="班次数量" type="text" maxlength="2" onkeyup="changeTimeShifNum(this)">
                <br/>
                <br/>
                <input value="确定" class="btn" style="height:30px;width: 15%;font-size: 15px;" type="button">
            </div>
            <hr/>
            <div class="editScheduleRule" style="font: normal 17px '黑体';">
                <div class="rule">
                    <input value="153" style="display: inline;" class="id" type="hidden">
                    <input value="1" style="display: inline;" class="isshift" type="hidden">
                    <input value="146" style="display: inline;" class="lsid" type="hidden">
                </div>
                <div class="shiftdetail">
                    <div style="margin-left: 30px;">
                        间隔时间 ：<input class="intevalminute" type="text" maxlength="3">
                        <input class="btn btn_changestarttime" value="确定" style="margin-top: -7px;" type="button">
                    </div>
                    <br>
                    <div class="row" style="margin-left: 5px;">
                        <div class="span1" style="width: 10px;">
                            <input class="chooseAllShif" type="checkbox">
                        </div>
                        <div class="span1">班次号</div>
                        <div class="span1">发车时间</div>
                        <div class="span"></div>
                        <div class="span"></div>
                    </div>
                    <div class="row scheduleDetail" style="margin-left: 5px;padding-top: 10px;display: none;">
                        <div class="span1" style="width: 10px;">
                            <input class="updatetime" type="checkbox">
                        </div>
                        <div class="span1 shiftcode">
                            &nbsp;
                        </div>
                        <div class="span1">
                            <input value="00:00" readonly="readonly" style="width: 85px;" class="starttime" type="text">
                        </div>
                        <div class="span1">
                            <input class="btn btn_adddetail" value="新增" type="button">
                        </div>
                        <div class="span1">
                            <input class="btn btn_deldetail" value="删除" type="button">
                            <input style="display: inline;" class="isdel" value="0" type="hidden">
                            <input style="display: inline;" class="id" type="hidden">
                        </div>
                    </div>
                    <div class="row scheduleDetail" style="margin-left: 5px; padding-top: 10px; display: block;">
                        <div class="span1" style="width: 10px;">
                            <input class="updatetime" type="checkbox">
                        </div>
                        <div class="span1 shiftcode">CAS01</div>
                        <div class="span1">
                            <input value="00:00" readonly="readonly" style="width: 85px;" class="starttime" type="text">
                        </div>
                        <div class="span1">
                            <input class="btn btn_adddetail" value="新增" type="button">
                        </div>
                        <div class="span1">
                            <input class="btn btn_deldetail" value="删除" type="button">
                            <input style="display: inline;" class="isdel" value="0" type="hidden">
                            <input value="1130" style="display: inline;" class="id" type="hidden">
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>

</div>
</body>
</html>
