package entity.enemie;

import entity.player.Player;
import main.GamePanel;
import map.MapDefault;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemiesService {
    private GamePanel gp;
    private MapDefault map;
    private int lastEnemySpawnPosition = 0;
    private int LEFT = 200;
    private int RIGHT = 1080;
    private Player player;
    List<Enemie> enemies = new ArrayList<>();

    public EnemiesService(GamePanel gp, Player player, MapDefault map) {
        this.gp = gp;
        this.player = player;
        this.map = map;
    }

    public double randomGenerator() {
        double randomValue = Math.random();
        return -1 + (randomValue * 1.8);
    }

    public void updateCreateEnemies(int positionPlayer) {
        if (positionPlayer - lastEnemySpawnPosition >= 190000) {
            createEnemy(positionPlayer, this.map);
            lastEnemySpawnPosition = positionPlayer;
        }
    }

    private void createEnemy(int positionPlayer, MapDefault map) {
        Enemie enemie = new Enemie(gp, 0, 0, 50, player, map, 400, positionPlayer + 50000, randomGenerator());
        enemies.add(0,enemie);
    }

    public void drawEnemies(Graphics2D g2) {
        for (Enemie enemie : enemies) {
            //  enemie.draw(g2);
        }
    }

    public void updateEnemies(int positionPlayer) {
        updateCreateEnemies(positionPlayer);
        for (Enemie enemie : enemies) {
            enemie.update();
            enemie.checkOvertaken(map);
        }
    }

    public void createEnemies(int positionPlayer, int segL, List<MapDefault.Line> lines, Graphics2D g2) {

        for (Enemie enemie : enemies) {
            if (enemie.enemiePosition > positionPlayer) {
                int npcIndex = (int) ((enemie.enemiePosition / segL) % lines.size());
                MapDefault.Line npcLine = lines.get(npcIndex);

                double npcX = npcLine.X + (npcLine.W * enemie.xInMap);

                double distance = enemie.enemiePosition - positionPlayer;


                int scale = (int) ((enemie.enemiePosition - positionPlayer) / 150);
                int npcY = (int) (npcLine.Y - (100 - (double) scale /2));

                System.out.println(scale);
                if (200 - scale > 0) {
                    g2.drawImage(enemie.carro, (int) npcX, npcY, 190 - scale, 95 - scale / 2, null);
                }
            }
        }
    }
}

