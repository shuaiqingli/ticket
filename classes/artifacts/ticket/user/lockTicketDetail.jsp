<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>>
    <script type="text/javascript" src="<%=basePath%>/js/lockTicketDetail.js"></script>
    <title>车票详情</title>
    <style type="text/css">
        .chair{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;cursor: pointer;
        }
        .unsel{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;
        }
        .sold_out{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;background-color: #ccc;color: #fff;cursor: default;
        }
        .presale{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;background-color: #3FC7A3;color: #fff;
        }
        .lock{
            margin-bottom: 5px;width: 20px;height: 20px;border: 1px solid #ccc;background-color: #211DD0;color: #fff;
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
    <input type="hidden" name="id" value="${param.id}">
    <div style="width: 90%;margin: 0 auto;">
        <table style="width: 100%;">
            <tr>
                <td>${ticketLine.linename}/${fn:substring(ticketLine.shiftcode, 0, 3)}</td>
                <td><fmt:formatDate value="${ticketDate}" pattern="MM-dd"/>/<fmt:formatDate value="${ticketDate}" pattern="E"/></td>
                <td style="color: #ff6861;"><img src="wx/images/surpri.png" style="height: 15px;margin-right: 5px;">${ticketLine.showcontent}</td>
            </tr>
        </table>
        <div class="record_item" style="overflow:hidden;margin-bottom: 10px;background-color: #fff;border:1px solid #ccc;">
            <div style="display: inline-block;width: 20%;float: left;">
                <div style="height: 40px;line-height: 40px;text-indent: 10px;font-size: 18px;font-weight: bold;">${ticketLine.starttime}</div>
                <div style="height: 40px;line-height: 40px;text-indent: 10px;font-size: 18px;font-weight: bold;">${ticketLine.shiftcode}</div>
            </div>
            <div style="display: inline-block;width: 20%;float: left;">
                <div style="height: 40px;line-height: 40px;"><div style="display: inline-block;background-color: #63B263;color: #fff;height: 17px;line-height: 17px;font-size: 13px;padding: 0 2px;border-radius: 3px;position:relative;right: 5px;">始</div>${ticketLine.ststartname}</div>
                <div style="height: 40px;line-height: 40px;"><div style="display: inline-block;background-color: #ff6861;color: #fff;height: 17px;line-height: 17px;font-size: 13px;padding: 0 2px;border-radius: 3px;position:relative;right: 5px;">终</div>${ticketLine.starrivename}</div>
            </div>
            <div style="display: inline-block;width: 20%;float: left;">
                <div style="height: 25px;line-height: 25px;">余票:<span style="color:#63B263;font-weight: bold;">${ticketLine.balancequantity}</span>张</div>
                <div style="height: 25px;line-height: 25px;">已售:<span style="color:#ff6861;font-weight: bold;">${ticketLine.allquantity - ticketLine.balancequantity - ticketLine.lockquantity}</span>张(预售<span style="color:#3FC7A3;font-weight: bold;">${fn:length(ticketLine.lockedseatnolist)}</span>张)</div>
                <div style="height: 25px;line-height: 25px;">已锁:<span style="color:#211DD0;font-weight: bold;">${ticketLine.lockquantity}</span>张</div>
            </div>
            <div style="display: inline-block;width: 40%;float: left;height: 80px;line-height: 80px;text-align: center;color: #ff6861;font-size: 25px;font-weight: bold;">
                &yen;${ticketLine.ticketprice}
            </div>
        </div>
        <div>
            <c:if test="${not empty seatList}">
                <div class="seat_container" >
                    <div style="text-align: center;">
                        <div style="display:inline-block;">
                            <table>
                                <c:forEach begin="0" end="${fn:length(seatList)/4 + (fn:length(seatList) % 4 == 0 ? -1 : 0)}" var="i">
                                    <tr>
                                        <c:forEach begin="0" end="3" var="j">
                                            <c:choose>
                                                <c:when test="${i*4+j+1 > fn:length(seatList)}"><td></td></c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <div chairNo='${seatList[i*4+j].seatno}' class="chair
                                                            <%--<c:if test="${seatList[i*4+j].issale == 1}">sold_out</c:if>--%>
                                                            <c:if test="${seatList[i*4+j].issale == 2}">lock</c:if>
                                                            <c:if test="${seatList[i*4+j].issale == 1}">
                                                                sold_out
                                                                <c:set var="ispresale" value="false" />
                                                                <c:forEach items="${ticketLine.lockedseatnolist}" var="seatno">
                                                                    <c:if test="${seatno.SeatNO eq seatList[i*4+j].seatno}">
                                                                        <c:set var="ispresale" value="true" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                <c:if test="${ispresale}">presale</c:if>
                                                            </c:if>
                                                            ">${seatList[i*4+j].seatno}</div>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                    <div class="chair_items" style="display: inline-block;border: 1px solid #ccc;display: none;margin-top: 10px;margin-right: 10px;margin-bottom: 10px;"><span class="chairNo">1</span>号</div>
                    <div style="border-bottom: 1px solid #ccc;line-height: 30px;">座位号</div>
                    <div class="show_list" style="border-bottom: 1px solid #ccc;height: 40px;">

                    </div>
                </div>
            </c:if>
            <div style="border-bottom: 1px solid #ccc;line-height: 30px;">联系人信息</div>
            <div style="text-align: center;margin-top: 90px;">
                <div style="display:inline-block;">
                    <table cellspacing="10px;">
                        <tr <c:if test="${not empty seatList}">style="display: none;" </c:if>>
                            <td style="line-height: 30px;">数量</td>
                            <td><input type="text" style="padding: 10px 10px;" name="quantity" onkeyup="value=value.replace(/[^0-9]/g,'')"/></td>
                        </tr>
                        <tr>
                            <td style="line-height: 30px;">姓名</td>
                            <td><input type="text" style="padding: 10px 10px;" name="name"/></td>
                        </tr>
                        <tr>
                            <td style="line-height: 30px;">电话</td>
                            <td><input type="text" maxlength="11" style="padding: 10px 10px;" name="mobile"/></td>
                        </tr>
                    </table>
                </div>
                <div>
                    <a class="submit_btn" style="display:inline-block;border: 1px solid #ccc;line-height: 40px;width: 80px;border-radius: 5px;cursor: pointer;margin-top: 50px;" href="javascript:void(0)" onclick="buy();">提交</a>
                </div>
            </div>

        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
