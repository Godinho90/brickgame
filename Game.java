import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {

    private  boolean play = false;
    private int score = 0;
    private int totalBricks = 48;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;

    private MapGenerator map;

    public Game() {
        map = new MapGenerator(4,12);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint (Graphics graf) {
        //background
        graf.setColor(Color.black);
        graf.fillRect(1,1,692,592);

        //map
        map.draw((Graphics2D) graf);

        //borders
        graf.setColor(Color.black);
        graf.fillRect(0,0,3,592);
        graf.fillRect(0,0,692,3);
        graf.fillRect(691,0,3,592);

        //score
        graf.setColor(Color.white);
        graf.setFont(new Font("serif", Font.BOLD, 25));
        graf.drawString("" + score, 590,30);

        //paddle
        graf.setColor(Color.green);
        graf.fillRect(playerX, 550, 100, 8);

        //ball
        graf.setColor(Color.blue);
        graf.fillOval(ballPosX,ballPosY,20,20);

        //won
        if(totalBricks <= 0) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            graf.setColor(Color.RED);
            graf.setFont(new Font("serif", Font.BOLD, 30));
            graf.drawString("You Won!!", 260,300);

            graf.setColor(Color.RED);
            graf.setFont(new Font("serif", Font.BOLD, 20));
            graf.drawString("Press (Enter) to Restart", 230,350);
        }
        //lose
        if(ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            graf.setColor(Color.RED);
            graf.setFont(new Font("serif", Font.BOLD, 30));
            graf.drawString("Game Over!!\n" + "Score: "+score,190,300 );

            graf.setColor(Color.RED);
            graf.setFont(new Font("serif", Font.BOLD, 20));
            graf.drawString("Press (Enter) to Restart", 230,350);
        }
        graf.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(4,12);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                ballDirY = -ballDirY;
                ballDirX = -2;
            } else if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                ballDirY = -ballDirY;
                ballDirX = ballDirX +1;
            } else if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                ballDirY = -ballDirY;
            }

            loop: for(int i = 0; i<map.map.length; i++) {
                for(int j = 0; j<map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRectangle = rectangle;

                        if(ballRectangle.intersects(brickRectangle)) {
                            map.setBrickValue(0, i, j);
                            score += 5;
                            totalBricks--;

                            if(ballPosX + 19 <= brickRectangle.x || ballPosX + 1 >= brickRectangle.x + brickRectangle.width) {
                                ballDirX = -ballDirX;
                            }else{
                                ballDirY = -ballDirY;
                            }
                            break loop;
                        }
                    }
                }
            }
            ballPosX += ballDirX;
            ballPosY += ballDirY;

            if(ballPosX < 0) {
                ballDirX = -ballDirX;
            }

            if(ballPosY < 0) {
                ballDirY = -ballDirY;
            }

            if(ballPosX > 670) {
                ballDirX = -ballDirX;
            }

            repaint();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }
}
