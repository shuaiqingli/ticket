$(".ruleform").Validform({
	tiptype:4, 
	postonce:true,
	ajaxPost:true,
	beforeCheck:function(curform){
		return true;
	},
	beforeSubmit:function(curform){
		var items = $('.time').not($('.time').eq(0));
		var submit = true;
		var prefix = "times";
		$.each(items,function(i,v){
			if(submit==false){return;}
			var v = $(this);
			var val = v.find('.advancetime').val();//deduction
			if(val==''||isNaN(val)){
				layer.msg('退票小时填写不正确！');
				submit = false;
			}
			val = parseFloat(v.find('.deduction').val());
			if(isNaN(val)||(val>100||val<0)){
				console.debug((val>100||val<0));
				layer.msg('退票百分比填写不正确！');
				submit = false;
			}
			v.find('.advancetime').attr('name',prefix+'['+i+'].advancetime');
			v.find('.deduction').attr('name',prefix+'['+i+'].deduction');
			v.find('.id').attr('name',prefix+'['+i+'].id');
			var id = v.find('.id');
			var isdel = v.find('.isdel').attr('name',prefix+'['+i+'].isdel');
//			if(id==''||id==undefined){
//				isdel.val(0);
//			}else{
//				isdel.val(1);
//			}
		});
		return submit;
	},
	callback:function(data){
		console.debug(data);
		if(data>=1){
			location.href = basePath+'/user/returnedRuleList';
			layer.msg('操作成功');
		}
	}
});


$(function(){
	
	if($show=='2'){
		$('.active').removeClass('active')
		$('#home2').addClass('active');
		$('.show').eq('1').addClass('active');
	}
	
	$('[name=begindate]').datetimepicker().on('changeDate',function(ev){
		$('[name=enddate]').datetimepicker('setStartDate', ev.date);
	});
	
	$('[name=begindate],[name=enddate]').datetimepicker().on('changeDate',function(ev){
		$(this).next().remove();
	});
	
	$('.time_remove').click(function(){
		var p = $(this).parents('.time');
		var id = p.find('.id').val();
		if(id==''){
			p.remove();
		}else{
			p.find('.isdel').val(1);
			console.debug(p.find('.isdel').val())
			p.hide();
		}
	});
	
	$('.add_time').click(function(){
		var time = $('.time').eq(0).clone(true);
		time.find('input').val('');
		$('.time').last().after(time.show());
	});
	
	$('.selectlines').bind('click',function(){
		 layer.open({
		        type: 2,
		        title: '选取线路',
		        btn: ['确定', '取消'],
		        shadeClose: true,
		        shade: 0.8,
		        area: ['980px', 'auto'],
		        content: basePath + '/user/selectRefundRuleLine?rrid=' + $id,
		        success: function (layero, index) {
		            $iframe = layero.find('iframe');
		        },
		        yes: function () {
		            var list = [];
		            var type = $iframe.contents().find('select[name="type"] option:checked').val();
		            $iframe.contents().find('.check_single:checked').each(function () {
		            	list.push($(this).val());
		            });
		            if (list.length > 0) {
//		               console.debug(list);
		               var prefix = "lines";
		               var params = {};
		               $.each(list,function(i,v){
		            	   params[prefix+'['+i+'].lmid']=v;
		            	   params[prefix+'['+i+'].rrid']=$id;
		               });
//		               console.debug(params);
		               ajax(params,basePath+"/user/bindRefundRuleLines",function(json){
		            	   if(json>=1){
		            		   layer.msg('操作成功！');
		            		   location.href = basePath+'/user/returnedRuleEdit?show=2&id='+$id;
//		            		   location.reload();
		            	   }else{
		            		   layer.msg('操作失败！');
		            		   throw 'error';
		            	   }
		               });
//		               throw 'error';
		            }else{
		            	layer.msg('没有选择线路！');
		            	throw 'error';
		            }
		        }
		    });
	});
	
	$('.deleteline').click(function(){
		var $this =$(this);
		var lmid = $this.attr('lmid');
		var params = {};
		params.lmid = lmid;
		params.rrid=$id;
		layer.confirm('你确定要解绑该线路吗？',{offset:'30%'},function(){
			ajax(params,basePath+"/user/unbindRefundRuleLine",function(json){
         	   if(json>=1){
         		   layer.msg('操作成功！',{offset:'30%'});
         		   $this.parents('tr').remove();
         	   }else{
         		   layer.msg('操作失败！',{offset:'30%'});
         		   throw 'error';
         	   }
            });
		});
	});
	
})


$('.date').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});
