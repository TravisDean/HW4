/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */

import java.util.Set;
import java.util.TreeMap;


public class UserMatchApp
{

    private TreeMap<String,Profile> profileMap = new TreeMap<String, Profile>();

    public static void main(String[] args)
    {
        // Here's some example code in main() showing how to use a UserMatchApp object.
        // Our JUnit testing code will work this way, i.e. it will create an instance,
        // and then call loadData() on that instance with our testing data-source.
        // Then we'll test your methods with our data.

        UserMatchApp app = new UserMatchApp();
        ProfileDataSource ps = new MockProfileSource();
        app.loadData(ps);


    }

    public void loadData(ProfileDataSource dataSource)
    {
        dataSource.init();
        Set<String> profIDs = dataSource.getIds();
        Set<String> categories = dataSource.getCategories();
        Set<String> groups = dataSource.getGroups();

        for (String pid : profIDs) {
            // need to create a new Profile, so complete the following line somehow:
            // Profile newProfile = new Profile( ????

            // Next, somehow get info about category values in the data-source that
            // we should store in the profile for the given profile-ID.

            // And of course, we need to make sure this is stored into the profileMap somehow.
        }

        dataSource.close(); // close up the ProfileDataSource before exiting
    }

    // need the other methods described in the assignment, of course!

}