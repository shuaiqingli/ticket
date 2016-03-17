<%@ page import="com.hengyu.ticket.util.DateUtil" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/common/css/slick.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/lockTicketManage.css">
    <script type="text/javascript" src="<%=basePath%>/common/js/slick.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/lockTicketManage.js"></script>
    <title>锁票管理</title>
    <style type="text/css">
        .slider > div {
            background-color: #fff;
            border: 1px solid #ccc;
        }

        .sel_opt {
            cursor: pointer;
            font-size: 13px;
        }

        .content h3 {
            text-align: center;
            background-color: #eee;
            margin: 10px 10px;
            cursor: pointer;
            line-height: 40px;
            font-size: 18px;
            color: #555;
        }
        .chair{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;
        }
        .unsel{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;
        }
        .sold_out{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;background-color: #ccc;color: #fff;
        }
        .sel{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #63B263;background-color: #63B263;color: #fff;
        }
        .submit_btn:hover{
            background-color: #ccc;
            color: #fff;
        }
    </style>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="row">
        <h3>锁票管理</h3>

        <div class="pull-left" style="padding-right:10px;width: 100%;" id="startArea">
            <span>出发地点:</span>
            <select name="startCity" class="span2" onchange="refreshStation();">
                <c:forEach items="${cityList}" var="city">
                    <option value="${city.id}">${city.cityname}</option>
                </c:forEach>
            </select>
            <select name="startStation" class="span2">
                <option value="">--请选择站点--</option>
            </select>
        </div>
        <div class="pull-left" style="padding-right:10px;width: 100%;" id="endArea">
            <span>到达地点:</span>
            <select name="endCity" class="span2" onchange="refreshStation();">
                <c:forEach items="${cityList}" var="city" varStatus="i">
                    <option value="${city.id}"
                            <c:if test="${i.index == 1}">selected</c:if> >${city.cityname}</option>
                </c:forEach>
            </select>
            <select name="endStation" class="span2">
                <option value="">--请选择站点--</option>
            </select>
        </div>
        <div class="pull-left" style="padding-right:10px;">
            <span>出发日期:</span>
            <input type="text" name="startDate" placeholder="出发日期" style="height:30px;" readonly="readonly"
                   value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd"/>">
            <a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="javascript:void(0)"
               onclick="searchShiftStartData();">搜索</a>
            <a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="<%=basePath%>/user/freezeTicketList.do">锁定列表</a>
            <a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="<%=basePath%>/user/lockTicketList.do">售票列表</a>
        </div>
    </div>
    <div class="row" style="height: 100px;margin-top: 30px;" id="dateList">
        <div class="content">
            <div class="slider single-item">
                <% int num = 0;%>
                <c:forEach begin="0" end="14" step="1">
                    <c:set var="currentDate" value="<%=DateUtil.getAfterDate(new Date(), num++)%>"/>
                    <div>
                        <h3 date="<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/>">
                            <fmt:formatDate value="${currentDate}" pattern="MM-dd"/><br>
                            <fmt:formatDate value="${currentDate}" pattern="E"/>
                        </h3>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 30px;" id="timeList">
        <div style="background-color: #fff;color: #555;border:1px solid #ccc;padding: 10px 10px;">
            <table>
                <tr>
                    <td style="font-weight: bold;">发车时间:</td>
                    <td class="sel_opt" startTime="00:00" endTime="23:59">&nbsp;不限&nbsp;</td>
                    <td class="sel_opt" startTime="06:00" endTime="12:00">&nbsp;上午(6:00~12:00)&nbsp;</td>
                    <td class="sel_opt" startTime="12:00" endTime="18:00">&nbsp;下午(12:00~18:00)&nbsp;</td>
                    <td class="sel_opt" startTime="18:00" endTime="22:00">&nbsp;晚上(18:00~22:00)&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row">
        <div style="color:#555;margin-top: 10px;font-size: 16px;" class="record_list">
            <div class="record_item"
                 style="overflow:hidden;margin-bottom: 10px;background-color: #fff;border:1px solid #ccc;display: none;">
                <div style="display: inline-block;width: 20%;float: left;">
                    <div style="height: 40px;line-height: 40px;text-indent: 10px;font-size: 18px;font-weight: bold;"
                         class="starttime"></div>
                    <div style="height: 40px;line-height: 40px;text-indent: 10px;font-size: 14px;"
                         class="shiftcode"></div>
                </div>
                <div style="display: inline-block;width: 20%;float: left;">
                    <div style="height: 40px;line-height: 40px;">
                        <div style="display: inline-block;background-color: #63B263;color: #fff;height: 17px;line-height: 17px;font-size: 13px;padding: 0 2px;border-radius: 3px;position:relative;right: 5px;">
                            始
                        </div>
                        <span class="ststartname"></span>
                    </div>
                    <div style="height: 40px;line-height: 40px;">
                        <div style="display: inline-block;background-color: #ff6861;color: #fff;height: 17px;line-height: 17px;font-size: 13px;padding: 0 2px;border-radius: 3px;position:relative;right: 5px;">
                            终
                        </div>
                        <span class="starrivename"></span>
                    </div>
                </div>
                <div style="display: inline-block;width: 20%;float: left;">
                    <div style="height: 25px;line-height: 25px;">余票:<span style="color:#63B263;font-weight: bold;"
                                                                          class="balancequantity"></span>张
                    </div>
                    <div style="height: 25px;line-height: 25px;">已售:<a style="cursor: pointer;"><span style="color:#ff6861;font-weight: bold;"
                                                                          class="saledquantity"></span></a>张
                        (预售<span style="color:#3FC7A3;font-weight: bold;" class="prepayquantity"></span>张)
                    </div>
                    <div style="height: 25px;line-height: 25px;">已锁:<span style="color:#211DD0;font-weight: bold;"
                                                                          class="lockquantity"></span>张
                    </div>
                </div>
                <div style="display: inline-block;width: 20%;float: left;height: 80px;line-height: 80px;text-align: center;color: #ff6861;font-size: 25px;font-weight: bold;">
                    &yen;<span class="ticketprice">100</span><span class="showcontent" style="color: grey;font-size: 14px;font-weight:normal;"></span>
                </div>
                <div style="display: inline-block;width: 20%;float: left;height: 80px;line-height: 80px;text-align: center;">
                    <div style="height: 50px;line-height: 50px;margin: 15px 0;width: 3em;background-color: #63B263;color:#fff;display: inline-block;cursor:pointer;width: 60px;border-radius: 5px;"
                        class="unfreeze">
                        解锁
                    </div>
                    <div style="height: 50px;line-height: 50px;margin: 15px 0;width: 3em;background-color: #63B263;color:#fff;display: inline-block;cursor:pointer;width: 60px;border-radius: 5px;"
                        class="freeze">
                        锁定
                    </div>
                    <div style="height: 50px;line-height: 50px;margin: 15px 0;width: 3em;background-color: #ff6861;color:#fff;display: inline-block;cursor:pointer;width: 60px;border-radius: 5px;"
                         class="buy">售票
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>

