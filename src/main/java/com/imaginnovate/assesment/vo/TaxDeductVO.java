package com.imaginnovate.assesment.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaxDeductVO {

	private int empCode;
	private String firstName;
	private String lastName;
	private double yearlySalary;
	private double taxAmount;
	private double cessAmount;
	private String taxMessage;
}
