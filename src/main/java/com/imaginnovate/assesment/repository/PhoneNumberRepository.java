package com.imaginnovate.assesment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imaginnovate.assesment.model.PhoneNumberData;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumberData, Integer>{

}
