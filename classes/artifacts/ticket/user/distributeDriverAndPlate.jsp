<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/distributeDriverAndPlate.js"></script>
    <script type="text/javascript" src="<%=basePath%>/common/js/jquery.ui.min.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/common/css/jquery.ui.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/distributeDriverAndPlate.css">
    <title>司机排班</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <input type="hidden" name="lineid" value="${param.id}">
    <div style="overflow: hidden;height: 50px;line-height: 50px;font-size: 25px;border-bottom: 1px solid #ccc;">
        <div style="float:left;">司机排班</div>
        <div style="float:right;" class="date" groupid="${data.groupid}" date="${data.date}">${data.date}</div>
    </div>
    <div style="border-bottom: 1px solid #ccc;" class="cityA" cityID="${data.dataMapForCityA.line.cityStartID}">
        <div class="main_tit cityName"><img src="images/CITY_03.png">${data.dataMapForCityA.line.cityStartName}/${data.dataMapForCityA.line.lineID}</div>
        <div style="text-align: center;overflow: hidden;width: initial;">
            <div class="cell_mod">
                <div class="tit">班次</div>
                <div class="cont ui-sortable shiftStartList" style="cursor: default;">
                    <c:forEach items="${data.dataMapForCityA.shiftStartList}" var="shiftStart" varStatus="i">
                        <div class="cell">
                            <span class="index">${i.count}</span>
                            ${shiftStart.ShiftCode}<span style="border-left:1px solid ;margin: 6px 8px 0px;height: 14px;display: inline-block;line-height: 30px;"></span>${shiftStart.OriginStartTime}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="cell_mod">
                <div class="tit">车牌</div>
                <div class="cont ui-sortable plateList work">
                    <c:forEach items="${data.dataMapForCityA.plateListForCity}" var="plate" varStatus="i">
                        <div class="cell" plateid="${plate.ID}">
                            <span class="index">${i.count}</span>${plate.PlateNum}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="cell_mod">
                <div class="tit">不排班车牌</div>
                <div class="cont ui-sortable plateList"></div>
            </div>
            <div class="cell_mod">
                <div class="tit">司机${data.driverQuantity ==2 ? '(1)' : ''}</div>
                <div class="cont ui-sortable driverList work">
                    <c:forEach items="${data.dataMapForCityA.driverListWithFixedForCity}" var="driver" varStatus="i">
                        <div class="cell" driverid="${driver.ID}">
                            <span class="index">${i.count}</span>${driver.DName}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${data.driverQuantity == 2}">
                <div class="cell_mod">
                    <div class="tit">司机(2)</div>
                    <div class="cont ui-sortable driverList work">
                        <c:forEach items="${data.dataMapForCityA.driverList2WithFixedForCity}" var="driver" varStatus="i">
                            <div class="cell" driverid="${driver.ID}">
                                <span class="index">${i.count}</span>${driver.DName}
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="cell_mod">
                <div class="tit">机动司机</div>
                <div class="cont ui-sortable driverList">
                    <c:forEach items="${data.dataMapForCityA.driverListWithoutFixedForCity}" var="driver" varStatus="i">
                        <div class="cell" driverid="${driver.ID}">
                            <span class="index">${i.count}</span>${driver.DName}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="cell_mod">
                <div class="tit">不排班司机</div>
                <div class="cont ui-sortable driverList"></div>
            </div>
        </div>
    </div>
    <div style="border-bottom: 1px solid #ccc;" class="cityB" cityID="${data.dataMapForCityB.line.cityStartID}">
        <div class="main_tit cityName"><img src="images/CITY_03.png">${data.dataMapForCityB.line.cityStartName}/${data.dataMapForCityB.line.lineID}</div>
        <div style="text-align: center;overflow: hidden;width: initial;">
            <div class="cell_mod">
                <div class="tit">班次</div>
                <div class="cont ui-sortable shiftStartList" style="cursor: default;">
                    <c:forEach items="${data.dataMapForCityB.shiftStartList}" var="shiftStart" varStatus="i">
                        <div class="cell">
                            <span class="index">${i.count}</span>
                            ${shiftStart.ShiftCode}<span style="border-left:1px solid ;margin: 6px 8px 0px;height: 14px;display: inline-block;line-height: 30px;"></span>${shiftStart.OriginStartTime}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="cell_mod">
                <div class="tit">车牌</div>
                <div class="cont ui-sortable plateList work">
                    <c:forEach items="${data.dataMapForCityB.plateListForCity}" var="plate" varStatus="i">
                        <div class="cell" plateid="${plate.ID}">
                            <span class="index">${i.count}</span>${plate.PlateNum}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="cell_mod">
                <div class="tit">不排班车牌</div>
                <div class="cont ui-sortable plateList"></div>
            </div>
            <div class="cell_mod">
                <div class="tit">司机${data.driverQuantity ==2 ? '(1)' : ''}</div>
                <div class="cont ui-sortable driverList work">
                    <c:forEach items="${data.dataMapForCityB.driverListWithFixedForCity}" var="driver" varStatus="i">
                        <div class="cell" driverid="${driver.ID}">
                            <span class="index">${i.count}</span>${driver.DName}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${data.driverQuantity == 2}">
                <div class="cell_mod">
                    <div class="tit">司机(2)</div>
                    <div class="cont ui-sortable driverList work">
                        <c:forEach items="${data.dataMapForCityB.driverList2WithFixedForCity}" var="driver" varStatus="i">
                            <div class="cell" driverid="${driver.ID}">
                                <span class="index">${i.count}</span>${driver.DName}
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="cell_mod">
                <div class="tit">机动司机</div>
                <div class="cont ui-sortable driverList">
                    <c:forEach items="${data.dataMapForCityB.driverListWithoutFixedForCity}" var="driver" varStatus="i">
                        <div class="cell" driverid="${driver.ID}">
                            <span class="index">${i.count}</span>${driver.DName}
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="cell_mod">
                <div class="tit">不排班司机</div>
                <div class="cont ui-sortable driverList"></div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="span2" style="margin-top:12px;">
            <input type="submit" class="btn" value="排班" onclick="saveOrPreview(1);">
            <input type="submit" class="btn" value="预览" onclick="saveOrPreview(2);">
        </div>
    </div>

</div>

<%@include file="../common/footer.jsp" %>

<div class="layer_dialog" style="display:none;max-height: 600px;" id="previewDialog">
    <div style="margin: 10px 10px 40px 10px;">
        <div style="border-bottom: 1px solid #ccc;">
            <h3>广州/SAZ</h3>
            <table class="table table-striped" style="font-size: 14px;">
                <thead></thead>
                <tbody></tbody>
            </table>
        </div>
        <div style="border-bottom: 1px solid #ccc;">
            <h3>广州/SAZ</h3>
            <table class="table table-striped" style="font-size: 14px;">
                <thead></thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
