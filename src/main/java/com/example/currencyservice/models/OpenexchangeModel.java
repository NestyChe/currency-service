package com.example.currencyservice.models;


import lombok.Data;

import java.util.Map;

/**
 * Модель для работы с ответами от сервиса openexchangerates.org
 */

@Data
public class OpenexchangeModel {

    private String disclaimer;

    private String license;

    private Integer timestamp;

    private String base;

    private Map<String, Double> rates;

    public OpenexchangeModel() {}
}
