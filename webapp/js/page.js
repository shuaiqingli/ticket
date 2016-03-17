/*
 * 
 ........ demo .........
 $('.page').pagelist({
		size:10,
		count:1000,
		items:15,
		currpage:1,
		click:null}
	);
 * */
jQuery.fn.extend({
	pagelist:function(ps,param){
		var $this = this;
		this.ps = ps;
		this.element = $(this);
		if($this.element.length==0){
			 console.debug('element is not found ....');
			 return;
		}
		
		var data = this.data('params');
		if(data==undefined||data==null){
			var pageparams = {
					size:0,
					count:0,
					items:5,
					currpage:1,
					pagecount:0,
					click:null
			};
			$this.pageparams = $this.data('params',pageparams);
		}
		this.pageparams = this.data('params');
		if(typeof ps == 'string'){
			$this.ps = param;
		}
		if($this.ps!=null&&$this.ps!==undefined){
			$.each($this.ps,function(k,v){
				if(v!=''&&v!=null){
					$this.pageparams[k] = v;
				}
			});
		}
		$.each($this.pageparams,function(k,v){
			  if(k=='click'&&v!=null&&v!=undefined&&typeof v !='function'){
				  console.debug('click require  a funciton ....');
			  }else if(k!='click'&&isNaN(v)&&v!=''){
				  console.debug( k + '  error...............')
			  }else if(typeof v == 'string'){
				  $this.pageparams[k] = parseInt(v);
			  }
		});
		this.bind = function(){
			$this.find('.first').click(function(e){
				e.preventDefault();
				$(this).removeClass('active');
				pageparams.currpage = 1;
				$this.change(pageparams);
			});
			
			$this.find('.last').click(function(e){
				e.preventDefault();
				$(this).removeClass('active');
				pageparams.currpage = pageparams.pagecount;
				$this.change(pageparams);
			});
			$this.find('.next').click(function(e){
				e.preventDefault();
				if(pageparams.currpage>=pageparams.pagecount){
					return;
				}
				pageparams.currpage = pageparams.currpage+1;
				$this.change(pageparams);
			});
			
			$this.find('.prev').click(function(e){
				e.preventDefault();
				if(pageparams.currpage<=1){
					return;
				}
				pageparams.currpage = pageparams.currpage-1;
				$this.change(pageparams);
			});
			return $this;
		};
		
		this.apppend = function (begin,end,pageparams){
			$this.element.find('.active').removeClass('active');
			for(var i = begin;i<=end;i++){
				var pageli = $('<li>');
				pageli.append('<a href="javascript:void(0)">'+i+'</a>');
				$this.element.find('.next').before(pageli);
				if(i==pageparams.currpage){
					pageli.addClass('active');
					continue;
				}
				pageli.on('click',function(e){
					e.preventDefault();
					var val = $(this).find('a').text();
					if(isNaN(val)==false&&val!=''){
						val = parseInt(val);
					}
					if(pageparams.click!=null){
						pageparams.click($(this),val);
					}
					pageparams.currpage = val;
					$this.change(pageparams);
				});
			}
			return $this;
		}
		
		this.change = function (){
			var pageparams = $this.data('params');
			$this.find('.active').removeClass('active');
			var pageCount = parseInt((pageparams.count  +  pageparams.size  - 1) /  pageparams.size); 
			pageparams.pagecount = pageCount;
			$this.find('.next,.prev,.first,.last').removeClass('disabled').show();
			if(pageparams.currpage==1||isNaN(pageCount)||pageCount==0){
				$this.find('.first,.prev').addClass('disabled');
			}
			if(pageparams.currpage==pageparams.pagecount||isNaN(pageCount)||pageCount==0){
				$this.find('.last,.next').addClass('disabled');
			}
			$this.find('li:not(".next,.prev,.first,.last")').remove();
			if(pageparams.pagecount<=0||isNaN(pageCount)){
				return;
			}
			pageparams.currpage = parseInt(pageparams.currpage);
			if(pageparams.currpage<=0){
				pageparams.currpage=1;
			}
			var temp = parseInt(pageparams.currpage-pageparams.items/2)
			var count = pageCount-pageparams.currpage;
			
//			var begin = p.currpage-(p.items%2==0?p.items/2:p.items/2+1);
//			var end = p.currpage+(p.items%2==0?p.items/2:p.items/2+1);
			var begin = parseInt(pageparams.currpage-(pageparams.items/2));
			var end = parseInt(pageparams.currpage+(pageparams.items/2));
			if(begin<=0){
				end = end-begin+1;
				begin = 1;
			}
			if(end>=pageparams.pagecount){
				begin -= end - pageparams.pagecount;
				end = pageparams.pagecount;
			}
			if(end<pageparams.pagecount){
				end = end - 1;
			}
			$this.apppend(begin, end, pageparams)
			return $this;
		}
		if(typeof ps == 'string'){
			eval(('$this.'+ps+'()'));
		}else{
			if($this.element.find('.pagination').length==0){
			  	var pagestr = '<nav><ul class="pagination"><li class="first"><a href="javascript:void(0)">首页</a></li><li class="prev"><a href="javascript:void(0)" fui-arrow-left">上一页</a></li><li class="next"><a href="javascript:void(0)" class="fui-arrow-right">下一页</a></li><li class="last"><a href="javascript:void(0)">尾页</a></li></ul></nav>';
			  	$this.append(pagestr);
			  	$this.find('.next,.prev,.first,.last').unbind('click');
			  	$this.bind();
			}
			$this.change();
		}
		return $this;
	}
});