<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>票务设置</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/tickectconfig" method="get">
			<div class="page-header ishide">
				<h2>票务设置</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="hidden" name="ishide" value="${param.ishide}" />
					<input type="text" name="linename" value="${page.param.linename }"
						placeholder="线路号/公司名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" data-toggle="modal" data-target="#myModal">全部导出</a>
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
						<th>最后审核日期</th>
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
								<c:if test="${not empty o.lineSchedues and o.lineSchedues.size()!=0  }">
									<c:if test="${not empty o.lineSchedues[0].begindate and not empty o.lineSchedues[0].enddate}">
										${o.lineSchedues[0].begindate } 至 ${o.lineSchedues[0].enddate}
									</c:if>
								</c:if>
							</td>
							<td>
								${o.lastticketdate}
							</td>
							<td class="ishide">
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/user/ticketmanage?lmid=${o.id}">
										<i class="icon-list"></i> 车票管理
									</a> 
									<a class="btn exprot" href="javascript:void(0)" data-toggle="modal" data-target="#myModal">
										<i class="icon-print"></i>  导出班次信息
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
	
	

	<%@include file="../common/footer.jsp" %>
</body>
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
