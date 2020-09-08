package com.imaginnovate.assesment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imaginnovate.assesment.model.EmpDetails;
import com.imaginnovate.assesment.model.PhoneNumberData;
import com.imaginnovate.assesment.repository.EmpDetailsRepository;
import com.imaginnovate.assesment.repository.PhoneNumberRepository;
import com.imaginnovate.assesment.vo.BaseResponseVO;
import com.imaginnovate.assesment.vo.EmpDetailsVO;
import com.imaginnovate.assesment.vo.PhoneNumber;
import com.imaginnovate.assesment.vo.TaxDeductVO;

@Service
public class EmpDetailsService {

	@Autowired
	private EmpDetailsRepository empDetailsRepo;
	
	@Autowired
	private PhoneNumberRepository phoneNumberRepo;
	
	private BaseResponseVO baseResponseVO = BaseResponseVO.getInstance();
	
	public BaseResponseVO validateEmpDetails(EmpDetailsVO empDetailsVO) {
		
		EmpDetails empDetails = new EmpDetails();
		List<PhoneNumberData> listPhoneData = new ArrayList<>();
		PhoneNumberData phoneData;
		
		empDetails.setEmpCode(empDetailsVO.getCode());
		String nameRegex = "^[a-zA-Z]+$";
		Pattern namePattern = Pattern.compile(nameRegex);
		Matcher firstNameMatcher = namePattern.matcher(empDetails.getFirstName());
		Matcher lastNameMatcher = namePattern.matcher(empDetails.getFirstName());
		if(firstNameMatcher.matches()) {
			empDetails.setFirstName(empDetails.getFirstName());
		}else {
			baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
			baseResponseVO.setResponseMessage("Please Provide Only Alphabets for FirstName");
			return baseResponseVO;
		}
		
		if(lastNameMatcher.matches()) {
			empDetails.setLastName(empDetailsVO.getLastName());
		}else {
			baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
			baseResponseVO.setResponseMessage("Please Provide Only Alphabets for LasttName");
			return baseResponseVO;
		}
		
		
		
		String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(empDetailsVO.getEmail());
		if(matcher.matches()) {
			empDetails.setEmail(empDetailsVO.getEmail());
		}else {
			baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
			baseResponseVO.setResponseMessage("Please Provide Valid Email");
			return baseResponseVO;
		}
		
		// pattern YYYY-MM-DD or YYYY-M-D
		String dateRegex = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
		Pattern datePattern = Pattern.compile(dateRegex);
		Matcher dateMatcher = datePattern.matcher(String.valueOf(empDetailsVO.getDateOfJoining()));
		if(dateMatcher.matches()) {
			empDetails.setDateOfJoining(empDetailsVO.getDateOfJoining());
		}else {
			baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
			baseResponseVO.setResponseMessage("Please Provide Valid Date (YYYY-MM-DD or YYYY-M-D)");
			return baseResponseVO;
		}
		
		empDetails.setSalary(empDetailsVO.getSalary());
		
		// adding list of phone numbers to empcode
		Pattern phonePattern = Pattern.compile("^\\d{10}$");
	    
		if(empDetailsVO.getPhoneNumber() != null) {
			for(PhoneNumber phone:empDetailsVO.getPhoneNumber()) {
				phoneData = new PhoneNumberData();
				Matcher phoneMatcher = phonePattern.matcher(String.valueOf(phone.getPhoneNo()));
				if(phoneMatcher.matches()) {
					phoneData.setPhoneNumber(phone.getPhoneNo());
					phoneNumberRepo.save(phoneData);
					listPhoneData.add(phoneData);
				}else {
					baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
					baseResponseVO.setResponseMessage("Please Provide Valid MobileNO");
					return baseResponseVO;
				}
			}
			empDetails.setPhoneNumbers(listPhoneData);
			
			empDetailsRepo.save(empDetails);
			
			baseResponseVO.setResponseCode(HttpStatus.OK.value());
			baseResponseVO.setResponseMessage("SUCCESS");
			baseResponseVO.setData(empDetails);
		}else {
			baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
			baseResponseVO.setResponseMessage("ERROR");
		}
		return baseResponseVO;
	}

	public BaseResponseVO validateTaxDataByEmpCode(int empCode) {
		List<EmpDetails> empDtls = empDetailsRepo.getSalaryDataByEmpCode(empCode);
		TaxDeductVO taxDeductVO = new TaxDeductVO();
		double sumSalary;
		double totalSalary;
		if(empDtls !=  null) {
			sumSalary = empDtls.stream().collect(
					   Collectors.summingDouble(EmpDetails::getSalary));
			totalSalary = sumSalary * empDtls.size();
			if(totalSalary <= 250000) {
				taxDeductVO.setTaxMessage("NoTax");
			
			}else if(totalSalary > 250001 && totalSalary <= 500000) {
				taxDeductVO.setTaxMessage("5% of Tax");
				taxDeductVO.setTaxAmount(0.05*totalSalary);
				taxDeductVO.setCessAmount(0.02*taxDeductVO.getTaxAmount());
			}else if(totalSalary > 500001 && totalSalary <= 1000000){
				taxDeductVO.setTaxMessage("10% of Tax");
				taxDeductVO.setTaxAmount(0.1*totalSalary);
				taxDeductVO.setCessAmount(0.02*taxDeductVO.getTaxAmount());
			}else if (totalSalary > 1000000) {
				taxDeductVO.setTaxMessage("20% of Tax");
				taxDeductVO.setTaxAmount(0.2*totalSalary);
				taxDeductVO.setCessAmount(0.02*taxDeductVO.getTaxAmount());
			}
			
			
			taxDeductVO.setEmpCode(empDtls.get(0).getEmpCode());
			taxDeductVO.setFirstName(empDtls.get(0).getFirstName());
			taxDeductVO.setLastName(empDtls.get(0).getLastName());
			taxDeductVO.setYearlySalary(totalSalary);
			taxDeductVO.setTaxAmount(taxDeductVO.getTaxAmount());
			taxDeductVO.setCessAmount(taxDeductVO.getCessAmount());
			taxDeductVO.setTaxMessage(taxDeductVO.getTaxMessage());
			
			baseResponseVO.setResponseCode(HttpStatus.OK.value());
			baseResponseVO.setResponseMessage("SUCCESS");
			baseResponseVO.setData(taxDeductVO);
				
		}else {
			baseResponseVO.setResponseCode(HttpStatus.BAD_REQUEST.value());
			baseResponseVO.setResponseMessage("ERROR");
		}
		
		return baseResponseVO;
	}

}
