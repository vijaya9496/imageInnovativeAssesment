package com.imaginnovate.assesment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imaginnovate.assesment.model.EmpDetails;

@Repository
public interface EmpDetailsRepository extends JpaRepository<EmpDetails, Integer>{

	@Query(value="select e from EmpDetails e where e.empCode =:empCode")
	List<EmpDetails> getSalaryDataByEmpCode(@Param("empCode")int empCode);

}
