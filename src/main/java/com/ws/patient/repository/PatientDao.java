package com.ws.patient.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ws.patient.model.Patient;
@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {

	public Patient findOne(Long id);
	public List<Patient> findAll();
	public Patient findByNumeroSs(String ss);
	
	public List<Patient> findByNomContainingIgnoreCase(String ch);
	public List<Patient> findByPrenomContainingIgnoreCase(String ch);
}
