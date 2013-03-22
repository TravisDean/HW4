/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */
import java.util.Set;

public interface ProfileDataSource
{
    // must be called once before other methods to do any initialization of the data source
    public void init();

    // should be called when done with the data source to clean up any resources
    public void close();

    // returns all the profile IDs stored in this data source
    public Set<String> getIds();

    // returns all the category values stored in this data source
    public Set<String> getCategories();

    // for a given profile ID, return its name. (return null if ID not in data source)
    public String getName(String profId);

    // for a given profile ID, return its group. (return null if ID not in data source)
    public String getGroup(String profId);

    // for a given profile ID and a category value, return all the category-values stored for that ID.
    // (return null if ID or category not in data source, and return empty Set if no values stored for that category)
    public Set<String> getCategoryValues(String profId, String category);

    // returns all the group values stored in this data source
    public Set<String> getGroups();

    // for a given group value, return all the profile IDs that are members of that group.
    // (return null if group parameter not stored in data source)
    public Set<String> getGroupMembers(String group);

}