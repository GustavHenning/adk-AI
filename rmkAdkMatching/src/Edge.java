
public class Edge {
	public int id, inverseId;
	public int x, y;
	public int cap, capFlowDif, flow;
	public Edge inverse;
	
	Edge(int x, int y, int cap){
		this.x = x;
		this.y = y;
		this.cap = cap;
		capFlowDif = this.cap;
		flow = 0;
	}
	
}