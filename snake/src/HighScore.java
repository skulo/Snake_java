
public class HighScore{

    private final String name;
    private final int score;
    private final int seconds;
    /**
     * A HighScore osztály konstruktora. Egy HighScore példányt hoz létre.
     *
     * @param name    A játékos neve.
     * @param score   A játékos pontszáma.
     * @param seconds A játékidő másodpercekben.
     */
    public HighScore(String name, int score, int seconds){
        this.name = name;
        this.score = score;
        this.seconds=seconds;
    }
    /**
     * Visszaadja a játékos nevét.
     *
     * @return A játékos neve.
     */
    public String getName(){
        return name;
    }
    /**
     * Visszaadja a játékos pontszámát.
     *
     * @return A játékos pontszáma.
     */
    public int getScore(){
        return score;
    }
    /**
     * Visszaadja a játékidőt.
     *
     * @return A játékidő másodpercekben.
     */
    public int getSeconds(){
        return seconds;
    }

}
