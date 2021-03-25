package com.uniajc.schoolpickup.beans;

import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.services.PickupRequestService;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@Named("PickupRequestsPoll")
@ViewScoped
public class PickupRequestsPollBean {
  @Autowired private PickupRequestService service;

  private List<PickupRequest> slotOne;
  private int slotOneSize;
  private List<PickupRequest> slotTwo;
  private int slotTwoSize;
  private List<PickupRequest> slotThree;
  private int slotThreeSize;
  
  @PostConstruct
  public void init() {
	callService();
  }

  public List<PickupRequest> getSlotOne() {
    return slotOne;
  }

  public List<PickupRequest> getSlotTwo() {
    return slotTwo;
  }

  public List<PickupRequest> getSlotThree() {
    return slotThree;
  }

  public int getSlotOneSize() {
    return slotOneSize;
  }

  public int getSlotTwoSize() {
    return slotTwoSize;
  }

  public int getSlotThreeSize() {
    return slotThreeSize;
  }

  public void callService() {
    List<PickupRequest> pending = service.findPending();
    this.slotOne =
        Arrays.asList(pending.stream().filter(r -> r.getSlot() == 1).toArray(PickupRequest[]::new));
    this.slotOneSize = slotOne.size();
    this.slotTwo =
        Arrays.asList(pending.stream().filter(r -> r.getSlot() == 2).toArray(PickupRequest[]::new));
    this.slotTwoSize = slotTwo.size();
    this.slotThree =
        Arrays.asList(pending.stream().filter(r -> r.getSlot() == 3).toArray(PickupRequest[]::new));
    this.slotThreeSize = slotThree.size();
  }
}