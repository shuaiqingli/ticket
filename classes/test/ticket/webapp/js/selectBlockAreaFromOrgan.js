$(function() {
	//单选|全选
	$('.check_all').click(function(){
		if($('.check_all').attr('checked') == 'checked'){
			$('.check_single').attr('checked','true');
		}else{
			$('.check_single').removeAttr('checked');
		}
	});
	$('.check_single').click(function(){
		if($(this).attr('checked') != 'checked'){
			$('.check_all').removeAttr('checked');
		}
	});
	init();
});

function init(){
	//自适应高度
	var $iframe = parent.$('div.layui-layer-iframe').find('iframe');
	$iframe.height($iframe.contents().height());
}