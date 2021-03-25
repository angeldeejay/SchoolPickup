package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.generics.EntityController;
import com.uniajc.schoolpickup.services.PickupRequestService;
import com.uniajc.schoolpickup.util.AbstractController;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value = "/pickup-requests")
public class PickupRequestController extends AbstractController
    implements EntityController<PickupRequest> {

  @Autowired PickupRequestService pickupRequestService;

  // Route: GET /pickup-requests
  @Override
  @ResponseBody
  @RequestMapping(
      value = "",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PickupRequest> getAll() {
    return pickupRequestService.findAllEntities();
  }

  // Route: GET /pickup-requests/{id}
  @Override
  @ResponseBody
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<PickupRequest> getById(@PathVariable Long id) {
    return pickupRequestService.findEntityById(id);
  }

  // Route: POST /pickup-requests
  @Override
  @ResponseBody
  @RequestMapping(
      value = "",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public PickupRequest add(PickupRequest pickupRequest) {
    return pickupRequestService.saveEntity(pickupRequest);
  }

  // Route: DELETE /pickup-requests/{id}
  @Override
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    pickupRequestService.deleteEntity(id);
  }

  // Route: PUT /pickup-requests/{id}
  @Override
  @ResponseBody
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<PickupRequest> update(@PathVariable Long id, PickupRequest pickupRequest) {
    return pickupRequestService.updateEntity(id, pickupRequest);
  }
}
