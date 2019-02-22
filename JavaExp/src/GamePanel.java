import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static final int IMWD = 500, IMHT = 500;
    private Thread thread;
    private boolean running;
    private BodyPart b;
    private ArrayList<BodyPart> snake;
    private boolean right = true, left = false, up = false, down = false;
    private Food food;
    private ArrayList<Food> foods;

    private Random r;

    private int xCoor = 10, yCoor = 10, size = 15;
    private int ticks = 0;

    public GamePanel() {
        setFocusable(true);

        setPreferredSize(new Dimension(IMWD, IMHT));
        addKeyListener(this);

        snake = new ArrayList<BodyPart>();
        foods = new ArrayList<Food>();

        r = new Random();

        startGame();
    }

    public void startGame() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stopGame() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        if(snake.size() == 0) {
            b = new BodyPart(xCoor, yCoor, 10);
            snake.add(b);
        }
        ticks++;
        if(ticks > 250000) {
            if(right) xCoor++;
            if(left) xCoor--;
            if(up) yCoor--;
            if(down) yCoor++;

            ticks = 0;

            b = new BodyPart(xCoor, yCoor, 10);
            snake.add(b);

            if(snake.size() > size) {
                snake.remove(0);
            }
        }
        if(foods.size() == 0) {
            int xCoor = r.nextInt(49);
            int yCoor = r.nextInt(49);

            food = new Food(xCoor, yCoor, 10);
            foods.add(food);
        }

        for(int i = 0; i < foods.size(); i++) {
            if(xCoor == foods.get(i).getxCoor() && yCoor == foods.get(i).getyCoor()) {
                size++;
                foods.remove(i);
                i++;
            }
        }

        //Checking the collision on the snake body itself
        for(int i = 0; i < snake.size(); i++) {
            if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
                if(i != snake.size() - 1) {
                    System.out.println("Game Over");
                    stopGame();
                }
            }
        }

        //Checking the collision on the border of the grid
        if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) {
            System.out.println("Game Over");
            stopGame();
        }
    }

    public void paint(Graphics g) {

        g.clearRect(0, 0, IMWD, IMHT);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, IMWD, IMHT);

        for(int i = 0; i < IMWD/10; i++) {
            g.drawLine(i * 10, 0, i * 10, IMHT);
        }
        for(int i = 0; i < IMHT/10; i++) {
            g.drawLine(0, i * 10, IMHT, i * 10);
        }
        for(int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(g);
        }
        for(int i = 0; i < foods.size(); i++) {
            foods.get(i).draw(g);
        }
    }

    @Override
    public void run() {
        while(running) {
            tick();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }
        if(key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }
        if(key == KeyEvent.VK_UP && !down) {
            up = true;
            right = false;
            left = false;
        }
        if(key == KeyEvent.VK_DOWN && !up) {
            down = true;
            right = false;
            left = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
