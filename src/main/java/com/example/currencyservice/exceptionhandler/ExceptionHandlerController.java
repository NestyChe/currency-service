package com.example.currencyservice.exceptionhandler;

import com.example.currencyservice.models.GiphyModel;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * контроллер для обработки исключений
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Обрабатывает ошибку с неверными параметрами запроса
     * @param exception ловит FeignException.BadRequest
     * @return GiphyModel с сообщением ошибки и HTTP статусом 400
     */
    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<GiphyModel> handleFeignStatus_400Exception(FeignException exception) {
        GiphyModel.Meta meta = new GiphyModel.Meta();
        meta.setMsg(exception.contentUTF8());
        return new ResponseEntity<>(new GiphyModel(meta), HttpStatus.valueOf(exception.status()));
    }

    /**
     * Обрабатывает ошибку со пробемой со стороны стороннего сервиса
     * @param exception ловит FeignException.InternalServerError
     * @return GiphyModel с сообщением ошибки и HTTP статусом 500
     */
    @ExceptionHandler(FeignException.InternalServerError.class)
    public ResponseEntity<GiphyModel> handleFeignStatus_500Exception(FeignException exception) {
        GiphyModel.Meta meta = new GiphyModel.Meta();
        meta.setMsg(exception.contentUTF8());
        return new ResponseEntity<>(new GiphyModel(meta), HttpStatus.valueOf(exception.status()));
    }


}
