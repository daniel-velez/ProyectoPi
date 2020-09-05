/*
 * Programación Interactiva
 * Autor: Daniel Felipe Vélez C. - 1924306
 * email: daniel.cuaical@correounivalle.edu.co
 * Caso 1: Juego Craps
 * 23-08-2020
 */

package craps;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Dado. Esta clase simula un dado y permite ver el valor obtenido en la cara visible.
 */
public class Dado {
	
	/** The cara visible. El valor (entre 1 y 6) obtenido al lanzar el dado.*/
	private int caraVisible;
	
	/**
	 * Gets the cara visible. Determina el valor de la cara visible del dado y lo retorna.
	 *
	 * @return caraVisible. Es un valor entre 1 y 6.
	 */
	public int getCaraVisible() {
		Random aleatorio = new Random();
		caraVisible = aleatorio.nextInt(6) + 1;
		
		return caraVisible;
	}
}
