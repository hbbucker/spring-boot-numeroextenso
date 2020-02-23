package br.com.bucker.numextenso.controller;

import br.com.bucker.numextenso.model.NumeroTraduzido;
import br.com.bucker.numextenso.service.TraduzirNumeroService;
import br.com.bucker.numextenso.exceptions.ValorInvalidoException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumeroExtensoController {

    @GetMapping("/{numero}")
    public NumeroTraduzido traduzirNumero(@PathVariable("numero") Integer numero) throws ValorInvalidoException {
        return TraduzirNumeroService.traduzirNumero(numero);
    }
}
