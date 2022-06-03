package com.example.currencyservice.services.impl;

import com.example.currencyservice.clients.GiphyClient;
import com.example.currencyservice.models.GiphyModel;
import com.example.currencyservice.services.ServiceGiphy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceGiphyImpl implements ServiceGiphy {

    /**
     * Ключ для подключения к сервису giphy.com
     */
    @Value("${giphy.key}")
    private String api;

    /**
     * Тег для запроса картинки если курс вырос
     */
    @Value("${giphy.rich}")
    private String upper;

    /**
     * Тег для запроса картинки если курс упал
     */
    @Value("${giphy.broke}")
    private String broke;

    @Autowired
    private GiphyClient giphy;


    @Override
    public GiphyModel getRich() {
        return giphy.getGif(api, upper);
    }

    @Override
    public GiphyModel getBroke() {
        return giphy.getGif(api, broke);
    }

}
