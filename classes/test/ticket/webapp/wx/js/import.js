var debug = true;

var version = 1.2;


function importjs(arr){
	if($('[src*="wx.js"]').length==0){
		var wx = $('<script type="text/javascript" src="js/wx.js?version='+version+'"></script>');
		$("body").after(wx);
	}
	if(arr){
		$.each(arr,function(i,v){
			var js = $('<script type="text/javascript" src="js/'+v+'?version='+version+'"></script>');
			$("body").after(js);
		});
	}
	if(debug&&arr){
		console.debug('import js ============ >> ' + arr);
	}
}