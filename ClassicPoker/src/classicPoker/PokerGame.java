/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import pokerView.*;
import classicPoker.Jugador.TipoJugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.rowset.CachedRowSet;
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

        CardImage.loadImage(this);
        Resources.loadCasino(getClass().getResourceAsStream("/fonts/CasinoFlat.ttf"));
        Resources.loadLounge(getClass().getResourceAsStream("/fonts/LoungeBait-JpVa.ttf"));

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
            Thread.sleep(TimeControl.LOADING);

            while (pokerView == null)
                Thread.sleep(100);

            pokerView.initGUI();

            iniciarRonda();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Empieza la ronda de juego.
     * @throws InterruptedException
     */
    private void iniciarRonda() throws InterruptedException {
        // pokerView.iniciarRonda(); //# TEMPORAL

        // hacer la apuesta inicial
        for (Jugador jugador : jugadores)
            mesaDeApuesta.put(jugador, jugador.aportar(apuestaInicial));

        pokerView.showBigMessage("Monto de la apuesta: " + getMontoApuestas());
        pokerView.showMessage("Apuesta inicial: $" + apuestaInicial, TimeControl.apuestaInicial);
        // pokerView.showMessage("Cada jugador ha hecho su apuesta inicial", 2000);

        repartirCartas();

        // ! voy a comentar shuffle para que el orden de los turnos siempre sea el
        // mismo,
        // ! mientras hacemos las pruebas
        // selecciona el jugador “mano”
        // Collections.shuffle(jugadores); // ! no se tiene en cuenta lo del jugador a
        // la derecha

        /*
        do {
            rondaDeApuesta();
        } while (!seHanIgualadoTodasLasApuestas());
        */
        rondaDeDescarte();
        /*
        do {
            rondaDeApuesta();
        } while (!seHanIgualadoTodasLasApuestas());
        */
        descubrirCartas();
        determinarJuego();
    }

    /**
     * 
     */
    private void repartirCartas() {
        //Para pruebas.

        //jugadores.get(4).recibirCartas(mazo.manoEscaleraReal(Palos.corazones));
        //jugadores.get(3).recibirCartas(mazo.manoEscaleraReal(Palos.diamantes));

        //for (int i = 0; i < jugadores.size()-2; i++) 
        //    jugadores.get(i).recibirCartas(mazo.sacarCartas(5));

        // Por defecto.

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
                mesaDeApuestaUpdate(jugador, jugador.apostar());
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

        // Para pruebas.
        /*
        for (Jugador jugador : jugadores) {
            if (!jugador.seHaRetirado() && jugador.getName() != "P4")
                jugador.descartar();
        }
        */

        // Por defecto
        for (Jugador jugador : jugadores) {
            if (!jugador.seHaRetirado())
                jugador.descartar();
        }
        numeroRonda = 0;
    }

    /**
     * Muestra el contenido de las cartas de los jugadores.
     */
    private void descubrirCartas() {
        for (Jugador jugador : jugadores)
            jugador.descubrirCartas();
    }

    /**
     * 
     * @throws InterruptedException
     */
    private void determinarJuego() throws InterruptedException {
        //Map <Jugador, Integer> valores = new HashMap<Jugador, Integer>();
        List<Jugador> ganadores = new ArrayList<Jugador>();

        //# Determinar ganador/ganadores
        //int valorGanador = 100;
        /*
        for (Jugador jugador : jugadores) {
            if (!jugador.seHaRetirado())
                valores.put(jugador, PokerRules.determinarMano(jugador.getMano()));
            if (valores.get(jugador) < valorGanador)
                valorGanador = valores.get(jugador);
        }
        */
        Jugador auxiliar;
        for (int i = 0; i < jugadores.size() - 1; i++) {
            if (i == 0)
                auxiliar = PokerRules.determinarMano(jugadores.get(0), jugadores.get(1));

            else
                auxiliar = PokerRules.determinarMano(ganadores.get(ganadores.size() - 1), jugadores.get(i + 1));

            if (auxiliar != null) { // Posible único ganador.
                ganadores.add(auxiliar);
                if (ganadores.size() > 1) { // eliminar los posibles ganadores anteriores.
                    for (int j = ganadores.size() - 2; j >= 0; j--)
                        ganadores.remove(j);
                }
            } else if (auxiliar == null) { // empate, se agregan los dos jugadores a la lista de ganadores.
                if (i == 0) {
                    ganadores.add(jugadores.get(0));
                    ganadores.add(jugadores.get(1));
                } else
                    ganadores.add(jugadores.get(i + 1));
            }
        }

        /*
        List <Jugador> ganadores = new ArrayList<Jugador>(valores.keySet());
        for (Jugador jugador : valores.keySet()) {
            if (valores.get(jugador) != valorGanador) 
                ganadores.remove(jugador);
        }
        */

        //# Repartir el premio
        int dineroARecibir = getMontoApuestas();

        if (ganadores.size() == 1) {
            if (ganadores.get(0).getTipo() == TipoJugador.Simulado) {
                pokerView.showBigMessage("¡El jugador " + ganadores.get(0).getName() + " ha ganado!");
                pokerView.showMessage("recibe " + dineroARecibir, 0);
            } else {
                pokerView.showBigMessage("¡Has ganado!");
                pokerView.showMessage("Recibes " + dineroARecibir, 0);
            }
            ganadores.get(0).recibirDinero(dineroARecibir);
        } else {
            String nombres = "";
            dineroARecibir /= ganadores.size();

            for (Jugador jugador : ganadores) {
                jugador.recibirDinero(dineroARecibir);
                nombres += jugador.getName() + " ";
            }
            pokerView.showBigMessage("Los jugadores " + nombres + "han ganado!");
            pokerView.showMessage("Recibe " + dineroARecibir + " cada uno", 0);
        }

        for (Jugador p : jugadores)
            mesaDeApuesta.replace(p, 0);
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

    /**
     * Determinar si todas las apuestan son iguales.
     * @return true si se han igualado las apuestas, false en caso contrario.
     */
    private boolean seHanIgualadoTodasLasApuestas() {
        int apuestaMasAlta = Collections.max(mesaDeApuesta.values());
        for (Jugador jugador : jugadores)
            if (!jugador.seHaRetirado())
                if (mesaDeApuesta.get(jugador) != apuestaMasAlta)
                    return false;
        return true;
    }

    /**
     * Determinar si queda un jugador en juego.
     * @return true si 4 de los jugadores se han retirado.
     */
    private boolean quedaUnJugador() {
        int contador = 0;
        for (Jugador jugador : jugadores) {
            if (jugador.seHaRetirado())
                contador++;
        }
        return contador == 4 ? true : false;
    }

    /**
     * @return Una cantidad aleatoria de dinero entre el min y el max.
     */
    private int getRandomMoney() {
        int MAX_MONEY = 70000;
        int MIN_MONEY = 30000;
        return (aleatorio.nextInt(MAX_MONEY) + MIN_MONEY) / 100 * 100;
    }

    /**
    * Actualiza el valor de la apuesta de un jugador en la mesaDeApuesta
    * @param jugador jugador que hace la apuesta.
    * @param val valor de la apuesta.
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
