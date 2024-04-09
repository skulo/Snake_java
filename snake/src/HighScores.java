
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;


public class HighScores{

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;
    /**
     * A HighScores osztály konstruktora.
     *
     * @param maxScores A maximális pontszámok száma az adatbázisban.
     * @throws SQLException Kivétel, ha hiba történik az adatbáziskapcsolat létrehozásakor.
     */
    public HighScores(int maxScores) throws SQLException{
        this.maxScores = maxScores;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "root");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/highscores";
        connection = DriverManager.getConnection(dbURL, connectionProps);


        String insertQuery = "INSERT INTO HIGHSCORES (NAME, SCORE, SECONDS) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE SCORE=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }
    /**
     * Visszaadja a legmagasabb pontszámok listáját.
     *
     * @return Egy lista, amely tartalmazza a legmagasabb pontszámokat HighScore objektumok formájában.
     * @throws SQLException Kivétel, ha hiba történik az adatbázis lekérdezésekor.
     */
    public ArrayList<HighScore> getHighScores() throws SQLException{
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()){
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            int seconds = results.getInt("SECONDS");
            highScores.add(new HighScore(name, score, seconds));
        }
        sortHighScores(highScores);
        return highScores;
    }
    /**
     * Hozzáad egy új pontszámot az adatbázishoz.
     *
     * @param name    A játékos neve.
     * @param score   A játékos pontszáma.
     * @param seconds A játékidő másodpercekben.
     * @return A hozzáadott pontszám.
     * @throws SQLException Kivétel, ha hiba történik az adatbázis módosításakor.
     */
    public int putHighScore(String name, int score, int seconds) throws SQLException{
        ArrayList<HighScore> highScores = getHighScores();
        if (highScores.size() < maxScores){
            insertScore(name, score, seconds);
        } else{
            int leastScore = highScores.get(highScores.size() - 1).getScore();
            if (leastScore < score) {
                deleteScores(leastScore);
                insertScore(name, score, seconds);
            }
        }
        return score;
    }

    /**
     * Rendezi a legmagasabb pontszámokat tartalmazó listát csökkenő sorrendben.
     *
     * @param highScores A rendezendő HighScore objektumokat tartalmazó lista.
     */
    private void sortHighScores(ArrayList<HighScore> highScores){
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }
    /**
     * Egy új pontszámot rak az adatbázisba.
     *
     * @param name    A játékos neve.
     * @param score   A játékos pontszáma.
     * @param seconds A játékidő másodpercekben.
     * @throws SQLException Kivétel, ha hiba történik az adatbázis módosításakor.
     */
    private void insertScore(String name, int score, int seconds) throws SQLException{
        insertStatement.setString(1, name);
        insertStatement.setInt(2, score);
        insertStatement.setInt(3, seconds);
        insertStatement.executeUpdate();
    }

    /**
     * Törli a megadott pontszámnál alacsonyabb pontszámokat az adatbázisból.
     *
     * @param score A küszöbérték, aminél alacsonyabb pontszámú adatok törlésre kerülnek.
     * @throws SQLException Kivétel, ha hiba történik az adatbázis módosításakor.
     */
    private void deleteScores(int score) throws SQLException{
        deleteStatement.setInt(1, score);
        deleteStatement.executeUpdate();
    }
}

