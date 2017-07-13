<!DOCTYPE html>
<html>
 <head>
    <title>eTrucks</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>


<body>

<h2> Prototype, version of Real-time location updation</h2>

<p id="demo"></p>
<p id="data"></p>

<p id="lat"></p>
<p id="lng"></p>

<script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
<script src='vertx-eventbus.js'></script>



 <div id="map">
    <script>
      
document.getElementById("demo").innerHTML = "eTrucks, Waiting for Location update";


var eb = new EventBus('http://54.183.83.176:8082/eventbus');

//var eb = new EventBus('http://localhost:8082/eventbus');

var map, infoWindow;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 34.0685561, lng: -118.4433375},
      zoom: 11
    });
    
    infoWindow = new google.maps.InfoWindow;

    var pos = {
            lat: 35.068,
            lng: -118.44
          };
    
    //infoWindow.setPosition(pos);
   // infoWindow.setContent('Location found.');
   // infoWindow.open(map);
    map.setCenter(pos);
    
  }



eb.onopen = function() {

	
  // set a handler to receive a message
  eb.registerHandler('ping-address', function(error, message) {
    console.log('received a message: ' + message.body);
    
    var str=message.body;
    //String data=message;
    document.getElementById("data").innerHTML = message.body;
    
    var res = str.split(":");
    
    
    
    document.getElementById("lat").innerHTML = parseFloat(res[0]);
    document.getElementById("lng").innerHTML = parseFloat(res[1]);
    
    
    var pos = {  lat: parseFloat(res[0]),lng: parseFloat(res[1])};
    
   
    
   map.setCenter(pos);
    var marker = new google.maps.Marker({
        position: pos,
        map: map,
        title: 'Loc'
      });

   
   
   
    });
   }
    </script>
    
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCqFLbW3fCwnIcA_1KHKYXtBYYb-_eL3rk&callback=initMap">
    </script>
 
 
</div>

</body>
</html> 