import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	/**
	 * Example: 2 3 4 X = [1,2] Y = [3,4,5]
	 * 
	 * 
	 */
	public static BipGraph readMatchingProblem(Kattio io) {
		int[] X = new int[io.getInt()];
		int[] Y = new int[io.getInt()];
		/* Fill X */
		for (int i = 1; i < X.length + 1; i++) {
			X[i - 1] = i;
		}
		/* Fill Y */
		for (int i = X.length; i < X.length + Y.length; i++) {
			Y[i - X.length] = i;
		}
		/* Read edges */
		EdgeList E = new EdgeList();
		int numEdges = io.getInt();
		for (int i = 0; i < numEdges; i++) {
			/* */
			int x = io.getInt();
			int y = io.getInt();
			E.add(x, y);
		}
		return new BipGraph(X, Y, E);
	}

	/**
	 * Reduces a matching problem to a flow problem
	 * 
	 * @param X
	 * @param Y
	 */
	public static FlowGraph matchingToFlow(BipGraph bg) {
		int[] fX = new int[bg.X.length + 1];
		int[] fY = new int[bg.Y.length + 1];

		/* translate vertices */
		for (int i = 0; i < fX.length; i++) {
			fX[i] = i + 1;
		}
		for (int i = 0; i < fY.length; i++) {
			fY[i] = fX.length + i + 1;
		}

		/* translate edges */
		EdgeList fE = new EdgeList();
		/* add e(x,s) to fE */
		for (int i = 1; i < fX.length; i++) {
			fE.add(fX[0], fX[i]);
		}
		/* add E to fE */
		for (Integer x : bg.E.edges.keySet()) {
			ArrayList<Integer> ys = bg.E.edges.get(x);
			for (Integer y : ys) {
				fE.add(x + 1, y + 1);
			}
		}
		/* add e(y,t) to S */
		for (int i = 0; i < fY.length - 1; i++) {
			fE.add(fY[i], fY[fY.length - 1]);
		}
		/* create capacity */
		HashMap<Integer, ArrayList<Edge>> cap = new HashMap<Integer, ArrayList<Edge>>();
		for (Integer x : fE.edges.keySet()) {
			ArrayList<Integer> ys = fE.edges.get(x);
			for (Integer y : ys) {
				ArrayList<Edge> e = cap.get(x);
				if (e == null) {
					e = new ArrayList<Edge>();
					e.add(new Edge(y, 1));
					cap.put(x, e);
				} else {
					e.add(new Edge(y, 1));
					cap.put(x, e);
				}
			}
		}

		return new FlowGraph(fX, fY, fE, cap, 1, fY[fY.length - 1]);
	}

	/**
	 * Translates a flow solution to a matching solution.
	 * 
	 * @param fg
	 * @return
	 */
	public static BipGraph flowToMatching(FlowGraph fg) {

		return new BipGraph(null, null, null);
	}

	public static FlowGraph readFlowProblem(Kattio io) {
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int numE = io.getInt();
		
		int[] V = new int[v];
		for(int i = 0; i < v; i++){
			V[i] = i+1;
		}
		
		EdgeList E = new EdgeList();	
		
		HashMap<Integer, ArrayList<Edge>> cap = new HashMap<Integer, ArrayList<Edge>>();
		for (int i = 0; i < numE; i++) {
			int x = io.getInt();
			int y = io.getInt();
			E.add(x, y);
			int c = io.getInt();
			
			ArrayList<Edge> e = cap.get(x);
			if (e == null) {
				e = new ArrayList<Edge>();
				e.add(new Edge(y, c));
				cap.put(x, e);
			} else {
				e.add(new Edge(y, c));
				cap.put(x, e);
			}
			
		}
		return new FlowGraph(V,E,cap,s,t);
	}

	// public static FlowGraph readFlowSolution(Kattio io) {
	// return new FlowGraph();
	// }
	public static void writeFlowProblem(Kattio io, FlowGraph fg) {
		io.println(fg.X.length + fg.Y.length);
		io.println(fg.s + " " + fg.t);
		io.println(fg.E.numEdges);
		for (Integer x : fg.E.edges.keySet()) {
			ArrayList<Integer> ys = fg.E.edges.get(x);
			for (Integer y : ys) {
				io.println(x + " " + y + " " + 1);
			}
		}
		io.flush();
	}

	public static BipGraph readFlowToMatching(Kattio io, FlowGraph fg) {
		int t = io.getInt();
		io.getInt();
		io.getInt();
		io.getInt();
		EdgeList bE = new EdgeList();
		int numE = io.getInt();
		for (int i = 0; i < numE; i++) {
			int x = io.getInt();
			int y = io.getInt();
			io.getInt();
			if (x == 1 || y == t) {
				continue;
			}
			bE.add(x - 1, y - 1);
		}
		int[] bX = new int[fg.X.length - 1];
		int[] bY = new int[fg.Y.length - 1];
		for (int i = 0; i < bX.length; i++) {
			bX[i] = fg.X[i];
		}
		for (int i = 0; i < bY.length; i++) {
			bY[i] = fg.Y[i] - 1;
		}
		BipGraph bg = new BipGraph(bX, bY, bE);
		return bg;
	}

	public static void writeMatchingSolution(Kattio io, BipGraph bg) {
		// io.println(Xl + " " + Yl);
		//
		// int numSolutions = 0;
		// for (int i = 1; i < solution.length; i++) {
		// if(!(solution[i] == 0))
		// numSolutions++;
		// }
		// io.println(numSolutions);
		//
		// for (int i = 1; i < solution.length; i++) {
		// if(!(solution[i] == 0))
		// io.println(i + " " + solution[i]);
		// }
		//
		// /* flush out */
		// io.flush();
	}

}
