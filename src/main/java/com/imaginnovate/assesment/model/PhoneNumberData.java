package com.imaginnovate.assesment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="phonenumber")
@Setter
@Getter
public class PhoneNumberData {

	@Id
	@Column(name="phoneId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int phoneId;
	
	@Column(name="phoneNumber")
	private int phoneNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empCode")
	private EmpDetails empDetails;
}
