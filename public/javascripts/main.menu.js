$(document).ready(function(){
	var pathname = window.location.pathname;
	$( "#menu_items ul li" ).each(function( index ) {
		  console.log( index + ": " + $(this).text() );
	});
});