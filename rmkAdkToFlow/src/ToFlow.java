
import java.util.ArrayList;

public class ToFlow {
	int X, Y, E, V, C, sink, source;
	Kattio io;
	ArrayList<Edge>[] edges;
	Edge currentEdge, inverseEdge;
	
	public ToFlow(){
		io = new Kattio(System.in, System.out);
		X = io.getInt();
		Y = io.getInt();
		V = X + Y;
		E = io.getInt();
		io.flush();
		C = 1;
		source = 0;
		sink = V + 1;
		
		edges = new ArrayList[V + 2]; /* + 2 because source | X, Y | sink */
		
		/* Create sink, source */
		for(int i = 0; i < sink; i++){
			if(i <= X){
				createEdge(source, i);
			} else {
				createEdge(i, sink);
			}
		}
		
		for(int i = 0; i < E; i++){
			createEdge(io.getInt(), io.getInt());
			io.flush();
		}
		
		
	}
	
	void createEdge(int x, int y){
		/* Init neighbor list */
		if (edges[x] == null)
			edges[x] = new ArrayList<Edge>();
		/* Backwards too */
		if (edges[y] == null)
			edges[y] = new ArrayList<Edge>();
		/* Create edges */
		currentEdge = new Edge(x, y, C);
		inverseEdge = new Edge(y, x, 0);
		/* Refer them */
		currentEdge.inverse = inverseEdge;
		inverseEdge.inverse = currentEdge;
		/* Add the new edges */
		edges[x].add(currentEdge);
		edges[y].add(inverseEdge);
	}
}
