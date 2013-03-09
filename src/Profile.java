import java.util.*;

/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */
public class Profile {
    private final String name;
    private final String id;
    private final String group;
    private TreeMap<String, TreeSet<String>> interests;
    private final static double MATCHING_COEFF = 1.0;
    private final static double NON_MATCHING_COEFF = 0.25;

    public Profile(String id, String name, String group) {
        this.name = name;
        this.id = id;
        this.group = group;
        interests = new TreeMap<String, TreeSet<String>>();
    }

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

    public void addValues(String category, TreeSet<String> values) {
        if (interests.containsKey(category)) {
            interests.get(category).addAll(values);
        }
        else {
            interests.put(category, values);
        }
    }

    public int sharedInterestsCount(Profile prof2, String category) {
        // Clone to not modify original
        //noinspection unchecked
        Set<String> simCategory = (Set<String>)prof2.interests.get(category).clone();
        simCategory.retainAll(interests.get(category)); // Intersection
        return simCategory.size();
    }

    public int nonSharedInterestsCount(Profile prof2, String category) {
        Set<String> difCategory = prof2.interests.get(category);
        // Clone to not modify original.
        //noinspection unchecked
        Set<String> notShared = (TreeSet)interests.get(category).clone();
        notShared.removeAll(difCategory);       // Difference
        return notShared.size();
    }

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
