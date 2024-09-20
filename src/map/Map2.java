package map;

import entity.enemie.EnemiesService;
import entity.player.Player;
import main.GamePanel;
import main.KeyHandler;

import java.util.ArrayList;
import java.util.List;

public class Map2 extends MapDefault {
    public static final int pointsToWin = 10;
    Player player;
    int contadorFrameBack = 0;
    List<Line> lines = new ArrayList<Line>();
    int N;
    int pointX, pointY, backX = -60, backY = -34;
    EnemiesService enemiesService;
    int voltas = 1;

    public Map2(GamePanel gp, KeyHandler keyH, Player player) {
        this.gp = gp;
        this.keyH = keyH;
        this.player = player;
        enemiesService = new EnemiesService(gp, player, this);

        tamanhoDaPista = 3000;
        for (int i = 0; i < ((tamanhoDaPista * voltas) + 500); i++) {
            Line line = new Line();
            line.z = i * segL;
            for (int j = 0; j < voltas; j++) {
                percurssoDoMapa(i, tamanhoDaPista, line, j);
            }
            lines.add(line);
        }
        N = lines.size();
    }

    public void percurssoDoMapa(int i, int tamanho, Line line, int v) {
        v = v * tamanho;
        if (i > 300 + v && i < 350 + v) {
            line.curve = 15;
        }
        if (i > 350 + v && i < 450 + v) {
            line.curve = -7;
        }
        if (i > 520 + v && i < 570 + v) {
            line.curve = 15;
        }
        if (i > 700 + v && i < 750 + v) {
            line.curve = 15;
        }
        if (i > 850 + v && i < 1000 + v) {
            line.curve = 0.7;
        }
        if (i > 1070 + v && i < 1500 + v) {
            line.curve = -1.5;
        }
        if (i > 1550 + v && i < 1700 + v) {
            line.curve = 12;
        }
        if (i > 1900 + v && i < 2000 + v) {
            line.curve = 15;
        }
        if (i > 2150 + v && i < 2170 + v) {
            line.curve = -20;
        }
        if (i > 2170 + v && i < 2190 + v) {
            line.curve = 20;
        }
        if (i > 2195 + v && i < 2215 + v) {
            line.curve = 20;
        }
        if (i > 2215 + v && i < 2235 + v) {
            line.curve = -20;
        }
        if (i > 2450 + v && i < 3000 + v) {
            line.curve = 3;
        }

    }

}
