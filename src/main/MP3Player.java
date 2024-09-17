package main;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MP3Player {
    private Player player;
    private boolean loop = true;  // Controlador de loop

    // Método para tocar a música em loop
    public void playMP3InBackground(String filePath) {
        // Cria uma nova thread para tocar a música
        Thread musicThread = new Thread(() -> {
            while (loop) {  // Continua rodando enquanto "loop" for true
                try {
                    FileInputStream fis = new FileInputStream(filePath);
                    player = new Player(fis);
                    player.play();  // Toca a música até o fim
                } catch (FileNotFoundException | JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });

        // Inicia a thread para rodar a música em paralelo
        musicThread.start();
    }

    // Método para parar o loop da música
    public void stopMP3() {
        loop = false;  // Para o loop
        if (player != null) {
            player.close();  // Para o player
        }
    }}
