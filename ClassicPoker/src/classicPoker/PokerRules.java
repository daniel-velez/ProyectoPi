/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Clase que determina una mano de poker.
 */
public class PokerRules {
    
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
        int manoPoker=-1; 

        if(escaleraReal(mano)) {
            manoPoker = 1;
        }
        
        return manoPoker;
    }

    /**
     * Determina si las cartas de una lista son del mismo palo.
     * @param mano lista de cartas.
     */
    private static boolean determinarPalo(List<Carta> mano) {
        for (int i = 1; i < mano.size(); i++) {
            if (mano.get(i).palo != mano.get(0).palo)
                return false;
        }
        return true;
    }

    /**
     * valor de la escaleraReal = 1.
     * @return true si la mano es una escaler real.
     */
    private static boolean escaleraReal(List<Carta> mano) {

        if (determinarPalo(mano)) {
            for(int i = 0; i<5; i++) {
                if ( mano.get(i).numero != i+10 ) 
                    return false;
            }
        }
        return true;
    }

    

    

    // #---------------------------------------------------------------------------
    // # FUNCIONES AUXILIARES
    // #---------------------------------------------------------------------------

    /**
     * Ordena de manera ascendente la lista de cartas.
     * @param mano lista de cartas para ordenar.
     */
    public static void ordenarMano(List<Carta> mano) {
        //mano.sort(Comparator.comparing(numero));
        Collections.sort(mano, new Comparator<Carta>() {
            @Override
            public int compare(Carta carta1, Carta carta2) {
               if( carta1.numero > carta2.numero ){
                   return 1;
               }
               if( carta1.numero < carta2.numero ){
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
                if (A.get(j+1).numero < A.get(j).numero) {
                    aux = A.get(j + 1);
                    A.remove(j+1);
                    A.add(j+1, A.get(j));
                    A.remove(j);
                    A.add(j, aux);
                }
            }
        }
    }
}
