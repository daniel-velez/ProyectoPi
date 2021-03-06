/*
 * Programacion Interactiva
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import classicPoker.Carta.Palos;

/**
 * Clase que modela un mazo de cartas.
 */
public class MazoDeCartas {
    private List<Carta> cartas;
    private List<Carta> mazoDescarte;

    /**
     * Instantiates a new mazo de cartas.
     */
    public MazoDeCartas() {
        this.cartas = new ArrayList<Carta>();
        this.mazoDescarte = new ArrayList<Carta>();
        crearBaraja();
        revolverMazo();
    }

    /**
     * Crea una baraja de cartas organizada.
     */
    public void crearBaraja() {
        for (Palos palo : Palos.values()) //crear las cartas de cada palo
            for (int i = 2; i <= 14; i++)
                cartas.add(new Carta(i, palo));
    }

    /**
     * Revuelve el mazo de manera aleatoria.
     */
    public void revolverMazo() {
        Collections.shuffle(cartas);
    }

    /**
     * Retorna una lista de n cartas.
     * @param n
     */
    public List<Carta> sacarCartas(int n) {
        List<Carta> mano = new ArrayList<Carta>();
        mano.addAll(cartas.subList(0, n));
        cartas.removeAll(mano);
        return mano;
    }

    /**
     * Elimina la carta en la posicion 0 de la lista de cartas.
     * @return La carta que se ha eliminado de la lista de cartas.
     */
    public Carta sacarCarta() {
        return cartas.remove(0);
    }

    /**
     * Agrega una lista de cartas para descartar al mazoDescarte.
     * @param cartas 
     */
    public void descartar(List<Carta> cartas) {
        mazoDescarte.addAll(cartas);
    }

    /**
     * Agrega una carta para descartar al mazoDescarte.
     * @param carta
     */
    public void descartar(Carta carta) {
        mazoDescarte.add(carta);
    }

    /**
     * Combina las cartas del mazoDescarte y la lista cartas.
     */
    public void combinarMazos() {
        cartas.addAll(mazoDescarte);
        mazoDescarte.clear();
        revolverMazo();
    }


    //# Metodos para obtener una mano con jugada.

    /**
     * Crea y retorna una lista de cartas con la jugada Escalera Real del palo especificado.
     * @param palo el palo de las cartas.
     * @return La mano con la jugada Escalera Real.
     */
    public List<Carta> manoEscaleraReal(Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();

        // 10, J, Q, K, A.
        for (int i = 0; i < 5; i++) 
            mano.add(new Carta(i+10, palo));
        return mano;
    }

    /**
     * Crea y retorna una lista de cartas con la jugada Poker del valor de carta especificado.
     * @param valor el valor de las cartas que componen la jugada Poker
     * @param valor2 el valor de la quinta carta la cual no compone la jugada Poker.
     * @param palo
     * @return la mano con la jugada Poker
     */
    public List<Carta> manoPoker(int valor, int valor2, Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();
        
        for (int i = 0; i<4; i++) 
            mano.add(new Carta(valor, Palos.values()[i]));
        
        mano.add(new Carta(valor2, palo));
        return mano;
    }

    /**
     * Crea y retorna una lista de cartas con la jugada Escalera Color empezando con el 
     * valor ingresado del palo especificado.
     * @param valor el valor de la primer carta.
     * @param palo el palo de las cartas.
     * @return La mano con la jugada Escalera Color.
     */
    public List<Carta> manoEscaleraColor(int valor, Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();

        for (int i = 0; i < 5; i++) 
            mano.add(new Carta(valor+i, palo));
        return mano;
    }

    /**
     * Crea y retorna una mano con la jugada Full, con los valores del trio y pareja especificados.
     * @param valor1 el valor de las cartas que componen el trio.
     * @param valor2 el valor de las cartas que componen la pareja.
     * @return la lista de cartas con la jugada Full.
     */
    public List<Carta> manoFull(int valor1, int valor2) {
        List<Carta> mano = new ArrayList<Carta>();

        mano.add(new Carta(valor1,Palos.corazones));
        mano.add(new Carta(valor1,Palos.diamantes));
        mano.add(new Carta(valor1,Palos.picas));
        mano.add(new Carta(valor2,Palos.treboles));
        mano.add(new Carta(valor2,Palos.diamantes));

        return mano;
    }

    /**
     * Crea y retorna una mano con la jugada Color.
     * @return la lista de cartas con la jugada Color
     */
    public List<Carta> manoColor() {
        List<Carta> mano = new ArrayList<Carta>();

        mano.add(new Carta(2,Palos.diamantes));
        mano.add(new Carta(4,Palos.diamantes));
        mano.add(new Carta(8,Palos.diamantes));
        mano.add(new Carta(6,Palos.diamantes));
        mano.add(new Carta(7,Palos.diamantes));

        return mano;
    }

    /**
     * Crea y retorna una mano con la jugada Escalera.
     * @return la lista de cartas con la jugada Escalera.
     */
    public List<Carta> manoEscalera() {
        List<Carta> mano = new ArrayList<Carta>();

        mano.add(new Carta(2,Palos.corazones));
        mano.add(new Carta(3,Palos.diamantes));
        mano.add(new Carta(4,Palos.picas));
        mano.add(new Carta(5,Palos.treboles));
        mano.add(new Carta(6,Palos.diamantes));
        return mano;
    }

    /**
     * Crea y retorna una lista de cartas con la jugada Trio del valor de carta especificado.
     * @param valor
     * @return la mano con la jugada Trio.
     */
    public List<Carta> manoTrio(int valor) {
        List<Carta> mano = new ArrayList<Carta>();

        // valor, valor, valor, 7, 9
        mano.add(new Carta(valor,Palos.corazones));
        mano.add(new Carta(valor,Palos.diamantes));
        mano.add(new Carta(valor,Palos.picas));
        mano.add(new Carta(7,Palos.treboles));
        mano.add(new Carta(9,Palos.diamantes));
        return mano;
    }

    /**
     * Crea y retorna una lista de cartas con la jugada Doble Pareja.
     * @return la mano con la jugada Doble Pareja.
     */
    public List<Carta> manoDoblePareja() {
        List<Carta> mano = new ArrayList<Carta>();

        mano.add(new Carta(3,Palos.corazones));
        mano.add(new Carta(3,Palos.diamantes));
        mano.add(new Carta(7,Palos.picas));
        mano.add(new Carta(7,Palos.treboles));
        mano.add(new Carta(9,Palos.diamantes));
        return mano;
    }

     /**
     * Crea y retorna una lista de cartas con la jugada Pareja.
     * @return la mano con la jugada Pareja.
     */
    public List<Carta> manoPareja() {
        List<Carta> mano = new ArrayList<Carta>();

        mano.add(new Carta(2,Palos.corazones));
        mano.add(new Carta(2,Palos.diamantes));
        mano.add(new Carta(7,Palos.picas));
        mano.add(new Carta(5,Palos.treboles));
        mano.add(new Carta(9,Palos.diamantes));
        return mano;
    }
}
