package com.corrector.app;

import com.corrector.app.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRoboprof {
	
	private Roboprof unRoboprof;
	private Parcial unParcial;
	
	@Before
	public void setUp() {
		Pregunta unaPregunta = new Pregunta();
		unaPregunta.descripcion = "¿Cual es la capital de Italia?";
		unaPregunta.respuestaCorrecta = "Roma";
		unaPregunta.pesoEspecifico = 10;
		Pregunta otraPregunta = new Pregunta();
		otraPregunta.descripcion = "¿Cual es la capital de España?";
		otraPregunta.respuestaCorrecta = "Madrid";
		otraPregunta.pesoEspecifico = 5;
		
		Item unItem = new Item();
		unItem.pregunta = unaPregunta;
		unItem.respuestaAlumno = "Roma";
		Item otroItem = new Item();
		otroItem.pregunta = otraPregunta;
		otroItem.respuestaAlumno = "Madrid";
		
		unRoboprof = new Roboprof();
		
		unParcial = new Parcial();
		unParcial.items = new ArrayList<Item>();
		unParcial.items.add(otroItem);
		unParcial.items.add(unItem);
	}

	@Test
	public void testCorrigePorRestaYSaca12() {
		Assert.assertEquals(12, unRoboprof.corregirConResta(unParcial, 3), 0);
	}
	
	@Test
	public void testCorrigePorReglaDeTresYSaca10() {
		Assert.assertEquals(10, unRoboprof.corregirConReglaDeTres(unParcial), 0);
	}
	
	@Test
	public void testCorrigePorTablaYSaca5() {
		Assert.assertEquals(5, unRoboprof.corregirPorTabla(unParcial), 0);
	}
	
	@Test
	public void testCorrigeConNotaMasAltaYSaca() {
		Assert.assertEquals(10, unRoboprof.corregirTomandoNotaMasAlta(unParcial), 0);
	}
	
	@Test
	public void testCorrigeConPromedioYSaca8() {	
		Assert.assertEquals(8, unRoboprof.corregirTomandoPromedio(unParcial), 0);
	}

}