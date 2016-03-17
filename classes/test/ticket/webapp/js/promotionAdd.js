$(function() {
	var tabIndex = parseInt(location.hash.split('#')[1]);
	if(!isNaN(tabIndex) && $('.nav-tabs li').length>tabIndex){
		$('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
		$('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
	}
	initPromotionForm();
});

function initPromotionForm(){
	window.promotionForm = $("#promotionForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		ignoreHidden:true,
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
				$("#promotionForm").attr('action',basePath+'/promotionAdd.do');
			}else{
				$("#promotionForm").attr('action',basePath+'/promotionEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				location.href = basePath + '/promotionList.do';
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
		istime : false,
		istoday : false,
		festival : true,
		choose : function(dates){
			end.min = dates;
			promotionForm.check(false,'#begindate');
		}
	};
	var end = {
		elem : '#enddate',
		format : 'YYYY-MM-DD',
		istime : false,
		istoday : false,
		festival : true,
		choose : function(dates){
			start.max = dates;
			promotionForm.check(false,'#enddate');
		}
	};
	laydate(start);
	laydate(end);
}

function showStock(){
	var enableStock = $('#enableStock').is(':checked');
	if(enableStock){
		$('input[name="totalquantity"]').parents('div.row').show();
	}else{
		$('input[name="totalquantity"]').parents('div.row').hide();
		$('input[name="totalquantity"]').val('');
	}
}

function chooseOrgan(){
	var $iframe = null;
	layer.open({
	    type: 2,
	    title: '选取餐馆',
	    btn: ['确定', '取消'],
	    shadeClose: true,
	    shade: 0.8,
	    offset: '150px',
	    area: ['800px', 'auto'],
	    content: basePath+'/selectOrgan.do?',
	    success: function(layero, index){
	    	$iframe = layero.find('iframe');
	    },
	    yes : function(){
	    	var organIdList = [];
	    	$iframe.contents().find('.check_single:checked').each(function(){
	    		organIdList.push($(this).val());
	    	});
	    	if(organIdList.length > 0){
	    		var id = $('input[name="id"]').val();
	    		$.ajax({
					type : 'POST',
					url : basePath+'/bindOrganToPromotion.do',
					data : {id:id,organIdList:organIdList.join(',')},
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
	}); 
}

function delOrgan(organid){
	if(typeof(organid) == 'string' && organid.length > 0){
		var id = $('input[name="id"]').val();
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/unbindOrganToPromotion.do',
				data : {id:id,organid:organid},
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
		}, function(){});
	}
}