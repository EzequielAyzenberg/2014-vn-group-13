package com.tpa.app;

public class PorHandicap implements Criterio {
	private String nombre = "Por Handicap";

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public double dameTuValor(Inscripcion inscripcion) {
		return inscripcion.getJugador().getHandicap();
	}
}
