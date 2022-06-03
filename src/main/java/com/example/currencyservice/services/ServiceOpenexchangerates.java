package com.example.currencyservice.services;

import com.example.currencyservice.clients.OpenexchangeratesClient;
import com.example.currencyservice.models.GiphyModel;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис для работы с клиентом сервиса валют Openexchangerates.org
 */
public interface ServiceOpenexchangerates {

    /**
     * Запрашивает  сегодняшнее и вчерашнее значения для базовой и сравниваемой валют у {@link OpenexchangeratesClient},
     * далее сравнивает значения, если базовая валюта выросла возвращает картинку со значение rich,
     * иначе со значением broke
     * @param code строка кода валюты для сравненеия с базовой(USD)
     * @return GiphyModel с сообщением, статусом ответа и ссылкой на картинку
     */
    GiphyModel getChanges(String code);

    /**
     * Генерирует вчерашнюю дату при каждом запросе
     * @return вчерашнюю дату в формате уууу-ММ-dd
     */
    LocalDate getYesterdayDate();

    /**
     * Запрашивает у {@link OpenexchangeratesClient} все доступные для сравнения валюты
     * @return список валютных буквенных кодов
     */
    List<String> getListCurrency();
}
