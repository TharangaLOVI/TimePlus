var map = '';
var apiRequest = 'https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyC_Ik2uCp2m9bRDcXfHwHeZn61Km394LVg&callback=initialize';
$(document).ready(function () {
    var height = ((window.innerHeight * 3 / 4)) + "px";
    document.getElementById('map-canvas').style.height = height;

    $(".map-show").click(function () {
        //document.getElementById('map-canvas').removeChild();
        loadScript();
    });
});

function initialize() {
    var mapOptions = {
        zoom: 8,
        center: new google.maps.LatLng(-34.397, 150.644)
    };
    if(!map){
        map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);
    }    

}

function loadScript() {
    var state = true;
    $('script').each(function () {
        if (this.src.substring(0, 13) === apiRequest.substring(0, 13)) {

            state = false;
            return false;
        }
    });
    if (state) {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = apiRequest;
        document.body.appendChild(script);
    }else{
        initialize();
    }
}