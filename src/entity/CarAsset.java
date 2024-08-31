package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CarAsset {
    BufferedImage image;
    public CarAsset(String url) {
        try{
            image = ImageIO.read(getClass().getResourceAsStream(url));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
