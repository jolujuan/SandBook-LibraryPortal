package org.sandbook.model;

import org.sandbook.interfaces.ImagenValida;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ImagenUsuario {
	 @Size(min = 5, message = "El usuario debe de tener un tamaño")
	 private String nickname;
	 @NotNull
	 @ImagenValida
	 private MultipartFile imagen;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public MultipartFile getImagen() {
		return imagen;
	}
	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}
	public ImagenUsuario( String nickname,MultipartFile imagen) {
		super();
		this.nickname = nickname;
		this.imagen = imagen;
	}
	public ImagenUsuario(String nickname) {
		this.nickname=nickname;
		// TODO Auto-generated constructor stub
	}
	
	public ImagenUsuario() {}
	 
	 
}
