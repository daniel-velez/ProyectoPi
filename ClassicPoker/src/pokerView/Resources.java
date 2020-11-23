package pokerView;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Resources {
    public static Font Casino;
    public static Font Lounge;

    public static void loadLounge(InputStream src) {
        try {
            Lounge = Font.createFont(Font.TRUETYPE_FONT, src);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCasino(InputStream src) {
        try {
            Casino = Font.createFont(Font.TRUETYPE_FONT, src);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
