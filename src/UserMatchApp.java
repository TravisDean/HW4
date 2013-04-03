/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */


import java.util.*;


public class UserMatchApp
{
    private TreeMap<String, Profile> profileMap =
            new TreeMap<String, Profile>();

    public static void main(String[] args)
    {
        UserMatchApp app = new UserMatchApp();
        ProfileDataSource ps = new MockProfileSource();
        app.loadData(ps);

        // Test stuff with mock source.
        System.out.println("The current data:\n" + app.profileMap.toString());
        System.out.println("Best matches:\n" + app.findBestMatches(2));
        System.out.println("Best id1 Matches:\n" +
                app.findBestMatches("id1", 2));
        System.out.println("Best group1 matches:\n" +
                app.findBestGroupMatches("grp1", 2));
        System.out.println("Best group2 matches:\n" +
                app.findBestGroupMatches("grp2", 2));
        System.out.println("Best group2 and id1 matches:\n" +
                app.findBestGroupMatches("id1", "grp2", 2));
        System.out.println("Best group2 and id2 matches:\n" +
                app.findBestGroupMatches("id2", "grp2", 2));

        // Test stuff with the jar file resources.
        UserMatchApp jar = new UserMatchApp();
        ProfileDataSource csPS = new CS2110Profile();
        jar.loadData(csPS);
        System.out.println("Best matches:\n" +
                jar.findBestMatches(3));
        System.out.println("Best tjd2qj Matches:\n" +
                jar.findBestMatches("tjd2qj", 2));
        System.out.println("Best lab 102 matches:\n" +
                jar.findBestGroupMatches("102", 2));
        System.out.println("Best lab 102 and tjd2qj matches:\n" +
                jar.findBestGroupMatches("tjd2qj", "102", 2));
    }

    /**
     * Finds the Profiles that have the most similarity to the Profile with the
     * given ID.
     * @param id the Profile to match
     * @param num how many matches to return
     * @return a List with the best matches
     */
    public List<ProfileMatch> findBestMatches(String id, int num) {
        ArrayList<ProfileMatch> matchScores = new ArrayList<ProfileMatch>();
     	Profile p1 = profileMap.get(id);
    	
    	for (String person : profileMap.keySet()) {
    		if (person.equals(id)) {
    			continue;
    		}
    		Profile p2 = profileMap.get(person);
    		matchScores.add(new ProfileMatch(id, person, p1.similarity(p2)));
    	}
    	return cullList(matchScores, num);
    }

    /**
     * Finds the best matching profiles.
     * TODO: clean up to match rest of methods.
     * @param num number of matches to return
     * @return List of ProfileMatches
     */
    public List<ProfileMatch> findBestMatches(int num) {
    	ArrayList<ProfileMatch> matches = new ArrayList<ProfileMatch>();
    	ArrayList<String> keys = new ArrayList<String>();
    	keys.addAll(profileMap.keySet());

    	for (int p1It = 0; p1It < profileMap.size() - 1; p1It++) {
    		Profile p1 = profileMap.get(keys.get(p1It));
    		for (int p2It = p1It + 1; p2It < profileMap.size(); p2It++) {
    			Profile p2 = profileMap.get(keys.get(p2It));

    			matches.add(new ProfileMatch(p1.getId(),
                        p2.getId(), p1.similarity(p2)));
    		}
    	}

    	return cullList(matches, num);
    }

    /**
     * Finds the Profiles that best match the given Profile in the group.
     * Returns empty list if group is not valid with given parameters.
     * @param id Profile to match
     * @param group group to constrain matches to
     * @param num number of matches to return
     * @return List of ProfileMatches
     */
    public List<ProfileMatch> findBestGroupMatches(String id,
                                                   String group, int num) {
    	List<ProfileMatch> groupMatches =
                findBestGroupMatches(group, profileMap.size());
    	
    	List<ProfileMatch> idMatches = new ArrayList<ProfileMatch>();
    	
    	for (ProfileMatch profMatch : groupMatches) {
            Profile p1 = profileMap.get(profMatch.getProfileId1());
    		Profile p2 = profileMap.get(profMatch.getProfileId2());
    		
    		if (p1.getId().equals(id) || p2.getId().equals(id)) {
    			idMatches.add(profMatch);
    		}
    	}

    	return cullList(idMatches, num);
    }

    /**
     * Finds the best matching Profiles in the given group. Returns empty if
     * group is not valid with the given parameters.
     * @param group group to constrain to
     * @param num number of matches to find
     * @return List of ProfileMatches
     */
    public List<ProfileMatch> findBestGroupMatches(String group, int num) {
        List<ProfileMatch> orderedMatches = findBestMatches(profileMap.size());

        List<ProfileMatch> bestGroupMatches = new ArrayList<ProfileMatch>();

        for (ProfileMatch profMatch : orderedMatches) {
            String grp1 = profileMap.get(profMatch.getProfileId1()).getGroup();
            String grp2 = profileMap.get(profMatch.getProfileId2()).getGroup();
            if (grp1.equals(grp2) && grp1.equals(group)) {
                bestGroupMatches.add(profMatch);
            }
        }

        return cullList(bestGroupMatches, num);
    }

    /**
     * Loads data from a ProfileDataSource.
     * @param dataSource to load from
     */
    public void loadData(ProfileDataSource dataSource)
    {
        dataSource.init();
        Set<String> profIDs = dataSource.getIds();
        Set<String> categories = dataSource.getCategories();

        for (String pid : profIDs) {
        	Profile profile = new Profile(pid,
                    dataSource.getName(pid), dataSource.getGroup(pid));
        	
        	for (String category : categories) {
        		Set<String> values = dataSource.getCategoryValues(pid,
                        category);
        		profile.addValues(category, values);
        	}
        	
        	profileMap.put(pid, profile);
        }
             
        dataSource.close(); // close up the ProfileDataSource before exiting
    }

    /**
     * Sorts and cuts a list down to the given size. If num is not in the
     * correct range, entire list is returned.
     * @param list
     * @param num
     * @return
     */
    private List<ProfileMatch> cullList(List<ProfileMatch> list, int num) {
        Collections.sort(list, new SimilaritySort());
        if (num <= 0 || list.isEmpty()) {
            //System.out.println("Cull list called with empty parameters.");
            return new ArrayList<ProfileMatch>();
        }
        else if (num >= list.size()) {
            return list;
        }
        try {
            int start = list.size() - num;
            int end   = list.size();
            return list.subList(start, end);
        }
        catch (Exception e) {
            System.out.println("Cull list exception caught.");
            return list;
        }
    }
}
