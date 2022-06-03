package com.example.currencyservice.clients;

import com.example.currencyservice.models.GiphyModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Клиент OpenFeign который обеспечивает интеграцию с сервисом изображений giphy.com
 */
@FeignClient(name = "client", url = "${giphy.url}")
public interface GiphyClient {

    /**
     * Запрашивает случайную картинку и возвращает модель с ссылкой на картинку
     * @param api ключ для доступа к сервису giphy.com
     * @param tag значение для поиска определенного типа картинки
     * @return GiphyModel
     */
    @GetMapping(consumes = "application/json")
    GiphyModel getGif(@RequestParam("api_key") String api, @RequestParam("tag") String tag);

}
