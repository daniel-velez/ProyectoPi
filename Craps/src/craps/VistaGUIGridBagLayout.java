package craps;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import misComponentes.Titulos;

public class VistaGUIGridBagLayout extends JFrame {

	//atributos 
	private JFrame meVistaGUIGridBaglayout;
	private JPanel zonaJuego;
	private JPanel zonaResultados;
	private JLabel dado1;
	private JLabel dado2;
	private JLabel tiro;
	private JLabel punto;
	private JTextField valorTiro;
	private JTextField valorPunto;
	private JButton botonSalir;
	private JButton botonLanzar;
	private ImageIcon imagen;
	private JTextArea areaMensajes; //for the text area in the lower right corner.
	private ControlCraps controlCraps;
	private Escucha listener;
	private Titulos tituloJuego;
	private Titulos resultado;
	
	
	//metodos
	
	public VistaGUIGridBagLayout() {
		initGUI(); // graphic components are created here.

		this.setUndecorated(true);
		//set default window configuration.
		this.pack(); //modify the size of the JFrame.
		this.setBackground(new Color(250, 250, 0));
		this.setLocationRelativeTo(null); //to center the window.
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to terminated the executing of the program by closing the window.
		
		
	}
	
	private void initGUI() { //doesn't return anything, it is procedural method.
		
		//set up container - layout (container configuration and its layout).
		meVistaGUIGridBaglayout = this;
		this.getContentPane().setLayout(new GridBagLayout()); //returns a reference to the window container.
		GridBagConstraints constraints = new GridBagConstraints();
		
		//create objects Listener, Control and Others.
		listener = new Escucha();
		controlCraps = new ControlCraps();
		
		
		//set up GUIComponents
		
		tituloJuego = new Titulos("Juego Craps", 30, Color.white, new Color(0,0,0));
		tituloJuego.addMouseListener(listener);
		tituloJuego.addMouseMotionListener(listener);
		tituloJuego.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		//constraints of the grafic component tituloJuego
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth=2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(tituloJuego, constraints); //add the component into the container according the constrains.
		
		zonaJuego = new JPanel();
		zonaJuego.setPreferredSize(new Dimension(310,180));
		zonaJuego.setBorder(new TitledBorder("Zona Juego"));
		imagen = new ImageIcon("src/imagenes/dado.png");
		dado1 = new JLabel(imagen);
		dado2 = new JLabel(imagen);
		botonLanzar = new JButton("Lanzar");
		botonLanzar.addActionListener(listener);
		//add the grafics components to the zonaJuego.
		zonaJuego.add(dado1);
		zonaJuego.add(dado2);
		zonaJuego.add(botonLanzar);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 3;
		constraints.fill = GridBagConstraints.NONE;
		add(zonaJuego, constraints);
		
		botonSalir = new JButton("Salir");
		botonSalir.addActionListener(listener);
		botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.LAST_LINE_START;
		add(botonSalir, constraints);
		
		resultado = new Titulos("Resultados", 24, Color.white, new Color(0, 100, 255));
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		add(resultado, constraints);
		
		zonaResultados = new JPanel();
		zonaResultados.setLayout(new GridLayout(2,2));
		tiro = new JLabel("Tiro");
		punto = new JLabel("Punto");
		valorTiro = new JTextField(2);
		valorTiro.setEditable(false);
		valorPunto = new JTextField(2);
		valorPunto.setEditable(false);
		zonaResultados.add(tiro);
		zonaResultados.add(valorTiro);
		zonaResultados.add(punto);
		zonaResultados.add(valorPunto);
		zonaResultados.setBackground(Color.YELLOW);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		add(zonaResultados, constraints);
		
		areaMensajes = new JTextArea(10,20);
		areaMensajes.setText("Lanza los dados para empezar. \n"); //clears the text area and puts the message
		areaMensajes.setEditable(false);
		JScrollPane scroll = new JScrollPane(areaMensajes);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.anchor = GridBagConstraints.CENTER;
		add(scroll, constraints);
		
		
		
	}
	
	private class Escucha implements ActionListener, MouseListener, MouseMotionListener{
		
		private int x;
		private int y;
		
		@Override
		public void actionPerformed(ActionEvent eventAction) {
			
			if(eventAction.getSource() == botonSalir) {
				System.exit(0);
			}else {
				controlCraps.calcularTiro();
				
				//Graphic visualization of the dice.
				imagen = new ImageIcon("src/imagenes/"+controlCraps.getCarasDados()[0]+".png");
				dado1.setIcon(imagen);
				imagen = new ImageIcon("src/imagenes/"+controlCraps.getCarasDados()[1]+".png");
				dado2.setIcon(imagen);
				
				controlCraps.determinarEstadoJuego();

				valorTiro.setText(String.valueOf(controlCraps.getTiro()));
				
				switch(controlCraps.getEstado()) {
				
					case 1: //win
						areaMensajes.append("Has ganado.\n"); // puts the text in the message area without cleaning
						break;
						
					case 2: //lose
						areaMensajes.append("Perdiste. \n");
						break;
						
					case 3: //point
						valorPunto.setText(String.valueOf(controlCraps.getPunto()));
						String mensaje = "Ronda de punto: debes seguir lanzando. \n" +
										 "Si sacas " + controlCraps.getPunto() + " ganarás. \n" +
										 "Pierdes si sacas 7. \n";
						areaMensajes.append(mensaje);
						break;
					
				}
				
			}
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			
		}

		@Override
		public void mouseExited(MouseEvent event) {
			
		}

		@Override
		public void mousePressed(MouseEvent event) {
			x = event.getX();
			y = event.getY();	
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			
		}

		@Override
		public void mouseDragged(MouseEvent eventMotion) {
			setLocation(meVistaGUIGridBaglayout.getLocation().x+eventMotion.getX()-x, 
						meVistaGUIGridBaglayout.getLocation().y + eventMotion.getY()-y);
		}

		@Override
		public void mouseMoved(MouseEvent eventMotion) {
			
			
		}
		
	}
}
