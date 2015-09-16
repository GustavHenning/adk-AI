package role;
public class Edge {
	int x, y;
	boolean inverse = false;

	public Edge(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Edge inverse(){
		inverse = true;
		return this;
	}
}