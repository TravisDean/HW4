import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static junit.framework.Assert.assertEquals;

/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 *
 * TODO: WebCat style.
 */
public class ProfileTest {
    Profile travis = new Profile("tjd2qj", "Travis", "UVA");
    Profile alex = new Profile("cam4sn", "Alex", "UVA");

    @Before
    public void setUp() throws Exception {
        TreeSet<String> travisArtists = new TreeSet<String>();
        travisArtists.add("Monet");
        travisArtists.add("Bach");
        travisArtists.add("Wagner");
        travis.addValues("artist", travisArtists);
        TreeSet<String> travisBooks = new TreeSet<String>();
        travisBooks.add("Godel, Escher, Bach");
        travis.addValues("book", travisBooks);
        TreeSet<String> travisMovies = new TreeSet<String>();
        travisMovies.add("Requiem for a Dream");
        travisMovies.add("Moonrise Kingdom");
        travis.addValues("movie", travisMovies);

        TreeSet<String> alexArtists = new TreeSet<String>();
        alexArtists.add("Wagner");
        alex.addValues("artist", alexArtists);
        TreeSet<String> alexBooks = new TreeSet<String>();
        alexBooks.add("Kingpin");
        alexBooks.add("Godel, Escher, Bach");
        alex.addValues("book", alexBooks);
        TreeSet<String> alexMovies = new TreeSet<String>();
        alexMovies.addAll(travisMovies);
        alexMovies.add("Scarface");
        alex.addValues("movie", alexMovies);
    }

    @Test
    public void testAddValue() throws Exception {
        Profile person = new Profile("tes1id", "Test", "group");
        person.addValue("book", "GED");
        assertEquals("Profile{name='Test', id='tes1id', group='group'," +
                " interests={book=[GED]}}", person.toString());
    }

    @Test
    public void testAddValues() throws Exception {
        Profile person = new Profile("tes1id", "Test", "group");
        TreeSet<String> books = new TreeSet<String>();
        books.add("GED");
        books.add("MYB");
        person.addValues("book", books);
        assertEquals("Profile{name='Test', id='tes1id', group='group'," +
                " interests={book=[GED, MYB]}}", person.toString());

    }

    @Test
    public void testSharedInterestsCount() throws Exception {
        int artistsSharedWithAlex = travis.sharedInterestsCount(alex, "artist");
        assertEquals(1, artistsSharedWithAlex);
        int artistsSharedWithTravis =
                alex.sharedInterestsCount(travis, "artist");
        assertEquals(1, artistsSharedWithTravis);

        int booksSharedWithAlex = travis.sharedInterestsCount(alex, "book");
        assertEquals(1, booksSharedWithAlex);
        int booksSharedWithTravis = alex.sharedInterestsCount(travis, "book");
        assertEquals(1, booksSharedWithTravis);

        int moviesSharedWithAlex = travis.sharedInterestsCount(alex, "movie");
        assertEquals(2, moviesSharedWithAlex);
        int moviesSharedWithTravis = alex.sharedInterestsCount(travis, "movie");
        assertEquals(2, moviesSharedWithTravis);
    }

    @Test
    public void testNonSharedInterestsCount() throws Exception {
        int artistsDiffWithAlex =
                travis.nonSharedInterestsCount(alex, "artist");
        assertEquals(2, artistsDiffWithAlex);
        int artistsDiffWithTravis =
                alex.nonSharedInterestsCount(travis, "artist");
        assertEquals(0, artistsDiffWithTravis);

        int booksDiffWithAlex = travis.nonSharedInterestsCount(alex, "book");
        assertEquals(0, booksDiffWithAlex);
        int booksDiffWithTravis = alex.nonSharedInterestsCount(travis, "book");
        assertEquals(1, booksDiffWithTravis);

        int moviesDiffWithAlex = travis.nonSharedInterestsCount(alex, "movie");
        assertEquals(0, moviesDiffWithAlex);
        int moviesDiffWithTravis =
                alex.nonSharedInterestsCount(travis, "movie");
        assertEquals(1, moviesDiffWithTravis);
    }

    @Test
    public void testSimilarity() throws Exception {
        assertEquals(3.0, travis.similarity(alex), 0.001);
    }

    @Test
    public void testCaseIgnore() throws Exception {
        Profile p1 = new Profile("test", "test", "test");
        Profile p2 = new Profile("test2", "test2", "test2");
        p1.addValue("case", "testvalue");
        p2.addValue("case", "TESTVALUE");
        assertEquals(1, p1.sharedInterestsCount(p2, "case"));

        TreeSet<String> lowerCaseVals = new TreeSet<String>();
        lowerCaseVals.add("test1");
        lowerCaseVals.add("test2");
        p1.addValues("case 2", lowerCaseVals);
        TreeSet<String> upperCaseVals = new TreeSet<String>();
        upperCaseVals.add("TEST1");
        upperCaseVals.add("Test2");
        p2.addValues("case 2", upperCaseVals);
        assertEquals(2, p1.sharedInterestsCount(p2, "case 2"));
    }

    @Test
    public void testNonOverlappingCategories() throws Exception {
        Profile p1 = new Profile("test", "test", "test");
        Profile p2 = new Profile("test2", "test2", "test2");
        p1.addValue("book", "GED");
        p1.addValue("book", "Code Complete");
        p2.addValue("book", "GED");
        assertEquals(0.75, p1.similarity(p2), 0.001);

        p1.addValue("game", "DOTA");
        assertEquals(0.5, p1.similarity(p2));
        p2.addValue("class", "SWDEV");
        assertEquals(0.25, p1.similarity(p2));
    }
}
