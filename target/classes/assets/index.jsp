<!DOCTYPE html>
<html>
<body>

<h2>JavaScript in Body</h2>

<p id="demo"></p>

<script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
<script src='vertx-eventbus.js'></script>

<script>
document.getElementById("demo").innerHTML = "My First JavaScript";


var eb = new EventBus('http://localhost:8080/eventbus');

eb.onopen = function() {

	
  // set a handler to receive a message
  eb.registerHandler('ping-address', function(error, message) {
    console.log('received a message: ' + message.body);
    
    //String data=message;
    document.getElementById("demo").innerHTML = message.body;
    
  });
	
	/*
  eb.consumer("ping-address", message -> {

	     // System.out.println("Received message: " + message.body());
	      // Now send back reply
	      message.reply("pong!");
	});
  */
	
  // send a message
  //eb.send('ping-address', {name: 'tim', age: 587});

}



//document.getElementById("demo").innerHTML = "My First JavaScript 2";

</script>

</body>
</html> 