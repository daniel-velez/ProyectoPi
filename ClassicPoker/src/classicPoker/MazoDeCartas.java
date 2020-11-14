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


    //# Metodos para obtener una mano con jugada.

    /**
     * Crea y retorna una lista de cartas con la jugada Escalera Real del palo especificado.
     * @param palo 
     * @return La mano con la jugada Escalera Real.
     */
    public List<Carta> manoEscaleraReal(Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();

        // 10, J, Q, K, A.
        for (int i = 0; i < 5; i++) {
            mano.add(new Carta(i+10, palo));
            eliminarCarta(mano.get(i));
        }
        return mano;
    }

    /**
     * Crea y retorna una lista de cartas con la jugada Escalera Color del palo especificado.
     * @param palo
     * @return La mano con la jugada Escalera Color.
     */
    public List<Carta> manoEscaleraColor(Palos palo) {
        List<Carta> mano = new ArrayList<Carta>();

        // 2, 3, 4, 5, 6.
        for (int i = 0; i < 5; i++) {
            mano.add(new Carta(2+i, palo));
            eliminarCarta(mano.get(i));
        }
        return mano;
    }

    /**
     * Elimina una carta especificada de la lista de cartas.
     * @param unaCarta
     * @return true si se ha encontrado y eliminado la carta especificada, false en caso contrario.
     */
    private boolean eliminarCarta(Carta unaCarta) {
        for (Carta carta : cartas) {
            if (carta.numero == unaCarta.numero && carta.palo == unaCarta.palo) {
                cartas.remove(carta);
                return true;
            }
        }
        return false;
    }

}
