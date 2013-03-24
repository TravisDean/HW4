/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */


import java.util.*;


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
        
        System.out.println(app.findBestMatches(2));
    }
    
    public List<ProfileMatch> findBestMatches(String id, int num) {
        ArrayList<ProfileMatch> matchScores = new ArrayList<ProfileMatch>();
     	Profile p1 = profileMap.get(id);
    	
    	for (String person : profileMap.keySet()) {
    		if (person.equals(id)) {
    			continue;
    		}
    		
    		Profile p2 = profileMap.get(person);
    		
    		matchScores = new ArrayList<ProfileMatch>();
    		
    		matchScores.add(new ProfileMatch(id,person,p1.similarity(p2)));
    	}
    	
    	Collections.sort(matchScores,new SimilaritySort());
    	
    	List<ProfileMatch> bestMatches = new ArrayList<ProfileMatch>();
    	
    	for (int numI = 0; numI < num && numI < matchScores.size(); numI++) {
    		bestMatches.add(matchScores.get(numI));
    	}
    	
    	return bestMatches;
    }
    
    public List<ProfileMatch> findBestMatches(int num) {
    	ArrayList<ProfileMatch> matches = new ArrayList<ProfileMatch>();
    	ArrayList<String> keys = new ArrayList<String>();
    	keys.addAll(profileMap.keySet());
    	
    	for (int p1It = 0; p1It < profileMap.size() - 1; p1It++) {
    		Profile p1 = profileMap.get(keys.get(p1It));
    		for (int p2It = p1It + 1; p2It < profileMap.size(); p2It++) {
    			Profile p2 = profileMap.get(keys.get(p2It));
    			
    			matches.add(new ProfileMatch(p1.getId(),p2.getId(),p1.similarity(p2)));
    		}
    	}
    	
    	Collections.sort(matches,new SimilaritySort());
    	
    	List<ProfileMatch> bestMatches = new ArrayList<ProfileMatch>();
    	
    	for (int numBest = 0; numBest < num && numBest < matches.size(); numBest++) {
    		bestMatches.add(matches.get(numBest));
    	}

    	return bestMatches;
    }
    
    public List<ProfileMatch> findBestGroupMatches(String id, String group, int num) {
    	Profile p1 = profileMap.get(id);
    	
    	List<ProfileMatch> orderedMatches = findBestMatches(id,profileMap.size());
    	
    	List<ProfileMatch> bestGroupMatches = new ArrayList<ProfileMatch>();
    	
    	for (int match = 0; match < num && match < orderedMatches.size(); match++) {
    		Profile p2 = profileMap.get(orderedMatches.get(match));
    		
    		if (p2.getGroup().equals(group)) {
    			bestGroupMatches.add(orderedMatches.get(match));
    		}
    	}
    	
    	return bestGroupMatches;
    }
    
    
    public List<ProfileMatch> findBestGroupMatches(String group,int num) {
    	final int numPossMatches = (((profileMap.size() - 1) + 1)/2) * (profileMap.size() - 1);
 
    	List<ProfileMatch> orderedMatches = findBestMatches(numPossMatches);
    	
    	int numGroupMatches = 0;
    	List<ProfileMatch> bestGroupMatches = new ArrayList<ProfileMatch>();
    	
    	for (int match = 0; match < numPossMatches; match++) {
    		ProfileMatch currentMatch = orderedMatches.get(match);
    		Profile p1 = profileMap.get(currentMatch.getProfileId1());
    		Profile p2 = profileMap.get(currentMatch.getProfileId2());
    		
    		if (p1.getGroup().equals(p2.getGroup()) && p1.getGroup().equals(group)) {
    			bestGroupMatches.add(currentMatch);
    			numGroupMatches++;
    			
    			if (numGroupMatches == num) {
    				break;
    			}
    		}
    	}
    	
    	return bestGroupMatches;
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
        	
        	Profile profile = new Profile(pid,dataSource.getName(pid),dataSource.getGroup(pid));
        	
        	for (String category : categories) {
        		Set<String> values = dataSource.getCategoryValues(pid, category);
        		profile.addValues(category, values);
        	}
        	
        	profileMap.put(pid, profile);
        }
             
        dataSource.close(); // close up the ProfileDataSource before exiting
    }
    
    // need the other methods described in the assignment, of course!
}
