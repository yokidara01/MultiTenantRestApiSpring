package com.ws.patient.controller;
/**
 * @author KORTAS ALADINE
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.ws.patient.model.Patient;
import com.ws.patient.service.PatientService;

@RestController
@RequestMapping("patientApi")
public class RestPatientController {
	public static final String uploadingdir = System.getProperty("user.home") + File.separator + "mmtpro"
			+ File.separator + "patient";
	@Autowired
	PatientService patientService;
	public static final Logger LOGGER = LoggerFactory.getLogger(RestPatientController.class);

	@RequestMapping(value = "/patients/", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> listAllPatients() {

		LOGGER.info("Récupérer tous les patients");
		List<Patient> patients = patientService.findAll();
		if (patients.isEmpty()) {
			LOGGER.debug("Liste des patients est vide");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND

		}
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	@RequestMapping(value = "/patient/add", method = RequestMethod.POST)
	public ResponseEntity<?> addPatient(@RequestBody Patient p) {

		try {

			patientService.savePatient(p);
			LOGGER.info("patient ajouté");
			return new ResponseEntity<String >(p.getId().toString(),HttpStatus.CREATED);
		} catch (Exception ex) {
			LOGGER.error("erreur ajout patient");
	
			ex.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/patient/file_upload/{filename}/{id}")
	public ResponseEntity<?> fileUpload(@PathVariable("id") long id, @PathVariable("filename") String fileName,

			@RequestParam("file") MultipartFile file, UriComponentsBuilder uriBuilder) {
		Patient patient = null;

		System.out.println(id + "id patient");
		patient = patientService.findById(id);

		String patientDir = uploadingdir + File.separator + id + "-" + patient.getNom() + "-" + patient.getPrenom()
				+ File.separator;
		new File(patientDir).mkdirs();

		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());

		try {

			if (fileName.equals("ald")) {
				String filename = "ald." + file.getContentType().split("/")[1];
				System.out.println(file.getName());
				System.out.println(file.getContentType());
				Path downloadedFile = Paths.get(patientDir + filename);
				Files.deleteIfExists(downloadedFile);
				Files.copy(file.getInputStream(), downloadedFile);
				patient.setAld(filename);

			} else if (fileName.equals("attestation_cmu")) {
				String filename = "attestation_cmu." + file.getContentType().split("/")[1];
				Path downloadedFile = Paths.get(patientDir + filename);
				Files.deleteIfExists(downloadedFile);
				Files.copy(file.getInputStream(), downloadedFile);
				patient.setAttestationCMU(filename);

			} else if (fileName.equals("mutuelle")) {
				String filename = "mutuelle." + file.getContentType().split("/")[1];
				Path downloadedFile = Paths.get(patientDir + filename);
				Files.deleteIfExists(downloadedFile);
				Files.copy(file.getInputStream(), downloadedFile);
				patient.setAttestationCMU(filename);

			}

			else {
				String filename = "carte_vitale." + file.getContentType().split("/")[1];
				Path downloadedFile = Paths.get(patientDir + filename);
				Files.deleteIfExists(downloadedFile);
				Files.copy(file.getInputStream(), downloadedFile);
				patient.setCarteVitale(filename);

			}

			patientService.savePatient(patient);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriBuilder.path("mmtApi/patient/{id}").buildAndExpand(patient.getId()).toUri());

			return new ResponseEntity<String>(headers, HttpStatus.CREATED);

		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).error("picture upload", e);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriBuilder.path("mmtApi/patient/{id}").buildAndExpand(patient.getId()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/patient/search-nom-prenom/{ch}", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> searchByNomPrenom(@PathVariable("ch") String ch) {
		System.out.print("CH ::" + ch);
		List<Patient> patients = patientService.searchNomPrenom(ch);
		if (patients.isEmpty()) {
			LOGGER.debug("Liste des patients est vide");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND

		}
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	@RequestMapping(value = "/patient/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPatient(@PathVariable("id") long id) {

		LOGGER.debug("Recuperation du patient avec id {}", id);
		Patient p = patientService.findById(id);

		return new ResponseEntity<Patient>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/patient/numSs/{ss}", method = RequestMethod.GET)
	public ResponseEntity<?> getPatientBySs(@PathVariable("ss") String ss) {

		LOGGER.debug("Recuperation du patient avec Ss {}", ss);
		Patient patient = patientService.findBySs(ss);

		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	
	
	@RequestMapping(value = "/patient/{id}/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateChauffeur(@PathVariable("id") long id, @RequestBody Patient patient) {

		LOGGER.debug("Mise a jour du chauffeur avec id {}", id);
		Patient currentPatient = patientService.findById(id);

		
		currentPatient.setNom(patient.getNom());
		currentPatient.setPrenom(patient.getPrenom());
		currentPatient.setAdresse(patient.getAdresse());
		currentPatient.setCodePostal(patient.getCodePostal());
		currentPatient.setVille(patient.getVille());
		currentPatient.setTelephone(patient.getTelephone());
		currentPatient.setNumeroSs(patient.getNumeroSs());
		currentPatient.setCivilite(patient.getCivilite());
		currentPatient.setCodePostal(patient.getCodePostal());
		currentPatient.setDateNaissance(patient.getDateNaissance());
		currentPatient.setEmail(patient.getEmail());
		
		try {
			patientService.savePatient(currentPatient);
			return new ResponseEntity<Patient>(currentPatient, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.debug("error updating Patient");
			return new ResponseEntity(HttpStatus.NO_CONTENT) ;
		}
		
		

	}
}
