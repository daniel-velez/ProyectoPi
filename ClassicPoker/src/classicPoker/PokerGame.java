package classicPoker;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que modela el juego de poker clasico.
 */
public class PokerGame {

    //Tiene que tener jugadores
    private List <Integer> mesaDeApuesta;
    private MazoDeCartas mazo;
    private int turno; // de tipo int o Jugador <- pendiente.


    /**
     * Instantiates a new poker game.
     */
    public PokerGame () {
        this.mesaDeApuesta = new ArrayList<>();
        this.mazo = new MazoDeCartas();
        this.turno = 0;

        // crear los jugadores y darles el dinero inicial.
        
    }


}
