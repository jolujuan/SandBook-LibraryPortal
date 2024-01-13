package org.sandbook.model;

import java.util.Objects;

public class LogError implements Comparable<LogError>{
	private Integer id;
	private String tipo;
	private String explicacion;
	public Integer getId() {
		return id;
	}
	public LogError(Integer id) {}
	
	public LogError( String tipo, String explicacion) {
		super();
		this.tipo = tipo;
		this.explicacion = explicacion;
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
	public String getExplicacion() {
		return explicacion;
	}
	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}
	@Override
	public int compareTo(LogError arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int hashCode() {
		return Objects.hash(explicacion, id, tipo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogError other = (LogError) obj;
		return Objects.equals(explicacion, other.explicacion) && Objects.equals(id, other.id)
				&& Objects.equals(tipo, other.tipo);
	}

	
}
