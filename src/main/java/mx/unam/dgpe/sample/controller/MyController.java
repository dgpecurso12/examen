package mx.unam.dgpe.sample.controller;

import java.util.HashMap;
import java.util.Map;
import java.lang.*;

import org.apache.log4j.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MyController extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(MyController.class);
    private static String pba;

    public void start(Future<Void> fut) {
        logger.info("Inicializando Vertical");
        Router router = Router.router(vertx);
        //router.route("/*").handler(StaticHandler.create("assets")); // para invocar asi: http://localhost:8080/index.html
        // el directorio "upload-folder" será creado en la misma ubicación que el jar fue ejecutado
        router.route().handler(BodyHandler.create().setUploadsDirectory("upload-folder"));
        router.get("/api/primero").handler(this::primero);
        router.post("/api/segundo").handler(this::segundo);
        router.get("/api/suma").handler(this::suma);
	router.get("/api/resta").handler(this::resta);
	router.get("/api/divide").handler(this::divide);
	router.get("/api/multiplica").handler(this::multiplica);
	
        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer().requestHandler(router::accept).listen(
                config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });        
        
        logger.info("Vertical iniciada !!!");
	pba=System.getenv("PBA");
	System.out.println(pba);
    }  
    private void primero(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String mode = request.getParam("mode");
        String jsonResponse = procesa(mode);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }
    
    private void segundo(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        String decoded = routingContext.getBodyAsString();
        String jsonResponse = procesa(decoded);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }

    private String procesa(String decoded) {
        Map<String, String> autos = new HashMap<>();
        autos.put("primero", "Ferrari");
        autos.put("segundo", "Lamborgini");
        autos.put("tercero", "Bugatti");
        
        Map<Object, Object> info = new HashMap<>();
        info.put("decoded", decoded);
        info.put("nombre", "gustavo");
        info.put("edad", "21");
        info.put("autos", autos);
        return Json.encodePrettily(info);
    }

	private void suma(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String operacion = "suma";
	String operandoA = request.getParam("a");
	String operandoB = request.getParam("b");
        String jsonResponse = calculadora(operacion,operandoA,operandoB);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }
	private void resta(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String operacion = "resta";
	String operandoA = request.getParam("a");
	String operandoB = request.getParam("b");
        String jsonResponse = calculadora(operacion,operandoA,operandoB);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }
private void divide(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String operacion = "divide";
	String operandoA = request.getParam("a");
	String operandoB = request.getParam("b");
        String jsonResponse = calculadora(operacion,operandoA,operandoB);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }
private void multiplica(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        HttpServerRequest request = routingContext.request();
        String operacion = "multiplica";
	String operandoA = request.getParam("a");
	String operandoB = request.getParam("b");
        String jsonResponse = calculadora(operacion,operandoA,operandoB);
        response.setStatusCode(200).
        putHeader("content-type", "application/json; charset=utf-8").
        end(jsonResponse);
    }

	private String calculadora(String tipo, String operandoA, String operandoB) {

	Double r = new Double("0.0");
	Double a = new Double("0.0");
	Double b = new Double("0.0");
	a = a.parseDouble(operandoA);
	b = b.parseDouble(operandoB);

	if(tipo.equals("suma")){
		r = a+b;
	}else if(tipo.equals("resta")){
		r = a-b;
	}else if(tipo.equals("multiplica")){
		r = a*b;
	}else if(tipo.equals("divide")){
		r = a/b;
	}else{
		r=-1.0;
	}
	
        Map<String, String> resultado = new HashMap<>();
        resultado.put("operación", tipo);
    	resultado.put("resultado", ""+r);
	resultado.put("servidor", ""+pba);
        
  
        return Json.encodePrettily(resultado);
    }

}
