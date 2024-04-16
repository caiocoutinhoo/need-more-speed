package map;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map1 extends MapDefault{

    List<Line> lines = new ArrayList<Line>();
    int N;


    public Map1(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        for (int i = 0; i < 1600; i++) {
            Line line = new Line();
            line.z = i*segL;
            lines.add(line);
        }

        N = lines.size();

    }
    void desenharObjetos(Graphics2D g){
        // Desenhar ruas

        int starPosition= playerPosition/segL;

        for (int n = starPosition; n < starPosition+300; n++) {
            Line l= lines.get(n%N);
            l.project(0, 1500, playerPosition);
            Color grass= ((n/2)%2)==0? new Color(16,200,16):new Color(0,154,0);
            Color rumble= ((n/2)%2)==0? new Color(255,255,255):new Color(255,0,0);
            //Color road= ((n/2)%2)==0? new Color(0, 0, 0):new Color(31, 31, 31);
            Color road= Color.black;

            Line p = null;

            if (n == 0) {
                p = l;
            }else {
                p = lines.get((n - 1) % N);
            }

            desenharQuadrado(g, grass, 0, (int) p.Y, gp.D_W, 0, (int) l.Y, gp.D_W);
            desenharQuadrado(g, rumble, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y,
                    (int) (l.W * 1.4));
            desenharQuadrado(g, road, (int) p.X, (int) p.Y, (int) (p.W * 1.2), (int) l.X, (int) l.Y, (int) (l.W* 1.2));

        }


        // Desenhar céu
        g.setColor(new Color(0x4141EE));
        g.fillRect(0,0, 1280, 456);

    }



    public void update(){
        if (keyH.upPressed)
            playerPosition += 200;
    }
    public void draw(Graphics2D g2){
        desenharObjetos(g2);
    }

}
