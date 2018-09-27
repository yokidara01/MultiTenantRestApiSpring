package com.ws.patient.model;
 
import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "patient", schema = "sc_mmt")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5755585928823529555L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Id")
	private Long id;

	
	
	@Column(name = "Nom")
	private String nom;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	
	@Column(name = "Civilite",length=1)
	private String civilite="c";

	
	@Column(name = "Prenom")
	private String prenom="";

	
	@Column(name = "Date_naissance")
	private Date dateNaissance=null;

	
	@Column(name = "Adresse")
	private String adresse="";

	
	@Column(name = "Code_postal")
	private int codePostal=0;

	
	@Column(name = "Ville")
	private String ville="";

	
	@Column(name = "Telephone")
	private String telephone="";

	
	@Column(name = "Mail")
	private String email="";

	
	@Column(name = "Numero_ss", length = 15)
	private String numeroSs="";

	
	@Column(name = "Carte_vitale")
	private String carteVitale="";

	
	@Column(name = "Mutuelle")
	private String mutuelle="";

	
	@Column(name = "Attestation_CMU")
	private String attestationCMU="";

	
	@Column(name = "Ald")
	private String ald="";

	
	@Column(name = "Nom_medecin")
	private String nomMedecin="";

	
	@Column(name = "Adresse_medecin")
	private String adresseMedecin="";

	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idPatient")
//	private List<PriseEnCharge> priseEnCharges;

	
	 
	public Patient() {

	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

//	public List<PriseEnCharge> getPriseEnCharges() {
//		return priseEnCharges;
//	}

//	public void setPriseEnCharges(List<PriseEnCharge> priseEnCharges) {
//		this.priseEnCharges = priseEnCharges;
//	}

	public String getNumeroSs() {
		return numeroSs;
	}

	public void setNumeroSs(String numeroSs) {
		this.numeroSs = numeroSs;
	}

	public String getCarteVitale() {
		return carteVitale;
	}

	public void setCarteVitale(String carteVitale) {
		this.carteVitale = carteVitale;
	}

	public String getMutuelle() {
		return mutuelle;
	}

	public void setMutuelle(String mutuelle) {
		this.mutuelle = mutuelle;
	}

	public String getAttestationCMU() {
		return attestationCMU;
	}

	public void setAttestationCMU(String attestationCMU) {
		this.attestationCMU = attestationCMU;
	}

	public String getAld() {
		return ald;
	}

	public void setAld(String ald) {
		this.ald = ald;
	}


	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomMedecin() {
		return nomMedecin;
	}

	public void setNomMedecin(String nomMedecin) {
		this.nomMedecin = nomMedecin;
	}

	public String getAdresseMedecin() {
		return adresseMedecin;
	}

	public void setAdresseMedecin(String adresseMedecin) {
		this.adresseMedecin = adresseMedecin;
	}

}
