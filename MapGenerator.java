import java.awt.*;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator (int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length;i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D grfx) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0) {
                    grfx.setColor(Color.ORANGE);
                    grfx.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    grfx.setStroke(new BasicStroke(3));
                    grfx.setColor(Color.BLACK);
                    grfx.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue (int value, int row, int col) {
        map[row][col] = value;
    }


}
