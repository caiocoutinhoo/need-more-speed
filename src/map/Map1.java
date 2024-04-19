package map;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map1 extends MapDefault{
    Player player;
    List<Line> lines = new ArrayList<Line>();
    int N;
    double angulo = 0;

    public Map1(GamePanel gp, KeyHandler keyH, Player player){
        this.gp=gp;
        this.keyH=keyH;
        this.player = player;

        for (int i = 0; i < 1200; i++) {
            Line line = new Line();
            line.z = i*segL;
            if (i > 200 && i < 400) {
                line.curve = 5;
            }
            if (i > 399 && i < 500) {
                line.curve = -5;
            }
            if (i > 150 && angulo < 360 * 2){
                line.y = Math.sin(angulo++/180 * Math.PI) * 2000;
            }
            lines.add(line);
        }

        N = lines.size();


    }
    void desenharObjetos(Graphics2D g){

        // Desenhar céu
        g.setColor(new Color(0x4141EE));
        g.fillRect(0,0, 1280, 456);

        // Desenhar ruas

        int starPosition= playerPosition/segL;
        double x = 0, dx = 0;
        double maxY = 900;
        int camH = 1500 + (int) lines.get(starPosition).y;

        for (int n = starPosition; n < starPosition+300; n++) {

            Line l= lines.get(n%N);
            l.project(playerX - (int) x, camH, playerPosition);
            x += dx;
            dx += l.curve;

            if (l.Y > 0 && l.Y < maxY) {
                maxY = l.Y;

                Color grass= ((n/2)%2)==0? new Color(16,200,16):new Color(0,154,0);
                Color rumble= ((n/2)%2)==0? new Color(255,255,255):new Color(255,0,0);
                Color road= ((n/2)%2)==0? new Color(24, 24, 24):new Color(26, 26, 26);
                //Color road= Color.black;

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


        }




    }



    public void update(){
        if (keyH.upPressed){
            playerPosition += player.velocidade;
        }
        if (!keyH.upPressed){
            playerPosition += player.velocidade;
        }

        if (playerPosition > 120000 && playerPosition < 420000 ){
            //player.x -= 2;
        }
    }
    public void draw(Graphics2D g2){
        desenharObjetos(g2);
    }

}
