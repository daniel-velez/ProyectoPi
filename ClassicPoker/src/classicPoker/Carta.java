/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

public class Carta {

    public enum Palos {
        picas, corazones, diamantes, treboles
    }

    public final int numero;
    public final Palos palo;
    
    Carta(int numero, Palos palo) {
        this.numero = numero;
        this.palo = palo;
    }
}