<div class="layer_dialog" style="display:none;" id="freezeDialog">
    <input type="hidden" name="id">
    <div style="margin: 10px 10px 10px 10px;">
        <div class="seat_container" >
            <div style="text-align: center;">
                <div style="display:inline-block;">
                    <table></table>
                </div>
            </div>
            <div class="chair_items" style="display: inline-block;border: 1px solid #ccc;display: none;margin-top: 10px;margin-right: 10px;margin-bottom: 10px;"><span class="chairNo">1</span>号</div>
            <div style="border-bottom: 1px solid #ccc;line-height: 30px;">座位号</div>
            <div class="show_list" style="border-bottom: 1px solid #ccc;height: 40px;"></div>
        </div>
        <label class="quantity">
            <span class="title">数量 :</span>
            <input type="text" name="quantity" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
        </label>
    </div>
    <div class="row" style="margin-top: 20px;margin-bottom: 20px;text-align: center;">
        <input type="submit" value="保存" onclick="freeze();">
        <input type="reset" value="取消" onclick="layer.closeAll();">
    </div>
</div>

<div class="layer_dialog" style="display:none;" id="unfreezeDialog">
    <input type="hidden" name="id">
    <div style="margin: 10px 10px 10px 10px;">
        <div class="seat_container" >
            <div style="text-align: center;">
                <div style="display:inline-block;">
                    <table></table>
                </div>
            </div>
            <div class="chair_items" style="display: inline-block;border: 1px solid #ccc;display: none;margin-top: 10px;margin-right: 10px;margin-bottom: 10px;"><span class="chairNo">1</span>号</div>
            <div style="border-bottom: 1px solid #ccc;line-height: 30px;">座位号</div>
            <div class="show_list" style="border-bottom: 1px solid #ccc;height: 40px;"></div>
        </div>
        <label class="quantity">
            <span class="title">数量 :</span>
            <input type="text" name="quantity" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
        </label>
    </div>
    <div class="row" style="margin-top: 20px;margin-bottom: 20px;text-align: center;">
        <input type="submit" value="保存" onclick="unfreeze();">
        <input type="reset" value="取消" onclick="layer.closeAll();">
    </div>
</div>

</body>
</html>
