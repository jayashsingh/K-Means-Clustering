import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class KMeans {
	int numClusters;
	ArrayList<Cluster> clusters= new ArrayList<Cluster>();
	SinglyLinkedList dataSet=new SinglyLinkedList();
	int min;
	int max;
	int n;
	int dataMode;
	int inputX;
	int inputY;
	int inputClusterX;
	int inputClusterY;
	int graphOption;
	
	//Init method to create and initialize each point and clusters/centroids
	public void initialize()
	{
		//input and random number generation handlers
		Scanner input=new Scanner(System.in);
		Random rand = new Random();
		
		//Setting up basics of the algorithm
		System.out.println("Please enter the number of clusters (K) you want to run K Means with");
		numClusters=input.nextInt();
		
		System.out.println("Please enter the number of points you would like to run K Means with");
		n=input.nextInt();
		
		//Asking user for optional graphical portion
		do
		{
			System.out.println("Please enter 1 if you would like to show a graphical representation of this algorithm, or 2 if you would like only a text representation");
			graphOption=input.nextInt();
			if(graphOption!=1&&graphOption!=2)
			{
				System.out.println("Invalid input please try again.");
			}
		}while(graphOption!=1&&graphOption!=2);
		
		//Asking user how they want to generate the data
		do
		{
			System.out.println("Please enter 1 if you would like to define your own data set or 2 if you would like to random generate the data set");
			dataMode=input.nextInt();
			if(dataMode!=1&&dataMode!=2)
			{
				System.out.println("Invalid input please try again.");
			}
		}while(dataMode!=1&&dataMode!=2);
		
		//Asking for data points or generating randomly based on option selected
		if(dataMode==1)
		{
			for(int i=0;i<n;i++)
			{
				System.out.println("Enter X value for point "+(i+1));
				inputX=input.nextInt();
				System.out.println("Enter Y value for point "+(i+1));
				inputY=input.nextInt();
				dataSet.addStart(inputX, inputY);
			}
			
			for(int i=0;i<numClusters;i++)
			{
				clusters.add(new Cluster());
				System.out.println("Set X value for Initial Centroid of Cluster #"+(i+1));
				inputClusterX=input.nextInt();
				System.out.println("Set Y value for Initial Centroid of Cluster #"+(i+1));
				inputClusterY=input.nextInt();
				Centroid c= new Centroid (inputClusterX, inputClusterY);
				clusters.get(i).setCentroid(c);
			}
		}
		else if (dataMode==2)
		{
			System.out.println("Please enter min value for x and y coordinate values");
			min = input.nextInt();
			
			System.out.println("Please enter max value for x and y coordinate values");
			max = input.nextInt();
			
			//Creating Random Points
			for(int i=0;i<n;i++)
			{
				dataSet.addStart(min +(max-min)*rand.nextDouble(), min +(max-min)*rand.nextDouble());
			}
			
			//Creating Clusters
			for(int i=0;i<numClusters;i++)
			{
				clusters.add(new Cluster());
				(clusters.get(i)).createInitialCentroid(min+(max-min)*rand.nextDouble(), min+(max-min)*rand.nextDouble());
			}
		}
		input.close();
		//Outputting Initial randomly assigned centroids
		for(int i = 0; i<numClusters;i++)
		{
			System.out.println("Initial Centroids: ");
			System.out.println(clusters.get(i).getCentroidX()+", "+clusters.get(i).getCentroidY());
		}
		
	}
	
	//Main algorithm
	public void kMeanAlgorithm() 
	{
		//Counting iterations
		int iter=1;
		//Boolean to check if finished
		boolean complete = false;
		//Keeping track of previous centroids from last iterations
		ArrayList<Centroid> previousCentroids=new ArrayList<Centroid>();
		
		
		while(!complete)
		{
			
			//Keeping track of previous Clusters
			if(iter==1)
			{
				for(int i=0;i<numClusters;i++)
				{
					previousCentroids.add(clusters.get(i).getCentroid());
				}
			}
			else
			{
				for(int i=0;i<numClusters;i++)
				{
					previousCentroids.set(i, clusters.get(i).getCentroid());
				}
			}
			emptyClusters();
			//Assigning Clusters for each point
			assignClusters();
			//Assigning new Centroids
			assignCentroids();
			if(!complete)
			{
				System.out.println("\n***ITERATION: "+iter+"***");
				for(int i=0;i<numClusters;i++)
				{
					System.out.print("\nCluster "+(i+1)+":");
					System.out.print(" "+clusters.get(i).getNumPoints()+" points");
					System.out.println("\nCentroid: ("+clusters.get(i).getCentroidX()+","+clusters.get(i).getCentroidY()+")");
					
				
				}
			}
			
			//OPTIONAL GRAPHING PORTION
			if(graphOption==1)
			{
				ArrayList<XYSeries> graphingClusters=new ArrayList<XYSeries>();
				XYSeriesCollection graphData=new XYSeriesCollection();
				for (int i=0;i<numClusters;i++)
				{
					graphingClusters.add(new XYSeries("Cluster "+(i+1)));
					for(int j=0;j<clusters.get(i).getNumPoints();j++)
					{
						graphingClusters.get(i).add(clusters.get(i).getPoints().getNode(j).getX(), clusters.get(i).getPoints().getNode(j).getY());
					}
					graphData.addSeries(graphingClusters.get(i));
				}
				JFreeChart graph=ChartFactory.createScatterPlot("Iteration "+iter, "X", "Y", graphData);
				JFrame frame = new JFrame();
				if(iter==1)
				{
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				frame.setBounds(100, 100, 1000, 800);
				ChartPanel chartPanel = new ChartPanel(graph);
			    chartPanel.setPreferredSize(new java.awt.Dimension(800, 800));
			    frame.setContentPane(chartPanel);
			    chartPanel.setVisible(true);
			    frame.setVisible(true);
			}
			
			iter++;
			
			for(int i=0;i<numClusters;i++)
			{
				if(previousCentroids.get(i).getX()==clusters.get(i).getCentroidX()&&previousCentroids.get(i).getY()==clusters.get(i).getCentroidY())
				{
					complete=true;
				}
				else
				{
					complete=false;
				}
			}
			
			
		}
		
		
	}
	//Asigning clusters function
	public void assignClusters()
	{
		//Holding min distance
		double minDistance = max;
		double distance;
		Cluster currentCluster = new Cluster();
		//Iterating through all points and clusters and assigning based on min distance to each cluster
		for(int i=0;i<n;i++)
		{
			minDistance=max;
			for (int j=0;j<numClusters;j++)
			{
				distance=dataSet.getDistance(dataSet.getNode(i), clusters.get(j).getCentroid());
				if(distance<minDistance)
				{
					minDistance=distance;
					currentCluster=clusters.get(j);
				}
			}
			currentCluster.addPoint(dataSet.getNode(i).getX(), dataSet.getNode(i).getY());
		}
		System.out.println(clusters.get(0).getNumPoints());
	}
	
	//Generating new centroids for each cluster
	public void assignCentroids()
	{
		//Iterating through all the point in each cluster, then taking mean to find new centroid
		for (int i=0;i<numClusters;i++)
		{
			double sumOfX=0;
			double sumOfY=0;
			int numPoints=clusters.get(i).getNumPoints();
			
			for (int j=0;j<numPoints;j++)
			{
				sumOfY = sumOfY + clusters.get(i).getPoints().getNode(j).getY();
				sumOfX = sumOfX + clusters.get(i).getPoints().getNode(j).getX();
			}
			
			if(numPoints!=0)
			{
				Centroid newCentroid = new Centroid(sumOfX/numPoints,sumOfY/numPoints);
				clusters.get(i).setCentroid(newCentroid);
			}
			else
			{
				Centroid newCentroid = new Centroid(0,0);
				clusters.get(i).setCentroid(newCentroid);
			}
		}
	}
	//Function to empty the clusters out
	public void emptyClusters()
	{
		for(int i = 0;i<numClusters;i++)
		{
			while(clusters.get(i).getNumPoints()>0)
			{
				clusters.get(i).getPoints().removeEnd();
				clusters.get(i).setNumPoints(clusters.get(i).getNumPoints()-1);
				
			}	
		}
	}
}

