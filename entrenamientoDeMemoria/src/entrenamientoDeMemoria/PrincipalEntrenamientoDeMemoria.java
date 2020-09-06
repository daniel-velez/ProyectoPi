package entrenamientoDeMemoria;

import java.awt.EventQueue;

public class PrincipalEntrenamientoDeMemoria {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				VistaEntrenamientoDeMemoria myGame = new VistaEntrenamientoDeMemoria();
			}
		});

	}

}
