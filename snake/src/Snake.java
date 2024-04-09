import java.awt.*;
import java.util.*;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;
    private Image headImage;
    private Image bodyImage;
    /**
     * A Snake osztály konstruktora.
     *
     * @param snakePosition kezdeti pozíciója a kígyónak
     * @param headImage     a kígyó fejének képe
     * @param bodyImage     a kígyó testének képe
     */
    public Snake(Point snakePosition, Image headImage, Image bodyImage){
        this.headImage = headImage;
        this.bodyImage = bodyImage;
        body = new LinkedList<>();
        body.add(snakePosition);

        Point tail = new Point(snakePosition.x - 1, snakePosition.y);
        body.add(tail);

        Random random = new Random();
        int randomDirection = random.nextInt(4);
        switch (randomDirection){
            case 0:
                direction = Direction.UP;
                break;
            case 1:
                direction = Direction.DOWN;
                break;
            case 2:
                direction = Direction.LEFT;
                break;
            case 3:
                direction = Direction.RIGHT;
                break;
        }
    }
    /**
     * Visszaadja a kígyó testét tartalmazó pontok listáját.
     *
     * @return a kígyó testét reprezentáló pontok listája
     */
    public LinkedList<Point> getBody(){

        return body;
    }
    /**
     * Visszaadja a kígyó testének képét.
     *
     * @return a kígyó testének képe
     */
    public Image getBodyImage(){

        return bodyImage;
    }
    /**
     * Visszaadja a kígyó fejének képét.
     *
     * @return a kígyó fejének képe
     */
    public Image getHeadImage(){

        return headImage;
    }
    /**
     * Visszaadja a kígyó fejének helyét.
     *
     * @return a kígyó fejének pozíciója
     */
    public Point getHead(){

        return body.getFirst();
    }
    /**
     * Beállítja a kígyó irányát az újra, ha az nem az aktuális iránnyal ellentétes.
     *
     * @param newDirection az új irány
     */
    public void setDirection(Direction newDirection){
        if (newDirection != direction.opposite()){
            direction = newDirection;
        }
    }
    /**
     * A kígyó mozgásáért felelős. A kígyó fejét az új fej pozíciójára mozgatja,
     * és a kígyó végét rövidíti 1-el.
     */
    public void move(){
        Point head = body.getFirst();
        Point newHead = NewHead(head);
        body.addFirst(newHead);
            body.removeLast();

    }
    /**
     * A kígyó testének növelését végzi. Hozzáad egy új véget a kígyó testéhez.
     */
    public void grow(){
        Point tail = body.getLast();
        Point newTail = NewTail(tail);
        body.addLast(newTail);
    }
    /**
     * Visszaadja az új fej pozícióját a megadott fej pozíció alapján.
     *
     * @param Head a kígyó fejének aktuális pozíciója
     * @return az új fej pozíciója
     */
    private Point NewHead(Point Head){
        int x = (int) Head.getX();
        int y = (int) Head.getY();

        switch (direction){
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        return new Point(x, y);
    }
    /**
     * Visszaadja az új farok pozícióját a megadott farok pozíciója alapján.
     *
     * @param Tail a kígyó farkának aktuális pozíciója
     * @return az új farok pozíciója
     */
    private Point NewTail(Point Tail){
        int x = (int) Tail.getX();
        int y = (int) Tail.getY();

        switch (direction){
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
            case LEFT:
                x++;
                break;
            case RIGHT:
                x--;
                break;
        }

        return new Point(x, y);
    }
    /**
     * Ellenőrzi, hogy a kígyó ütközött-e önmagával.
     *
     * @return true, ha a kígyó ütközött önmagával, különben false
     */
    public boolean hitSnake(){
        Point head = getHead();
        for (int i = 1; i < body.size(); i++){
            if (head.equals(body.get(i))){
                return true;
            }
        }
        return false;
    }
    /**
     * Ellenőrzi, hogy a kígyó ütközött-e a pálya szélével.
     *
     * @param width  a pálya szélessége
     * @param height a pálya magassága
     * @return true, ha a kígyó ütközött a pálya szélével, különben false
     */
    public boolean hitWall(int width, int height){
        Point head = getHead();
        return head.x < 0 || head.x >= width || head.y < 0 || head.y >= height;
    }
}