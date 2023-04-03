package com.mini.autorizador.autorizador.controller;

import com.mini.autorizador.autorizador.DTO.CartaoDTO;
import com.mini.autorizador.autorizador.mock.CartaoDTOMock;
import com.mini.autorizador.autorizador.repository.CartaoRespository;
import com.mini.autorizador.autorizador.service.CartaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartoesController.class)
class CartoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartaoService mockService;

    @MockBean
    private CartaoRespository cartaoRespository;

    @Test
    @WithMockUser(value = "spring")
    void testPostCartao() throws Exception {
        var cartaoDto = CartaoDTOMock.getAll();
        var json = CartaoDTOMock.json();
        // Setup
        when(mockService.salvarNovoCartao(cartaoDto))
                .thenReturn(cartaoDto);

        // Run the test
        var response = mockMvc.perform(post("/cartoes")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(422);
        assertThat(response.getContentAsString().contains("numeroCartao"))
                .isEqualTo(json.contains("numeroCartao"));
        verify(mockService).salvarNovoCartao(any(CartaoDTO.class));
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetSaldo() throws Exception {
        // Setup
        when(mockService.getSaldo("0000000000000050")).thenReturn(new BigDecimal("0.00"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/cartoes/{numero}", "numero")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }
}
