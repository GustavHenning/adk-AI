import java.util.ArrayList;
import java.util.LinkedList;


public class BipGraph {
	public int[] X;
	public int[] Y;
	public EdgeList E;
	
	public BipGraph(int[] X, int[] Y, EdgeList E){
		this.X = X;
		this.Y = Y;
		this.E = E;
	}
	
	public void printProblem(Kattio io){
		io.println(X.length + " " + Y.length);
		io.println(E.numEdges);
		for (int x = 0; x < E.edges.length; x++) {
			for (Edge y : E.listByX(x)) {
				io.println(x + " " + y.a);
			}
		}
		io.flush();
	}
	
}
