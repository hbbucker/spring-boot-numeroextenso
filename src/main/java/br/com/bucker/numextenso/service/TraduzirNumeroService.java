package br.com.bucker.numextenso.service;

import br.com.bucker.numextenso.exceptions.ValorInvalidoException;
import br.com.bucker.numextenso.model.NumeroTraduzido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TraduzirNumeroService {

    private static final String[] extensoUnidade = {"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove"};
    private static final String[] extensoDezena1 = {"dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezesste", "dezoito", "dezenove"};
    private static final String[] extensoDezena2 = {"", "", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"};
    private static final String[] extensoCentena = {"", "cem|cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"};
    private static final String extensoMilhar = "mil";

    public static NumeroTraduzido traduzirNumero(Integer numero) throws ValorInvalidoException {
        int max = 99999;
        int min = -99999;

        String retval = null;

        if (min > numero || numero > max) {
            throw new ValorInvalidoException();
        }

        boolean negativo = false;
        if (numero < 0) {
            negativo = true;
            numero = numero * -1;
        }

        List<Integer> digitos = pegarDigitos(numero);

        retval = "";
        int count = 0;

        switch (digitos.size()) {
            case 1:
                retval = extensoUnidade[numero];
                break;
            case 2:
                retval = traduzirDezena(digitos);
                break;
            case 3:
                retval = traduzirCentena(digitos);
                break;
            default:
                retval = traduzirMilhar(digitos);
        }

        if (negativo)
            retval = "menos " + retval;

        return new NumeroTraduzido(retval);

    }

    private static String traduzirDezena(List<Integer> digitos) throws ValorInvalidoException {
        int max = 99;
        int min = 0;

        removerZerosAEsquerda(digitos);

        String n = "";
        for (Integer digito : digitos)
            n += digito + "";

        Integer numero = Integer.valueOf(n);

        String retval = "";

        if (min > numero || numero > max) {
            throw new ValorInvalidoException();
        }

        if (numero < 10)
            retval = extensoUnidade[numero];
        else if (numero > 9 && numero < 20)
            retval = extensoDezena1[numero - 10];
        else
            retval = extensoDezena2[digitos.get(0)] + (digitos.get(1) > 0 ? " e " + extensoUnidade[digitos.get(1)] : "");

        return retval;
    }

    private static String traduzirCentena(List<Integer> digitos) throws ValorInvalidoException {
        int max = 999;
        int min = 0;

        removerZerosAEsquerda(digitos);

        String n = "";
        for (Integer digito : digitos)
            n += digito + "";

        Integer numero = Integer.valueOf(n);

        String retval = "";

        if (min > numero || numero > max) {
            throw new ValorInvalidoException();
        }

        if (numero < 100)
            retval = traduzirDezena(digitos);
        else if (numero == 100)
            retval = extensoCentena[digitos.get(0)].split("\\|")[0];
        else if (numero > 100 && numero < 200)
            retval = extensoCentena[digitos.get(0)].split("\\|")[1] + " e " + traduzirDezena(digitos.subList(1, 3));
        else
            retval = extensoCentena[digitos.get(0)] + " e " + traduzirDezena(digitos.subList(1, 3));

        return retval;
    }

    public static String traduzirMilhar(List<Integer> digitos) throws ValorInvalidoException {
        int max = 99999;
        int min = 0;

        String n = "";
        for (Integer digito : digitos)
            n += digito + "";

        Integer numero = Integer.valueOf(n);

        String retval = "";

        if (min > numero || numero > max) {
            throw new ValorInvalidoException();
        }

        if (numero > 999 && numero < 2000) {
            retval = extensoMilhar;
        } else {
            retval = traduzirCentena(digitos.subList(0, digitos.size() - 3)) + " " + extensoMilhar;
        }

        retval += " e " + traduzirCentena(digitos.subList(digitos.size() - 3, digitos.size()));

        return retval;

    }

    private static List<Integer> pegarDigitos(Integer numero) {
        List<Integer> digitos = new ArrayList<>();
        if (numero / 10 > 0) {
            digitos.addAll(pegarDigitos(numero / 10));
        }
        digitos.add(numero % 10);
        return digitos;
    }

    private static void removerZerosAEsquerda(List<Integer> digitos) {
        //remover zeros a esquerda
        Collections.reverse(digitos);
        for (int i = digitos.size() - 1; i >= 0; i--) {
            Integer digito = digitos.get(i);
            if (digito == 0) {
                digitos.remove(i);
            } else
                break;
        }
        Collections.reverse(digitos);
    }

}
