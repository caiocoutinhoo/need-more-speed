package entity.enemie;

import entity.player.Player;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class EnemiesService {
    private GamePanel gp;
    private int lastEnemySpawnPosition = 0;
    private int LEFT = 200;
    private int RIGHT = 1080;
    private Player player;
    List<Enemie> enemies = new ArrayList<>();

    public EnemiesService(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    public void updateCreateEnemies(int positionPlayer) {
        if(positionPlayer - lastEnemySpawnPosition >= 100){
            createEnemy(positionPlayer);
            lastEnemySpawnPosition = positionPlayer;
        }
    }

    private void createEnemy(int positionPlayer){
        Enemie enemieLeft = new Enemie(gp, 200, 550, 1, player, this, 400, 0);
        Enemie enemieRight = new Enemie(gp, 1080, 550, 1, player, this, 500, 0);
        enemies.add(enemieRight);
    }
}
