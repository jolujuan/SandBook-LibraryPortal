package org.sandbook.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import org.sandbook.interfaces.Modificable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Libro implements Modificable<Libro>, Serializable, Comparable<Libro> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Size(min = 5, message = "El nombre debe tener mínimo 5 carácteres")
	@NotNull(message = "El nombre no puede ser nulo")
	private String titulo;
	@Size(min = 5, message = "El autor debe tener mínimo 5 carácteres")
	@NotNull(message = "El autor no puede ser nulo")
	private String autor;
	@NotNull(message = "El isbn no puede ser nulo")
	private int isbn;
	@Min(value = 1900, message = "El año debe ser al menos 1900")
    @Max(value = 2100, message = "El año no puede ser mayor de 2100")	
	@NotNull(message = "El año no puede ser nulo")
	private int año;

	private Date ts;
	private String user;

	private String[] interesadoEn;
	private String[] genero;

	private ArrayList<DocLibro> docsLibro;
	public ArrayList<DocLibro> getDocsLibro() {
		if (this.docsLibro == null) {
			this.docsLibro = new ArrayList<>();
		}
		return this.docsLibro;
	}
	public void setDocsLibro(ArrayList<DocLibro> docsLibro) {
		this.docsLibro = docsLibro;
	}

	public Libro() {
	}
	
	public Libro(String titulo, String autor, int isbn, int año) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.año = año;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String[] getInteresadoEn() {
		return interesadoEn;
	}

	public void setInteresadoEn(String[] interesadoEn) {
		this.interesadoEn = interesadoEn;
	}

	public String[] getGenero() {
		return genero;
	}

	public void setGenero(String[] genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", isbn=" + isbn + ", año=" + año + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(isbn, other.isbn);
	}

	@Override
	public int compareTo(Libro a2) {
		return this.getTitulo().compareTo(a2.getTitulo());
	}

	public boolean sePuedeModificarUtilizando(Libro itemModificado) {

		if (this.getUser() != null && this.getTs() != null) {

			// Existe un usuario y una fecha Inicial y tenemos que comprobar
			String userActual = this.getUser();
			String userModificado = itemModificado.getUser();
			// formateamos fechas gracias a la clase Ts que formatea fechas
			Date fechaActual = Ts.parseIso(Ts.formatIso(this.getTs()));
			Date fechaModificada = Ts.parseIso(Ts.formatIso(itemModificado.getTs()));
			if (!userActual.equals(userModificado) || !fechaActual.equals(fechaModificada)) {
				// El usuario no es el mismo o la fecha cambia
				return false;
			}
		}
		// No tenemos fecha o usuario -> 1a modificación, por lo que se puede modificar
		return true;
	}

	public String mensajeNoSePuedeModificar() {
		String msg = "<span style='color:red; font-weight: bold;'>\r\n\t[ERROR]\r\n<br/>"
				+ "\t'$item' ha sido modificado por otro usuario.\r\n<br/>"
				+ "\tPara evitar la pérdida de información se impide guardar '$item'.\r\n<br/>"
				+ "\tÚltima modificación realizada por [" + this.getUser() + "] el [" + Ts.ts(this.getTs())
				+ "]\r\n<br/></span>";

		// Para concretar el tipo de registro modificado sustituimos $item por Alumno
		return msg.replace("$item", "Libro");
	}
}
