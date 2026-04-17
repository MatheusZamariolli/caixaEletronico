package com.exemplo;

/**
 * Implementação padrão que retorna as notas clássicas: 100, 50, 20, 10.
 */
public class NotasProviderImpl implements NotasProvider {
    @Override
    public int[] getNotasDisponiveis() {
        return new int[]{100, 50, 20, 10};
    }
}
