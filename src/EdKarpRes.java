import java.util.HashMap;

public class EdKarpRes {

	private long flow;
	private int[][] posFlow;
	private int numV;
	private int s;
	private int t;
	private int numE;

	public EdKarpRes(int numV, int s, int t, long flow, int numE,
			int[][] posFlow) {
		this.flow = flow;
		this.posFlow = posFlow;
		this.numV = numV;
		this.s = s;
		this.t = t;
		this.numE = numE;
	}

	public void printRes(Kattio io) {
		io.println(numV);
		io.println(s + " " + t + " " + flow);
		io.println(numE);
		for (int j = 0; j < posFlow.length; j++) {
			for (int i = 0; i < posFlow.length; i++) {
				if (posFlow[i][j] != 0) {
					io.println(j + " " + i + " " + posFlow[i][j]);
				}
			}
		}
		io.flush();
	}
}
