/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

/**
 * Clase que modela un jugador.
 */
public class Jugador {

    private int dinero;
    private String nombre;
    private List<Carta> mano;
    private TipoJugador tipo;
    private boolean retirado;

    private PokerView pokerView;
    private JManoPanel manoPanel;
    private MazoDeCartas mazo;

    private Random aleatorio;

    /**
     * Instantiates a new jugador.
     * @param dinero el dinero del jugador
     * @param tipo el tipo de jugador (simulado o usuario). 
     */
    public Jugador(int dinero, TipoJugador tipo, MazoDeCartas mazo, String nombre) {
        this.dinero = dinero;
        this.tipo = tipo;
        this.retirado = false;
        this.mano = new ArrayList<Carta>();
        this.mazo = mazo;
        this.nombre = nombre;
        this.aleatorio = new Random();
    }

    public void defineView(PokerView pokerView) {
        this.pokerView = pokerView;
    }

    /**
     * Aporta la cantidad de dinero a la mesa de apuesta
     * @param cantidad la cantidad de dinero
     */
    public int aportar(int cantidad) {
        if (dinero >= cantidad) {
            dinero -= cantidad;
            pokerView.updateMoney(this);
            return cantidad;
        } else {
            //! manejar el caso en el que se haya quedado sin dinero
            return -1;
        }
    }

    /**
     * Agrega la lista de cartas a la mano del jugador.
     * @param cartas
     */
    public void recibirCartas(List<Carta> cartas) {
        mano.addAll(cartas);
        pokerView.descubrirCartas(this);
    }

    /**
     * 
     * @param ultimaRonda
     * @return
     * @throws InterruptedException
     */
    public int apostar(boolean ultimaRonda) throws InterruptedException {
        if (tipo == TipoJugador.Usuario)
            return aportar(pokerView.apostar());

        pokerView.showMessage(nombre + " está pensando su apuesta...", TimeControl.jugadorPensadoSuApuesta);
        

        int randomDecision = aleatorio.nextInt(2) + 1;
        randomDecision = 2;
        if (randomDecision == 1) { //apostar
            int nuevaApuesta = aportar(aleatorio.nextInt(dinero / 10) / 100 * 100);
            pokerView.showMessage(nombre + " ha apostado $" + nuevaApuesta, TimeControl.jugadorHaTomadoDecision);
            return nuevaApuesta;
        }
        pokerView.showMessage(nombre + " ha cedido el turno", TimeControl.jugadorHaTomadoDecision);
        return 0; //pasar
    }

    /**
     * Simula la decision de apostar
     * @param carrier valor para igualar la apuesta
     * @return valor de la apuesta o null en caso de pasar, retirarse
     * @throws InterruptedException
     */
    public int apostar(int valorParaIgualar, boolean ultimaRonda) throws InterruptedException {

        if (tipo == TipoJugador.Usuario) {
            if (ultimaRonda) 
                return aportar(pokerView.apostar(valorParaIgualar, true));
            else 
                return aportar(pokerView.apostar(valorParaIgualar, false));
        }

        pokerView.showMessage(nombre + " está pensando su apuesta...", TimeControl.jugadorPensadoSuApuesta);

        int randomDecision = ultimaRonda? aleatorio.nextInt(2) + 1 : aleatorio.nextInt(3) + 1;

        if (randomDecision == 1) { //igualar
            pokerView.showMessage(nombre + " ha igualado la apuesta", TimeControl.jugadorHaTomadoDecision);
            return aportar(valorParaIgualar);
        }
        if (randomDecision == 3) { //aumentar
            int nuevaApuesta = aportar(aleatorio.nextInt(dinero / 10) / 100 * 100);
            pokerView.showMessage(nombre + " ha incrementado la apuesta en $" + nuevaApuesta,
                    TimeControl.jugadorHaTomadoDecision);
            return nuevaApuesta + valorParaIgualar;
        }

        //retirarse
        retirarse();
        pokerView.showMessage(nombre + " se ha retirado", TimeControl.jugadorHaTomadoDecision);
        return 0;
    }

    /**
     * Simula la decicison de retirarse.
     */
    public void retirarse() {
        mazo.descartar(mano);
        mano.clear();
        manoPanel.descartar();
        retirado = true;
    }

    /**
     * Simula la decision de descartar
     * @return lista de cartas a descartar
     */
    public List<Carta> descartar() {
        return new ArrayList<Carta>();
    }

    /**
     * determina si el jugador esta jugando.
     * @return true si el jugador no se ha retirado.
     */
    public boolean seHaRetirado() {
        return retirado;
    }


    public void descubrirCartas() {
        manoPanel.descubrirCartas();
    }

    // #---------------------------------------------------------------------------
    // # GETTERS
    // #---------------------------------------------------------------------------

    /**
     * @return el dinero del jugador.
     */
    public Integer getDinero() {
        return dinero;
    }

    /**
     * @return el nombre del jugador.
     */
    public String getName() {
        return nombre;
    }

    /**
     * @return el tipo de jugador.
     */
    public TipoJugador getTipo() {
        return tipo;
    }

    /**
     * @return la mano del jugador.
     */
    public List<Carta> getMano() {
        return mano;
    }

    // #---------------------------------------------------------------------------
    // # SETTERS
    // #---------------------------------------------------------------------------

    public void setManoPanel(JManoPanel manoPanel) {
        this.manoPanel = manoPanel;
    }

    /**
     * 
     * @param index1
     * @param index2
     */
    public void cambiarCartas(int index1, int index2) {
        Carta carta1 = mano.get(index1);
        Carta carta2 = mano.get(index2);

        mano.set(index1, carta2);
        mano.set(index2, carta1);
    }
}

/**
 * enum que contiene los tipos de jugador (simulado o usuario).
 */
enum TipoJugador {
    Simulado, Usuario
}