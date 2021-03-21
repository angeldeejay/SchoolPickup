package com.uniajc.schoolpickup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uniajc.schoolpickup.entities.PickupRequest;

@Repository
public interface PickupRequestRepository extends JpaRepository<PickupRequest, Long> {
}
