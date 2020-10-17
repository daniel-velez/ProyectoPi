package classicPoker;

public class Carta {
    private int numero;
    private Palos palo;

    Carta(int numero, Palos palo) {
        this.numero = numero;
        this.palo = palo;
    }

    public int getNumero() {
        return numero;
    }

    public Palos getPalo() {
        return palo;
    }
}

enum Palos {
    picas, corazones, diamantes, tréboles
}