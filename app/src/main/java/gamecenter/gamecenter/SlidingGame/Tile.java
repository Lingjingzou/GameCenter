package gamecenter.gamecenter.SlidingGame;

import android.support.annotation.NonNull;

import java.io.Serializable;


/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() { return id; }

    public void setId(int i){this.id = i;}

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id the id
     *
     */
    Tile(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
