package org.sandbook.order;

import java.util.Comparator;

import org.sandbook.model.Libro;

public class ComparadorAño implements Comparator<Libro> {

	@Override
	public int compare(Libro a1, Libro a2) {
		int compararAño = a1.getAño()-(a2.getAño());

		if (compararAño != 0) {
			return compararAño;
		} else {
			return a1.getTitulo().compareTo(a2.getTitulo());
		}
	}
}
