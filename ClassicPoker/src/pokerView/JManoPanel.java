/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package pokerView;

import classicPoker.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JManoPanel extends JPanel {

    private List<JButton> JMano;
    private Jugador jugador;
    private JLabel nombre, dinero;

    private Boolean ordenarCartas;
    private List<JButton> cartasSeleccionadas;

    private Escucha listener;

    public JManoPanel(Jugador jugador) {
        this.jugador = jugador;
        this.ordenarCartas = true;
        this.cartasSeleccionadas = new ArrayList<JButton>();

        JMano = new ArrayList<JButton>();

        for (int i = 0; i < 5; i++)
            JMano.add(new JButton());

        initGUI();
    }

    private void initGUI() {
        listener = new Escucha();

        nombre = new JLabel(jugador.getName());
        dinero = new JLabel(Integer.toString(jugador.getDinero()));

        this.add(nombre);
        this.add(dinero);

        for (JButton JCarta : JMano) {
            if (jugador.getTipo() != Jugador.TipoJugador.Usuario) {
                JCarta.setSize(new Dimension(70, 80));
                JCarta.setMinimumSize(new Dimension(70, 80));
                JCarta.setPreferredSize(new Dimension(70, 80));
            } else {
                JCarta.setSize(new Dimension(100, 120));
                JCarta.setMinimumSize(new Dimension(100, 120));
                JCarta.setPreferredSize(new Dimension(100, 120));
                JCarta.addActionListener(listener);
            }
            this.add(JCarta);
        }
    }

    // #---------------------------------------------------------------------------
    // # MÉTODOS
    // #---------------------------------------------------------------------------

    public void mostrarDinero() {
        dinero.setText(jugador.getDinero().toString());
        revalidate();
        repaint();
    }

    public void descubrirCartas(List<Carta> mano) {
        for (int i = 0; i < JMano.size(); i++)
            JMano.get(i).setText(mano.get(i).numero + " " + mano.get(i).palo.toString().charAt(0));
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
     * 
     * @return
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
     * @return 
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
        } 
        else if (cartasSeleccionadas.indexOf(JCarta) != -1) {
            cartasSeleccionadas.remove(JCarta);
            JCarta.setBackground(null);
        }
        else {
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