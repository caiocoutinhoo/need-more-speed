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
    public Map1(GamePanel gp, KeyHandler keyH, Player player){
        this.gp=gp;
        this.keyH=keyH;
        this.player = player;
        tamanhoDaPista = 3000;
        int voltas = 3;
        for (int i = 0; i < ( (tamanhoDaPista*voltas)+500 ); i++) {
            Line line = new Line();
            line.z = i*segL;
            for (int j = 0; j < 3; j++) {
                percurssoDoMapa(i,tamanhoDaPista, line, j);
            }
            lines.add(line);
            }
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
                Color road= ((n/2)%2)==0? new Color(38, 38, 38):new Color(26, 26, 26);
                Color midel =  ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(0x131313);

                Line p = null;

                if (n == 0) {
                    p = l;
                }else {
                    p = lines.get((n - 1) % N);
                }

                desenharQuadrado(g, grass, 0, (int) p.Y, gp.D_W, 0, (int) l.Y, gp.D_W);
                desenharQuadrado(g, rumble, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y, (int) (l.W * 1.4));
                desenharQuadrado(g, road, (int) p.X, (int) p.Y, (int) (p.W * 1.2), (int) l.X, (int) l.Y, (int) (l.W* 1.2));
                desenharQuadrado(g, midel, (int) p.X, (int) p.Y, (int) (p.W * 0.03), (int) l.X, (int) l.Y, (int) (l.W* 0.03));
            }
        }
    }

    public void update(){
        if (keyH.upPressed){
            playerPosition += (int) player.velocidade;
        }
        if (!keyH.upPressed){
            playerPosition += (int) player.velocidade;
        }
        if (keyH.leftPressed ){
            playerX -= (int) (player.aderencia * player.velocidade);
        }
        if (keyH.rightPressed ){
            playerX += (int) (player.aderencia * player.velocidade);
        }
        int volta=1;
        curvasPlayer(volta);

        barreirada();
        //System.out.println(playerX);
    }
    public void draw(Graphics2D g2){
        desenharObjetos(g2);
    }
    public void percurssoDoMapa(int i, int tamanho, Line line, int v){
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

    }
    public void barreirada(){
        if ( (playerX < -3040 || playerX> 3040) && player.velocidade>130 ){
            player.velocidade -= 8;
        }
    }
    public void curvasPlayer(int volta){

        if (playerPosition > volta*300*600 && playerPosition < volta*350*600){
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }if (playerPosition > 350*600*volta && playerPosition < 450*volta*600){
            playerX += (int) curvaInercia(player.velocidade, 7);

        }
        if (playerPosition > 520*600*volta && playerPosition < 570*600*volta){
            //line.curve = 15;
        }
        if (playerPosition > 700*600*volta && playerPosition < 750*600*volta){
            //line.curve = 15;
        }
        if (playerPosition > 850*600*volta && playerPosition < 1000*600*volta){
            //line.curve = 0.7;
        }
        if (playerPosition > 1070*600*volta && playerPosition < 1500*600*volta){
           // line.curve = -1.5;
        }
        if (playerPosition > 1550*volta*600 && playerPosition < 1700*volta*600){
           // line.curve = 12;
        }
        if (playerPosition > 1900*600*volta && playerPosition < 2000*600*volta){
           // line.curve = 15;
        }
        if (playerPosition > 2150*600*volta && playerPosition < 2170*600*volta){
           // line.curve = -20;
        }
        if (playerPosition > 2170*600*volta && playerPosition < 2190*600*volta){
           // line.curve = 20;
        }
        if (playerPosition > 2195*600*volta && playerPosition < 2215*600*volta){
           // line.curve = 20;
        }
        if (playerPosition > 2215*600*volta && playerPosition < 2235*600*volta){
           // line.curve = -20;
        }
        if (playerPosition > 2450*600*volta && playerPosition < 3000*600*volta){
           // line.curve = 3;
        }








        if (playerPosition <= volta*3000*600){
            volta= volta+1;
            System.out.println(volta);
        }
       // System.out.println(volta);


    }
    public double curvaInercia(double velocidade, int curva){
        return ((curva * 3.5) * velocidade) /200;
    }

}


