<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>编辑线路规则</title>
</head>
<script type="text/javascript" src="${basePath}/common/js/jquery.ui.min.js"></script>
<script type="text/javascript" src="${basePath}/js/lineStationTimeRule.js"></script>
<script type="text/javascript">
    var isnew = "${param.isnew}";
    var $lmid = "${param.lmid}";
    $(function () {
        $("#sortable").sortable({
            items: "tr:not(.ui-state-disabled)"
        });
        $("#sortable1").sortable({
            items: "tr:not(.ui-state-disabled)"
        });
        $("#sortable2").sortable({
            items: "tr:not(.ui-state-disabled)"
        });
        $("#sortable3").sortable({
            items: "tr:not(.ui-state-disabled)"
        });
        $(".sortable").sortable({
            items: "tr:not(.ui-state-disabled)"
        });
        $(".extend").click(function () {
            if ($(this).attr('on') == 1) {
                $(this).attr('on', '0');
                $(this).find('img').attr("src", basePath + "/images/pointer1.png").attr("style", "position:relative;top:5px;");
                $(this).parent().parent().find(".cell_content").hide();
            } else {
                $(this).attr('on', '1');
                $(this).find('img').attr("src", basePath + "/images/pointer2.png").attr("style", "");
                $(this).parent().parent().find(".cell_content").show();
            }
        });
        $(".del_btn").click(function () {
            var $this = $(this);
            layer.confirm("您确定要删除该时间段规则吗？",{offset:"30%"},function(){
                $this.parents('.cell_item').remove();
            })
        });
    });
</script>
<style type="text/css">
    .container {
        width: 90%;
        margin: 0 auto;
    }

    #sortable tr {
        line-height: 40px;
    }

    #sortable1 tr {
        line-height: 40px;
    }

    #sortable2 tr {
        line-height: 40px;
    }

    .sortable tr {
        line-height: 40px;
    }

    .del_btn:hover {
        color: red;
    }
</style>


