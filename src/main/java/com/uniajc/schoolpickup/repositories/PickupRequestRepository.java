package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.entities.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRequestRepository extends JpaRepository<PickupRequest, Long> {

  @Query("SELECT pr FROM PickupRequest pr WHERE pr.pickedUpAt IS NULL")
  public List<PickupRequest> findPendingRequests();

  @Query("SELECT pr FROM PickupRequest pr WHERE pr.student = ?1 AND pr.pickedUpAt IS NULL")
  public List<PickupRequest> findPendingRequests(Student student);

  @Query("SELECT pr FROM PickupRequest pr WHERE pr.parent = ?1 AND pr.pickedUpAt IS NULL")
  public List<PickupRequest> findPendingRequests(Parent parent);

  @Query("SELECT pr FROM PickupRequest pr WHERE pr.student = ?1")
  public List<PickupRequest> findByEntity(Student student);

  @Query("SELECT pr FROM PickupRequest pr WHERE pr.parent = ?1")
  public List<PickupRequest> findByEntity(Parent parent);
}
