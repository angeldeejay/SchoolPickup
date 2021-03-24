package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.PickupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRequestRepository extends JpaRepository<PickupRequest, Long> {}
