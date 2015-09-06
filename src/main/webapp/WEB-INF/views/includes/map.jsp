<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
<script>
	var directionsDisplay;
	var directionsService = new google.maps.DirectionsService();
	var map;

	function initialize() {
		directionsDisplay = new google.maps.DirectionsRenderer();
		var kaduwela = new google.maps.LatLng(6.935746, 79.984239);
		var mapOptions = {
			zoom : 10,
			center : kaduwela
		};
		map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
		directionsDisplay.setMap(map);
		var start = '<c:out value="${startPoint}"/>';
		var end = '<c:out value="${endPoint}"/>';
		var ways =['<c:out value="${wayPoints}"/>'];
		var wayPoints = [];
		for (var i = 0; i < ways.length; i++) {
		    	wayPoints.push({
		          location:ways[i],
		          stopover:true});
		  }
		var request = {
			origin : start,
			destination : end,
			waypoints : wayPoints,
			optimizeWaypoints : true,
			provideRouteAlternatives : true,
			travelMode : google.maps.TravelMode.DRIVING

		};
		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				directionsDisplay.setDirections(response);
			} else {
				alert();
			}
		});
	}
	
	function calcRoute() {
		
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<div id="map-canvas" style="height: 100%; margin: 0px; padding: 0px;"></div>