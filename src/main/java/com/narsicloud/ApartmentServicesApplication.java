package com.narsicloud;

import com.narsicloud.apartment.dao.ApartmentRepository;
import com.narsicloud.apartment.dao.SampleApartmentRepository;
import com.narsicloud.apartment.handlers.ApartmentHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

@SpringBootApplication
public class ApartmentServicesApplication {

	public static void main(String[] args) throws Exception {
		ApartmentServicesApplication server = new ApartmentServicesApplication();
		server.startReactorServer();
		System.in.read();
	}

	public RouterFunction<ServerResponse> routingFunction() {
		ApartmentRepository repo = new SampleApartmentRepository();
		ApartmentHandler handler = new ApartmentHandler(repo);
		return nest(path("/apartments"),
				   nest(accept(APPLICATION_JSON),
						   route(method(HttpMethod.GET), handler::getAll)
						   )
				);

	}

	public void startReactorServer() throws InterruptedException {
		RouterFunction<ServerResponse> route = routingFunction();
		HttpHandler httpHandler = toHttpHandler(route);
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create("localhost", 8080);
		server.newHandler(adapter).block();
	}

}
