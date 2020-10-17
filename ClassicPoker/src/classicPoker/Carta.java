package classicPoker;

public class Carta {
    public final int numero;
    public final Palos palo;

    Carta(int numero, Palos palo) {
        this.numero = numero;
        this.palo = palo;
    }
}

enum Palos {
    picas, corazones, diamantes, tréboles
}