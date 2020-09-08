package com.imaginnovate.assesment.vo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpDetailsVO {

	private int code; //EmpCode
	private String firstName;
	private String lastName;
	private String email;
	private List<PhoneNumber> phoneNumber;
	private Date dateOfJoining;
	private Double salary;
	
	
}
