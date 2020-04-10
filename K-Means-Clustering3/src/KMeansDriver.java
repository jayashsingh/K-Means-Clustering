public class KMeansDriver 
{
	public static void main(String[] args) 
	{
		KMeans driver=new KMeans();
		long startTime = System.currentTimeMillis();
		driver.initialize();
		driver.kMeanAlgorithm();
		System.out.println("Successful test");
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken ms(): "+(endTime-startTime));
	}
}