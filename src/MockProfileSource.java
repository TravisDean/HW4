/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */
/*
 * MockProfileSource -- simple and very very poor example
 * of a ProfileDataSource class
 *
 * We hired a student from another university (we won't mention names) to
 * write us an example of a ProfileDataSource class that would implement all the
 * methods and return some data. Unfortunately, this person wrote us a really
 * really bad example of such a class. Sure, it has all the methods and returns
 * some sensible data, but it's written in a way that is very very inflexible
 * and hard to modify to use for any real testing.
 *
 * For your CS2110 homework,
 * you'll almost certainly want to write another class to replace this one that
 * uses Maps or some other means to let you test your code more effectively.
 * Maybe you'll want to read data from a file. (You certainly don't want to have
 * code like the code getCategoryValues() below!) Whatever you do, it should be
 * as simple as possible to let you test your other code so you know that you're
 * getting the answers you expect.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class MockProfileSource implements ProfileDataSource
{

    private boolean hasBeenInitialized = false;

    private String[] categories = { "book", "tv"};
    private String[] ids = { "id1", "id2", "id3" };
    private String[] names = { "name1", "name2", "name3" };
    private String[] groups = { "grp1", "grp2", "grp2" };
    private String[] booksForId1 = { "book1", "book2", "book3" };
    private String[] booksForId2 = { "book1", "book2", "book4", "book5" };
    private String[] tvForId1 = { "tv1", "tv2" };

    @Override
    public void init()
    {
        hasBeenInitialized = true;
    }

    @Override
    public Set<String> getIds()
    {
        checkInitialization();
        return new TreeSet<String>( Arrays.asList(ids) );
    }

    private void checkInitialization()
    {
        if ( !hasBeenInitialized ) {
            throw new RuntimeException(
                    "Error: attempt to use ProfileSource" +
                            " that has not been initialized.");
        }
    }

    @Override
    public Set<String> getCategories()
    {
        checkInitialization();
        return new TreeSet<String>( Arrays.asList(categories) );
    }

    @Override
    public String getName(String profId)
    {
        checkInitialization();
        List<String> tmpList = Arrays.asList(ids);
        int pos = tmpList.indexOf(profId);
        if ( pos == -1 ) {
            return null;
        }
        else {
            return names[pos];
        }
    }

    @Override
    public String getGroup(String profId)
    {
        checkInitialization();
        List<String> tmpList = Arrays.asList(ids);
        int pos = tmpList.indexOf(profId);
        if ( pos == -1 ) {
            return null;
        }
        else {
            return groups[pos];
        }
    }


    @Override
    public void close()
    {
        hasBeenInitialized = false;
    }

    @Override
    public Set<String> getGroups()
    {
        checkInitialization();
        return new TreeSet<String>( Arrays.asList(groups) );
    }

    @Override
    public Set<String> getGroupMembers(String grp)
    {
        checkInitialization();
        TreeSet<String> res = new TreeSet<String>();
        for (int i = 0; i < groups.length; ++i) {
            if ( groups[i].equals(grp) ) {
                res.add(ids[i]);
            }
        }
        return res;
    }


    @Override
    public Set<String> getCategoryValues(String profId, String cat)
    {
        checkInitialization();
        if ( cat.equals("book")) {
            if ( profId.equals("id1") || profId.equals("id3") ) {
            // id1 and id3 store the same data
                return new TreeSet<String>( Arrays.asList(booksForId1) );
            }
            else if ( profId.equals("id2") ) {
                return new TreeSet<String>( Arrays.asList(booksForId2) );
            }
            else {
                return null; // want to know about books for unknown id
            }
        }
        else if ( cat.equals("tv")) {
            if ( profId.equals("id1") || profId.equals("id3") ) {
            // id1 and id3 store the same data
                return new TreeSet<String>( Arrays.asList(tvForId1) );
            }
            else if ( profId.equals("id2") ) {
                return new TreeSet<String>();
                // if no tv shows for id2, return empty set
            }
            else {
                return null; // want to know about tv for unknown id
            }
        }
        else {
            return null;  // want to know about unknown category
        }
    }

}