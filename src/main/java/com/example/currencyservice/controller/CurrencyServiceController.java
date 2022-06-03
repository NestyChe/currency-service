package com.example.currencyservice.controller;

import com.example.currencyservice.models.GiphyModel;
import com.example.currencyservice.services.impl.ServiceOpenexchangeratesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * REST-контроллер для получения изображения по результатам подсчета валютных значений
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class CurrencyServiceController {

    @Autowired
    private final ServiceOpenexchangeratesImpl serviceOpenexchange;

    public CurrencyServiceController(ServiceOpenexchangeratesImpl serviceOpenexchange) {
        this.serviceOpenexchange = serviceOpenexchange;
    }

    /**
     * Принимает запрос на получение списка строковых кодов доступных валют
     * @return ответ со списком строковых кодов валют и HTTP статус 200
     */
    @GetMapping("currency")
    public ResponseEntity<List<String>> getCurrencyCode() {
        return new ResponseEntity<>(serviceOpenexchange.getListCurrency(), HttpStatus.OK);
    }

    /**
     * Принимает запрос на получение картинки отображающей результат сравнения курсов
     * @param code код валюты для сравнения с базовой
     * @return ответ с моделью в которой ссылка на картинку и HTTP статус 200
     */
    @GetMapping("get/{code}")
    public ResponseEntity<GiphyModel> getExchangeRateDifference(@PathVariable("code") String code)  {
        GiphyModel gif = serviceOpenexchange.getChanges(code);
        return new ResponseEntity<>(gif, HttpStatus.OK);
    }
}
