package com.ws.patient.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ws.patient.model.Patient;
import com.ws.patient.repository.*;
import com.ws.patient.utils.*;
@Service("patientService")
@Transactional
public class PatientService {

	@Autowired
	private PatientDao PatientDao;
	
	 
	public Patient findById(Long id) {
		return PatientDao.findOne(id);
	}
	
	public Patient findBySs(String ss) {
		return PatientDao.findByNumeroSs(ss);
	}
	
	public List<Patient>  findAll() {
		return PatientDao.findAll();
	}
	public List<Patient>  searchNomPrenom(String ch) {
		LinkedHashSet<Patient> hashSet = new LinkedHashSet<Patient>();
		
		NoDuplicatesList<Patient> ps= new NoDuplicatesList<>();
		ch=ch.toLowerCase();
		ps.addAll(PatientDao.findByNomContainingIgnoreCase(ch)) ;
		ps.addAll(PatientDao.findByPrenomContainingIgnoreCase(ch)) ;
		return ps ;
	}
	public void savePatient(Patient p) {
		PatientDao.save(p) ;
	}

}
