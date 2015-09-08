
import java.util.LinkedList;
import java.util.List;

public class EdgeList {
	public LinkedList<Edge>[] edges;
	public int numEdges = 0;

	@SuppressWarnings("unchecked")
	public EdgeList(int v) {
		edges = new LinkedList[v];
		for (int i = 0; i < v; i++) {
			edges[i] = new LinkedList<Edge>();
		}
	}

	public void add(int x, int y, int c) {
		numEdges++;
		edges[x].add(new Edge(y,c));
		edges[y].add(new Edge(x,-c).setInverse(true));
	}
	
	public List<Edge> listByX(int x){
		return edges[x];
	}
}
