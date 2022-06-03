package com.example.currencyservice.services.impl;

import com.example.currencyservice.clients.OpenexchangeratesClient;
import com.example.currencyservice.models.GiphyModel;
import com.example.currencyservice.models.OpenexchangeModel;
import com.example.currencyservice.services.ServiceOpenexchangerates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceOpenexchangeratesImpl implements ServiceOpenexchangerates {

    /**
     * Ключ для подключения к сервису валют openexchangerates.org
     */
    @Value("${openexchangerates.id}")
    private String appId;

    /**
     * Значение базовой валюты, курс которой сравнивается с выбранной пользователем валютой
     */
    @Value("${openexchangerates.base}")
    private String base;
    @Autowired
    private ServiceGiphyImpl serviceGiphy;
    @Autowired
    private OpenexchangeratesClient client;


    @Override
    public GiphyModel getChanges(String code) {
        try {
            OpenexchangeModel latest = client.getLatest(appId, base, code);
            OpenexchangeModel historical = client.getHistorical(getYesterdayDate(), appId, base, code);
            return getRate(latest, code)
                    > getRate(historical, code)
                    ? serviceGiphy.getRich() : serviceGiphy.getBroke();
        } catch (NullPointerException exception) {
            GiphyModel.Meta meta = new GiphyModel.Meta();
            meta.setMsg("Check code currency");
            return new GiphyModel(meta);
        }
    }

    /**
     * Получает стоимость валюты по буквенному коду из модели
     * @param object модель содержащая валюты и из стоимость по отношению к базовой
     * @param code буквенный код валюты которую стоимость которой нужно найти
     * @return double стоимость валюты
     */
    private Double getRate(OpenexchangeModel object, String code) {
        return object.getRates().get(code);
    }

    @Override
    public LocalDate getYesterdayDate() {
        return LocalDate.now().minusDays(1);
    }

    @Override
    public List<String> getListCurrency() {
        return new ArrayList<>(client.getAllCurrencies().keySet());
    }
}
