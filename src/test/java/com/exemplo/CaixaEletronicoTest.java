package com.exemplo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Habilita o Mockito nos testes
class CaixaEletronicoTest {

    @Mock
    private NotasProvider notasProviderMock;  // Mock da dependência

    @Test
    @DisplayName("Saque de R$ 30 deve retornar 1 nota de 20 e 1 de 10")
    void dadoValor30_deveRetornarUmaNota20EUmaNota10() {
        // Arrange
        when(notasProviderMock.getNotasDisponiveis()).thenReturn(new int[]{100, 50, 20, 10});
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock);
        Map<Integer, Integer> esperado = Map.of(20, 1, 10, 1);

        // Act
        Map<Integer, Integer> resultado = caixa.sacar(30);

        // Assert
        assertEquals(esperado, resultado);
        verify(notasProviderMock, times(1)).getNotasDisponiveis();  // Verifica se o mock foi chamado
    }

    @Test
    @DisplayName("Saque de R$ 380 deve retornar 3x100, 1x50, 1x20 e 1x10")
    void dadoValor380_deveRetornar3x100_1x50_1x20_1x10() {
        // Arrange
        when(notasProviderMock.getNotasDisponiveis()).thenReturn(new int[]{100, 50, 20, 10});
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock);
        Map<Integer, Integer> esperado = Map.of(100, 3, 50, 1, 20, 1, 10, 1);

        // Act
        Map<Integer, Integer> resultado = caixa.sacar(380);

        // Assert
        assertEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Saque de R$ 35 (inválido) deve lançar exceção")
    void dadoValor35_deveLancarExcecao() {
        // Arrange
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock); // mock é injetado, mas não usado
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> caixa.sacar(35));
    }

    @Test
    @DisplayName("Saque de R$ 0 ou negativo deve lançar exceção")
    void dadoValorZeroOuNegativo_deveLancarExcecao() {
        // Arrange
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> caixa.sacar(0));
        assertThrows(IllegalArgumentException.class, () -> caixa.sacar(-20));
    }

    @Test
    @DisplayName("Saque de R$ 80 deve retornar 1x50, 1x20 e 1x10")
    void dadoValor80_deveRetornar1x50_1x20_1x10() {
        // Arrange
        when(notasProviderMock.getNotasDisponiveis()).thenReturn(new int[]{100, 50, 20, 10});
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock);
        Map<Integer, Integer> esperado = Map.of(50, 1, 20, 1, 10, 1);

        // Act
        Map<Integer, Integer> resultado = caixa.sacar(80);

        // Assert
        assertEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Saque de R$ 200 deve retornar 2x100")
    void dadoValor200_deveRetornar2x100() {
        // Arrange
        when(notasProviderMock.getNotasDisponiveis()).thenReturn(new int[]{100, 50, 20, 10});
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock);
        Map<Integer, Integer> esperado = Map.of(100, 2);

        // Act
        Map<Integer, Integer> resultado = caixa.sacar(200);

        // Assert
        assertEquals(esperado, resultado);
    }

    // Teste adicional demonstrando que podemos mockar notas diferentes
    @Test
    @DisplayName("Deve funcionar com conjunto alternativo de notas (mockado)")
    void deveFuncionarComNotasAlternativasMockadas() {
        // Arrange
        int[] notasAlternativas = {50, 20, 10};  // sem nota de 100
        when(notasProviderMock.getNotasDisponiveis()).thenReturn(notasAlternativas);
        CaixaEletronico caixa = new CaixaEletronico(notasProviderMock);
        Map<Integer, Integer> esperado = Map.of(50, 1, 20, 1, 10, 1);  // para 80

        // Act
        Map<Integer, Integer> resultado = caixa.sacar(80);

        // Assert
        assertEquals(esperado, resultado);
        verify(notasProviderMock).getNotasDisponiveis();
    }
}