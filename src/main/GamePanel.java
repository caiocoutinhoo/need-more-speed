package main;
import entity.player.Player;
import map.Map1;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import UI.*;


public class GamePanel extends JPanel implements Runnable {



    // CONFIGURAÇÕES DE TELA
    public final int D_W = 1280;
    public final int D_H = 720;
    // TECLADO
    KeyHandler keyH = new KeyHandler(this);
    //  FPS
    int FPS = 60;
    // UI
    public UI ui = new TitleScreenUI(this);
    Thread gameThread;
    // Player
    Player player = new Player(this,keyH);
    Map1 map1 = new Map1(this, keyH, player);
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState =2;
    public final int garageState = 3;

    private long totalElapsedTime = 0;  // Tempo total acumulado
    private long pauseTime = 0;         // Tempo em que o jogo foi pausado
    private boolean isPaused = false;   // Controle de pausa
    private long currentTime;
    private Font customFont;
    GenericUI gi;


    public GamePanel(){
        this.setPreferredSize( new Dimension (D_W, D_H));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.gi = new GenericUI(this);

        try{    // carregando fonte personalizada
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/jgs_Font.ttf")).deriveFont(36f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        }
        catch (IOException | FontFormatException e){
            e.printStackTrace();
            customFont = new Font("Serif", Font.PLAIN, 24);
        }


    }
    public void starGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        gameState = titleState;
    }
    @Override
    public void run() {

        this.requestFocus();

        double drawnInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){
            //FPS
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawnInterval;
            lastTime = currentTime;

            if (delta >= 1){
                // 1 UPDATE: Atualizar a posição do player
                update();
                // 2 DRAW: Desenha na tela e atualiza o desenho
                repaint();
                delta--;
            }
        }
    }
    public int check_class(UI userInterface){
        if(userInterface instanceof GarageUI){
            return garageState;
        }
        if(userInterface instanceof TitleScreenUI){
            return titleState;
        }
        return -1;
    }


//    public void tocarMusica(String filePath){
//
//
//        MP3Player mp3Player = new MP3Player();
//
//        // Caminho para o arquivo MP3
//        //String filePath = "res/sound/song.mp3";
//
//        // Inicia a música em segundo plano
//        mp3Player.playMP3InBackground(filePath);
//    }

//    public void tocarEfeitoSonoro(String filePath){
//        MP3Player mp3Player = new MP3Player();
//
//        // Caminho para o arquivo MP3
//        //String filePath = "res/sound/song.mp3";
//
//        // Inicia a música em segundo plano
//        mp3Player.playSelect(filePath);
//
//    }


    public void update(){
        alterarImgCar();
        if(gameState == playState){
            if(isPaused){
                isPaused = false; //reseta pausa
            }
            else{
                // acumula o tempo do ultimo frame
                totalElapsedTime += System.nanoTime() - currentTime;
            }

            //atualiza o currentTime para o próximo frame
            currentTime = System.nanoTime();

            map1.update();
            player.update();
        }if(gameState == pauseState){
            if(!isPaused){
                pauseTime = System.nanoTime();
                isPaused = true;
            }
            ui.drawPause();

        }
        if(gameState == garageState){
            if(check_class(ui) != garageState){
                ui = new GarageUI(this);
            }
        }
        else if (gameState == titleState) {
            // currentTime só é iniciado quando entrar no playState
            currentTime = System.nanoTime();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState){
            ui.draw(g2);
        }else if(gameState == garageState){
            ui.draw(g2);
        }
        else if(gameState == playState){
            map1.draw(g2);
            //UI
            gi.draw(g2);

            g.setFont(customFont);

            //conta o tempo em segundos
            float elapsedTimeInSeconds = totalElapsedTime / 1_000_000_000.0f;

            //marcação do tempo
            g.drawString("TEMPO: " + String.format("%.2f", elapsedTimeInSeconds) + "s", 15, 42);

            //marcação dos pontos
            g.drawString("PONTOS: " + map1.playerPoints, 15, 80);
        }else if(gameState == pauseState){
            map1.draw(g2);
            ui.setG2(g2);
            ui.drawPause();


        }
        else{
            map1.draw(g2);
            //UI
            gi.draw(g2);

            g.setFont(customFont);
            //g.setColor(Color.white);
            //conta o tempo em segundos
            float elapsedTimeInSeconds = totalElapsedTime / 1_000_000_000.0f;

            //marcação do tempo
            g.drawString("TEMPO: " + String.format("%.2f", elapsedTimeInSeconds) + "s", 15, 42);

            //marcação dos pontos
            g.drawString("PONTOS: " + map1.playerPoints, 15, 80);

        }
        g2.dispose();
    }

    public void alterarImgCar(){
        switch (ui.getCarIndex()){
            case 0:
                player.setCarImage("/car1/car1.png");
                break;
            case 1:
                player.setCarImage("/car1/car2.png");
                break;
            case 2:
                player.setCarImage("/car1/car3.png");
                break;
        }
    }
}


