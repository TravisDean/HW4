import java.util.Comparator;

public class SimilaritySort implements Comparator<ProfileMatch> {
  @Override
	public int compare(ProfileMatch p1, ProfileMatch p2) {
		return Double.compare(p1.getSimilarity(), p2.getSimilarity());
	}
}
