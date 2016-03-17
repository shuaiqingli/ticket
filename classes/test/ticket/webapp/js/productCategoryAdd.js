$(function() {
	var tabIndex = parseInt(location.hash.split('#')[1]);
	if(!isNaN(tabIndex) && $('.nav-tabs li').length>tabIndex){
		$('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
		$('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
	}
	
	initProductCategoryForm();
	
	$('#timeSet').find('input.bootstrap-timepicker').focus(function(){
		$(this).timepicker({
			showMeridian : false,
			showInputs : false,
			defaultTime : false,
			format:'H:i',
			minuteStep : 1
		});
	});
});

function initProductCategoryForm(){
	window.productCategoryForm = $("#productCategoryForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			var weekdays = '';
			$('#weekdayList').find('input:checkbox').each(function(){
				if($(this).is(':checked')){
					weekdays += '1';
				}else{
					weekdays += '0';
				}
			});
			$('input[name="weekdays"]').val(weekdays);
		},
		beforeSubmit:function(curform){
			if(flag == 'add'){
				$("#productCategoryForm").attr('action',basePath+'/productCategoryAdd.do');
			}else{
				$("#productCategoryForm").attr('action',basePath+'/productCategoryEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				var organid = $('input[name="organ.organid"]').val();
				location.href = basePath + '/organEditPage.do?organid='+organid+'#2';
			}else{
				if(typeof(data.info) == 'string' && data.info.length > 0){
					layer.msg(data.info);
				}else{
					layer.msg('保存失败');
				}
			}
		}
	});
	
	var start = {
		elem : '#begindate',
		format : 'YYYY-MM-DD',
		istime : true,
		istoday : false,
		festival : true,
		choose : function(dates){
			end.min = dates;
			productCategoryForm.check(false,'#begindate');
		}
	};
	var end = {
		elem : '#enddate',
		format : 'YYYY-MM-DD',
		istime : true,
		istoday : false,
		festival : true,
		choose : function(dates){
			start.max = dates;
			productCategoryForm.check(false,'#enddate');
		}
	};
	laydate(start);
	laydate(end);
}

function chooseBlockArea(){
	var organid = $('input[name="organ.organid"]').val();
	var pcid = $('input[name="pcid"]').val();
	var $iframe = null;
	layer.open({
	    type: 2,
	    title: '选取片区',
	    btn: ['确定', '取消'],
	    shadeClose: true,
	    shade: 0.8,
	    offset: '150px',
	    area: ['800px', 'auto'],
	    content: basePath+'/selectBlockAreaFromOrgan.do?organid='+organid,
	    success: function(layero, index){
	    	$iframe = layero.find('iframe');
	    },
	    yes : function(){
	    	var blockAreaIdList = [];
	    	$('span[name="blockAreaIdForProductCategory"]').each(function(){
	    		var id = $(this).text();
	    		if(typeof(id)=='string' && id.length>0){
	    			blockAreaIdList.push(id);
	    		}
	    	});
	    	$iframe.contents().find('.check_single:checked').each(function(){
	    		var id = $(this).val();
	    		for(var i=0;i<blockAreaIdList.length;i++){
	    			if(blockAreaIdList[i] == id) return true;
	    		}
	    		blockAreaIdList.push(id);
	    	});
	    	if(blockAreaIdList.length > 0){
	    		var organId = $('input[name="organid"]').val();
	    		$.ajax({
					type : 'POST',
					url : basePath+'/productCategorySendAreasEdit.do',
					data : {pcid:pcid,sendareas:blockAreaIdList.join(',')},
					dataType : "json",
					success : function(data){
						if(data.status=='y'){
							location.href=location.href.split('#')[0]+'#2';
							location.reload();
						}else{
							if(typeof(data.info) == 'string' && data.info.length > 0){
								layer.msg(data.info);
							}else{
								layer.msg('操作失败');
							}
						}
					},
					error : function(){
						layer.msg('操作失败');
					}
				});
	    	}
	    }
	}); 
}

function delBlockArea(obj){
	layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
		var pcid = $('input[name="pcid"]').val();
		var blockAreaIdList = [];
		$(obj).parents('tr').siblings().each(function(){
			var id = $(this).find('span[name="blockAreaIdForProductCategory"]').text();
			blockAreaIdList.push(id);
		});
		$.ajax({
			type : 'POST',
			url : basePath+'/productCategorySendAreasEdit.do',
			data : {pcid:pcid,sendareas:blockAreaIdList.join(',')},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					location.href=location.href.split('#')[0]+'#2';
					location.reload();
				}else{
					if(typeof(data.info) == 'string' && data.info.length > 0){
						layer.msg(data.info);
					}else{
						layer.msg('操作失败');
					}
				}
			},
			error : function(){
				layer.msg('操作失败');
			}
		});
	}, function(){});
}

