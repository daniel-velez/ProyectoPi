package entrenamientoDeMemoria;

import java.awt.EventQueue;
import java.util.ArrayList;

public class PrincipaEntrenamientoDeMemoria {

	public static void main(String[] args) {
		ControlEntrenamientoDeMemoria control = new ControlEntrenamientoDeMemoria();
		ArrayList<Integer> myli = control.revolverCartas();

		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// VistaGUIGridBagLayout myGame = new VistaGUIGridBagLayout();
				VistaEntrenamientoDeMemoria newGame = new VistaEntrenamientoDeMemoria();

			}
		});

	}

}
