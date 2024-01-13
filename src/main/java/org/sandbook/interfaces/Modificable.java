package org.sandbook.interfaces;

import java.util.Date;

public interface Modificable <AnyType> {
	//Getters y setters necesarios en el itemActual
	public Date getTs();
	
	public String getUser();
	
	public void setTs(Date ts);
	
	public void setUser(String user);
	
	//Sobre el item actual 
	//se aplicara el metodo "sePuedeModificarUtilizando(itemModificado)"
	//para saber si se puede modificar. Si no tenemos los datos
	//actualizados (fecha y usuario iguales) nos dira que no
	public boolean sePuedeModificarUtilizando(AnyType itemModificado);
	
	//Mensaje en caso de que no podamos actualizar el Item
	public String mensajeNoSePuedeModificar();
}
