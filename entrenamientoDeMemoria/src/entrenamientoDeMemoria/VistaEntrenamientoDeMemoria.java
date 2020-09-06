package entrenamientoDeMemoria;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VistaEntrenamientoDeMemoria extends JFrame {
    // atributos
    private Escucha listener;
    private ControlEntrenamientoDeMemoria control;
    private JLabel[] cartasLabel;

    private ImageIcon[] imagenes;

    // metodos
    VistaEntrenamientoDeMemoria() {
        initGUI(); // graphic components are created here.

        this.setTitle("Entrenamiento de memoria");
        // this.setSize(800, 500);
        // this.pack();
        ; // modify the size of the JFrame.
        this.setLocationRelativeTo(null); // null -> muestra la ventana centrada
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // al cerrar la ventana se termina la ejecución del
                                                             // programa
        this.setVisible(true);
    }

    private void initGUI() {
        this.getContentPane().setLayout(new FlowLayout());

        // crear objeto escucha y objeto control
        Escucha listener = new Escucha();
        control = new ControlEntrenamientoDeMemoria();

        cartasLabel = new JLabel[12];
        imagenes = new ImageIcon[12];
        for (int i = 0; i < 12; i++) {
            cartasLabel[i] = new JLabel();
            cartasLabel[i].setSize(new Dimension(20, 20));
            cartasLabel[i].addMouseListener(listener);
        }

        mostrarCartas();

    }

    private void mostrarCartas() {
        ArrayList<Integer> cartas = control.revolverCartas();

        this.setSize(180 * cartas.size() / 2, 360);

        for (int i = 0; i < cartas.size(); i++) {
            cartasLabel[i].setIcon(
                    new ImageIcon(new ImageIcon(this.getClass().getResource("/images/" + cartas.get(i) + ".png"))
                            .getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            cartasLabel[i].setMaximumSize(new Dimension(20, 20));

            add(cartasLabel[i]);
        }

    }

    private class Escucha implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent event) {

            // identifico la fuente del evento (en este caso "lanzar")
            /*
             * if (event.getSource() == lanzar) {
             * 
             * }
             */

        }

        @Override
        public void mouseClicked(MouseEvent e) {

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
