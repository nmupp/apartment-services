package com.narsicloud.apartment.dao;

import reactor.core.publisher.Flux;

import java.util.Collections;

public class SampleApartmentRepository implements ApartmentRepository {

    @Override
    public Flux<Apartment> findAll() {
        return Flux.fromIterable(Collections.singletonList(new Apartment("Narsi",10309L)));
    }
}
