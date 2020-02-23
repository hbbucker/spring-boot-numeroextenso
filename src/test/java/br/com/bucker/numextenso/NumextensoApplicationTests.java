package br.com.bucker.numextenso;

import br.com.bucker.numextenso.exceptions.ValorInvalidoException;
import br.com.bucker.numextenso.model.NumeroTraduzido;
import br.com.bucker.numextenso.service.TraduzirNumeroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class NumextensoApplicationTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    Integer[] num1 = {0, 4, 13, 45, 3060, 101, 1001, 5483, 99999, -78987};
    String[] ret = {"zero", "quatro", "treze", "quarenta e cinco", "três mil e sessenta",
            "cento e um", "mil e um", "cinco mil e quatrocentos e oitenta e três",
            "noventa e nove mil e novecentos e noventa e nove",
            "menos setenta e oito mil e novecentos e oitenta e sete"};

    @Test
    public void testTraduzirNumero() throws ValorInvalidoException {
        for (int i = 0; i < num1.length; i++) {
            NumeroTraduzido res = TraduzirNumeroService.traduzirNumero(num1[i]);
            assertEquals(ret[i], res.extenso);
        }
    }

    @Test
    public void getTraducaoNumero() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < num1.length; i++) {
            String uri = "/" + num1[i];
            MvcResult mvcResult = mvc.perform(
                    MockMvcRequestBuilders
                            .get(uri)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            mvcResult.getResponse().setCharacterEncoding("UTF-8");
            String content = mvcResult.getResponse().getContentAsString();
            NumeroTraduzido extenso = objectMapper.readValue(content, NumeroTraduzido.class);
            assertEquals(ret[i], extenso.extenso);
        }
    }
}
