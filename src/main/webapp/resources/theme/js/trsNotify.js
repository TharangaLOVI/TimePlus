/**
 * show notification
 * @param title title of the notification
 * @param msg message of the notification
 * @param type [danger,warning,success,info]
 * @param afterElement after which element notification should append
 */
function notify(title,msg,type,afterElement){
	
	var html = "<div id='noty' class='alert alert-dismissable alert-"+type+"'  style='display:none;'>"+
    			"<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
    			"<h4>"+title+"</h4>"+
    			"<p>"+msg+"</p>"+
    			"</div>";
	
	$(html).insertAfter(afterElement);
	
	$('#noty').slideDown("slow").delay(6000).slideUp("slow");
	
	
}

/**
 * Html 5 web notification
 * @param titleText
 * @param bodyText
 * @returns {Boolean}
 */
function Notify(titleText, bodyText)
{
	//Determine the correct object to use
	var notification = window.Notification || window.mozNotification || window.webkitNotification;

	// The user needs to allow this
	if ('undefined' === typeof notification)
	    alert('Web notification not supported');
	else
	    notification.requestPermission(function(permission){});
	
    if ('undefined' === typeof notification)
        return false;       //Not supported....
    var noty = new notification(
        titleText, {
            body: bodyText,
            dir: 'auto', // or ltr, rtl
            lang: 'EN', //lang used within the notification.
            tag: 'notificationPopup', //An element ID to get/set the content
            icon: 'resources/images/icons/notifications.png' //The URL of an image to be used as an icon
        }
    );
    noty.onclick = function () {
        console.log('notification.Click');
    };
    noty.onerror = function () {
        console.log('notification.Error');
    };
    noty.onshow = function () {
        console.log('notification.Show');
    };
    noty.onclose = function () {
        console.log('notification.Close');
    };
    return true;
}