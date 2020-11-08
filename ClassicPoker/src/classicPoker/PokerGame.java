/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */

package classicPoker;

import pokerView.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.UIManager;

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
    //private int apuestaMasAlta;
    private int numeroRonda = 0;
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
        jugadores.add(new Jugador(getRandomMoney(), Jugador.TipoJugador.Simulado, mazo, "P1"));
        jugadores.add(new Jugador(getRandomMoney(), Jugador.TipoJugador.Simulado, mazo, "P2"));
        jugadores.add(new Jugador(getRandomMoney(), Jugador.TipoJugador.Simulado, mazo, "P3"));
        jugadores.add(new Jugador(getRandomMoney(), Jugador.TipoJugador.Simulado, mazo, "P4"));
        jugadores.add(new Jugador(getRandomMoney(), Jugador.TipoJugador.Usuario, mazo, "User"));

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                pokerView = new PokerView(jugadores);

                for (Jugador jugador : jugadores)
                    jugador.defineView(pokerView);
            }
        });
    }

    /**
     * 
     */
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

    /**
     * 
     * @throws InterruptedException
     */
    private void iniciarRonda() throws InterruptedException {
        //pokerView.iniciarRonda(); //# TEMPORAL

        // hacer la apuesta inicial
        for (Jugador jugador : jugadores)
            mesaDeApuesta.put(jugador, jugador.aportar(apuestaInicial));

        pokerView.showBigMessage("Monto de la apuesta: " + getMontoApuestas());
        pokerView.showMessage("Apuesta inicial: $" + apuestaInicial, TimeControl.apuestaInicial);
        //pokerView.showMessage("Cada jugador ha hecho su apuesta inicial", 2000);

        repartirCartas();

        //! voy a comentar shuffle para que el orden de los turnos siempre sea el mismo, 
        //! mientras hacemos las pruebas
        // selecciona el jugador “mano”
        //Collections.shuffle(jugadores); // ! no se tiene en cuenta lo del jugador a la derecha

        /*
        do {
            rondaDeApuesta();
        } while (!seHanIgualadoTodasLasApuestas());
        */
        rondaDeDescarte();
        //rondaDeApuesta();
        //descubrirCartas();
        //determinarJuego();
    }

    /**
     * 
     */
    private void repartirCartas() {
        for (Jugador jugador : jugadores)
            jugador.recibirCartas(mazo.sacarCartas(5));
    }

    /**
     * 
     * @throws InterruptedException
     */
    private void rondaDeApuesta() throws InterruptedException {
        numeroRonda += 1;
        int apuestaMasAlta = Collections.max(mesaDeApuesta.values());
        boolean ultimaRonda = numeroRonda == 2 ? true : false;

        if (numeroRonda == 1)
            pokerView.showMessage("Empieza la ronda de apuesta", TimeControl.rondaDeApuesta);
        else
            pokerView.showMessage("Empieza otra ronda de apuesta", TimeControl.rondaDeApuesta);

        for (Jugador jugador : jugadores) {
            if (jugador.seHaRetirado())
                continue;

            if (apuestaMasAlta == apuestaInicial)
                mesaDeApuestaUpdate(jugador, jugador.apostar(ultimaRonda));
            else
                mesaDeApuestaUpdate(jugador, jugador.apostar(apuestaMasAlta - mesaDeApuesta.get(jugador), ultimaRonda));

            apuestaMasAlta = Collections.max(mesaDeApuesta.values());
            pokerView.showBigMessage("Monto de la apuesta: " + getMontoApuestas());
        }
    }

    /**
     * 
     * @throws InterruptedException
     */
    private void rondaDeDescarte() throws InterruptedException {
        pokerView.showMessage("Empieza la ronda de descarte", 1000);

        for (Jugador jugador : jugadores) {
            if (!jugador.seHaRetirado())
                jugador.descartar();
        }
    }

    /**
     * Muestra el contenido de las cartas de los jugadores.
     */
    private void descubrirCartas() {
        for (Jugador jugador : jugadores)
            jugador.descubrirCartas();
    }

    private void determinarJuego() {

    }

    /**
     * Obtiene el monto de apuestas de la mesa de apuesta.
     * @return la suma de los valores de la mesa de apuestas
     */
    private int getMontoApuestas() {
        int monto = 0;
        for (Integer val : mesaDeApuesta.values())
            monto += val;
        return monto;
    }

    // #---------------------------------------------------------------------------
    // # FUNCIONES AUXILIARES
    // #---------------------------------------------------------------------------

    private boolean seHanIgualadoTodasLasApuestas() {
        int apuestaMasAlta = Collections.max(mesaDeApuesta.values());
        for (Jugador jugador : jugadores)
            if (!jugador.seHaRetirado())
                if (mesaDeApuesta.get(jugador) != apuestaMasAlta)
                    return false;
        return true;
    }

    /**
     * Devuelve una cantidad aleatoria de dinero entre el min y el max.
     */
    private int getRandomMoney() {
        int MAX_MONEY = 70000;
        int MIN_MONEY = 30000;
        return (aleatorio.nextInt(MAX_MONEY) + MIN_MONEY) / 100 * 100;
    }

    /**
    * Actualiza el valor de la apuesta de un jugador en la mesaDeApuesta
    * @param jugador
    * @param val
    */
    private void mesaDeApuestaUpdate(Jugador jugador, int val) {
        mesaDeApuesta.replace(jugador, mesaDeApuesta.get(jugador) + val);
    }

    /**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception e) {
		}

		Thread pokerGame = new Thread(new PokerGame());
		pokerGame.start();
	}
}
