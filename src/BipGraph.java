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
		for(Integer x : E.edges.keySet()){
			ArrayList<Integer> ys = E.edges.get(x);
			for(Integer y : ys){
				io.println(x + " " + y);
			}
		}
		io.flush();
	}
	
}
