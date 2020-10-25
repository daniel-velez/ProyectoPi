/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */

package classicPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import java.awt.EventQueue;

/**
 * Clase que modela el juego de poker clasico.
 */
public class PokerGame implements Runnable {
    private Map<Jugador, Integer> mesaDeApuesta;
    private MazoDeCartas mazo;
    private int turno; // de tipo int o Jugador <- pendiente.
    private List<Jugador> jugadores;
    private int apuestaInicial;
    private PokerView pokerView;

    private Random aleatorio;

    /**
     * Instantiates a new poker game.
     */
    public PokerGame() {
        this.mesaDeApuesta = new HashMap<Jugador, Integer>();
        this.mazo = new MazoDeCartas();
        this.apuestaInicial = 1000;
        this.aleatorio = new Random();

        // crear los jugadores y darles el dinero inicial
        jugadores = new ArrayList<Jugador>();
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Simulado));
        jugadores.add(new Jugador(getRandomMoney(), TipoJugador.Usuario));

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                pokerView = new PokerView(jugadores);

                for (Jugador jugador : jugadores)
                    jugador.defineView(pokerView);
            }
        });
    }

    @Override
    public void run() {
        try {
            while (pokerView == null)
                Thread.sleep(100);

            iniciarRonda();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarRonda() throws InterruptedException {
        pokerView.iniciarRonda();

        // hacer la apuesta inicial
        for (Jugador jugador : jugadores) {
            jugador.aportar(apuestaInicial);
            mesaDeApuesta.put(jugador, apuestaInicial);
        }

        pokerView.showMessage("Apuesta inicial: $" + apuestaInicial, 3000);

        repartirCartas();

        // selecciona el jugador “mano”
        Collections.shuffle(jugadores); // ! no se tiene en cuenta lo del jugador a la derecha

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

        rondaDeDescarte(); // ! una manera de escoger entre descarte o descubrir cartas
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

        /*
        for (int i = 1; i < mesaDeApuesta.length; i++)
            if (mesaDeApuesta[i] != mesaDeApuesta[0])
                return false;
        */
        List<Jugador> listaJugadores = (List<Jugador>) mesaDeApuesta.keySet();
        for (int i = 1; i < listaJugadores.size(); i++) {
            if (mesaDeApuesta.get(listaJugadores.get(i)) != mesaDeApuesta.get(listaJugadores.get(0)))
                return false;
        }
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
