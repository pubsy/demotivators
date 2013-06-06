$(document).ready(function(){
	$( ".pagination-container" ).addClass("pagination pagination-centered");
	$( "ul.pagination li a.active" ).parent().addClass("active");
	$( "ul.pagination li.off" ).addClass("disabled");
});