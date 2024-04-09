import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Food{
    private Point foodposition;
    /**
     * A Food osztály konstruktora. Az étel inicializálásakor generálja a pozícióját.
     *
     * @param snake  a kígyó teste
     * @param stones a pályán található kövek
     */
    public Food(LinkedList<Point> snake, LinkedList<Point> stones){

        generateFood(20, 15, snake, stones);
    }
    /**
     * Generál egy új ételt a pályán, elkerülve a kígyót és a köveket.
     *
     * @param width  a pálya szélessége
     * @param height a pálya magassága
     * @param snake  a kígyó teste
     * @param stones a pályán található kövek
     */
    public void generateFood(int width, int height, LinkedList<Point> snake, LinkedList<Point> stones){
        List<Point> foodPos = new ArrayList<>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                Point maybePosition = new Point(x, y);
                if (!snake.contains(maybePosition) && !stones.contains(maybePosition)){
                    foodPos.add(maybePosition);
                }
            }
        }

        if (!foodPos.isEmpty()){
            Random random = new Random();
            int randomIndex = random.nextInt(foodPos.size());
            foodposition = foodPos.get(randomIndex);
        } else{
            foodposition = null;
        }
    }
    /**
     * Visszaadja az étel aktuális pozícióját a pályán.
     *
     * @return az étel pozíciója a pályán
     */
    public Point getPosition(){

        return foodposition;
    }
}