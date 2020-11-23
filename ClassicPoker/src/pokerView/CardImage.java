package pokerView;

import classicPoker.*;
import classicPoker.Carta.Palos;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

public class CardImage {
    private static BufferedImage cardsImage;
    private static final int top = 23;
    private static final int left = 10;

    private static final int cardWidth = 103;
    private static final int cardHeight = 157;

    private static final int verticalSpace = 8;
    private static final int horizontalSpace = 11;

    public static void loadImage(URL src) {
        try {
            cardsImage = ImageIO.read(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon get(Carta carta, Dimension reSize) {
        int x = cardWidth + horizontalSpace, y = cardHeight + verticalSpace;

        if (carta.palo == Palos.treboles)
            y = 0 * y;
        if (carta.palo == Palos.corazones)
            y = 1 * y;
        if (carta.palo == Palos.picas)
            y = 2 * y;
        if (carta.palo == Palos.diamantes)
            y = 3 * y;

        x = x * (carta.numero - 1);
        if (carta.numero == 14)
            x = 0;

        return new ImageIcon(resize(cardsImage.getSubimage(left + x, top + y, cardWidth, cardHeight),
                (int) reSize.getWidth(), (int) reSize.getHeight()));
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}