$(document).ready(function(){
	$( ".pagination-container" ).addClass("text-center");
	$( "ul.pagination li a.active" ).parent().addClass("active");
	$( "ul.pagination li.off" ).addClass("disabled");
});