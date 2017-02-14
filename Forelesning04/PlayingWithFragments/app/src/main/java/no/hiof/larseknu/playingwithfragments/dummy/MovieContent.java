package no.hiof.larseknu.playingwithfragments.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieContent {

    /**
     * An array of movie items.
     */
    public static List<MovieItem> MOVIES = new ArrayList<>();


    public static Map<Integer, MovieItem> MOVIE_MAP = new HashMap<Integer, MovieItem>();

    // Adding some static sample movies
    static {
        addItem(new MovieItem(1, "The Matrix", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."));
        addItem(new MovieItem(2, "The Hobbit: The Desolation of Smaug", "The dwarves, along with Bilbo Baggins and Gandalf the Grey, continue their quest to reclaim Erebor, their homeland, from Smaug. Bilbo Baggins is in possession of a mysterious and magical ring."));
        addItem(new MovieItem(3, "The Avengers", "Nick Fury of S.H.I.E.L.D. assembles a team of superhumans to save the planet from Loki and his army."));
        addItem(new MovieItem(4, "Reign Over Me", "A man who lost his family in the September 11 attack on New York City runs into his old college roommate. Rekindling the friendship is the one thing that appears able to help the man recover from his grief."));
        addItem(new MovieItem(5, "Into The Wild", "After graduating from Emory University, top student and athlete Christopher McCandless abandons his possessions, gives his entire $24,000 savings account to charity and hitchhikes to Alaska to live in the wilderness. Along the way, Christopher encounters a series of characters that shape his life."));
        addItem(new MovieItem(6, "Wall-E", "In the distant future, a small waste collecting robot inadvertently embarks on a space journey that will ultimately decide the fate of mankind."));
    }

    private static void addItem(MovieItem item) {
        MOVIES.add(item);
        MOVIE_MAP.put(item.id, item);
    }

    /**
     * A movie item representing a movie content.
     */
    public static class MovieItem {
        public int id;
        public String content;
        public String description;

        public MovieItem(int id, String content, String description) {
            this.id = id;
            this.content = content;
            this.description = description;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
