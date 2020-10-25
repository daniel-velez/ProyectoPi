/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import javax.swing.UIManager;

/**
 * Clase que contiene el metodo main
 * e inicia el programa.
 */
public class PrincipalPoker {
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
