import java.util.ArrayList;
import java.util.HashMap;


public class FlowGraph {
	int[] X, Y, V;
	boolean bipartit = false;
	EdgeList E;
	public HashMap<Integer, ArrayList<Edge>> c;
	int s, t;
	int ctot = 0;
	public FlowGraph(int[] V, EdgeList E, HashMap<Integer, ArrayList<Edge>> capacities, int s, int t){
		this.V = V;
		this.E = E;
		this.c = capacities;
		this.s = s;
		this.t = t;
	}
	public FlowGraph(int[] X, int[] Y, EdgeList E, HashMap<Integer, ArrayList<Edge>> capacities, int s, int t){
		this.X = X;
		this.Y = Y;
		bipartit = true;
		this.E = E;
		this.c = capacities;
		this.s = s;
		this.t = t;
	}
	public void printProblem(Kattio io){
		
	}
	
}
