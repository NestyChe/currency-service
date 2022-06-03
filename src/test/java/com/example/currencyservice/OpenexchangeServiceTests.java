package com.example.currencyservice;

import com.example.currencyservice.clients.OpenexchangeratesClient;
import com.example.currencyservice.models.GiphyModel;
import com.example.currencyservice.models.OpenexchangeModel;
import com.example.currencyservice.services.impl.ServiceOpenexchangeratesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OpenexchangeServiceTests {

    @Autowired
    private ServiceOpenexchangeratesImpl service;

    @MockBean
    private OpenexchangeratesClient openexchangeratesClient;

    @Value("${openexchangerates.base}")
    private String base;
    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;
    private OpenexchangeModel openexchangeModelOne;
    private OpenexchangeModel openexchangeModelTwo;

    private OpenexchangeModel openexchangeModelError;
    private final String RATE = "RUB";
    @BeforeEach
    public void init() {
        int timestamp = 1407982435;
        Map<String, Double> rateOne = new HashMap<>();
        rateOne.put(RATE, 71.9043);
        this.openexchangeModelOne = new OpenexchangeModel();
        this.openexchangeModelOne.setTimestamp(timestamp);
        this.openexchangeModelOne.setBase(base);
        this.openexchangeModelOne.setRates(rateOne);

        Map<String, Double> rateTwo = new HashMap<>();
        rateTwo.put(RATE, 79.9043);
        this.openexchangeModelTwo = new OpenexchangeModel();
        this.openexchangeModelTwo.setTimestamp(timestamp);
        this.openexchangeModelTwo.setBase(base);
        this.openexchangeModelTwo.setRates(rateTwo);

        Map<String, Double> rateThree = new HashMap<>();
        rateThree.put("", null);
        this.openexchangeModelError = new OpenexchangeModel();
        this.openexchangeModelError.setTimestamp(timestamp);
        this.openexchangeModelError.setBase(base);
        this.openexchangeModelError.setRates(rateThree);
    }

    @Test
    public void whenRichTest() {
        when(openexchangeratesClient.getLatest(anyString(), anyString(), anyString()))
                .thenReturn(this.openexchangeModelTwo);
        when(openexchangeratesClient.getHistorical(any(LocalDate.class), anyString(), anyString(), anyString()))
                .thenReturn(this.openexchangeModelOne);
        GiphyModel resultChanges = service.getChanges(RATE);
        List<String> resultCurrencyList = service.getListCurrency();
        assertNotNull(resultChanges);
        assertThat(resultChanges, instanceOf(GiphyModel.class));
        assertNotNull(resultCurrencyList);
    }

    @Test
    public void whenBrokeTest() {
        when(openexchangeratesClient.getLatest(anyString(), anyString(), anyString()))
                .thenReturn(this.openexchangeModelOne);
        when(openexchangeratesClient.getHistorical(any(LocalDate.class), anyString(), anyString(), anyString()))
                .thenReturn(this.openexchangeModelTwo);
        GiphyModel resultChanges = service.getChanges(RATE);
        List<String> resultCurrencyList = service.getListCurrency();
        assertNotNull(resultChanges);
        assertThat(resultChanges, instanceOf(GiphyModel.class));
        assertNotNull(resultCurrencyList);
    }

    @Test
    public void whenNullTest() {
        when(openexchangeratesClient.getLatest(anyString(), anyString(), anyString()))
                .thenReturn(openexchangeModelError);
        when(openexchangeratesClient.getHistorical(any(LocalDate.class), anyString(), anyString(), anyString()))
                .thenReturn(openexchangeModelError);
        assertEquals(service.getChanges("").getMeta().getMsg(), "Check code currency");
        assertTrue(service.getListCurrency().isEmpty());
    }
}
