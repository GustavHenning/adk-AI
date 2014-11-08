
public class Edge {

	public int x, y;
	
	public Edge(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return x + " " + y;
	}
	public boolean equals(Object o){
		Edge e = (Edge) o;
		return e.x == this.x && e.y == this.y;
		
	}
}
