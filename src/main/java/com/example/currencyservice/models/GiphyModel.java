package com.example.currencyservice.models;

import lombok.Data;

/**
 * Модель для работы с ответами от сервиса giphy.com
 */
@Data
public class GiphyModel {
    /**
     * Мета данные, содержит сообщение и статус ответа
     */
    private Meta meta;
    /**
     * Тело ответа, содержит тип и ссылку на картинку
     */
    private Data data;

    public GiphyModel(Meta meta) {
        this.meta = meta;
    }

    public GiphyModel() {
    }


    @lombok.Data
    public static class Meta {

        private String msg;
        private Integer status;

    }

    @lombok.Data
    public static class Data {
        private String type;
        private String id;

        public String getId() {
            return new StringBuffer().append("https://media0.giphy.com/media/").append(id).append("/giphy.gif").toString();
        }

    }
}
