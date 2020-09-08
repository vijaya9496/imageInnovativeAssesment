package com.imaginnovate.assesment.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name="empDetails")
@Setter
@Getter
public class EmpDetails {

	@Id
	@Column(name="empCode")
	private int empCode;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="email")
	
	private String email;
	
	@Column(name="DOJ")
	private Date dateOfJoining;
	
	@Column(name="salary")
	private double salary;
	
	@OneToMany(mappedBy = "empDetails")
	private List<PhoneNumberData> phoneNumbers;
	
	
	
}
