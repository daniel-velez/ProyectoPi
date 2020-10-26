package classicPoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JManoPanel extends JPanel {

    private List <JButton> JMano;
    private Jugador jugador;
    private JLabel nombre, dinero;

    private Boolean ordenarCartas;
    private List<JButton> cartasSeleccionadas;

    private Escucha listener;

    public JManoPanel(Jugador jugador) {
        this.jugador = jugador;
        this.ordenarCartas = true;
        this.cartasSeleccionadas = new ArrayList<JButton>();

        JMano = new ArrayList <JButton>();

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
            if (jugador.getTipo() != TipoJugador.Usuario) {
                JCarta.setSize(new Dimension(60, 80));
                JCarta.setMinimumSize(new Dimension(60, 80));
                JCarta.setPreferredSize(new Dimension(60, 80));
            } else {
                JCarta.setSize(new Dimension(100, 120));
                JCarta.setMinimumSize(new Dimension(100, 120));
                JCarta.setPreferredSize(new Dimension(100, 120));
            }
            JCarta.addActionListener(listener);
            this.add(JCarta);
        }
    }

    public void mostrarDinero() {
        dinero.setText(jugador.getDinero().toString());
        revalidate();
        repaint();  
    }

    public void descubrirCartas() {

        List<Carta> mano = jugador.getMano();

        for (int i = 0; i<JMano.size(); i++) 
            JMano.get(i).setText(mano.get(i).numero + " " + mano.get(i).palo.toString().charAt(0));
    }

    public void taparCartas() {

    }

    /**
     * 
     * @return
     */
    public List<Integer> getCartasSeleccionadas() {
        List<Integer> cartas = new ArrayList<Integer>();
        for (JButton JCarta : cartasSeleccionadas)
            cartas.add(JMano.indexOf(JCarta));
        return cartas;
    }

    /**
     * 
     * @return
     */
    public JLabel getUserName() {
        return nombre;
    }

    /**
     * 
     */
    public JLabel getUserMoney() {
        return dinero;
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
                descubrirCartas();
                /*
                int index = JMano.indexOf(cartasSeleccionadas.get(0));
                JButton boton = JCarta;
                List <Carta> mano = jugador.getMano();

                JMano.get(JMano.indexOf(cartasSeleccionadas.get(0))).setText(boton.getText());
                JCarta.setText(mano.get(index).numero + " " + mano.get(index).palo.toString().charAt(0));
                */
                cartasSeleccionadas.clear();
            }
        } else
            cartasSeleccionadas.add(JCarta);
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
            for (JButton JCarta : JMano)
                if (event.getSource() == JCarta)
                    onJCartaClick(JCarta);
        }
    }
}