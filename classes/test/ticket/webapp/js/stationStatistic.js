$(function() {
	$('input[name="startDate"]').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd',
		autoclose: true,
		todayHighlight: true,
		forceParse: true,
		minView: 2,
		startDate: '2000-00-00',
		endDate: '2030-00-00'
	}).on('changeDate', function (ev) {
		$('input[name="endDate"]').datetimepicker('setStartDate', ev.date);
	});
	$('input[name="endDate"]').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd',
		autoclose: true,
		todayHighlight: true,
		forceParse: true,
		minView: 2,
		startDate: '2000-00-00',
		endDate: '2030-00-00'
	}).on('changeDate', function (ev) {
		$('input[name="startDate"]').datetimepicker('setEndDate', ev.date);
	});
});

function exportExcel(obj){
	$(obj).parents('form').attr('action',basePath+"/user/stationStatisticExport.do");
	$(obj).parents('form').submit();
	$(obj).parents('form').attr('action',basePath+"/user/stationStatistic.do");
}