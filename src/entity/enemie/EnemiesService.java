package entity.enemie;

import entity.player.Player;
import main.GamePanel;
import map.MapDefault;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public void updateCreateEnemies(int positionPlayer) {
        if (positionPlayer - lastEnemySpawnPosition >= 190000) {
            createEnemy(positionPlayer, this.map);
            lastEnemySpawnPosition = positionPlayer;
        }
    }

    private void createEnemy(int positionPlayer, MapDefault map) {
        Enemie enemieLeft = new Enemie(gp, 0, 0, 20, player, map, 300, positionPlayer + 50000);
        Enemie enemieRight = new Enemie(gp, 0, 0, 2, player, map, 400, positionPlayer + 110000);
        //enemies.add(enemieRight);
        enemies.add(enemieLeft);
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
        }
    }

    public void testeNPC(int positionPlayer, int segL, List<MapDefault.Line> lines, Graphics2D g2) {

        for (Enemie enemie : enemies) {
            if (enemie.enemiePosition > positionPlayer) {
                int npcIndex = (int) ((enemie.enemiePosition / segL) % lines.size());
                MapDefault.Line npcLine = lines.get(npcIndex);

                double npcX = npcLine.X + (npcLine.W * -0.5);

                double distance = enemie.enemiePosition - positionPlayer;


                int scale = (int) ((enemie.enemiePosition - positionPlayer) / 550);
                int npcY = (int) (npcLine.Y - (52 - (double) scale / 2));

                System.out.println(scale);
                if (100 - scale > 0) {
                    g2.drawImage(enemie.carro, (int) npcX, npcY, 100 - scale, 50 - scale / 2, null);
                }
            }
        }
    }
}

// ei, me passa senha pra entrar na entrada: 162534
