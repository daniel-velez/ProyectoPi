/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
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
        for (Carta.Palos palo : Carta.Palos.values()) //crear las cartas de cada palo
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
    //! qué pasa si se intentan sacar 0 cartas?
    public List<Carta> sacarCartas(int n) {
        List<Carta> mano = new ArrayList<Carta>();
        mano.addAll(cartas.subList(0, n));
        cartas.removeAll(mano);
        return mano;
    }

    /**
     * 
     * @return
     */
    public Carta sacarCarta() {
        return cartas.remove(0);
    }

    /**
     * Agrega una lista de cartas para descartar al mazoDescarte.
     * @param cartas 
     */
    public void descartar(List<Carta> cartas) {
        for (Carta carta : cartas)
            mazoDescarte.add(carta);
    }

    /**
     * Agrega una carta para descartar al mazoDescarte.
     * @param carta
     */
    public void descartar(Carta carta) {
        mazoDescarte.add(carta);
    }


    //# Metodos para obtener una mano con jugada.

    /**
     * 
     * @param palo
     * @return la mano con la jugada color.
     */
    public static List<Carta> manoColor(Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();

        // 3, 4, 5, 6, 7.
        for (int i = 0; i < 5; i++)
            mano.add(new Carta(i+3, palo));
        return mano;
    }

    /**
     * 
     * @param palo
     * @return la mano con la jugada escalera color.
     */
    public static List<Carta> manoEscaleraColor(Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();

        // 2, 3, 4, 5, 6.
        for (int j = 0; j < 5; j++) 
            mano.add(new Carta(2+j, palo));

        return mano;
    }

}
