function addComment(){
	var text = $('#add_comment');
	$.ajax({
		url: saveCommentAction.url({demoId: demoId}),
		type: saveCommentAction.method,
		data: 'text=' + text.val()
	})
	.fail(function( jqXHR, textStatus ) {
		alert( "Request failed: " + textStatus );
	})
	.done(function() {
		$('.comments').append('<div class="well well-small"><div class="user_display_name">' 
								+ userDisplayName + '</div><div>' + text.val() + '</div></div>');
		text.val('');
	});
}

$(document).ready(function(){
	$('#single_demotivator_image').load(function(){
		window.scroll(0, findPos(document.getElementById("single_demotivator_image")));
	});
});

//Finds y value of given object
function findPos(obj) {
	var curtop = 0;
	if (obj.offsetParent) {
		do {
			curtop += obj.offsetTop;
		} while (obj = obj.offsetParent);
		return [curtop];
	}
}
