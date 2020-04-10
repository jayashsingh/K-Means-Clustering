import java.util.Random;

public class Cluster 
{ 
	private Centroid centroid;
	private SinglyLinkedList clusterPoints;
	private int numPoints;
	
	public Cluster()
	{
		centroid = null;
		clusterPoints = new SinglyLinkedList();
		numPoints=0;
	}
	public void addPoint(double x, double y)
	{
		clusterPoints.addStart(x, y);
		numPoints++;
	}
	public void setCentroid(Centroid c)
	{
		centroid = c;
	}
	public Centroid getCentroid()
	{
		return centroid;
	}
	public double getCentroidX()
	{
		return centroid.getX();
	}
	public double getCentroidY()
	{
		return centroid.getY();
	}
	public int getNumPoints()
	{
		return numPoints;
	}
	public void setNumPoints(int i)
	{
		numPoints=i;
	}
	public SinglyLinkedList getPoints()
	{
		return clusterPoints;
	}
	public Centroid createInitialCentroid(double min, double max)
	{
		Random rand=new Random();
		double x=min +(max-min)*rand.nextDouble();
		double y=min +(max-min)*rand.nextDouble();
		centroid=new Centroid(x, y);
		return centroid;
		
	}
	
}
