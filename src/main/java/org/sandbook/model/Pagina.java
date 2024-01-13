package org.sandbook.model;

public class Pagina {
	String titulo;
	String paginaActiva;
	
	public Pagina(String titulo, String paginaActiva) {
		super();
		this.titulo = titulo;
		this.paginaActiva = paginaActiva;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPaginaActiva() {
		return paginaActiva;
	}
	public void setPaginaActiva(String paginaActiva) {
		this.paginaActiva = paginaActiva;
	}
	
	public String getStrBootStrapActiva(String pagina) {
		if (paginaActiva.equals(pagina)) {
			return "active";
		}return "";
	}
}
