
public class Edge {

	public int y, capacity;
	public boolean counted = false;
	public boolean inverse = false;
	
	public Edge(int y, int capacity){
		this.y = y;
		this.capacity = capacity;
	}
	
	public String toString(){
		return y + " " + capacity;
	}
	public boolean equals(Object o){
		Edge e = (Edge) o;
		return e.y == this.y && e.capacity == this.capacity;
		
	}

	public Edge setInverse(boolean inverse) {
		this.inverse = inverse;
		return this;
	}
}
