package org.sandbook.validaciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sandbook.interfaces.DocumentoLibroValido;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorDocumentoAlumno implements ConstraintValidator<DocumentoLibroValido, MultipartFile> {
	public static final List<String> tiposDeDocumentos = Arrays.asList("image/png", "image/jpg", "image/jpeg",
			"image/gif", "text/plain", "application/pdf", "application/msword", "application/vnd.ms-excel",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/excel",
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			"application/vnd.oasis.opendocument.text", "application/vnd.oasis.opendocument.spreadsheet",
			"application/x-rar-compressed", "application/zip", "application/x-zip-compressed", "multipart/x-zip");
	public static final long MAX_BYTES = 2097152;// 2MB de tamaño máximo

	@Override
	public void initialize(DocumentoLibroValido constraintAnnotation) {
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		if (multipartFile == null || multipartFile.isEmpty()) {
			return true; // Considera válido si no se sube un archivo
		}
		boolean result = true;
		// comprobar lista de errores
		ArrayList<String> listaErrores = mensajesErrorDocumento(multipartFile);
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

	public static ArrayList<String> mensajesErrorDocumento(MultipartFile fichero) {
		ArrayList<String> errores = new ArrayList<String>();
		// Validar tipo de fichero
		if (fichero.isEmpty() && "application/octet-stream".equals(fichero.getContentType())) {
			return errores; // No hay errores si el archivo está vacío y es de tipo octet-stream
		}
		String contentType = fichero.getContentType();
		// System.out.println("Tipo de documento de '"+fichero.getName()+"':
		// "+contentType);
		if (!tipoDeDocumentoValido(contentType)) {
			errores.add("Tipo de documento '" + contentType + "' no permitido.");
		}
		// Comprobar tamaño máximo
		if (fichero.getSize() > MAX_BYTES) {
			errores.add("Tamaño máximo del documento excedido (" + MAX_BYTES + " bytes)");
		}
		return errores;
	}

	// Comprueba si el tipo de imagen es uno de los predefinidos en
	// "tiposDeImagenes"
	private static boolean tipoDeDocumentoValido(String contentType) {
		return tiposDeDocumentos.contains(contentType);
	}

	/**
	 * METODOS PUBLICOS PARA UTILIZAR EN OTRAS CLASE, POR EJEMPLO FileService.java
	 **/
	public static boolean documentoValido(MultipartFile fichero) {
		ArrayList<String> listaErrores = mensajesErrorDocumento(fichero);
		// Si esta vacia no hay errores
		return listaErrores.isEmpty() ? true : false;
	}

	// Comprueba si el tipo de fichero esta en la lista "tiposDeImagenes" y devuelve
	// solo la extension. Si no esta devuelve ""
	public static String getExtension(MultipartFile fichero) {
		if (fichero != null && !fichero.isEmpty()) {
			String contentType = fichero.getContentType();
			if (contentType != null && contentType.contains("/")) {
				return contentType.split("/")[1]; // Devuelve solo "png", "jpg", etc.
			}
		}
		return "";
	}

}
