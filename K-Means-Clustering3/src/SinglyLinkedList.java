public class SinglyLinkedList 
{
	public class Node
	{
		private double x;
		private double y;
		private Node pointer;
		
		public Node(double x, double y, Node pointer)
		{
			this.x = x;
			this.y = y;
			this.pointer = pointer;
		}
		
		public double getX()
		{
			return x;
		}
		public void setX(double x)
		{
			this.x = x;
		}
		public double getY()
		{
			return y;
		}
		public void setY(double y)
		{
			this.y = y;
		}
		public Node getpointer()
		{
			return pointer;
		}
		public void setpointer(Node pointer)
		{
			this.pointer = pointer;
		}
	}
	
	private Node start;
	private Node end;
	private double counter = 0;
	
	public void addStart(double x, double y)
	{
		start = new Node(x, y, start);

		if (start == null)
		{
			end = start;
		}
		
		counter++;
	}
	
	public void addEnd(double x, double y)
	{
		Node newNode = new Node(x, y, null);

		if (end == null)
		{
			end = newNode;
			start = end;
		}
		else
		{
			end.setpointer(newNode);
			end = newNode;
		}
		
		counter++;
	}
	
	public void removeEnd()
	{
		if (counter == 1)
		{
			start = null;
			end = null;
			counter--;
		}
		else
		{
			Node secondLast = start;
			while(secondLast.getpointer().getpointer() != null) 
			{
				secondLast = secondLast.pointer;
			}
			secondLast.pointer = null;
			counter--; 
		}
	}
	
	public Node getNode(int n)
	{
		Node current = start;
		
		for (int i = 0; i < n; i++)
		{
			if (n == counter)
			{
				current = end;
			}
			else 
			{	
				current = current.getpointer();	
			}
		}
		
		return current;
	}
	
	public double getDistance(Node point, Centroid c)
	{
		return Math.sqrt(Math.pow(c.getY() - point.getY(),2) + Math.pow(c.getX() - point.getX(), 2));
	}
}
