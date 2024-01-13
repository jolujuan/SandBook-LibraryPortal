package org.sandbook.order;

import java.util.Comparator;

import org.sandbook.model.Libro;

public class ComparadorIsbn implements Comparator<Libro> {

	@Override
	public int compare(Libro a1, Libro a2) {
		int comparadorIsbn = a1.getIsbn()-(a2.getIsbn());

		if (comparadorIsbn != 0) {
			return comparadorIsbn;
		} else {
			return a1.getTitulo().compareTo(a2.getTitulo());
		}
	}
}
