/*
 * Programacion Interactiva
 * Mini proyecto 4: Juego de Blackjack.
 */
package clientebj;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * Clase encargada de solicitar el nombre a un jugador por medio de un JPanel.
 */
public class JLoginPanel extends JPanel {

    private JLabel encabezado, indicacion, label;
    private JTextField nombre;
    private JButton ingresar;
    private Dimension panelSize;
    private ActionListener listener;

    /**
     * Instantiates a new JLoginPanel.
     * @param nombre
     * @param listener
     */
    public JLoginPanel(JTextField nombre, ActionListener listener) {
        panelSize = new Dimension(400, 150);
        Resources.setJPanelSize(this, panelSize);
        this.nombre = nombre;
        this.listener = listener;
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.setBackground(Color.GRAY);
        initGUI();
    }

    /**
     * Inits the GUI.
     */
    private void initGUI() {
        encabezado = new JLabel("Bienvenido a Black Jack", SwingConstants.CENTER);
        encabezado.setFont(Resources.HelveticaNeue.deriveFont(24f));
        encabezado.setForeground(Color.WHITE);
        Resources.setJLabelSize(encabezado, new Dimension(panelSize.width, 30));

        indicacion = new JLabel("Registre su nombre para ingresar", SwingConstants.CENTER);
        indicacion.setFont(Resources.Univers.deriveFont(18f));
        indicacion.setForeground(Color.WHITE);
        Resources.setJLabelSize(indicacion, new Dimension(panelSize.width, 20));

        label = new JLabel("Nombre ");
        label.setFont(Resources.RobotoBold.deriveFont(14f));
        label.setForeground(Color.WHITE);

        ingresar = new JButton();
        ingresar.setContentAreaFilled(false);
        ingresar.setFocusPainted(false);
        ingresar.setBorder(null);
        ingresar.setIcon(Resources.getImage("ing.png"));
        ingresar.setRolloverIcon(Resources.getImage("ing-roll.png"));
        ingresar.addActionListener(listener);

        add(Box.createRigidArea(new Dimension(panelSize.width, 10)));
        add(encabezado);
        add(indicacion);
        add(Box.createRigidArea(new Dimension(panelSize.width, 10)));
        add(label);
        add(nombre);
        add(ingresar);
    }

}
