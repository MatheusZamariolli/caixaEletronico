package com.exemplo;

import java.util.LinkedHashMap;
import java.util.Map;

public class CaixaEletronico {

    private final NotasProvider notasProvider;

    // Injeção de dependência via construtor (boa prática)
    public CaixaEletronico(NotasProvider notasProvider) {
        this.notasProvider = notasProvider;
    }

    /**
     * Calcula a menor quantidade de notas para um valor de saque.
     *
     * @param valor Valor a ser sacado (deve ser múltiplo de 10 e positivo)
     * @return Mapa com a quantidade de cada nota utilizada
     * @throws IllegalArgumentException se o valor for inválido
     */
    public Map<Integer, Integer> sacar(int valor) {
        if (valor <= 0 || valor % 10 != 0) {
            throw new IllegalArgumentException("Valor inválido para saque");
        }

        int[] notasDisponiveis = notasProvider.getNotasDisponiveis();
        Map<Integer, Integer> resultado = new LinkedHashMap<>();

        for (int nota : notasDisponiveis) {
            if (valor >= nota) {
                int quantidade = valor / nota;
                valor %= nota;
                resultado.put(nota, quantidade);
            }
        }

        return resultado;
    }
}