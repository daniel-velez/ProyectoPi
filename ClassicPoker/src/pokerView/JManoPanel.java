/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package pokerView;

import classicPoker.*;
import classicPoker.Jugador.TipoJugador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 */
public class JManoPanel extends JPanel {
    private List<JButton> JMano;
    private Jugador jugador;
    private JLabel nombre, dinero;
    private boolean isHuman;
    private Dimension JCartaSize;
    private Dimension panelSize;

    private Boolean ordenarCartas;
    private List<JButton> cartasSeleccionadas;

    private Escucha listener;

    /**
     * Instantiates a new JManoPanel.
     * @param jugador
     */
    public JManoPanel(Jugador jugador) {
        this.jugador = jugador;
        this.ordenarCartas = true;
        this.cartasSeleccionadas = new ArrayList<JButton>();
        this.isHuman = (jugador.getTipo() == TipoJugador.Usuario) ? true : false;
        this.JCartaSize = (isHuman) ? new Dimension(88, 132) : new Dimension(58, 88); // base: 350x530
        this.panelSize = (isHuman) ? new Dimension(650, 155) : new Dimension(325, 135);
        this.setOpaque(false);

        JMano = new ArrayList<JButton>();

        for (int i = 0; i < 5; i++)
            JMano.add(new JButton());

        initGUI();
    }

    private void initGUI() {
        this.setPreferredSize(panelSize);
        //this.setBackground(Color.ORANGE);

        listener = new Escucha();

        nombre = new JLabel(jugador.getName());
        dinero = new JLabel(Integer.toString(jugador.getDinero()));

        nombre.setFont(Resources.Casino.deriveFont(16f));
        dinero.setFont(Resources.Casino.deriveFont(16f));

        this.add(nombre);
        if (!isHuman)
            this.add(Box.createRigidArea(new Dimension(245, 0)));
        this.add(dinero);

        for (JButton JCarta : JMano) {
            JCarta.setSize(JCartaSize);
            JCarta.setMinimumSize(JCartaSize);
            JCarta.setPreferredSize(JCartaSize);
            JCarta.setContentAreaFilled(false);
            JCarta.setBorder(null);
            if (isHuman)
                JCarta.addActionListener(listener);
            this.add(JCarta);
        }
    }

    // #---------------------------------------------------------------------------
    // # MÉTODOS
    // #---------------------------------------------------------------------------

    /**
     * Muestra el dinero del jugador por medio de un JLabel.
     */
    public void mostrarDinero() {
        dinero.setText(jugador.getDinero().toString());
        revalidate();
        repaint();
    }

    /**
     * Muestra el contenido de las cartas del jugador.
     * @param mano lista de cartas.
     */
    public void descubrirCartas(List<Carta> mano) {
        for (int i = 0; i < JMano.size(); i++)
            JMano.get(i).setIcon(CardImage.get(mano.get(i), JCartaSize));
    }

    public void taparCartas() {

    }

    /**
     * 
     */
    public void descartar() {
        for (JButton JCarta : JMano)
            JCarta.setText(null);
    }

    /**
     * 
     * @param index
     */
    public void descartar(int index) {
        JMano.get(index).setText(null);
    }

    /**
     * @return La lista de enteros que corresponden a la posicion de las cartas que el usuario ha seleccionado.
     */
    public List<Integer> getCartasSeleccionadas() {
        List<Integer> cartas = new ArrayList<Integer>();
        for (JButton JCarta : cartasSeleccionadas) {
            cartas.add(JMano.indexOf(JCarta));
            JCarta.setBackground(null);
        }
        cartasSeleccionadas.clear();
        return cartas;
    }

    // #---------------------------------------------------------------------------
    // # GETTERS & SETTERS
    // #---------------------------------------------------------------------------

    /**
     * @return El JLabel con el nombre del jugador.
     */
    public JLabel getUserName() {
        return nombre;
    }

    /**
     * @return El JLabel con el dinero del jugador.
     */
    public JLabel getUserMoney() {
        return dinero;
    }

    /**
     * Establece el atributo ordenarCartas en false en caso de estar en la ronda de descarte.
     * @param flag true si está en la ronda de descarte, false en caso contrario.
     */
    public void setRondaDeDescarte(boolean flag) {
        ordenarCartas = !flag;
    }

    /**
     * @return el entero correspondiente a la cantidad de cartas seleccionadas.
     */
    public int getCartasSeleccionadasSize() {
        return cartasSeleccionadas.size();
    }

    // #---------------------------------------------------------------------------
    // # EVENTS
    // #---------------------------------------------------------------------------

    /**
     * 
     * @param JCarta
     */
    private void onJCartaClick(JButton JCarta) {

        if (ordenarCartas) {
            if (cartasSeleccionadas.size() == 0)
                cartasSeleccionadas.add(JCarta);
            else if (cartasSeleccionadas.size() == 1) {
                int index1 = JMano.indexOf(cartasSeleccionadas.get(0));
                int index2 = JMano.indexOf(JCarta);
                jugador.cambiarCartas(index1, index2);
                cartasSeleccionadas.clear();
            }
        } else if (cartasSeleccionadas.indexOf(JCarta) != -1) {
            cartasSeleccionadas.remove(JCarta);
            JCarta.setBackground(null);
        } else {
            cartasSeleccionadas.add(JCarta);
            JCarta.setBackground(Color.WHITE);
        }
    }

    /**
     * The Class Escucha
     */
    private class Escucha implements ActionListener {
        /**
         * Action performed.
         * @param event the event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            if (!jugador.seHaRetirado())
                onJCartaClick((JButton) event.getSource());
        }
    }
}