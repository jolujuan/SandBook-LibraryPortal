package org.sandbook.srv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sandbook.model.LogError;
import org.springframework.stereotype.Service;

@Service
public class LogErrorService {

	private static List<LogError> errores = new ArrayList<LogError>();

	public List<LogError> listaErrores(String criterioOrd) {
		List<LogError> logOrdenado = new ArrayList<LogError>();

		switch (criterioOrd) {
		case "id":
			logOrdenado = errores.stream().sorted(Comparator.comparing(LogError::getId)).collect(Collectors.toList());
			break;

		case "tipo":
			logOrdenado = errores.stream().sorted(Comparator.comparing(LogError::getTipo)).collect(Collectors.toList());
			break;

		case "explicacion":
			logOrdenado = errores.stream().sorted(Comparator.comparing(LogError::getExplicacion))
					.collect(Collectors.toList());
			break;

		default:
			// Este és el metode per defecte
			logOrdenado = errores.stream().sorted(Comparator.comparing(LogError::getId)).collect(Collectors.toList());
		}
		return logOrdenado;
	}

	public void addError(LogError logError) {

		logError.setId(siguienteIdError());
		errores.add(logError);
		
	}

	public int siguienteIdError() {
		int idMaximo;
		Optional<LogError> idMaxEncontrado = errores.stream().max(Comparator.comparing(LogError::getId));
		if (idMaxEncontrado.isPresent()) {
			idMaximo = idMaxEncontrado.get().getId() + 1;
		} else {
			idMaximo = 1;
		}
		return idMaximo;
	}
	
	public void delError(int errorId) {
		LogError logAEliminar= errores.stream().filter(error -> error.getId()==errorId).findFirst().orElse(null);
		if (logAEliminar!=null) {
			errores.remove(logAEliminar);
		}
	}
	
	public List<String> listaFiltrar() {
		List<String> listaFiltrar = new ArrayList<String>();
		listaFiltrar.add("Id");
		listaFiltrar.add("Tipo");
		listaFiltrar.add("Explicacion");

		return listaFiltrar;
	}
	
	public List<LogError> filtrarErrores(String campo, String valor) {
		List<LogError> erroresFiltrados = new ArrayList<>();

		switch (campo) {
		case "Id":
			Optional<LogError> errorLeidoId = errores.stream().filter(id -> id.getId() == Integer.parseInt(valor)).findFirst();
			erroresFiltrados.add(errorLeidoId.get());
			break;

		case "Tipo":
			Optional<LogError> errorLeidoTipo = errores.stream().filter(tipo -> tipo.getTipo().equals(valor)).findFirst();
			erroresFiltrados.add(errorLeidoTipo.get());
			break;

		case "Explicacion":
			List<LogError> errorLeidoExp = errores.stream().filter(expl -> expl.getExplicacion().equals(valor))
					.collect(Collectors.toList());
			erroresFiltrados.addAll(errorLeidoExp);
			break;

		default:
			break;
		}
		return erroresFiltrados;
	}
}
