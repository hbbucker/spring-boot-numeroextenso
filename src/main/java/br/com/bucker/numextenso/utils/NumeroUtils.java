package br.com.bucker.numextenso.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumeroUtils {
    /**
     * Quebra os digitos do número inteiro
     * @param numero
     * @return
     */
    public static List<Integer> pegarDigitos(Integer numero) {
        List<Integer> digitos = new ArrayList<>();
        if (numero / 10 > 0) {
            digitos.addAll(pegarDigitos(numero / 10));
        }
        digitos.add(numero % 10);
        return digitos;
    }

    /**
     * Remove os zeros a esquerda da lista de digitos
     * @param digitos
     */
    public static void removerZerosAEsquerda(List<Integer> digitos) {
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
