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
    double[] angulo;

    public Map1(GamePanel gp, KeyHandler keyH, Player player){
        this.gp=gp;
        this.keyH=keyH;
        this.player = player;
        tamanhoDaPista = 3000;
        angulo = new double[10];
        for (int i = 0; i < 10; i++) {
            angulo[i] = 0;
        }

        int voltas = 3;
        for (int i = 0; i < (tamanhoDaPista*voltas); i++) {
            Line line = new Line();
            line.z = i*segL;
            for (int j = 0; j < 3; j++) {
                percurssoDoMapa(i,tamanhoDaPista, line, player, j);
                for (int k = 0; k < 10; k++) {
                    angulo[k] = 0;
                }
            }
/*                if (i > 200 + (1200*(j-1)) && i < 400) {
                    line.curve = 5;
                }
                if (i > 399 + (1200*(j-1)) && i < 499) {
                    line.curve = -20;
                }
                if (i > 150 + (1200*(j-1)) && angulo <= 360 * 0.5){
                    line.y = -Math.sin(angulo++/180 * Math.PI) * 10000;
                }
                if (i > 399 + (1200*(j-1)) && angulo2 <= 360 * 0.5){
                    line.y = Math.sin(angulo2++/180 * Math.PI) * 10000;
                }
  */              lines.add(line);
            }
            //angulo=angulo2=0;
      //  }


        N = lines.size();


    }
    void desenharObjetos(Graphics2D g){

        // Desenhar céu
        g.setColor(new Color(0x4141EE));
        g.fillRect(0,0, 1280, 700);

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

        if (playerPosition > 300*600 && playerPosition < 350*600){
            player.x -= 15/2;
        }

    }
    public void draw(Graphics2D g2){
        desenharObjetos(g2);
    }
    public void percurssoDoMapa(int i, int tamanho, Line line, Player p, int v){
        v = v * tamanho;
        if (i > 300+v && i < 350+v){
            line.curve = 15;
        }
        if (i > 350+v && i < 450+v){
            line.curve = -7;
        }
        if (i > 520+v && i < 570+v){
            line.curve = 15;
        }
        if (i > 700+v && i < 750+v){
            line.curve = 15;
        }
        if (i > 850+v && i < 1000+v){
            line.curve = 0.7;
        }
        if (i > 1070+v && i < 1500+v){
            line.curve = -1.5;
        }
        if (i > 1550+v && i < 1700+v){
            line.curve = 12;
        }
        if (i > 1900+v && i < 2000+v){
            line.curve = 15;
        }
        if (i > 2150+v && i < 2170+v){
            line.curve = -20;
        }
        if (i > 2170+v && i < 2190+v){
            line.curve = 20;
        }
        if (i > 2195+v && i < 2215+v){
            line.curve = 20;
        }
        if (i > 2215+v && i < 2235+v){
            line.curve = -20;
        }



        if (i > 2450+v && i < 3000+v){
            line.curve = 3;
        }
        if (i > 50+v && angulo[0] <= 360 * 0.5){
            line.y = Math.sin(angulo[0]++/180 * Math.PI) * 3000;
        }
        if (i > 350+v && angulo[1] <= 360 * 1.5){
            line.y = -Math.sin(angulo[1]++/180 * Math.PI) * 7000;
        }
        if (i > 1070+v && angulo[3] <= 360 * 1.5){
            line.y = Math.sin(angulo[3]++/180 * Math.PI) * 15000;
        }



    }
}


