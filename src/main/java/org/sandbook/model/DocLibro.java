package org.sandbook.model;

import java.util.Objects;

import org.sandbook.interfaces.DocumentoLibroValido;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DocLibro implements Comparable<DocLibro> {
	private int isbn;
	@NotNull(message = "El id no puede estar vacio")
	private Integer id;
	@NotNull(message = "El tipo no puede estar vacio")
	private String tipo;
	@NotNull(message = "La cantidad no puede ser nula")
	@Min(1)
    @Max(20)
	int cantidad;
	
	@NotNull
	@DocumentoLibroValido
	private MultipartFile fichero;

	String tipoFichero = "";
	String contentTypeFichero = "";

	public DocLibro() {
	}

	public DocLibro(Integer id) {
		super();
		this.id = id;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public DocLibro(int isbn, @NotNull(message = "El id no puede estar vacio") Integer id,
			@NotNull(message = "El tipo no puede estar vacio") String tipo,
			@NotNull(message = "La cantidad no puede ser nula") int cantidad) {
		super();
		this.isbn = isbn;
		this.id = id;
		this.tipo = tipo;
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public MultipartFile getFichero() {
		return fichero;
	}

	public void setFichero(MultipartFile fichero) {
		this.fichero = fichero;
	}

	public String getTipoFichero() {
		return tipoFichero;
	}

	public void setTipoFichero(String tipoFichero) {
		this.tipoFichero = tipoFichero;
	}

	public String getContentTypeFichero() {
		return contentTypeFichero;
	}

	public void setContentTypeFichero(String contentTypeFichero) {
		this.contentTypeFichero = contentTypeFichero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocLibro other = (DocLibro) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int compareTo(DocLibro o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
