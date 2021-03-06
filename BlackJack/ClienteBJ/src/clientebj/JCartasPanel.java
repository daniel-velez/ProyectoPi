/*
 * Programacion Interactiva
 * Mini proyecto 4: Juego de Blackjack.
 */
package clientebj;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comunes.Carta;
import comunes.estadoJugador;

/**
 * Clase que administra el panel de las cartas.
 */
public class JCartasPanel extends JPanel {
    private final int cartaWidth = 70;
    private final int cartaHeight = 106;
    private GridBagConstraints gbc;
    private JLabel mensaje;
    private List<Carta> mano;
    private float alpha;

    /**
     * Instantiates a new JCartasPanel.
     * @param size dimension del panel
     */
    public JCartasPanel(Dimension size) {
        Resources.setJPanelSize(this, size);
        this.setBackground(new Color(0, 118, 58));
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.setLayout(new GridBagLayout());

        this.gbc = new GridBagConstraints();
        this.mano = new ArrayList<Carta>();
        this.alpha = 1f;

        this.mensaje = new JLabel();
        mensaje.setFont(Resources.HelveticaNeue.deriveFont(24f));
    }

    /**
     * Dibujar el JPanel
     * @param Graphics g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
        for (int i = 0; i < mano.size(); i++)
            g2d.drawImage(Resources.getImage(mano.get(i).toString()).getImage(), getCartaX(i + 1), 7, this);
        g2d.dispose();
    }

    /**
     * Determinar la posicion x de la carta dentro del JPanel
     * @param index indice de la carta en el mazo
     * @return posicion x
     */
    private int getCartaX(int index) {
        int padding = 7;
        int length = mano.size();

        int spaceBetween = (length > 1) ? ((this.getWidth() - 2 * padding) - (length * cartaWidth)) / (length - 1) : 0;
        spaceBetween = (spaceBetween > 5) ? 5 : spaceBetween;

        int extremos = (-(length * cartaWidth) - (length - 1) * spaceBetween + (this.getWidth() - 2 * padding)) / 2;

        int xPosition = padding + extremos + (index - 1) * (cartaWidth + spaceBetween);

        return xPosition;
    }

    /**
     * Guarda la mano recibida y repinta el JPanel
     * @param mano mano a mostrar
     */
    public void mostrarCartas(List<Carta> mano) {
        this.mano = mano;
        repaint();
    }

    /**
     * Muestra un mensaje o estado del jugador en el JPanel
     * @param msj mensaje
     * @param estado estado del jugador
     */
    public void mostrarMensaje(String msj, estadoJugador estado) {
        Color stateColor = Color.BLACK;

        if (estado == estadoJugador.ganador)
            stateColor = new Color(158, 198, 0);

        if (estado == estadoJugador.perdedor)
            stateColor = new Color(198, 0, 0);

        if (estado == estadoJugador.empate)
            stateColor = new Color(255, 166, 0);

        if (estado == estadoJugador.volo) {
            mensaje.setOpaque(false);
            mensaje.setForeground(Color.WHITE);
            mensaje.setBorder(null);
            this.setBackground(Color.BLACK);
            alpha = 0;
        } else {
            mensaje.setOpaque(true);
            mensaje.setForeground(Color.WHITE);
            mensaje.setBackground(stateColor);
            mensaje.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(stateColor, 4),
                    BorderFactory.createEmptyBorder(4, 12, 2, 12)));
        }

        mensaje.setText(msj);
        add(mensaje, gbc);
        repaint();

        if (estado == estadoJugador.volo) {
            Thread animation = new Thread(new VoloAnimation());
            animation.start();
        }
    }

    /**
     * Resetea el JPanel a su estado inicial
     */
    public void reset() {
        this.setBackground(new Color(0, 118, 58));
        remove(mensaje);
        mano.clear();
        repaint();
    }

    /**
     * Clase que implementa el hilo para la animacion de "el jugador ha volado"
     */
    private class VoloAnimation implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);

                int time = 4500;
                for (int i = 255; i >= 0; i--) {
                    mensaje.setForeground(new Color(255, 255, 255, i));
                    alpha = 1 - ((float) i) / 255;
                    repaint();
                    Thread.sleep(time / 255);
                }
                alpha = 1f;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
