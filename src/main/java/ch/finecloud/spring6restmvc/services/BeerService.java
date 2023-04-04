package ch.finecloud.spring6restmvc.services;

import ch.finecloud.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
