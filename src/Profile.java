import java.util.*;

/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 *
 * Todo: Fix for WebCat style.
 */
public class Profile {
    private final String name;
    private final String id;
    private final String group;
    private TreeMap<String, TreeSet<String>> interests;
    private final static double MATCHING_COEFF = 1.0;
    private final static double NON_MATCHING_COEFF = 0.25;

    /**
     * Create a new Profile.
     * @param id ID to use
     * @param name name
     * @param group group that it belongs to
     */
    public Profile(String id, String name, String group) {
        this.name = name;
        this.id = id;
        this.group = group;
        interests = new TreeMap<String, TreeSet<String>>();
    }

    /**
     * Adds the specified value to the profile's
     * interests under the given category.
     * @param category category of value
     * @param value value to add
     */
    public void addValue(String category, String value) {
        if (interests.containsKey(category)) {
            interests.get(category).add(value);
        }
        else {
            TreeSet<String> values = new TreeSet<String>();
            values.add(value);
            interests.put(category, values);
        }
    }
    /**
     * Adds the specified values to the category.
     * @param category category of values
     * @param values values to add
     */
    public void addValues(String category, TreeSet<String> values) {
        if (interests.containsKey(category)) {
            interests.get(category).addAll(values);
        }
        else {
            interests.put(category, values);
        }
    }

    /**
     * Gets the number of interests shared between this profile and the given
     * one, in the given category. Ignores case when comparing.
     * TODO: ignore case
     * @param prof2 the other profile to compare
     * @param category the category to check
     * @return number of shared interests
     */
    public int sharedInterestsCount(Profile prof2, String category) {
        // Clone to not modify original
        Set<String> origCategory = toLowerCase(interests.get(category));
        @SuppressWarnings("unchecked") Set<String> simCategory =
                toLowerCase((Set<String>)prof2.interests.get(category).clone());
        simCategory.retainAll(origCategory); // Intersection
        return simCategory.size();
    }

    /**
     * Gets the number of interests in this Profile that the given
     * Profile does not share, in the given category. Ignores case when
     * comparing.
     * @param prof2 the other profile to compare
     * @param category the category to check
     * @return number of not shared interests
     */
    public int nonSharedInterestsCount(Profile prof2, String category) {
        Set<String> difCategory = toLowerCase(prof2.interests.get(category));
        // Clone to not modify original.
        @SuppressWarnings("unchecked") Set<String> notShared =
                toLowerCase((TreeSet)interests.get(category).clone());
        notShared.removeAll(difCategory);       // Difference
        return notShared.size();
    }

    /**
     * Calculate a similarity score between this Profile and the given one.
     * Uses constants to determine weight of matching and non-matching.
     * @param prof2 the other profile
     * @return similarity score
     */
    public double similarity(Profile prof2) {
        double similarity = 0;
        for (String category : interests.keySet()) {
            double sim =
                MATCHING_COEFF * sharedInterestsCount(prof2, category)
                - NON_MATCHING_COEFF * nonSharedInterestsCount(prof2, category)
                - NON_MATCHING_COEFF * prof2.nonSharedInterestsCount(this, category);
            similarity += sim;
            // Testing code:
//            System.out.println(category + ": " + sim);
//            System.out.println("\tMatch: " + sharedInterestsCount(prof2, category));
//            System.out.println("\tNot m: -0.25*" + nonSharedInterestsCount(prof2, category));
//            System.out.println("\tNot m: -0.25*" + prof2.nonSharedInterestsCount(this, category));
        }
        return  similarity;
    }

    private Set<String> toLowerCase(Set<String> strings) {
        TreeSet<String> lowerCased = new TreeSet<String>();
        for (String s : strings) { lowerCased.add(s.toLowerCase()); }
        return lowerCased;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Profile");
        sb.append("{name='").append(name).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", interests=").append(interests);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Profile profile = (Profile) o;

        return id.equals(profile.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
