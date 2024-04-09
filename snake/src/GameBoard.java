import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class GameBoard extends JPanel{
    private Snake snake;
    private Food food;
    private LinkedList<Point> stones;
    private Image backgroundImage;
    /**
     * A GameBoard osztály konstruktora. Inicializálja a kígyót, az ételt és az köveket.
     *
     * @param snake  a kígyót
     * @param food   az élelmiszer
     * @param stones a kövek
     */
    public GameBoard(Snake snake, Food food, LinkedList<Point> stones){
        this.snake = snake;
        this.food = food;
        this.stones = stones;


        generateStones();

        setPreferredSize(new Dimension(1000, 750));
        setLayout(null);
        backgroundImage = new ImageIcon("sivatag.jpg").getImage();
    }
    /**
     * A játékpanel kirajzolása.
     *
     * @param g a grafikus objektum a rajzoláshoz
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        if (!snake.getBody().isEmpty()) {
            drawSnake(g);
            drawFood(g);
            drawStones(g);
        }
    }

    /**
     * Köveket hoz létre és ad hozzá a pályához
     */
    private void generateStones(){
        for (int i = 0; i < 7; ){
            int x = (int) (Math.random() * 20);
            int y = (int) (Math.random() * 15);

            if (!snake.getBody().contains(new Point(x, y)) && !food.getPosition().equals(new Point(x, y)) && !stones.contains(new Point(x, y))){
                stones.add(new Point(x, y));
                i++;
            }
        }
    }
    /**
     * A kígyó kirajzolása a játékpanelen.
     *
     * @param g a grafikus objektum a rajzoláshoz
     */
    private void drawSnake(Graphics g){
        LinkedList<Point> body = snake.getBody();

        Point head = body.getFirst();
        int headX = (int) head.getX();
        int headY = (int) head.getY();
        g.drawImage(snake.getHeadImage(), headX * 50, headY * 50, 50, 50, this);

        for (int i = 1; i < body.size(); i++) {
            Point bodyPart = body.get(i);
            int x = (int) bodyPart.getX();
            int y = (int) bodyPart.getY();
            g.drawImage(snake.getBodyImage(), x * 50, y * 50, 50, 50, this);
        }
    }
    /**
     * Az élelem kirajzolása a játékpanelen.
     *
     * @param g a grafikus objektum a rajzoláshoz
     */
    private void drawFood(Graphics g){
        Point foodPosition = food.getPosition();
        Image foodImage = new ImageIcon("food.png").getImage();
        g.drawImage(foodImage, foodPosition.x * 50, foodPosition.y * 50, 50, 50, this);
    }
    /**
     * A kövek kirajzolása a játékpanelen.
     *
     * @param g a grafikus objektum a rajzoláshoz
     */
    private void drawStones(Graphics g){
        for (Point stone : stones){
            int x = (int) stone.getX();
            int y = (int) stone.getY();
            Image stoneImage = new ImageIcon("stone.png").getImage();
            g.drawImage(stoneImage, x * 50, y * 50, 50, 50, this);
        }
    }
}

