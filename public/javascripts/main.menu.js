$(document).ready(function(){
	var pathname = window.location.pathname;
	$( "#menu_items ul li a" ).each(function() {
		  if($(this).attr("href") == pathname){
			  $(this).parent().addClass("active");
		  }
	});
}); 
