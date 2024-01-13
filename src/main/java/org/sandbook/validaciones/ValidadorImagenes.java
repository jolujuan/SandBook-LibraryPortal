package org.sandbook.validaciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sandbook.interfaces.ImagenValida;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorImagenes implements ConstraintValidator<ImagenValida, MultipartFile> {
	public static final List<String> tiposDeImagenes = Arrays.asList("image/png", "image/jpg", "image/jpeg",
			"image/gif");
	public static final long MAX_BYTES = 524288;// 512 KB de tamaño maximo

	public void initialize(ImagenValida constraintAnnotation) {
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		// Por defecto resultado de la comprobación válido hasta que encontremos un
		// error
		boolean result = true;
		// comprobar lista de errores
		ArrayList<String> listaErrores = mensajesErrorImagen(multipartFile);
		// Si hay errores añadirlos al contexto
		if (!listaErrores.isEmpty()) {
			context.disableDefaultConstraintViolation();
			// iteramos por la lista de errores para añadirlos al contexto
			for (String textoError : listaErrores) {
				context.buildConstraintViolationWithTemplate(textoError).addConstraintViolation();
			}
			// Comprobación incorrecta (resultado no valido)
			result = false;
		}
		// Devolvemos resultado de la comprobación
		return result;
	}

	public static ArrayList<String> mensajesErrorImagen(MultipartFile fichero) {

		ArrayList<String> errores = new ArrayList<String>();

		// Fichero no vacio
		String contentType = fichero.getContentType();
		if (!tipoDeImagenValido(contentType)) {
			errores.add("Solo se permiten imagenes PNG, JPG O GIF.");
		}

		// Comprobar tamaño máximo
		if (fichero.getSize() > MAX_BYTES) {
			errores.add("Tamaño máximo de la imagen excedido (" + MAX_BYTES + "bytes)");
		}

		return errores;
	}

	// Comprueba si el tipo de imagen es uno de los predefinidos en
	// "tiposDeImagenes"
	private static boolean tipoDeImagenValido(String contentType) {
		return tiposDeImagenes.contains(contentType);
	}

	/**
	 * METODOS PUBLICOS PARA UTILIZAR EN OTRAS CLASE, POR EJEMPLO FileService.java
	 **/
	public static boolean imagenValida(MultipartFile fichero) {
		ArrayList<String> listaErrores = mensajesErrorImagen(fichero);
		// Si esta vacia no hay errores
		return listaErrores.isEmpty() ? true : false;
	}

	// Comprueba si el tipo de fichero esta en la lista "tiposDeImagenes" y devuelve
	// solo la extension. Si no esta devuelve ""
	public static String getExtension(MultipartFile fichero) {
		String contentType = fichero.getContentType();

		// Verificar si el tipo de contenido está en la lista "tiposDeImagenes"
		if (tiposDeImagenes.contains(contentType)) {
			// Obtener la extensión del tipo de contenido
			int extensionIndex = contentType.lastIndexOf("/");
			if (extensionIndex >= 0) {
				return contentType.substring(extensionIndex + 1);
			}
		}
		return "";
	}

}
