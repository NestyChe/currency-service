package com.example.currencyservice.clients;

import com.example.currencyservice.models.OpenexchangeModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

/**
 * Клиент OpenFeign который обеспечивает интеграцию с сервисом валют openexchangerates.org
 */
@FeignClient(name = "restClient", url = "${openexchangerates.url}")
public interface OpenexchangeratesClient {

    /**
     * Получает курсы валют на сегодняшний день
     * @param appId ключ для доступа к сервису
     * @param base валюта курс которой сравнивается
     * @param symbols валюта к которой сравнивается базовая валюта
     * @return OpenexchangeModel с сообщением, статусом ответа и картой валюта-стоимость
     */
    @GetMapping(path = "/latest.json",
            consumes = "application/json")
    OpenexchangeModel getLatest(@RequestParam("app_id") String appId, @RequestParam("") String base, @RequestParam("") String symbols);

    /**
     *  Получает курсы валют на указанную первым параметром дату
     * @param data день для поиска значения курса
     * @param appId ключ для доступа к сервису
     * @param base валюта курс которой сравнивается
     * @param symbols валюта к которой сравнивается базовая валюта
     * @return OpenexchangeModel с сообщением, статусом ответа и картой валюта-стоимость
     */
    @GetMapping(path = "/historical/{date}.json", consumes = "application/json")
    OpenexchangeModel getHistorical(@PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
                                    @RequestParam("app_id") String appId, @RequestParam("") String base,
                                    @RequestParam("") String symbols);

    /**
     * Получает все доступные для просмотра курса валюты
     * @return карту валюта-стоимость
     */
    @GetMapping(path = "/currencies.json", consumes = "application/json")
    Map<String, String> getAllCurrencies();
}
