package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.PickupRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRequestRepository extends JpaRepository<PickupRequest, Long> {

  @Query("SELECT pr FROM PickupRequest pr WHERE pr.pickedUpAt IS NULL")
  public List<PickupRequest> findPendingRequests();
}
