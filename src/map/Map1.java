package map;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map1 extends MapDefault{
    Player player;
    List<Line> lines = new ArrayList<Line>();
    int N;
    int pointX=1069,pointY=130;
    int voltaPercorrida=0;
    public Map1(GamePanel gp, KeyHandler keyH, Player player){
        this.gp=gp;
        this.keyH=keyH;
        this.player = player;
        getMap1Image();

        tamanhoDaPista = 3000;
        int voltas = 3;
        for (int i = 0; i < ( (tamanhoDaPista*voltas)+500 ); i++) {
            Line line = new Line();
            line.z = i*segL;
            for (int j = 0; j < voltas; j++) {
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
    public void getMap1Image(){
        try {
            miniMap = ImageIO.read(getClass().getResourceAsStream("/map1/miniMap-01.png"));
            point = ImageIO.read(getClass().getResourceAsStream("/map1/point.png"));
        } catch (IOException e) {
            e.printStackTrace();
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
            if ((player.aderencia * player.velocidade)>=5){
                player.imageX1 = 177;
                player.imageX2 = 233;
            }
        }
        if (keyH.rightPressed ){
            playerX += (int) (player.aderencia * player.velocidade);
            if ((player.aderencia * player.velocidade)>=5){
                player.imageX1 = 66;
                player.imageX2 = 123;
            }
        }
        if (!keyH.leftPressed&&!keyH.rightPressed){
            player.imageX1 = 124;
            player.imageX2 = 176;


        }

        curvasPlayer();
        barreirada();
        //System.out.println(playerX);
    }
    public void draw(Graphics2D g2){
        desenharObjetos(g2);

        g2.drawImage(miniMap,1050, 50, 130,130, null);
        pointInMap(g2);
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
        if ( (playerX < -4040 || playerX> 4040) && player.velocidade>120 ){
            player.velocidade -= 12;
        }
    }
    public void curvasPlayer(){
        int tes = voltaPercorrida * tamanhoDaPista;

        System.out.println(playerPosition/600);

        if (playerPosition/600 > tes+(300 ) && playerPosition/600 < tes+(350 )){
            playerX -= (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition/600 > tes+(350 ) && playerPosition/600 < tes+(450 )){
            playerX += (int) curvaInercia(player.velocidade, 7);
        }
        if (playerPosition/600 > tes+(520 ) && playerPosition/600 < tes+(570 )){
            //line.curve = 15;
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }
        if (playerPosition/600 > tes+(700 ) && playerPosition/600 < tes+(750 )){
            //line.curve = 15;
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }
        if (playerPosition/600 > tes+(850 ) && playerPosition/600 < tes+(1000 )){
            //line.curve = 0.7;
            playerX -= (int) curvaInercia(player.velocidade, 1);

        }
        if (playerPosition/600 > tes+(1070 ) && playerPosition/600 < tes+(1500 )){
           // line.curve = -1.5;
            playerX += (int) curvaInercia(player.velocidade, 7);

        }
        if (playerPosition/600 > tes+(1550 ) && playerPosition/600 < tes+(1700 )){
           // line.curve = 12;
            playerX -= (int) curvaInercia(player.velocidade, 12);

        }
        if (playerPosition/600 > tes+(1900 ) && playerPosition/600 < tes+(2000 ) ){
           // line.curve = 15;
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }
        if (playerPosition/600 > tes+(2150 ) && playerPosition/600 < tes+(2170 )){
           // line.curve = -20;
            playerX += (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition/600 > tes+(2170 ) && playerPosition/600 < tes+(2190 )){
           // line.curve = 20;
            playerX -= (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition/600 > tes+(2195 ) && playerPosition/600 < tes+(2215 )){
           // line.curve = 20;
            playerX -= (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition/600 > tes+(2215 ) && playerPosition/600 < tes+(2235 )){
           // line.curve = -20;
            playerX += (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition/600 > tes+(2450) && playerPosition/600 < tes+(3000)){
           // line.curve = 3;
            playerX -= (int) curvaInercia(player.velocidade, 3);

        }

        if (playerPosition/600 >= tes+(3000) && playerPosition/600 <= tes+(3010)){
            voltaPercorrida++;

            System.out.println("--------------------------------------------------------");
        }

    }
    public double curvaInercia(double velocidade, int curva){
        return ((curva * 6) * velocidade) /350;
    }
    public void pointInMap(Graphics2D g2){
        int teste = voltaPercorrida * tamanhoDaPista;

        if (playerPosition/600 == 150+teste){
            pointY -= 14;
        }
        if (playerPosition/600 == 350+teste){
            pointY -=5;
            pointX += 5;
        }



        g2.drawImage(point,pointX, pointY, 12,12, null);
    }



}


