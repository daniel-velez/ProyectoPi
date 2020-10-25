/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.util.ElementScanner6;

/**
 * Clase que determina una mano de poker.
 */
public class PokerRules {

    private interface pokerRule {
        public int f(int x);
    }

    private static pokerRule[] hh = { (x) -> 2 * x, (x) -> 5 * x };

    //! hay que verificar que la mano sea de 5 cartas? o lo damos por hecho

    /**
     * 
     * @param mano1
     * @param mano2
     */
    public static List<Carta> determinarMano(List<Carta> mano1, List<Carta> mano2) {
        List<Carta> manoGanadora;

        manoGanadora = determinarMano(mano1) < determinarMano(mano2) ? mano1 : mano2;
        return manoGanadora;
    }

    /**
     * Determina si la mano especificada corresponde a la de una jugada.
     * @param mano lista de cartas
     * @return 1 = Escalera real
     */
    public static int determinarMano(List<Carta> mano) {
        ordenarMano(mano);
        int manoPoker = -1;

        if (escaleraReal(mano)) {
            manoPoker = 1;
        }

        return manoPoker;
    }

    /**
     * valor de la escaleraReal = 1.
     * @return true si la mano es una escaler real.
     */
    private static boolean escaleraReal(List<Carta> mano) {
        if (!color(mano))
            return false;
        for (int i = 0; i < 5; i++)
            if (mano.get(i).numero != i + 10)
                return false;
        return true;
    }

    private static boolean poker(List<Carta> mano) {
        Map<Carta, Integer> conteo = conteoDeCartas(mano);
        for (Integer n : conteo.values())
            if (n == 4)
                return true;
        return false;
    }

    //! falta la condicion para el AS
    private static boolean escaleraColor(List<Carta> mano) {
        if (!color(mano))
            return false;
        for (int i = 0; i < 5; i++)
            if (mano.get(i).numero != i + mano.get(0).numero)
                return false;
        return true;
    }

    private static boolean full(List<Carta> mano) {
        Map<Carta, Integer> conteo = conteoDeCartas(mano);
        for (Integer n : conteo.values())
            if (n != 3 && n != 2)
                return false;
        return true;
    }

    /**
     * Determina si las cartas de una lista son del mismo palo.
     * @param mano lista de cartas.
     */
    private static boolean color(List<Carta> mano) {
        for (int i = 1; i < mano.size(); i++)
            if (mano.get(i).palo != mano.get(0).palo)
                return false;
        return true;
    }

    //! falta la condicion para el AS
    private static boolean escalera(List<Carta> mano) {
        for (int i = 0; i < 5; i++)
            if (mano.get(i).numero != i + mano.get(0).numero)
                return false;
        return true;
    }

    private static boolean trio(List<Carta> mano) {
        Map<Carta, Integer> conteo = conteoDeCartas(mano);
        for (Integer n : conteo.values())
            if (n == 3)
                return true;
        return false;
    }

    private static boolean doblePareja(List<Carta> mano) {
        Map<Carta, Integer> conteo = conteoDeCartas(mano);
        int parejas = 0;
        for (Integer n : conteo.values())
            if (n == 2) {
                parejas++;
                if (parejas == 2)
                    return true;
            }
        return false;
    }

    private static boolean pareja(List<Carta> mano) {
        Map<Carta, Integer> conteo = conteoDeCartas(mano);
        for (Integer n : conteo.values())
            if (n == 2)
                return true;
        return false;
    }

    private static int cartaMásAlta(List<Carta> mano) {
        int max = 0;
        for (Carta carta : mano)
            if (carta.numero > max)
                max = carta.numero;
        return max;
    }

    // #---------------------------------------------------------------------------
    // # FUNCIONES AUXILIARES
    // #---------------------------------------------------------------------------

    private static Map<Carta, Integer> conteoDeCartas(List<Carta> mano) {
        Map<Carta, Integer> conteo = new HashMap<Carta, Integer>();
        for (Carta carta : mano)
            if (conteo.get(carta) == null)
                conteo.put(carta, 0);
            else
                conteo.replace(carta, conteo.get(carta) + 1);
        return conteo;
    }

    /**
     * Ordena de manera ascendente la lista de cartas.
     * @param mano lista de cartas para ordenar.
     */
    public static void ordenarMano(List<Carta> mano) {
        //mano.sort(Comparator.comparing(numero));
        Collections.sort(mano, new Comparator<Carta>() {
            @Override
            public int compare(Carta carta1, Carta carta2) {
                if (carta1.numero > carta2.numero) {
                    return 1;
                }
                if (carta1.numero < carta2.numero) {
                    return -1;
                }
                return 0;
            }
        });
    }

    //! No he probado este metodo xd
    /**
     * Ordena de manera ascendente una lista de cartas usando el algoritmo burbuja.
     */
    public static void burbuja(List<Carta> A) {
        int i, j;
        Carta aux;
        for (i = 0; i < A.size() - 1; i++) {
            for (j = 0; j < A.size() - i - 1; j++) {
                if (A.get(j + 1).numero < A.get(j).numero) {
                    aux = A.get(j + 1);
                    A.remove(j + 1);
                    A.add(j + 1, A.get(j));
                    A.remove(j);
                    A.add(j, aux);
                }
            }
        }
    }
}
