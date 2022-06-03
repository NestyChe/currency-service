package com.example.currencyservice.services;

import com.example.currencyservice.clients.GiphyClient;
import com.example.currencyservice.models.GiphyModel;

/**
 * Сервис для работы с клиентом сервиса изображений giphy.com
 */
public interface ServiceGiphy {

    /**
     * Запрашивает картинку со значением rich у {@link GiphyClient} и возвращает объектную модель ответа
     * с сообщением, статусом ответа и ссылкой на картинку
     * @return {@link GiphyModel}
     */

    GiphyModel getRich();

    /**
     * Запрашивает картинку со значением broke у {@link GiphyClient} и возвращает объектную модель ответа
     * с сообщением, статусом ответа и ссылкой на картинку
     * @return {@link GiphyModel}
     */

    GiphyModel getBroke();
}
