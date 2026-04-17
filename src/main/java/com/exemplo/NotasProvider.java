package com.exemplo;

/**
 * Interface que define o contrato para obter as notas disponíveis no caixa.
 */
public interface NotasProvider {
    int[] getNotasDisponiveis();
}