function addTime(obj){
	var ele = $('#timeTemplate').clone().css('display','');
	$(obj).parents('td').append(ele);
	$('#timeSet').find('input.bootstrap-timepicker').focus(function(){
		$(this).timepicker({
			showMeridian : false,
			showInputs : false,
			format:'H:i',
			minuteStep : 1
		});
	});
}

function removeTime(obj){
	if($(obj).parents('td').find('span').length > 1){
		$(obj).parents('span').remove();
	}else{
		$(obj).parents('span').find('input:text').val('');
	}
}

function saveTime(){
	var timeReg = /^\d{2}:\d{2}$/;
	var timeList = [];
	var timeFlag = true;
	$('#timeSet table.dataTable tbody').find('tr').each(function(){
		var timeSpec = {};
		var timeSpecFlag = true;
		var sendtimeList = [];
		var ordertimeList = [];
		$(this).find('td').eq(1).find('span').each(function(){
			var from = $(this).find('input').eq(0).val();
			var to = $(this).find('input').eq(1).val();
			if(typeof(from) != 'string' || !timeReg.test(from)){
				layer.msg('时间为空或格式错误');
				$(this).find('input').eq(0).focus();
				timeSpecFlag = false;
				return false;
			}
			if(typeof(to) != 'string' || !timeReg.test(to)){
				layer.msg('时间为空或格式错误');
				$(this).find('input').eq(1).focus();
				timeSpecFlag = false;
				return false;
			}
			sendtimeList.push(from+"-"+to);
		});
		if(!timeSpecFlag){
			timeFlag = false;
			return false;
		}
		$(this).find('td').eq(2).find('span').each(function(){
			var from = $(this).find('input').eq(0).val();
			var to = $(this).find('input').eq(1).val();
			if(typeof(from) != 'string' || !timeReg.test(from)){
				layer.msg('时间为空或格式错误');
				$(this).find('input').eq(0).focus();
				timeSpecFlag = false;
				return false;
			}
			if(typeof(to) != 'string' || !timeReg.test(to)){
				layer.msg('时间为空或格式错误');
				$(this).find('input').eq(1).focus();
				timeSpecFlag = false;
				return false;
			}
			ordertimeList.push(from+"-"+to);
		});
		if(!timeSpecFlag){
			timeFlag = false;
			return false;
		}
		timeSpec.sendtimeList=sendtimeList;
		timeSpec.ordertimeList=ordertimeList;
		timeList.push(timeSpec);
	});
	if(timeFlag){
		var pcid = $('input[name="pcid"]').val();
		$.ajax({
			type : 'POST',
			url : basePath+'/productCategoryTimeSetEdit.do',
			data : {pcid:pcid,timeset:JSON.stringify(timeList)},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					location.href=location.href.split('#')[0]+'#1';
					location.reload();
				}else{
					if(typeof(data.info) == 'string' && data.info.length > 0){
						layer.msg(data.info);
					}else{
						layer.msg('操作失败');
					}
				}
			},
			error : function(){
				layer.msg('操作失败');
			}
		});
	}
}

function delProduct(pid){
	if(typeof(pid) == 'number' && pid > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/productDel.do',
				data : {pid:pid},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
						location.href=location.href.split('#')[0]+'#3';
						location.reload();
					}else{
						if(typeof(data.info) == 'string' && data.info.length > 0){
							layer.msg(data.info);
						}else{
							layer.msg('操作失败');
						}
					}
				},
				error : function(){
					layer.msg('操作失败');
				}
			});
		}, function(){});
	}
}