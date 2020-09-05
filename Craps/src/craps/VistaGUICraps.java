package craps;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VistaGUICraps extends JFrame {
	//atributos
	private JLabel dado1;
	private JLabel dado2;
	private JButton lanzar;
	private ImageIcon imagen; //para insertar imagenes
	private Escucha listener;
	private ControlCraps controlCraps;
	
	
	//metodos
	public VistaGUICraps() {
		
		this.setTitle("Juego Craps");
		this.setSize(325, 210);
		this.setLocationRelativeTo(null); //null -> muestra la ventana centrada
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //al cerrar la ventana se termina la ejecución del programa
		this.setVisible(true);
		
		//container
		Container contenedor =  this.getContentPane();
		contenedor.setLayout(new FlowLayout());
		
		//crear objeto escucha y objeto control
		Escucha listener = new Escucha();
		controlCraps = new ControlCraps();
		
		
		//agregar los componentes gráficos
		imagen = new ImageIcon("src/imagenes/dado.png");
		dado1 = new JLabel(imagen);
		dado2 = new JLabel(imagen);
		
		lanzar = new JButton("lanzar");
		lanzar.addActionListener(listener);
		
		contenedor.add(dado1);
		contenedor.add(dado2);
		contenedor.add(lanzar);
	}


	//clase interna (se define despues de los metodos).
	
	private class Escucha implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			//identifico la fuente del evento (en este caso "lanzar")
			if(event.getSource() == lanzar) {
				visualizarCarasDado();
				controlCraps.determinarEstadoJuego();
				String tiro = "El tiro fue " + controlCraps.getTiro() + "\n";
				Icon icono;
				
				switch(controlCraps.getEstado()) {
					
				case 1:
					icono = new ImageIcon("src/imagenes/ganaste.png");
					JOptionPane.showMessageDialog(lanzar, tiro + "¡Ganaste!", "Resultado", JOptionPane.DEFAULT_OPTION, icono);
					break;
					
				case 2:
					icono = new ImageIcon("src/imagenes/perdiste.png");
					JOptionPane.showMessageDialog(lanzar, tiro + "Has perdido.", "Resultado", JOptionPane.DEFAULT_OPTION, icono);
					break;
					
				case 3:
					String punto = "Has establecido punto en " + controlCraps.getPunto() 
									+ "\nDebes volver a sacar el valor del punto para ganar. \nSi sacas 7 pierdes.";
					icono = new ImageIcon("src/imagenes/punto.png");
					JOptionPane.showMessageDialog(lanzar, tiro + "\n"+punto, "Resultado", JOptionPane.DEFAULT_OPTION, icono);
					break;
				}
			}
			else {
				
			}
			
		}
		
		private void visualizarCarasDado() {
			controlCraps.calcularTiro();
				
			//cara del dado1
			imagen = new ImageIcon("src/imagenes/"+ controlCraps.getCarasDados()[0] + ".png");
			dado1.setIcon(imagen);
			
			//cara del dado2
			imagen = new ImageIcon("src/imagenes/"+ controlCraps.getCarasDados()[1] + ".png");
			dado2.setIcon(imagen);
		}
		
	}
}
