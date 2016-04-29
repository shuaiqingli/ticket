<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>线路列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/linemanage.do" method="get">
			<div class="page-header ishide">
				<h2>线路列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="hidden" name="ishide" value="${param.ishide}" />
					<input type="text" name="linename" value="${page.param.linename }"
						placeholder="线路号/公司名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                    <a class="btn exportAll"
                       style="padding:5px 12px;margin:-8px 0 0 10px;"
                       href="javascript:void(0)" data-toggle="modal" data-target="#myModal">导出全部班次</a>
				</div>
				<div class="btn-group pull-right ishide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/lineadd.do">
						<i class="icon-plus-sign"></i>新增线路
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th class="isshow" style="display: none;">选择</th>
						<th class="">线路编号</th>
						<th>线路</th>
						<th>运输公司</th>
						<th>已排班日期</th>
						<th>发布车票日期</th>
						<th class="ishide pull-right">&nbsp;&nbsp;操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td class="isshow" style="display: none;"><input type="radio" name="lmid" value="${o.id}" /></td>
							<td class="">${o.lineid }</td>
							<td class="lineName">${o.linename }</td>
							<td>${o.transcompany }</td>
							<td>
								${o.lastshiftdate}
							</td>
							<td>
                                ${o.lastticketdate}
							</td>
							<td class="ishide">
								<div class="btn-group pull-right">
                                    <a class="btn" href="<%=basePath%>/user/lineadd.do?id=${o.id}">
										编辑
									</a>
									<a class="btn" href="${basePath}/user/shiftManage?lmid=${o.id}">
										班次管理
									</a>
									<a class="btn" href="${basePath}/user/ticketmanage?lmid=${o.id}">
										车票管理
									</a>
                                    <a class="btn exprot" href="javascript:void(0)" data-toggle="modal" data-target="#myModal">
                                        <i class="icon-print"></i>  导出
                                    </a>
									<a class="btn btn-danger deleteLine" href="javascript:void(0)" id="${o.id}" groupid="${o.groupid}">
										<i class="icon-remove icon-white"></i> 删除
									</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@include file="../common/page.jsp"%>
		</form>
	</div>

	<%@include file="../common/footer.jsp" %>


    <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 380px;">
        <form action="${basePath}/user/exportShiftStartInfo">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">导出班次信息</h3>
            </div>
            <div class="modal-body" style="font: normal 17px '黑体';">
                <div class="row" style="margin-left: 20px;margin-top: 30px;">
                    <span>起止日期：</span>
                    <input class="date begindate notnull" placeholder="开始日期" name="begindate" readonly="readonly" style="width: 150px;" type="text">
                    <span>至</span>
                    <input class="date enddate notnull" placeholder="结束日期"  name="enddate" readonly="readonly" style="width: 150px;" type="text" >
                </div>
                <br/>
                <br/>
                <br/>
                <div class="row" style="text-align: center;">
                    <input value="确定" type="submit" class="btn btn-success batch_approve_btn" style="height:40px;width: 15%;font-size: 18px;">
                    <input value="取消" data-dismiss="modal" type="button" class="btn btn-danger cancel" style="height:40px;width: 15%;font-size: 18px;">
                </div>
                <input class="lmid" name="lmid" type="hidden" placeholder="线路编号"/>
            </div>
        </form>
    </div>

</body>
<script type="text/javascript">
$(function(){

	$(".deleteLine").bind("click",function(){
		var $this = $(this);
		layer.confirm('确定删除该线路吗？', {
		    btn: ['确定', '取消']
		}, function(index, layero){
			var id = $this.attr("groupid");
			var json = {groupid:id};
			ajax(json,basePath+"/user/deleteLineManage",function(){
				layer.msg("删除成功！");
				setTimeout(function () {
					location.reload();
				},500);
			});
		});
		
	});
});
</script>
<script type="text/javascript">
    $(function(){
        $('.begindate,.enddate').datetimepicker({
            language:  'zh-CN',
            format:'yyyy-mm-dd',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
//		startDate:new Date(),
            forceParse: 0,
        });

        $('.begindate,.enddate').val(formatDate(new Date()));

        $('.exprot').click(function(){
            var lmid = $(this).parents('tr').find('[name=lmid]').val();
            $('#myModal').find('[name=lmid]').val(lmid)
        });

        $('.exportAll').click(function(){
            $('#myModal').find('[name=lmid]').val('')
        });

        $('#myModal').find('form').submit(function(){
            return validate($(this).find('.notnull'));
        });
    });

    function exportAll(){
        $('#myModal').find('[name=lmid]').val('')
    }

</script>
</html>
