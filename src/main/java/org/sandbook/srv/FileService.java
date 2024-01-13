package org.sandbook.srv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.sandbook.validaciones.ValidadorDocumentoAlumno;
import org.sandbook.validaciones.ValidadorImagenes;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	// En los FICHEROS ESTÁTICOS, los datos se almacenan en una carpeta dentro del
	// fichero comprimido .WAR que se ha cargado en Tomcat.
	// En los FICHEROS DINÁMICOS los ficheros se almacenan fuera del fichero
	// comprimido .WAR, esto significa que se guardará en una CARPETA DEL SERVIDOR
	// WEB
	/**
	 * CONSTANTES CON DATOS PARA SABER LA CARPETA PRINCIPAL DE LA APP DONDE
	 * GUARDAR/LEER LOS FICHEROS
	 */
	// Podemos almacenar los ficheros dinámicos donde queramos, pero si ponemos una
	// carpeta fija, dicha ruta tiene codificación Windows o Linux, por lo que
	// nuestra app solo podriamos ejecutarla en ese sistema operativo.
	// La carpeta $HOME del usuario se puede extraer gracias a la clase System
	// independientemente de que estemos en Windows, Linux o Mac.
	private static final String USER_HOME_TOMCAT = System.getProperty("user.home");
	// En linux el separador de carpetas es "/", en windows "\", para hacer nuestra
	// APP independiente del sistema operativo donde se cargue utilizamos el
	// File.separator.
	private static final String SEPARATOR = File.separator;
	// Carpeta donde se almacenaran todos los ficheros dinámicos de la app
	private static final String CARPETA_FICHEROS_DINAMICOS_WEBAPP = USER_HOME_TOMCAT + SEPARATOR
			+ "AlumnosWebApp_DynamicFiles" + SEPARATOR;
	/** CONSTANTES CON EL NOMBRE DE LAS CARPETAS DONDE GUARDAR LOS FICHEROS: **/
	// Carpeta con las imagenes de los usuarios
	private static final String CARPETA_IMAGENES_USUARIOS = CARPETA_FICHEROS_DINAMICOS_WEBAPP + "ImagenesUsuarios";
	// Carpeta con las imagenes de los usuarios
	private static final String CARPETA_DOCUMENTACION_ALUMNOS = CARPETA_FICHEROS_DINAMICOS_WEBAPP
			+ "DocumentacionAlumnos";

	public FileSystemResource getImagenUsuario(String fichero) {
		return new FileSystemResource(new File(CARPETA_IMAGENES_USUARIOS, fichero));
	}
	
	public FileSystemResource getDocumentoAlumno(String nombreFichero) {
		return new FileSystemResource(new File(CARPETA_DOCUMENTACION_ALUMNOS, nombreFichero));
	}
	
	/** METODO PRINCIPAL AL QUE LLAMARAN TODOS LOS MÉTODOS QUE GUARDEN FICHEROS **/
	// Guardar el 'fichero' del formulario en la 'ruta'.
	// Si falla devuelve un String con el error, en caso contrario nulo.
	// 'ruta' ya incluye el nombre final del fichero.
	// Ejemplo:
	// ruta='/home/joseramon/AlumnosWebApp_DynamicFiles/ImagenesUsuarios/imagenJoseRamon.jpg'
	private String guardarFichero(String ruta, MultipartFile fichero) {
		try {
			// Obtener datos del fichero
			byte[] fileBytes = fichero.getBytes();
			// Obtener ruta
			Path path = Paths.get(ruta);
			// Guardar fichero
			// El fichero no tiene porque existir en la ruta, pero
			// si la carpeta destino no existe se producirá una excepción
			Files.write(path, fileBytes);
		} catch (NoSuchFileException e) {
			return "No existe la carpeta para poder guardar '" + e.getMessage() + "'";
		} catch (IOException e) {
			// Si hay error devolver mensaje de error
			return e.getMessage();
		} // Si no hay error devolver null
		return null;
	}

	/** Guardar imagenes de los usuarios **/
	// Guardar fichero en la carpeta de las imagenes de usuario con un nombre
	// determinado
	// Si falla devuelve una lista con los errores detectados
	public ArrayList<String> guardaImagenUsuario(MultipartFile fichero, String nickName) {
		String nombreFichero = getNombreImagenUsuario(fichero, nickName);
		// Comprobaciones de errores
		if (!ValidadorImagenes.imagenValida(fichero)) {
			return ValidadorImagenes.mensajesErrorImagen(fichero);
		}
		// Guardar fichero
		String errorAlGuardar = guardarFichero(CARPETA_IMAGENES_USUARIOS + SEPARATOR + nombreFichero, fichero);
		if (errorAlGuardar == null)
			return new ArrayList<String>();
		else
			return new ArrayList<String>(List.of(errorAlGuardar));
	}

	// Consulta el tipo de extensión del fichero y devuelve un String"nickname.ext"
	public String getNombreImagenUsuario(MultipartFile fichero, String nomFicher) {
	    String extension = ValidadorImagenes.getExtension(fichero);
	    // Comprueba si nickName ya termina con la extensión correcta
	    if (!nomFicher.endsWith("." + extension)) {
	        nomFicher += "." + extension;
	    }
	    return nomFicher;
	}


////////////	PARA EL FICHERO ////////////
	public String getExtensionMultipartfile(MultipartFile fichero) {
		String extension = ValidadorDocumentoAlumno.getExtension(fichero);
		return extension;
	}

	public ArrayList<String> guardaDocumentacionAlumno(MultipartFile fichero, String nombreFicheroGuardar) {
		String nombreFichero = getNombreImagenUsuario(fichero, nombreFicheroGuardar);
		// Comprobaciones de errores
		if (!ValidadorDocumentoAlumno.documentoValido(fichero)) {
			return ValidadorDocumentoAlumno.mensajesErrorDocumento(fichero);
		}
		// Guardar fichero
		String errorAlGuardar = guardarFichero(CARPETA_DOCUMENTACION_ALUMNOS + SEPARATOR + nombreFichero, fichero);
		if (errorAlGuardar == null)
			return new ArrayList<String>();
		else
			return new ArrayList<String>(List.of(errorAlGuardar));
	}

	
}
