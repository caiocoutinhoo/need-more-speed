package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import UI.*;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public KeyHandler (GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não iremos utilizar
    }
    @Override // Tecla Pressionada
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_ESCAPE)       //apertar Esc pra fechar janela (apenas para testar o jogo mais rápido
            System.exit(1);           //sem ter que ficar indo até o mouse pra fechar)

        if(gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_W){
                gp.ui.setOption(0);
            }
            if (code == KeyEvent.VK_S){
                gp.ui.setOption(1);
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.getOption() == 0){
                    // Mudar o valor pra que não va direto para playState
//
                    gp.tocarEfeitoSonoro("res/sound/selectSong.mp3");
                    code = KeyEvent.VK_Z;
                    gp.gameState = gp.garageState;
                }
                if(gp.ui.getOption() == 1){
                    System.exit(0);
                }
            }
        }

        if(gp.gameState == gp.garageState){

            if (code == KeyEvent.VK_D){
                gp.ui.setCarIndex(gp.ui.getCarIndex() + 1);
                gp.tocarEfeitoSonoro("res/sound/optionSong.mp3");


            }
            if (code == KeyEvent.VK_A){
                gp.ui.setCarIndex(gp.ui.getCarIndex() - 1);
                gp.tocarEfeitoSonoro("res/sound/optionSong.mp3");

            }
            if(code == KeyEvent.VK_ENTER){

                gp.tocarEfeitoSonoro("res/sound/selectSong.mp3");
                code = KeyEvent.VK_Z;
                gp.ui.selected = true;
                if(gp.ui.selected){
                    gp.gameState = gp.playState;

                }

            }
        }

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P){
            //code = KeyEvent.VK_A;
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
                gp.tocarEfeitoSonoro("res/sound/pause.mp3");
            }else if(gp.gameState == gp.pauseState){
                gp.tocarEfeitoSonoro("res/sound/pause.mp3");
                gp.gameState = gp.playState;
            }

        }
    }

    @Override // Tecla Não-Pressionada
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }

    }

}

