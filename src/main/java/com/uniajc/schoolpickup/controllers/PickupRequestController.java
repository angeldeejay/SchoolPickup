package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.generics.GenericController;
import com.uniajc.schoolpickup.services.PickupRequestService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pickup-requests")
public class PickupRequestController extends GenericController<PickupRequest> {

  @Autowired PickupRequestService pickupRequestService;

  // Route: GET /pickup-requests
  @Override
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<PickupRequest> getAll() {
    return pickupRequestService.findAllEntities();
  }

  // Route: GET /pickup-requests/{id}
  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Optional<PickupRequest> getById(@PathVariable Long id) {
    return pickupRequestService.findEntityById(id);
  }

  // Route: POST /pickup-requests
  @Override
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public PickupRequest add(PickupRequest pickupRequest) {
    return pickupRequestService.saveEntity(pickupRequest);
  }

  // Route: DELETE /pickup-requests/{id}
  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    pickupRequestService.deleteEntity(id);
  }

  // Route: PUT /pickup-requests/{id}
  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Optional<PickupRequest> update(@PathVariable Long id, PickupRequest pickupRequest) {
    return pickupRequestService.updateEntity(id, pickupRequest);
  }

  // Route: GET /test
  @Override
  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String test() {
    return "Test done";
  }
}
