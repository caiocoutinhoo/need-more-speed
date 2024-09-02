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
        if(positionPlayer - lastEnemySpawnPosition >= 190000){
            createEnemy(positionPlayer, this.map);
            lastEnemySpawnPosition = positionPlayer;
        }
    }

    private void createEnemy(int positionPlayer, MapDefault map){
        Enemie enemieLeft = new Enemie(gp, 200, 550, 2, player, map, 400, positionPlayer + 100000);
        Enemie enemieRight = new Enemie(gp, 1080, 550, 2, player, map, 400, positionPlayer + 110000);
        enemies.add(enemieRight);
        enemies.add(enemieLeft);
    }

    public void drawEnemies(Graphics2D g2){
        for (Enemie enemie : enemies) {
            enemie.draw(g2);
        }
    }
    public void updateEnemies(int positionPlayer) {
        updateCreateEnemies(positionPlayer);
        for (Enemie enemie : enemies) {
            enemie.update();
        }
    }
}
