/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */

package classicPoker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase encargada de representar el juego y de realizar las operaciones de E/S por medio de un JFrame.
 */
public class PokerView extends JFrame {

    private JInstruccionesPanel instrucciones;
    private List <Jugador> jugadores;
    private Jugador usuario;
    private List <JManoPanel> playersView;
    private JLabel userMoney, userName;
    private JLabel textBig, textSmall;

    private List <JButton> fichas;
    private JButton apostar, pasar, igualar, aumentar, descartar;
    private JButton retirarse, ayuda, levantarse, saltar;

    private Escucha listener;
    
    /**
     * Instantiates a new poker view.
     */
    public PokerView(List<Jugador> jugadores) {

        this.jugadores = jugadores;
        this.instrucciones = new JInstruccionesPanel();

        for (Jugador jugador : jugadores) {
            if(jugador.getTipo() == TipoJugador.Usuario) 
                usuario = jugador;
        }

        initGUI();

        this.setTitle("Classic Poker");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.pack();
    }

    /**
     * Inits the GUI.
     */
    private void initGUI() {

        this.setLayout(new  GridLayout(3,9)); // olvide que si se pone 1 columna solo la ocupa 1 componente

        listener = new Escucha();


        //# Vista de los jugadores
        
        playersView = new ArrayList<JManoPanel>();
        for (Jugador jugador : jugadores) 
            playersView.add(new JManoPanel(jugador));


        for (int i = 0; i<3; i++)
            add(playersView.get(i));

        //# Indicaciones

        textBig = new JLabel();
        textSmall = new JLabel();

        textBig.setPreferredSize(new Dimension(200, 40));
        textBig.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(textBig);

        textSmall.setPreferredSize(new Dimension(100, 40));
        textSmall.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(textSmall);


        add(playersView.get(3));

        //# Usuario

        add(playersView.get(4));

        userMoney = new JLabel();
        userName = new JLabel();

        userMoney.setText(Integer.toString(usuario.getDinero()));
        userName.setText(usuario.getName());

        add(userMoney);
        add(userName);

        //# Botones

        fichas = new ArrayList <JButton>();

        fichas.add(new JButton("100"));
        fichas.add(new JButton("200"));
        fichas.add(new JButton("500"));
        fichas.add(new JButton("1000"));

        for (JButton boton : fichas) {
            configurarBoton(boton, listener);
        }

        apostar = new JButton();
        configurarBoton(apostar, listener);

        pasar = new JButton();
        configurarBoton(pasar, listener);

        igualar = new JButton();
        configurarBoton(igualar, listener);

        aumentar = new JButton();
        configurarBoton(aumentar, listener);

        retirarse = new JButton();
        configurarBoton(retirarse, listener);

        descartar = new JButton();
        configurarBoton(descartar, listener);

        levantarse = new JButton();
        configurarBoton(levantarse, listener);

        saltar = new JButton();
        configurarBoton(saltar, listener);

        ayuda = new JButton();
        configurarBoton(ayuda, listener);

        add(apostar);
        add(pasar);
        add(igualar);
        add(aumentar);
        add(retirarse);
        add(descartar);

    }
    
    //# Metodos para click de los botones



    //# Juego
    
    public void iniciarRonda() {

    }
    
    public void rondaDeApuesta() {

    }
    
    public void rondaDeDescarte() {

    }
    
    public void descubrirCartas() {

    }
    
    public void mostrarDinero() {

    }


    // #---------------------------------------------------------------------------
    // # Funciones auxiliares
    // #---------------------------------------------------------------------------

    /**
     * 
     * @param boton el boton a configurar
     * @param escucha el escucha de lso eventos
     */
    private void configurarBoton(JButton boton, Escucha escucha) {
        boton.setBorder(null);
        boton.setContentAreaFilled(false);
        boton.addActionListener(escucha);
    }
    

    // #---------------------------------------------------------------------------
    // # Listener
    // #---------------------------------------------------------------------------

    /**
     * The Class Escucha. Clase interna encargada de manejar los eventos de la ventana.
     */
    private class Escucha implements ActionListener {

        /**
         * Action performed.
         *
         * @param event the event
         */
        @Override
        public void actionPerformed(ActionEvent event) {

            for (JButton ficha : fichas) {
                if (event.getSource() == ficha) {
                    
                }
            }
        }
    }
}
