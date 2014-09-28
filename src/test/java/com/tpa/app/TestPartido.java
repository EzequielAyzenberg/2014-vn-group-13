package com.tpa.app;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.tpa.app.Inscripcion.PrioridadesInscripciones;
import com.tpa.app.NoEstaInscriptoExcepcion;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

;

public class TestPartido {

	@Mock
	MailSender mailSenderMock;
	
	Partido partido, partido2;
	Jugador jugador, otroJugador;
	Persona persona;
	Inscripcion inscEstandar;

	@Before
	public void setUp() {
		LocalDateTime fecha_y_hora = LocalDateTime.now();
		mailSenderMock = mock(MailSender.class);
		partido = new Partido(fecha_y_hora, "Parque Patricios", 10, mailSenderMock);
		partido2 = new Partido(fecha_y_hora, "Parque Patricios", 10, mailSenderMock);
		LocalDateTime fechaNac = LocalDateTime.of(1991, 9, 26, 23, 25);
		persona = new Persona(fechaNac, "ceciliazgr@gmail.com");
		jugador = new Jugador(persona);
		otroJugador = new Jugador(persona);
	}

	// Test #1 - Inscribir jugadores y notificar administrador
	// Test #1.1 - Inscribir 10 o más jugadores con cualquier modalidad y que se
	// notifique al administrador una vez por cada inscripción que supera o
	// iguala los 10 inscriptos
	@Test
	public void testNotificarAlAdministrador() {
		int i;
		for (i = 0; i < 10; i++)
			partido.inscribir(new Inscripcion(jugador, PrioridadesInscripciones.ESTANDAR, null));
		for (i = 0; i < 5; i++)
			partido.inscribir(new Inscripcion(jugador, PrioridadesInscripciones.SOLIDARIA, null));
		for (i = 0; i < 8; i++)
			partido.inscribir(new Inscripcion(jugador, PrioridadesInscripciones.CONDICIONAL, null));

		verify(mailSenderMock, times(14)).enviarMail(any(Mail.class));
	}

	// Test #1.2 - Inscribir menos de 10 jugadores con cualquier modalidad y que
	// no se notifique al administrador
	@Test
	public void testNoNotificarAlAdministrador() {
		for (int i = 0; i < 8; i++)
			partido2.inscribir(new Inscripcion(jugador, PrioridadesInscripciones.ESTANDAR, null));

		verify(mailSenderMock, times(0)).enviarMail(any(Mail.class));
	}
	
	// Test #2 - Calificar a un jugador correcto
	
	@Test
	public void testNotaDeCalificacionCorrectaParaCalificarJugadorValido() {
		Inscripcion inscripcionEstandar = new Inscripcion(jugador, PrioridadesInscripciones.ESTANDAR, null);
		partido.inscribir(inscripcionEstandar);
		partido.calificar(jugador, jugador, 10, "soy el mejor del mundo");

		Assert.assertEquals(partido.getCalificaciones().get(0).getNota(), 10);
	}

	@Test
	public void testJugadorDeCalificacionCorrectaParaCalificarJugadorValido() {
		Inscripcion inscripcionEstandar = new Inscripcion(jugador, PrioridadesInscripciones.ESTANDAR, null);
		partido.inscribir(inscripcionEstandar);
		partido.calificar(jugador, jugador, 10, "soy el mejor del mundo");

		Assert.assertEquals(partido.getCalificaciones().get(0).getJugador(), jugador);
	}

	@Test
	public void testCriticaDeCalificacionCorrectaParaCalificarJugadorValido() {
		Inscripcion inscripcionEstandar = new Inscripcion(jugador, PrioridadesInscripciones.ESTANDAR, null);
		partido.inscribir(inscripcionEstandar);
		partido.calificar(jugador, jugador, 10, "soy el mejor del mundo");

		Assert.assertEquals(partido.getCalificaciones().get(0).getCritica(), "soy el mejor del mundo");
	}

	// Test #2.1 - Calificar a un jugador incorrecto

	@Test(expected = NoEstaInscriptoExcepcion.class) 
	public void testCalificarJugadorNoValido() {
		partido.calificar(jugador, jugador, 3, "pesimo!!!!");
	}
}