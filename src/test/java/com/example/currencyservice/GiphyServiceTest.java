package com.example.currencyservice;

import com.example.currencyservice.clients.GiphyClient;
import com.example.currencyservice.models.GiphyModel;
import com.example.currencyservice.services.impl.ServiceGiphyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GiphyServiceTest {

    @Autowired
    private ServiceGiphyImpl service;
    @MockBean
    private GiphyClient client;
    private final GiphyModel richModel = new GiphyModel();
    private final GiphyModel brokeModel = new GiphyModel();
    private final GiphyModel.Data data = new GiphyModel.Data();
    private final GiphyModel.Meta meta = new GiphyModel.Meta();

    @BeforeEach
    public void init() {
        this.richModel.setData(data);
        this.brokeModel.setData(data);
        this.richModel.setMeta(meta);
        this.brokeModel.setMeta(meta);
    }

    @Test
    public void whenRich() {
        richModel.getData().setId("spvp4lkfspkwfe4");
        richModel.getMeta().setStatus(200);
        when(client.getGif(anyString(), anyString())).thenReturn(richModel);
        GiphyModel result = service.getRich();
        assertEquals(result.getMeta().getStatus(),richModel.getMeta().getStatus());
        assertEquals(result.getData().getId(), richModel.getData().getId());
    }

    @Test
    public void whenBroke() {
        brokeModel.getData().setId("asrl5550903lk");
        brokeModel.getMeta().setStatus(200);
        when(client.getGif(anyString(), anyString())).thenReturn(brokeModel);
        GiphyModel result = service.getBroke();
        assertEquals(result.getMeta().getStatus(),brokeModel.getMeta().getStatus());
        assertEquals(result.getData().getId(),brokeModel.getData().getId());
    }

    @Test
    public void whenNullReturn() {
        richModel.getMeta().setMsg("feignError");
        brokeModel.getMeta().setMsg("feignError");
        richModel.getMeta().setStatus(400);
        brokeModel.getMeta().setStatus(400);
        when(client.getGif(anyString(), anyString())).thenReturn(richModel);
        when(client.getGif(anyString(), anyString())).thenReturn(brokeModel);
        GiphyModel resultRich = service.getRich();
        GiphyModel resultBroke = service.getBroke();
        assertEquals(resultRich.getMeta().getStatus(),richModel.getMeta().getStatus());
        assertEquals(resultBroke.getMeta().getStatus(),brokeModel.getMeta().getStatus());
        assertEquals("feignError", resultRich.getMeta().getMsg());
        assertEquals("feignError", resultBroke.getMeta().getMsg());
    }
}

