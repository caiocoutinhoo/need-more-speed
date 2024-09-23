package entity.enemie;

import entity.player.Player;
import main.GamePanel;
import map.MapDefault;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EnemiesService {
    private GamePanel gp;
    private MapDefault map;
    private int lastEnemySpawnPosition = 0;
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
        Enemie enemie = new Enemie(gp, 0, 0, 50, player, map, 350, positionPlayer + 30000, randomGenerator());
        enemies.add(0,enemie);

        Enemie enemie1 = new Enemie(gp, 0, 0, 50, player, map, 350, positionPlayer + 60000, randomGenerator());
        enemies.add(0,enemie1);
    }

    public void updateEnemies(int positionPlayer, Graphics2D g2) {
        updateCreateEnemies(positionPlayer);
        enemies.sort(Comparator.comparingDouble(Enemie::getEnemiePosition));
        for (Enemie enemie : enemies) {
            enemie.update();
            enemie.checkOvertaken(map);
            checkColision(positionPlayer, enemie);
            //teste
//            g2.setColor(Color.RED);
//            Rectangle playerBounds = player.getBounds();
//            g2.drawRect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);
//
//            g2.setColor(Color.BLUE);
//            Rectangle enemieBounds = enemie.getBounds();
//            g2.drawRect(enemieBounds.x, enemieBounds.y, enemieBounds.width, enemieBounds.height);
        }
    }

    public void createEnemies(int positionPlayer, int segL, List<MapDefault.Line> lines, Graphics2D g2) {

        for (Enemie enemie : enemies) {
            if (enemie.enemiePosition > positionPlayer) {
                int npcIndex = (int) ((enemie.enemiePosition / segL) % lines.size());
                MapDefault.Line npcLine = lines.get(npcIndex);

                enemie.x = (int) (npcLine.X + (npcLine.W * enemie.xInMap));

                double scale = ((enemie.enemiePosition - positionPlayer) / 120);
                enemie.y = (int) (npcLine.Y - (100 - scale / 2));

                enemie.width = (int) (190 - scale);
                enemie.height = (int) (100 - scale / 2);

                if (190 - scale > 0) {
                    g2.drawImage(enemie.carro, enemie.x, enemie.y, enemie.width, enemie.height, null);
                }


            }
        }
    }
    public void checkColision(int positionPlayer, Enemie enemie) {
        Rectangle playerBounds = player.getBounds();
        Rectangle enemieBounds = enemie.getBounds();
        enemieBounds.width =+ 100;
        if (isColliding(playerBounds, enemieBounds)) {
            player.onCollision();
        }
    }

    public boolean isColliding(Rectangle r1, Rectangle r2) {
        return r1.x < r2.x + r2.width &&   // Verifica se o lado esquerdo do r1 está à esquerda do lado direito do r2
                r1.x + r1.width > r2.x &&   // Verifica se o lado direito do r1 está à direita do lado esquerdo do r2
                r1.y < r2.y + r2.height &&  // Verifica se o topo do r1 está acima da parte inferior do r2
                r1.y + r1.height > r2.y;    // Verifica se a parte inferior do r1 está abaixo do topo do r2
    }


}

