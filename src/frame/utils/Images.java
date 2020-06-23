package frame.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;

/**
 * A utils class used to store the textures and load them
 *
 * @author Q.Cosnier
 */
public class Images {

    public static BufferedImage LOGOIMAGE;
    public static BufferedImage DRAGONIMAGE;
    public static BufferedImage DRAGONFLIPPED;
    public static BufferedImage BOARD;
    public static BufferedImage BLACKPAWN;
    public static BufferedImage WHITEPAWN;
    public static BufferedImage ZENPAWN;

    /**
     * Create the textures of the game
     */
    public static void create() {
        try {
            LOGOIMAGE = ImageIO.read(Images.class.getResourceAsStream("/logo.png"));
            DRAGONIMAGE = ImageIO.read(Images.class.getResourceAsStream("/dragon.png"));
            BOARD = ImageIO.read(Images.class.getResourceAsStream("/board.png"));
            BLACKPAWN = ImageIO.read(Images.class.getResourceAsStream("/black.png"));
            WHITEPAWN = ImageIO.read(Images.class.getResourceAsStream("/white.png"));
            ZENPAWN = ImageIO.read(Images.class.getResourceAsStream("/zen.png"));
            DRAGONFLIPPED = new BufferedImage(DRAGONIMAGE.getWidth(), DRAGONIMAGE.getHeight(), DRAGONIMAGE.getType());
            WritableRaster dataFlipped = DRAGONFLIPPED.getRaster();
            Raster data = DRAGONIMAGE.getData();
            for (int i = 0; i < data.getWidth(); i++) {
                for (int j = 0; j < data.getHeight(); j++) {
                    dataFlipped.setPixel(i, j, data.getPixel(data.getWidth() - i - 1, j, (int[]) null));
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR READING IMAGES");
        }
    }
}
