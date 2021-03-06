/*
 * Programacion Interactiva
 * Mini proyecto 4: Juego de Blackjack.
 */

package clientebj;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase estatica que almacena los recursos globales
 */
public class Resources {
    public static Font HelveticaNeue;
    public static Font Univers;
    public static Font Roboto;
    public static Font RobotoBold;
    private static Object RObj;

    /**
     * Cargar las fuentes
     * @param obj
     */
    public static void loadFonts(Object obj) {
        RObj = obj;
        try {
            HelveticaNeue = Font.createFont(Font.TRUETYPE_FONT,
                    obj.getClass().getResourceAsStream("/fonts/HelveticaNeueBoldCond.ttf"));
            Univers = Font.createFont(Font.TRUETYPE_FONT,
                    obj.getClass().getResourceAsStream("/fonts/UniversLTStdCn_0.ttf"));
            Roboto = Font.createFont(Font.TRUETYPE_FONT,
                    obj.getClass().getResourceAsStream("/fonts/Roboto-Regular.ttf"));
            RobotoBold = Font.createFont(Font.TRUETYPE_FONT,
                    obj.getClass().getResourceAsStream("/fonts/Roboto-Bold.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name El nombre de la imagen.
     * @return Un ImageIcon con una imagen cargada.
     */
    public static ImageIcon getImage(String name) {
        return new ImageIcon(RObj.getClass().getResource("/images/" + name));
    }

    /**
     * @param name El nombre de la imagen.
     * @param size Dimension de la subimagen.
     * @return Un ImageIcon con una imagen cargada.
     */
    public static ImageIcon getImage(String name, Dimension size) {
        BufferedImage img = null;
        Image dimg = null;
        try {
            img = ImageIO.read(RObj.getClass().getResource("/images/" + name));
            dimg = img.getSubimage(0, 0, (int) size.getWidth(), (int) size.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(dimg);
    }

    /**
     * Establece el dimension de un JPanel de acuerdo a la dimension indicada.
     * @param obj JPanel al que se le cambia la dimension.
     * @param size nueva dimension.
     */
    public static void setJPanelSize(JPanel obj, Dimension size) {
        obj.setPreferredSize(size);
        obj.setMinimumSize(size);
        obj.setMaximumSize(size);
    }

    /**
     * Establee la dimension de un JLabel de acuerdo a la dimension indicada.
     * @param obj JLabel al que se le cambia la dimension.
     * @param size nueva dimension.
     */
    public static void setJLabelSize(JLabel obj, Dimension size) {
        obj.setPreferredSize(size);
        obj.setMinimumSize(size);
        obj.setMaximumSize(size);
    }
}
