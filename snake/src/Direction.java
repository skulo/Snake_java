public enum Direction{
    /**
     * Felfelé irány.
     */
    UP,
    /**
     * Lefelé irány.
     */
    DOWN,
    /**
     * Balra irány.
     */
    LEFT,
    /**
     * Jobbra irány.
     */
    RIGHT;

    /**
     * Az ellentétes irányt adja vissza a jelenlegi irányhoz képest.
     *
     * @return Az ellentétes irány.
     */
    public Direction opposite(){
        switch (this){
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return this;
        }
    }
}