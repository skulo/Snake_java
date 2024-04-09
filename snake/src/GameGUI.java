import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.ArrayList;
import java.sql.SQLException;

public class GameGUI extends JFrame{

    private LinkedList<Point> stones;
    private Snake snake;
    private Food food;
    private Timer timer;
    private int seconds;
    private Timer gameTime;



    /**
     * A GameGUI osztály konstruktora. Inicializálja a kígyót, az ételt, a köveket és létrehozza a grafikus felhasználói felületet.
     */
    public GameGUI(){

        Image headImage = new ImageIcon("fej.png").getImage();
        Image bodyImage = new ImageIcon("test.png").getImage();
        snake = new Snake(new Point(10, 7), headImage, bodyImage);
        stones = new LinkedList<>();
        food = new Food(snake.getBody(), stones);
        createMenu();
        GUI();
    }
    /**
     * A játékmenet aktuális idejét frissíti és megjeleníti a menüsorban.
     */
    private void updategameTime() {
        String status = "   Time: " + seconds + " seconds";
        ((JLabel) getJMenuBar().getComponent(1)).setText(status);
    }
    /**
     * Megjeleníti a toplistát tartalmazó ablakot.
     */
    private void viewHighScores(){
        try {
            HighScores highScores = new HighScores(10);
            ArrayList<HighScore> highScoreList = highScores.getHighScores();

            StringBuilder message = new StringBuilder();
            for (int i = 0; i < highScoreList.size(); i++){
                HighScore highScore = highScoreList.get(i);
                message.append(i + 1).append(". ")
                        .append(highScore.getName()).append(": ")
                        .append("Score: ").append(highScore.getScore()).append(", ")
                        .append("Time: ").append(highScore.getSeconds()).append(" seconds")
                        .append("\n");
            }

            JOptionPane.showMessageDialog(this, message.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * Elindít egy új játékot, leállítja az aktuálisat és inicializál egy újat.
     */
    private void startNewGame(){
        timer.stop();
        Image headImage = new ImageIcon("fej.png").getImage();
        Image bodyImage = new ImageIcon("test.png").getImage();
        snake = new Snake(new Point(10, 7), headImage, bodyImage);
        stones.clear();
        food.generateFood(20, 15, snake.getBody(), stones);

        dispose();

        GameGUI newGameGUI = new GameGUI();
        newGameGUI.pack();
        newGameGUI.setVisible(true);

    }   /**
     * Létrehozza a menüt, amiben a toplistát lehet megnyitni és új játékot kezdeni, és hozzáadja a játékhoz.
     */
    private void createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem viewHighScoresMenu = new JMenuItem("View High Scores");
        JMenuItem newGameMenu = new JMenuItem("New Game");

        viewHighScoresMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                viewHighScores();
            }
        });

        newGameMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                startNewGame();
            }
        });

        gameMenu.add(viewHighScoresMenu);
        gameMenu.add(newGameMenu);
        menuBar.add(gameMenu);

        JLabel statusBar = new JLabel("   Time: 0 seconds");
        menuBar.add(statusBar);

        setJMenuBar(menuBar);

        setJMenuBar(menuBar);
    }
    /**
     * Inicializálja a grafikus felhasználói felületet és a játékmenetet állítja be.
     */
    private void GUI(){
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameBoard gameBoard = new GameBoard(snake, food, stones);
        add(gameBoard);

        timer = new Timer(130, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                    snake.move();
                    checkHits();
                    checkFoodEat();
                    checkStoneHit();
                    gameBoard.repaint();

            }
        });
        timer.start();
        gameTime = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updategameTime();
            }
        });

        gameTime.start();


        addKeyListener(new KeyAdapter());
        setFocusable(true);

        food.generateFood(20,15,snake.getBody(), stones);
    }
    /**
     * Ellenőrzi, hogy a kígyó ütközött-e önmagával vagy a játék szélével.
     * Ha igen, leállítja az időzítőt és meghívja a GameOver() metódust.
     */
    private void checkHits(){
        if (snake.hitSnake() || snake.hitWall(20, 15)) {
            timer.stop();
            GameOver();
        }
    }

    /**
     * Ellenőrzi, hogy a kígyó ütközött-e egy kővel. Ha igen, leállítja az időzítőt
     * és meghívja a GameOver() metódust.
     */
    private void checkStoneHit(){
        Point head = snake.getHead();
        if (stones.contains(head)){
            GameOver();
            timer.stop();
        }
    }
    /**
     * Ellenőrzi, hogy a kígyó megevett-e egy ételt. Ha igen, a kígyó megnő 1-el,
     * és generál egy új ételt.
     */
    private void checkFoodEat(){
        Point head = snake.getHead();
        Point foodPosition = food.getPosition();

        if (head.equals(foodPosition)) {
            snake.grow();
            food.generateFood(20,15, snake.getBody(), stones);
        }
    }
    /**
     * A játék végét kezeli. Leállítja az időzítőket, megjeleníti a játékos pontszámát,
     * kéri a játékos nevét, majd elmenti az eredményt a HighScores adatbázisba.
     */
    private void GameOver(){

        gameTime.stop();
        int score = snake.getBody().size() - 2;
        String playerName = JOptionPane.showInputDialog(this, "Your score: " + score + "\n" + "Enter your name:");

        if (playerName != null && !playerName.isEmpty()){
            try{
                HighScores highScores = new HighScores(10);
                highScores.putHighScore(playerName, score, seconds);
            } catch (SQLException e){
                e.printStackTrace();
            }


        }
        snake.getBody().clear();
        food = new Food(snake.getBody(), stones);
        repaint();
    }

    /**
     * A játék belépési pontja. Elindítja a Snake játékot.
     *
     * @param args a parancssori argumentumok
     */
    public static void main(String[] args){

        GameGUI GameGUI = new GameGUI();
        GameGUI.pack();
        GameGUI.setVisible(true);

    }
    /**
     * A KeyAdapter osztály egy belső osztály a billentyűzetes események kezeléséhez.
     */
    private class KeyAdapter implements KeyListener{

        private long keypresstime = 0;
        private static final long minkeypress = 120;

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

                long currentTime = System.currentTimeMillis();
                if (currentTime - keypresstime < minkeypress) {
                    return;
                }

                int key = e.getKeyCode();


                switch (key) {
                    case KeyEvent.VK_UP:
                        snake.setDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        snake.setDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        snake.setDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.setDirection(Direction.RIGHT);
                        break;
                    default:
                        break;
                }

                keypresstime = currentTime;
            }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}