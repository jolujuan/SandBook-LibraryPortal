package org.sandbook.order;

import java.util.Comparator;

import org.sandbook.model.Libro;

public class ComparadorTitulo implements Comparator<Libro> {

	@Override
	public int compare(Libro a1, Libro a2) {
		return a1.getTitulo().compareTo(a2.getTitulo());	
	}
}
