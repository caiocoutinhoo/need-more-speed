package main;
import entity.Player;
import map.Map1;
import map.MapDefault;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DE TELA
    public final int D_W = 1280;
    public final int D_H = 720;
    // TECLADO
    KeyHandler keyH = new KeyHandler(this);
    //  FPS
    int FPS = 60;
    // UI
    public UI ui = new UI(this);
    Thread gameThread;
    // Player
    Player player = new Player(this,keyH);
    Map1 map1 = new Map1(this, keyH, player);
    MapDefault mapa = map1;
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState =2;

    private long totalElapsedTime = 0;  // Tempo total acumulado
    private long pauseTime = 0;         // Tempo em que o jogo foi pausado
    private boolean isPaused = false;   // Controle de pausa
    private long currentTime;

    private Font customFont;            //fonte customizada

    //controles do tempo de votla
    private float tempoDeVolta = 0;
    private boolean exibirTempoVolta = false;
    private float tempoInicioDaVolta = 0;
    private long tempoInicioExibicaoVolta = 0;
    private boolean tempo = false;
    private int contadorAnterior = 0;


    public GamePanel(){
        this.setPreferredSize( new Dimension (D_W, D_H));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try{    // carregando fonte personalizada
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/jgs_Font.ttf")).deriveFont(36f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        }       //
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

        double drawnInterval = (double) 1000000000 / FPS;
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
    public void update(){
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
        }
        else if(gameState == pauseState){
            if(!isPaused){
                pauseTime = System.nanoTime();
                isPaused = true;
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

        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            map1.draw(g2);
            // UI
            ui.draw(g2);

            // Conta o tempo total em segundos
            float elapsedTimeInSeconds = totalElapsedTime / 1_000_000_000.0f;


            // melhorar resolução
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(customFont.deriveFont(36f));

            String tempoTexto = "TEMPO: " + String.format("%.2f", elapsedTimeInSeconds) + "s";

            TextLayout tempoLayout = new TextLayout(tempoTexto, customFont.deriveFont(36f), g2.getFontRenderContext());
            Shape tempoOutline = tempoLayout.getOutline(null);

            AffineTransform tempoTransform = g2.getTransform();
            tempoTransform.translate(15, 42);  // Posição (15, 42)
            g2.transform(tempoTransform);

            //contorno
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2f));
            g2.draw(tempoOutline);

            g2.setTransform(new AffineTransform());

            g2.setColor(Color.WHITE);
            g2.drawString(tempoTexto, 15, 42);

            int score = 0;
            String pontosTexto = "PONTOS: " + score;

            TextLayout pontosLayout = new TextLayout(pontosTexto, customFont.deriveFont(36f), g2.getFontRenderContext());
            Shape pontosOutline = pontosLayout.getOutline(null);

            AffineTransform pontosTransform = g2.getTransform();
            pontosTransform.translate(15, 80);
            g2.transform(pontosTransform);

            //contorno
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2f));
            g2.draw(pontosOutline);

            g2.setTransform(new AffineTransform());

            g2.setColor(Color.WHITE);
            g2.drawString(pontosTexto, 15, 80);


            int contadorVolta = mapa.voltaPercorrida;
            String voltasTexto = "VOLTAS: " + contadorVolta;

            TextLayout voltasLayout = new TextLayout(voltasTexto, customFont.deriveFont(36f), g2.getFontRenderContext());
            Shape voltasOutline = voltasLayout.getOutline(null);

            AffineTransform voltasTransform = g2.getTransform();
            voltasTransform.translate(280, 42);
            g2.transform(voltasTransform);

            //contorno
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2f));
            g2.draw(voltasOutline);

            g2.setTransform(new AffineTransform());

            g2.setColor(Color.WHITE);
            g2.drawString(voltasTexto, 280, 42);


            // uma volta é completa
            if (contadorVolta > contadorAnterior) {
                // calculo do tempo de volta
                tempoDeVolta = elapsedTimeInSeconds - tempoInicioDaVolta;

                exibirTempoVolta = true;
                tempoInicioExibicaoVolta = System.currentTimeMillis(); // tempo de início da exibição
                contadorAnterior = contadorVolta; //contador de voltas atualizado
                tempoInicioDaVolta = elapsedTimeInSeconds; // o tempo de início da nova volta atualizado
            }


            // mostra tempo da volta por 10 segundos
            if (exibirTempoVolta) {
                long tempoAtual = System.currentTimeMillis();
                if (tempoAtual - tempoInicioExibicaoVolta < 10000) {
                    String texto = "TEMPO DE VOLTA: " + String.format("%.2f", tempoDeVolta).replace(",", ".") + "s";
                    g2.setFont(customFont.deriveFont(60f));

                    g2.setColor(Color.BLACK);
                    TextLayout textLayout = new TextLayout(texto, customFont.deriveFont(60f), g2.getFontRenderContext());

                    AffineTransform transform = g2.getTransform();
                    Shape outline = textLayout.getOutline(null);
                    transform.translate(340, 260);
                    g2.transform(transform);

                    g2.setStroke(new BasicStroke(3f)); //espessura do contorno
                    g2.draw(outline);

                    g2.setTransform(new AffineTransform());
                    g2.setColor(Color.WHITE);
                    g2.drawString(texto, 340, 260);
                }
                else {
                    exibirTempoVolta = false;
                }
            }
        }

        g2.dispose();
    }
}


