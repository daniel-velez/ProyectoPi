package entrenamientoDeMemoria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class VistaEntrenamientoDeMemoria extends JFrame {
    // atributos
    private Escucha listener;
    private ControlEntrenamientoDeMemoria control;
    private JLabel[] cartasLabel;
    private Timer timerOcultarCartas;
    private ArrayList<Integer> cartas;
    private JPanel zonaJuego, zonaIndicaciones;

    // metodos
    VistaEntrenamientoDeMemoria() {
        initGUI(); // graphic components are created here.

        this.setTitle("Entrenamiento de memoria");
        this.setSize(800, 650);
        // this.pack();
        ; // modify the size of the JFrame.
        this.setLocationRelativeTo(null); // null -> muestra la ventana centrada
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // al cerrar la ventana se termina la ejecución del
                                                             // programa
        this.setVisible(true);
    }

    private void initGUI() {
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // crear objeto escucha y objeto control
        listener = new Escucha();
        control = new ControlEntrenamientoDeMemoria();

        // #---------------------------------------------------------------------------

        zonaJuego = new JPanel();
        zonaJuego.setPreferredSize(new Dimension(700, 650));
        zonaJuego.setBackground(new Color(240, 240, 240));
        zonaJuego.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(zonaJuego);

        zonaIndicaciones = new JPanel();
        zonaIndicaciones.setPreferredSize(new Dimension(84, 650));// 234
        zonaIndicaciones.setBackground(new Color(255, 255, 0));
        zonaIndicaciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(zonaIndicaciones);

        // #---------------------------------------------------------------------------

        zonaJuego.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // definir los tamaños definitos de las imagenes
        // poner el caso 4 y caso 6, 9 con imagenes más grandes
        // encargarnos de las margenes

        cartasLabel = new JLabel[12];
        // mostrarAbstractCartas(4, 2, java.util.Collections.emptyList());
        // mostrarAbstractCartas(6, 3, java.util.Collections.emptyList());
        // mostrarAbstractCartas(9, 3, java.util.Arrays.asList(new Integer[] { 4 }));
        // mostrarAbstractCartas(12, 4, java.util.Arrays.asList(new Integer[] { 5, 6
        // }));
        mostrarAbstractCartas(12, 4, java.util.Collections.emptyList());

        // #---------------------------------------------------------------------------

        // timerOcultarCartas = new Timer(control.getTiempoDeEspera(), listener);

        // mostrarCartas();
    }

    private void mostrarAbstractCartas(int numeroDeCeldas, int columnas, List<Integer> restricciones) {
        GridBagConstraints constraints = new GridBagConstraints();
        for (int i = 0; i < numeroDeCeldas; i++) {
            if (restricciones.indexOf(i) != -1)
                continue;

            constraints.gridx = i % columnas;
            constraints.gridy = (int) Math.floor(i / columnas);
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            cartasLabel[i] = new JLabel();
            cartasLabel[i].setIcon(new ImageIcon(this.getClass().getResource("/images/2.png")));
            zonaJuego.add(cartasLabel[i], constraints);
        }
    }

    private void mostrarCartas() {
        cartas = control.revolverCartas();

        // this.setSize(180 * cartas.size() / 2, 360);

        for (int i = 0; i < cartas.size(); i++) {
            cartasLabel[i].removeMouseListener(listener);
            cartasLabel[i].setIcon(
                    new ImageIcon(new ImageIcon(this.getClass().getResource("/images/" + cartas.get(i) + ".png"))
                            .getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            zonaJuego.add(cartasLabel[i]);

        }
        // add(zonaJuego);
        timerOcultarCartas.setDelay(control.getTiempoDeEspera());
        timerOcultarCartas.start();
    }

    private void ocultarCartas() {
        for (int i = 0; i < cartas.size(); i++) {
            cartasLabel[i].setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/1.png")).getImage()
                    .getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            cartasLabel[i].addMouseListener(listener);
        }
        timerOcultarCartas.stop();
    }

    private void determinarJuego(int carta) {
        if (control.determinarRonda(carta)) {
            mostrarCartas();
        } else {
            mostrarCartas();
        }
    }

    private class Escucha implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == timerOcultarCartas)
                ocultarCartas();

        }

        @Override
        public void mouseClicked(MouseEvent event) {
            for (int i = 0; i < cartas.size(); i++)
                if (event.getSource() == cartasLabel[i]) {
                    determinarJuego(i);
                    break;
                }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

}
