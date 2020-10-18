package classicPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Clase que modela el juego de poker clasico.
 */
public class PokerGame implements Runnable {
    private int[] mesaDeApuesta;
    private MazoDeCartas mazo;
    private int turno; // de tipo int o Jugador <- pendiente.
    private List<Jugador> jugadores;
    private int apuestaInicial;

    private Random aleatorio;

    /**
     * Instantiates a new poker game.
     */
    public PokerGame() {
        this.mesaDeApuesta = new int[] { 0, 0, 0, 0, 0 };
        this.mazo = new MazoDeCartas();
        this.turno = 0;
        this.apuestaInicial = 1000;
        this.aleatorio = new Random();

        // crear los jugadores y darles el dinero inicial
        jugadores = new ArrayList<Jugador>();
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Usuario));
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    private void iniciarRonda() {
        // hacer la apuesta inicial
        for (Jugador jugador : jugadores) {
            jugador.aportar(apuestaInicial);
            mesaDeApuesta[0] = apuestaInicial; //! falta conectar mesa de apuesta con cada jugador
        }

        repartirCartas();

        //selecciona el jugador “mano”
        Collections.shuffle(jugadores); //! no se tiene en cuenta lo del jugador a la derecha

        rondaDeApuesta();
    }

    private void repartirCartas() {
        for (Jugador jugador : jugadores)
            jugador.recibirCartas(mazo.sacarCartas(5));
    }

    private void rondaDeApuesta() {
        int numeroRonda = 1;

        while (seHanIgualadoTodasLasApuestas()) {

            numeroRonda += 1;
        }

        rondaDeDescarte(); //! una manera de escoger entre descarte o descubrir cartas
        descubrirCartas();
    }

    private void rondaDeDescarte() {

    }

    private void descubrirCartas() {
        determinarJuego();
    }

    private void determinarJuego() {

    }

    // #---------------------------------------------------------------------------
    // # FUNCIONES AUXILIARES
    // #---------------------------------------------------------------------------

    private boolean seHanIgualadoTodasLasApuestas() {
        for (int i = 1; i < mesaDeApuesta.length; i++)
            if (mesaDeApuesta[i] != mesaDeApuesta[0])
                return false;
        return true;
    }

    /**
     * Devuelve una cantidad aleatoria de dinero entre el min y el max
     */
    private int getRandomMoney() {
        int MAX_MONEY = 70000;
        int MIN_MONEY = 30000;
        return (aleatorio.nextInt(MAX_MONEY) + MIN_MONEY) / 100 * 100;
    }

}
