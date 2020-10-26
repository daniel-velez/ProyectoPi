/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */

package classicPoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

/**
 * Clase encargada de representar el juego y de realizar las operaciones de E/S por medio de un JFrame.
 */
public class PokerView extends JFrame {

    private JInstruccionesPanel instrucciones;
    private List<Jugador> jugadores;
    private Jugador usuario;
    private Map<Jugador, JManoPanel> playersView;
    private JLabel textBig, textSmall;

    private List<JButton> fichas;
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
            if (jugador.getTipo() == TipoJugador.Usuario)
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
        this.setLayout(new GridLayout(4, 1)); // olvide que si se pone 1 columna solo la ocupa 1 componente
        JPanel rowPane = new JPanel();
        JPanel colPane = new JPanel();
        listener = new Escucha();

        //# Vista de los jugadores

        playersView = new HashMap<Jugador, JManoPanel>();
        for (Jugador jugador : jugadores)
            playersView.put(jugador, new JManoPanel(jugador));

        rowPane.setLayout(new FlowLayout());
        add(rowPane);

        rowPane.add(playersView.get(jugadores.get(0)));
        rowPane.add(Box.createRigidArea(new Dimension(50, 0)));
        rowPane.add(playersView.get(jugadores.get(1)));

        //# Indicaciones

        rowPane = new JPanel();
        rowPane.setLayout(new FlowLayout());
        add(rowPane);
        rowPane.add(playersView.get(jugadores.get(2)));

        colPane.setLayout(new BoxLayout(colPane, BoxLayout.PAGE_AXIS));
        textBig = new JLabel("textBig");
        textSmall = new JLabel("textSmall");

        textBig.setPreferredSize(new Dimension(200, 40));
        //textBig.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colPane.add(textBig);

        textSmall.setPreferredSize(new Dimension(100, 40));
        //textSmall.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colPane.add(textSmall);

        rowPane.add(colPane);
        rowPane.add(playersView.get(jugadores.get(3)));

        //# Usuario

        rowPane = new JPanel();
        rowPane.setLayout(new FlowLayout());
        add(rowPane);
        rowPane.add(playersView.get(jugadores.get(4)));

        rowPane = new JPanel();
        rowPane.setLayout(new BorderLayout());
        add(rowPane);

        colPane = new JPanel();
        colPane.setLayout(new BoxLayout(colPane, BoxLayout.PAGE_AXIS));

        colPane.add(playersView.get(jugadores.get(4)).getUserName());
        colPane.add(playersView.get(jugadores.get(4)).getUserMoney());
        rowPane.add(colPane, BorderLayout.LINE_START);

        //# Botones

        fichas = new ArrayList<JButton>();

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

        //rowPane.add(apostar);
        //rowPane.add(pasar);
        //rowPane.add(igualar);
        //rowPane.add(aumentar);
        //rowPane.add(retirarse);
        //rowPane.add(descartar);

    }

    // #---------------------------------------------------------------------------
    // # Métodos desde control a la vista
    // #---------------------------------------------------------------------------

    public void showMessage(String msg, int time) throws InterruptedException {
        textSmall.setText(msg);
        Thread.sleep(time);
    }

    public void updateMoney(Jugador player) {
            playersView.get(player).mostrarDinero();
    }

    public void descubrirCartas(Jugador player) {
        //! condicional sobre el tipo de jugador
        playersView.get(player).descubrirCartas();
    }

    //# Metodos para click de los botones

    // #---------------------------------------------------------------------------
    // # Juego
    // #---------------------------------------------------------------------------

    public void iniciarRonda() throws InterruptedException {
        for (int i = 3; i > 0; i--)
            showMessage("La ronda iniciará en " + i, 1000);

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
        //boton.setBorder(null);
        //boton.setContentAreaFilled(false);
        //boton.addActionListener(escucha);
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
