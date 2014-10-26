package com.tpa.db;

import static com.tpa.app.db.EntityManagerHelper.getEntityManager;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.tpa.app.db.EntityManagerHelper;
import com.tpa.app.model.Administrador;
import com.tpa.app.model.Infraccion;
import com.tpa.app.model.Jugador;
import com.tpa.app.model.Partido;

public class ContextTest {

	@Test
	public void contextUp() {
		EntityManagerHelper.getEntityManager();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void ListaJugadoresNoVacia() {
		
		List<Jugador> jugadoresPersistidos = getEntityManager()
				.createQuery("from Jugador")
				.getResultList();
		
		assertTrue(!jugadoresPersistidos.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void LevantarLosAdmins() {
		
		List<Administrador> administradores = getEntityManager()
				.createQuery("from Administrador")
				.getResultList();
		
		if (administradores == null)
			try { throw new Exception("Debe haber un admin en la BD para realizar este test");
			} catch (Exception e) {	e.printStackTrace(); }
		
		assertTrue(!administradores.isEmpty());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void LevantarLosPartidos() {
		
		List<Partido> partidosPersistidos = getEntityManager()
				.createQuery("from Partido")
				.getResultList();
		
		assertTrue(!partidosPersistidos.isEmpty());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void LevantarLosPartidosPendientesDeConfirmarDeUnAdmin() {
		
		List<Administrador> administradores = getEntityManager()
				.createQuery("from Administrador")
				.getResultList();
		
		if (administradores == null)
			try { throw new Exception("Debe haber un admin en la BD para realizar este test");
			} catch (Exception e) {	e.printStackTrace(); }
		
		Administrador admin = administradores.get(0);
		
		List<Partido> partidosPersistidos = 
				EntityManagerHelper
				.createQuery(String.format("from Partido where administrador.id = :idAdmin and confirmado = false"))
				.setParameter("idAdmin", admin.getId())
				.getResultList();
		
		assertTrue(!partidosPersistidos.isEmpty());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void LevantarJugadorConInfracciones() {
		
		List<Infraccion> infracciones = getEntityManager()
				.createQuery(" from Infraccion")
				.getResultList();
		
		if (infracciones == null)
			try { throw new Exception("Debe haber una infraccion en la BD para realizar este test");
			} catch (Exception e) {	e.printStackTrace(); }
		
		Infraccion infraccion = infracciones.get(0);
		
		assertTrue(!infraccion.getJugador().getInfracciones().isEmpty());
	}
	
	@Test
	public void contextUpWithTransaction() throws Exception {
		EntityManagerHelper.withTransaction(() -> {});
	}
}