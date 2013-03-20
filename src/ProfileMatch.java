/**
 * Name: Travis Dean and Alex Martinez
 * Unix ID: tjd2qj and cam4sn
 * Assignment: HW4
 * Lab Section: 102
 */

public class ProfileMatch
{
    private String profileId1;
    private String profileId2;
    private double similarity;

    public ProfileMatch(String profileId1, String profileId2, double similarity)
    {
        this.profileId1 = profileId1;
        this.profileId2 = profileId2;
        this.similarity = similarity;
    }

    public String getProfileId1()
    {
        return profileId1;
    }

    public String getProfileId2()
    {
        return profileId2;
    }

    public double getSimilarity()
    {
        return similarity;
    }

    public String toString() {
        return "(" + profileId1 + "," + profileId2 + "," + similarity + ")";
    }


}