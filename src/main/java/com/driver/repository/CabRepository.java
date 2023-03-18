package com.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.driver.model.Cab;

@Repository
public interface CabRepository extends JpaRepository<Cab, Integer> {

    @Query(value = "select * from cab where driver_driver_id =: id",nativeQuery = true)
    Cab findByDriverId(int id);

}
