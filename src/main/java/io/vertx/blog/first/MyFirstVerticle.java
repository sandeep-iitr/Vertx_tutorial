package io.vertx.blog.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class MyFirstVerticle extends AbstractVerticle {

	
	int i=0;
	
	double lat=0;
	double lng=0;
	
	@Override
	  public void start(Future<Void> fut) {
	   
		// Create a router object.
		 Router router = Router.router(vertx);
		 // Bind "/" to our hello message - so we are still compatible.
		 router.route("/").handler(routingContext -> {
		   HttpServerResponse response = routingContext.response();
		   response
		       .putHeader("content-type", "text/html")
		       .end("<h1>Hello sandeep 2 from my first Vert.x 3 application</h1>");
		 });
		 
		 // Serve static resources from the /assets directory
		 router.route("/assets/*").handler(StaticHandler.create("assets"));
		 
		 router.post("/api/SensorData").handler(this::ShowOnWeb);
		 
		 router.route("/api/SensorData").handler(this::ShowOnWebURL);
		 
		 
		// Create the HTTP server and pass the "accept" method to the request handler.
		 vertx
		     .createHttpServer()
		     .requestHandler(router::accept)
		     .listen(
		         // Retrieve the port from the configuration,
		         // default to 8080.
		         config().getInteger("http.port", 8082),
		         result -> {
		           if (result.succeeded()) {
		             fut.complete();
		           } else {
		             fut.fail(result.cause());
		           }
		         }
		     );
		 
		 
		 //eventbus
		 /*
		  * This JavaScript library uses the JavaScript SockJS client to tunnel the event bus traffic over SockJS connections terminating at at a SockJSHandler on the server-side.
		  * 
		  */
		// Let through any messages sent to 'demo.orderMgr' from the client
		 PermittedOptions inboundPermitted1 = new PermittedOptions().setAddress("ping-address");
		// Let through any messages coming from address 'ticker.mystock'
		 PermittedOptions outboundPermitted1 = new PermittedOptions().setAddress("ping-address");
		 
		 
		 
		 SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
		 BridgeOptions options = new BridgeOptions().addInboundPermitted(inboundPermitted1).addOutboundPermitted(outboundPermitted1);
		 sockJSHandler.bridge(options);

		 router.route("/eventbus/*").handler(sockJSHandler);
		 
		 /*
		  * eventbus sender starts
		  */
		 EventBus eb = vertx.eventBus();
		  
		 /*
		 vertx.setPeriodic(1000, event -> {
			 vertx.eventBus().publish("ping-address",i+": "+SensorData.lat+" : "+SensorData.lng);
			 //System.out.println("sending data:"+i);
			 i++;
		});
		*/
		
		/*
		  * eventbus sender starts
		  */
		  
		 
		 
		 
	  }//end start
	
	
	private void ShowOnWeb(RoutingContext routingContext) {
		
		try{
			
	    routingContext.request().bodyHandler(bodyHandler -> {
			    final String body = bodyHandler.toString();
			    System.out.println("Data is:"+body);
			    
			    JsonObject obj=new JsonObject(body);
			    
			    SensorData.lat=obj.getDouble("lat");
			    SensorData.lng=obj.getDouble("lng");
			    
			     vertx.eventBus().publish("ping-address",i+": "+SensorData.lat+" : "+SensorData.lng);
				 System.out.println("sending data:"+i);
				 i++;
				 
			    
			  });
			
		routingContext.response()
	      .setStatusCode(201)
	      .putHeader("content-type", "application/json; charset=utf-8")
	      .end(Json.encodePrettily("{}"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		}
	
private void ShowOnWebURL(RoutingContext routingContext) {
	
		
		System.out.println("Request received in ShowOnWebURL");
		
		  routingContext.response()
		      .setStatusCode(201)
		      .putHeader("content-type", "application/json; charset=utf-8")
		      .end(Json.encodePrettily("{"+lat+":"+lng+"}"));
		}
	
	
}