<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container" style="padding-top: 70px;">
    <form action="javascript:void(0)" method="get">
        <div class="row">
            <div class="span6" style="font: normal 22px '黑体';margin-top: 10px;margin-left: 80px;">
                &nbsp;&nbsp;&nbsp;${lm.lineid }（ ${lm.citystartname }${lm.ststartname }
                - ${lm.cityarrivename }${lm.starrivename } ）
            </div>
        </div>
    </form>

    <div class="container">
        <div style="line-height: 40px;">
            ${empty param.id ?'新增':'编辑'}线路&gt;<span style="font-weight: bold;">${empty param.id ?'新增':'编辑'}线路规则</span>
        </div>

        <div style="line-height: 40px;">
            <div style="display: inline-block;">名称</div>
            <div style="display: inline-block;">
                <input type="text" class="rule_name notnull" placeholder="规则名称" value="${rule.rulename}"/>
                <input type="hidden" class="rule_id" value="${rule.id}"/>
            </div>
        </div>
        <div style="line-height: 40px;">
            <input type="checkbox" class="isdefault" <c:if test="${rule.isdefault==1}">checked</c:if> />是否为默认规则
        </div>
        <br/>

        <div class="cell_item rule" style="margin-bottom: 20px;display: none;">
            <div style="background-color: #F2F2F2;line-height: 40px;overflow: hidden;">
                <div style="display: inline-block;padding: 0 10px;">时间段</div>
                <div style="display: inline-block;">
                    <input type="text" class="begintime" readonly value="00:00"/>
                </div>
                <div style="display: inline-block;">-</div>
                <div style="display: inline-block;">
                    <input type="text" class="endtime" readonly value="00:00"/>
                </div>
                <div class="extend" on="1"
                     style="display: inline-block;float: right;padding-right: 20px;padding-top: 5px;cursor: pointer;">
                    <img src="${basePath}/images/pointer2.png"/></div>
                <a class="del_btn" style="display: inline-block;float: right;margin-right: 80px;cursor: pointer;">删除</a>
            </div>
            <div class="cell_content"
                 style="width: 100%;overflow:hidden;margin-top: 10px;border-bottom: 1px solid #ccc;padding-bottom: 10px;">

                <div style="overflow: hidden;">
                    <div style="display: inline-block;width: 50%;float: left;line-height: 40px;" class="s_left" >
                        <div style="overflow: hidden;">
                            <div style="display: inline-block;float: left;color: #4D8A83;">始发城市途径站（${lm.citystartname}）</div>
                            <div style="display: inline-block;float: right;padding-right: 10px;">
                                <input type="checkbox"class="bs_all"/>全选
                            </div>
                        </div>
                        <c:set var="count" value="1"></c:set>
                        <table style="border-collapse:collapse;" class="bs_station">
                            <c:forEach var="o" items="${begincity}" varStatus="status">
                                <c:if test="${count==1}">
                                    <tr>
                                </c:if>
                                    <td style="padding: 5px 30px 5px 2px;">
                                        <input type="hidden" value="${o.id}" class="station_id"/>
                                        <input type="checkbox" class="bs"/>
                                        <span class="station_name">${o.cityname}</span>
                                    </td>
                                <c:set var="count" value="${count+1}"></c:set>
                                <c:if test="${count>3}">
                                    </tr>
                                    <c:set var="count" value="1"></c:set>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>

                    <div  style="display: inline-block;width: 49%;float: left;background-color: #f5f5f5;padding-bottom: 10px;">
                        <table class="sortable bs_right s_right" style="width: 100%;border-collapse:collapse;">
                            <tr class="ui-state-disabled">
                                <td style="padding-left: 10px;color: #4D8A83;">途经站（始发）</td>
                                <td style="color: #4D8A83;">间隔时间(分)</td>
                                <td style="color: #4D8A83;">里程(千米)</td>
                            </tr>
                            <tr class="bs_info_template" style="display: none;">
                                <input type="hidden" class="stid" placeholder="站点编号"/>
                                <input type="hidden" class="sort"/>
                                <td style="padding-left: 10px;" class="cityname">仙境</td>
                                <td><input type="text" class="requiretime" value="0" placeholder="间隔时间" maxlength="3"/></td>
                                <td><input type="text" class="submileage" value="0" placeholder="里程不能为空" maxlength="3"/></td>
                            </tr>

                        </table>
                    </div>
                </div>


                <div style="display: inline-block;width: 50%;float: left;line-height: 40px;" class="s_left">
                    <div style="overflow: hidden;margin-top: 10px;">
                        <div style="display: inline-block;float: left;color: #4D8A83;">到达城市途径站（${lm.cityarrivename}）</div>
                        <div style="display: inline-block;float: right;padding-right: 10px;">
                            <input type="checkbox" class="es_all"/>全选
                        </div>
                    </div>
                    <table style="border-collapse:collapse;" class="es_station">
                        <c:set var="count" value="1"></c:set>
                        <c:forEach var="o" items="${endcity}" varStatus="status">
                            <c:if test="${count==1}">
                                <tr>
                            </c:if>
                            <td style="padding: 5px 30px 5px 2px;">
                                <input type="hidden" value="${o.id}" class="station_id"/>
                                <input type="checkbox" class="es"/>
                                <span class="station_name">${o.cityname}</span>
                            </td>
                            <c:set var="count" value="${count+1}"></c:set>
                            <c:if test="${count>3}">
                                </tr>
                                <c:set var="count" value="1"></c:set>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>


                <div style="display: inline-block;width: 49%;float: left;background-color: #f5f5f5;padding-bottom: 10px;margin-top: 10px;">
                    <table class="sortable es_info es_right s_right" style="width: 100%;border-collapse:collapse;">
                        <tr class="ui-state-disabled">
                            <td style="padding-left: 10px;color: #4D8A83;">途经站（到达）</td>
                            <td style="color: #4D8A83;">间隔时间(分)</td>
                            <td style="color: #4D8A83;">里程(千米)</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <c:forEach var="rule" items="${rule.rules}">
            <div class="cell_item rule" style="margin-bottom: 20px;display: block;">
                <input type="hidden" class="time_rule_id" value="${rule.id}"/>
                <div style="background-color: #F2F2F2;line-height: 40px;overflow: hidden;">
                    <div style="display: inline-block;padding: 0 10px;">时间段</div>
                    <div style="display: inline-block;">
                        <input type="text" class="begintime" readonly value="${rule.begintime}"/>
                    </div>
                    <div style="display: inline-block;">-</div>
                    <div style="display: inline-block;">
                        <input type="text" class="endtime" readonly value="${rule.endtime}"/>
                    </div>
                    <div class="extend" on="1"
                         style="display: inline-block;float: right;padding-right: 20px;padding-top: 5px;cursor: pointer;">
                        <img src="${basePath}/images/pointer2.png"/></div>
                    <a class="del_btn" style="display: inline-block;float: right;margin-right: 80px;cursor: pointer;">删除</a>
                </div>
                <div class="cell_content"
                     style="width: 100%;overflow:hidden;margin-top: 10px;border-bottom: 1px solid #ccc;padding-bottom: 10px;">

                    <div style="overflow: hidden;">
                        <div style="display: inline-block;width: 50%;float: left;line-height: 40px;" class="s_left" >
                            <div style="overflow: hidden;">
                                <div style="display: inline-block;float: left;color: #4D8A83;">始发城市途径站（${lm.citystartname}）</div>
                                <div style="display: inline-block;float: right;padding-right: 10px;">
                                    <input type="checkbox"class="bs_all"/>全选
                                </div>
                            </div>
                            <c:set var="count" value="1"></c:set>
                            <table style="border-collapse:collapse;" class="bs_station">
                                <c:forEach var="o" items="${begincity}" varStatus="status">
                                    <c:if test="${count==1}">
                                        <tr>
                                    </c:if>
                                    <c:set var="ckStation" value="0"></c:set>
                                    <c:forEach var="s" items="${rule.stations}">
                                        <c:if test="${s.stid == o.id}">
                                            <c:set var="ckStation" value="1"></c:set>
                                        </c:if>
                                    </c:forEach>
                                        <td style="padding: 5px 30px 5px 2px;">
                                            <input type="hidden" value="${o.id}" class="station_id"/>
                                            <input type="checkbox" class="bs" <c:if test="${ckStation==1}">checked</c:if> />
                                            <span class="station_name">${o.cityname}</span>
                                        </td>
                                    <c:set var="count" value="${count+1}"></c:set>
                                    <c:if test="${count>3}">
                                        </tr>
                                        <c:set var="count" value="1"></c:set>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>

                        <div  style="display: inline-block;width: 49%;float: left;background-color: #f5f5f5;padding-bottom: 10px;">
                            <table class="sortable bs_right s_right" style="width: 100%;border-collapse:collapse;">
                                <tr class="ui-state-disabled">
                                    <td style="padding-left: 10px;color: #4D8A83;">途经站（始发）</td>
                                    <td style="color: #4D8A83;">间隔时间(分)</td>
                                    <td style="color: #4D8A83;">里程(千米)</td>
                                </tr>
                                <c:forEach var="station" items="${rule.stations}">
                                    <c:if test="${station.sort==0}">
                                        <tr class="station" sid="${station.stid}">
                                            <input type="hidden" class="id" value="${station.id}"/>
                                            <input type="hidden" class="stid" placeholder="站点编号" value="${station.stid}"/>
                                            <input type="hidden" class="sort" value="${station.sort}" />
                                            <td style="padding-left: 10px;" class="cityname">${station.stname}</td>
                                            <td><input type="text" class="requiretime" value="${station.requiretime}" placeholder="间隔时间" maxlength="3"/></td>
                                            <td><input type="text" class="submileage" value="${station.submileage}" placeholder="里程不能为空" maxlength="3"/></td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </div>


                    <div style="display: inline-block;width: 50%;float: left;line-height: 40px;" class="s_left">
                        <div style="overflow: hidden;margin-top: 10px;">
                            <div style="display: inline-block;float: left;color: #4D8A83;">到达城市途径站（${lm.cityarrivename}）</div>
                            <div style="display: inline-block;float: right;padding-right: 10px;">
                                <input type="checkbox" class="es_all"/>全选
                            </div>
                        </div>
                        <table style="border-collapse:collapse;" class="es_station">
                            <c:set var="count" value="1"></c:set>
                            <c:forEach var="o" items="${endcity}" varStatus="status">
                                <c:if test="${count==1}">
                                    <tr>
                                </c:if>
                                <c:set var="ckStation" value="0"></c:set>
                                <c:forEach var="s" items="${rule.stations}">
                                    <c:if test="${s.stid == o.id}">
                                        <c:set var="ckStation" value="1"></c:set>
                                    </c:if>
                                </c:forEach>
                                <td style="padding: 5px 30px 5px 2px;">
                                    <input type="hidden" value="${o.id}" class="station_id"/>
                                    <input type="checkbox" class="es" <c:if test="${ckStation == 1}">checked</c:if> />
                                    <span class="station_name">${o.cityname}</span>
                                </td>
                                <c:set var="count" value="${count+1}"></c:set>
                                <c:if test="${count>3}">
                                    </tr>
                                    <c:set var="count" value="1"></c:set>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>


                    <div style="display: inline-block;width: 49%;float: left;background-color: #f5f5f5;padding-bottom: 10px;margin-top: 10px;">
                        <table class="sortable es_info es_right s_right" style="width: 100%;border-collapse:collapse;">
                            <tr class="ui-state-disabled">
                                <td style="padding-left: 10px;color: #4D8A83;">途经站（到达）</td>
                                <td style="color: #4D8A83;">间隔时间(分)</td>
                                <td style="color: #4D8A83;">里程(千米)</td>
                            </tr>
                            <c:forEach var="station" items="${rule.stations}">
                                <c:if test="${station.sort==1}">
                                    <tr class="station" sid="${station.stid}">
                                        <input type="hidden" class="id" value="${station.id}"/>
                                        <input type="hidden" class="stid" placeholder="站点编号" value="${station.stid}"/>
                                        <input type="hidden" class="sort" value="${station.sort}" />
                                        <td style="padding-left: 10px;" class="cityname">${station.stname}</td>
                                        <td><input type="text" class="requiretime" value="${station.requiretime}" placeholder="间隔时间" maxlength="3"/></td>
                                        <td><input type="text" class="submileage" value="${station.submileage}" placeholder="里程不能为空" maxlength="3"/></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </c:forEach>

        <div style="margin-top: 20px;">
            <div class="newrule" style="display: inline-block;background-color: #4D8A83;color: #fff;line-height: 40px;padding: 0 10px;cursor: pointer;">
                新增时间段
            </div>
        </div>
        <div style="margin-top: 40px;text-align: center;">
            <%--<div style="display: inline-block;background-color: #4D8A83;color: #fff;line-height: 40px;padding: 0 10px;cursor: pointer;margin-right: 20px;">--%>
                <%--清空--%>
            <%--</div>--%>
            <div class="submit" style="display: inline-block;background-color: #4D8A83;color: #fff;line-height: 40px;padding: 0 10px;cursor: pointer;">
                保存
            </div>
            <div onclick="history.back();" style="margin-left: 50px;display: inline-block;background-color: #4D8A83;color: #fff;line-height: 40px;padding: 0 10px;cursor: pointer;margin-right: 20px;">
                返回
            </div>
        </div>

    </div>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
