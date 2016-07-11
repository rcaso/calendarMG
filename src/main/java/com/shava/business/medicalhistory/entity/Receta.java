package com.shava.business.medicalhistory.entity;

import java.io.Serializable;

public class Receta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6318159137719989117L;

	private String medicina;
	
	private String dosis;
	
	private Integer diasTratamiento;
	
	private Integer frecuenciaHoras;
	
	private String urlImagenReceta;

	public String getMedicina() {
		return medicina;
	}

	public void setMedicina(String medicina) {
		this.medicina = medicina;
	}

	public String getDosis() {
		return dosis;
	}

	public void setDosis(String dosis) {
		this.dosis = dosis;
	}

	public Integer getDiasTratamiento() {
		return diasTratamiento;
	}

	public void setDiasTratamiento(Integer diasTratamiento) {
		this.diasTratamiento = diasTratamiento;
	}

	public Integer getFrecuenciaHoras() {
		return frecuenciaHoras;
	}

	public void setFrecuenciaHoras(Integer frecuenciaHoras) {
		this.frecuenciaHoras = frecuenciaHoras;
	}

	public String getUrlImagenReceta() {
		return urlImagenReceta;
	}

	public void setUrlImagenReceta(String urlImagenReceta) {
		this.urlImagenReceta = urlImagenReceta;
	}

}
