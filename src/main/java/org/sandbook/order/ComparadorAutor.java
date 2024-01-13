package org.sandbook.order;

import java.util.Comparator;

import org.sandbook.model.Libro;

public class ComparadorAutor implements Comparator<Libro> {

	@Override
	public int compare(Libro a1, Libro a2) {
		return a1.getAutor().compareTo(a2.getAutor());	

	}
}
