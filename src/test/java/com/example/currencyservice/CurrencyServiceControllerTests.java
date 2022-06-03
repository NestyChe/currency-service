package com.example.currencyservice;

import com.example.currencyservice.controller.CurrencyServiceController;
import com.example.currencyservice.models.GiphyModel;
import com.example.currencyservice.services.impl.ServiceGiphyImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CurrencyServiceControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CurrencyServiceController controller;
	@MockBean
	private ServiceGiphyImpl gifService;

	private final String RATE = "RUB";
	@Test
	public void whenRichStatusOK() throws Exception {
		GiphyModel gif = new GiphyModel(new GiphyModel.Meta());
		gif.getMeta().setMsg("rich");
		ResponseEntity<GiphyModel> responseEntity = new ResponseEntity<>(gif, HttpStatus.OK);
		when(gifService.getRich()).thenReturn(gif);
		when(controller.getExchangeRateDifference(RATE)).thenReturn(responseEntity);
		mockMvc.perform(get("/get/" + RATE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.meta.msg").value("rich"));
	}

	@Test
	public void whenBrokeStatusOK() throws Exception {
		GiphyModel gif = new GiphyModel(new GiphyModel.Meta());
		gif.getMeta().setMsg("broke");
		ResponseEntity<GiphyModel> responseEntity = new ResponseEntity<>(gif, HttpStatus.OK);
		when(gifService.getRich()).thenReturn(gif);
		when(controller.getExchangeRateDifference(RATE)).thenReturn(responseEntity);
		mockMvc.perform(get("/get/" + RATE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.meta.msg").value("broke"));
	}

	@Test
	public void whenRichAndBrokeBAD_REQUEST() throws Exception {
		GiphyModel gif = new GiphyModel(new GiphyModel.Meta());
		gif.getMeta().setMsg("error");
		ResponseEntity<GiphyModel> responseEntity = new ResponseEntity<>(gif, HttpStatus.BAD_REQUEST);
		when(gifService.getRich()).thenReturn(gif);
		when(gifService.getBroke()).thenReturn(gif);
		when(controller.getExchangeRateDifference(RATE)).thenReturn(responseEntity);
		mockMvc.perform(get("/get/" + RATE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.meta.msg").value("error"));
	}
}

