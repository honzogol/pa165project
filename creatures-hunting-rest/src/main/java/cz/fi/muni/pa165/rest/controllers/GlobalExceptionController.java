package cz.fi.muni.pa165.rest.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import cz.fi.muni.pa165.rest.ApiError;
import cz.fi.muni.pa165.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

/**
 * Class handling the Rest exceptions, translating them to HttpStatuses
 *
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ApiError handleException(ResourceAlreadyExistingException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(InvalidParameterException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiError handleException(ResourceNotFoundException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(IllegalArgumentException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiError handleException(InvalidFormatException e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiError handleException(Exception e) {
		ApiError apiError = new ApiError();
		apiError.setErrors(Collections.singletonList(e.getMessage()));
		return apiError;
	}
}
