package org.sandbook.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.sandbook.validaciones.ValidadorImagenes;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//Información sobre como creear una anotación en Java:
//https://www.baeldung.com/java-custom-annotation
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidadorImagenes.class })
public @interface ImagenValida {
	String message() default "Imagen incorrecta";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}