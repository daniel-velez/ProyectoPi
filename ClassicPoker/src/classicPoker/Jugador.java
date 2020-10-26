/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

/**
 * Clase que modela un jugador.
 */
public class Jugador {

    private int dinero;
    private String nombre;
    private List<Carta> mano;
    private TipoJugador tipo;
    private boolean seHaRetirado;
    private PokerView pokerView;

    /**
     * Instantiates a new jugador.
     * @param dinero el dinero del jugador
     * @param tipo el tipo de jugador (simulado o usuario). 
     */
    public Jugador(int dinero, TipoJugador tipo) {
        this.dinero = dinero;
        this.tipo = tipo;
        this.seHaRetirado = false;
        this.mano = new ArrayList<Carta>();
        this.nombre = "Test";
    }

    public void defineView(PokerView pokerView) {
        this.pokerView = pokerView;
    }

    /**
     * Aporta la cantidad de dinero a la mesa de apuesta
     * @param cantidad la cantidad de dinero
     */
    public void aportar(int cantidad) {
        dinero -= cantidad;
        pokerView.updateMoney(this);
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
     * Simula la decision de apostar
     * @param carrier valor para igualar la apuesta
     * @return valor de la apuesta o null en caso de pasar, retirarse
     */
    public int apostar(int carrier) {
        return 0;
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
    public boolean estaJugando() {
        return !seHaRetirado;
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

	public void cambiarCartas(int index1, int index2) {
        Carta carta1 = mano.get(index1);
        Carta carta2 = mano.get(index2);
        
        mano.set(index1, carta2);
        mano.set(index2, carta1);
        
        /*
        mano.remove(index1);
        mano.add(index1, mano.get(index2));

        mano.remove(index2);
        mano.add(index2, carta1);
        */
	}
}



/**
 * enum que contiene los tipos de jugador (simulado o usuario).
 */
enum TipoJugador {
    Simulado, Usuario
}