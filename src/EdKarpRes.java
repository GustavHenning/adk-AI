import java.util.HashMap;
import java.util.LinkedList;

public class EdKarpRes {

	private long flow;
	private int[][] posFlow;
	private int numV;
	private int s;
	private int t;
	private int numE;
	private LinkedList<Edge> res;

	public EdKarpRes(int numV, int s, int t, long flow, int numE,
			int[][] posFlow, LinkedList<Edge> res) {
		this.flow = flow;
		this.posFlow = posFlow;
		this.res = res;
		this.numV = numV;
		this.s = s;
		this.t = t;
		this.numE = numE;
	}

	public void printRes(Kattio io) {
		io.println(numV);
		io.println(s + " " + t + " " + flow);
		io.println(numE);
		for(Edge e : res){
			io.println(e.x + " " + e.y + " " + posFlow[e.x][e.y]);
		}
		io.flush();
	}
}
