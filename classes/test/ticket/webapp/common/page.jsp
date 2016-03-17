<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<input type="hidden" name="pageNo" value="${page.pageNo }">
<input type="hidden" name="pageSize" value="${page.pageSize }">
<span style="display:none;" name="totalPage" >${page.totalPage }</span>
<span style="display:none;" name="totalCount" >${page.totalCount }</span>
<div class="row">
	<div class="span6">
		<div style="height: 40px;line-height:40px;overflow:hidden; ">共有${page.totalCount }条数据</div>
	</div>
	<div class="span6 pull-right">
		<div class="pull-right">
			<ul class="pagination">
				<li>
					<a href="javascript:void(0)" onclick="toPage(1);" <c:if test="${page.pageNo<=1 }">style="color:#848688;cursor:default;"</c:if> >首页</a>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="toPage(${page.pageNo-1 });" <c:if test="${page.pageNo<=1 }">style="color:#848688;cursor:default;"</c:if> >上一页</a>
				</li>				
				<c:forEach begin="${page.pageNo<=3?1:page.totalPage-page.pageNo>=2?page.pageNo-2:page.totalPage<5?1:page.totalPage-4 }" end="${page.totalPage<=5?page.totalPage:page.pageNo<=3?5:page.totalPage-page.pageNo>=2?page.pageNo+2:page.totalPage }" step="1" var="item">
					<li <c:if test="${item==page.pageNo}">class="active"</c:if> >
						<a href="javascript:void(0)" onclick="toPage(${item});">${item}</a>
					</li>
				</c:forEach>
				<li>
					<a href="javascript:void(0)" onclick="toPage(${page.pageNo+1 });" <c:if test="${page.totalPage<=page.pageNo }">style="color:#848688;cursor:default;"</c:if> >下一页</a>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="toPage(${page.totalPage });" <c:if test="${page.totalPage<=page.pageNo }">style="color:#848688;cursor:default;"</c:if> >尾页</a>
				</li>
			</ul>
		</div>
	</div>
</div>

<script type="text/javascript">
	var pageNo = $('input[name="pageNo"]').val();
	var pageSize = $('input[name="pageSize"]').val();
	var totalPage = $('span[name="totalPage"]').text();
	var totalCount = $('span[name="totalCount"]').text();
	function toPage(toPageNo){
		if(typeof(toPageNo) == 'number' && toPageNo >= 1 && toPageNo <= totalPage && toPageNo != pageNo) {
			$('[name=export]').val(0);
			$('input[name="pageNo"]').val(toPageNo);
			$('input[name="pageNo"]').parents('form')[0].submit();
		}
	}

</script>
