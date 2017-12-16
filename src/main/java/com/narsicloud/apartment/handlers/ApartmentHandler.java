package com.narsicloud.apartment.handlers;

import com.narsicloud.apartment.dao.Apartment;
import com.narsicloud.apartment.dao.ApartmentRepository;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ApartmentHandler {
  private final ApartmentRepository repository;

  public ApartmentHandler(ApartmentRepository repository) {
      this.repository = repository;
  }

  public Mono<ServerResponse> getAll(ServerRequest request) {
      Flux<Apartment> apartments = repository.findAll();
      return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(apartments, Apartment.class);
  }

}
