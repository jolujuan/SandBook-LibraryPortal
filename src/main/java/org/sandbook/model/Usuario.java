package org.sandbook.model;

import java.util.Date;

public class Usuario {
//	@Size(min = 5, message = "El usuario debe tener mínimo 5 carácteres")
	private String nickname;
//	@Size(min = 8, max = 16, message = "La contraseña debe tener entre 8 y 16 caracteres")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$", message = "La contraseña debe tener al menos un dígito, una minúscula, una mayúscula y un carácter no alfanumérico.")
	private String password;
	
	private String nombreFicheroConImagen;//contendra nickname.ext
	private Date ts;//fecha ultima actualizacion
	private String user;//persona que realiza modificacion
	
	public Usuario() {}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getNombreFicheroConImagen() {
		return nombreFicheroConImagen;
	}

	public void setNombreFicheroConImagen(String nombreFicheroConImagen) {
		this.nombreFicheroConImagen = nombreFicheroConImagen;
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

	public Usuario( String nickname, String password) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.nombreFicheroConImagen="Desconocido.jpg";
	}
	
}
	