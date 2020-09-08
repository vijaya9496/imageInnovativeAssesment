package com.imaginnovate.assesment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.assesment.service.EmpDetailsService;
import com.imaginnovate.assesment.vo.BaseResponseVO;
import com.imaginnovate.assesment.vo.EmpDetailsVO;

@RestController
@RequestMapping("/imageInnovateAssesment")
@Validated
public class EmpController {

	@Autowired
	private EmpDetailsService empDetailsService;
	
	@PostMapping("/saveEmpDetails")
	public ResponseEntity<BaseResponseVO> saveEmpDetails(@RequestBody EmpDetailsVO empDetailsVO){
		BaseResponseVO baseResponseVO = empDetailsService.validateEmpDetails(empDetailsVO);
		return ResponseEntity.ok().body(baseResponseVO);
		
	}
	
	@GetMapping("/getDeductedTax/{empCode}")
	public ResponseEntity<BaseResponseVO> taxReduction(@PathVariable("empCode") int empCode){
		BaseResponseVO baseResponseVO = empDetailsService.validateTaxDataByEmpCode(empCode);
		return ResponseEntity.ok().body(baseResponseVO);
		
	}
	
	
}
