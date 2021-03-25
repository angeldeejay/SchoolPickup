package com.uniajc.schoolpickup.repositories;

import com.uniajc.schoolpickup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.identification = ?1")
  public User findByIdentification(String identification);
}
