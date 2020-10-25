/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

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
		} catch (Exception e) {}
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				//PokerView myGame = new PokerView(jugadores);
			}
        });
	}
}
