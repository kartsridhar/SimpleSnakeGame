import java.awt.*;

public class Food {

    private int xCoor, yCoor, IMWD, IMHT;

    public Food(int xCoor, int yCoor, int tileSize) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        IMWD = tileSize;
        IMHT = tileSize;
    }

    public void tick() {

    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xCoor * IMWD, yCoor * IMHT, IMWD, IMHT);
    }

    public int getxCoor() {
        return xCoor;
    }

    public void setxCoor(int xCoor) {
        this.xCoor = xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }
}
