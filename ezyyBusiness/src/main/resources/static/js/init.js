/**
 * 初始化图案宽高
 */
function initItemSize(){
	
	  var width=document.body.clientWidth;
	  var height=document.body.clientHeight-108;
	  $(".item").css({"background-color":"white","width":width/3,"height":height/2})
	  $("#map").css({"background-color":"white","width":2*width/3-40,"height":height+8});

}


