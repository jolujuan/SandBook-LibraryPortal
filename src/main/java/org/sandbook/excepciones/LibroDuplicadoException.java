package org.sandbook.excepciones;

import org.sandbook.model.Libro;

public class LibroDuplicadoException extends Exception {
	private static final long serialVersionUID = 1L;

	Libro libroActual;
	Libro nuevoLibro;

	public LibroDuplicadoException(Libro libroActual, Libro nuevoLibro) {
		this.libroActual = libroActual;
		this.nuevoLibro = nuevoLibro;
	}

	@Override
	public String toString() {
		return "<p style=\"color: red; font-weight: bold;\">ERROR insertando Libro <br>" + "Libro Existente: <br>"
				+ "isbn: " + libroActual.getIsbn() + "<br>" + "nombre: " + libroActual.getTitulo() + "<br>"
				+ "Libro nuevo: <br>" + "isbn" + nuevoLibro.getIsbn() + "<br>"
				+ "nombre: Titulo del Libro duplicado <p>";
	}
}
