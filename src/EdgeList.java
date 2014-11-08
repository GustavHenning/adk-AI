
import java.util.ArrayList;
import java.util.HashMap;

public class EdgeList {
	public HashMap<Integer, ArrayList<Integer>> edges;
	public int numEdges = 0;
//	public boolean[][] e;

	public EdgeList(int v) {
		edges = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < v; i++) {
			edges.put(i, new ArrayList<Integer>());
		}
//		e = new boolean[v][v];
	}

	public void add(int x, int y) {
		numEdges++;
		Integer X = new Integer(x);
		ArrayList<Integer> ar;

		ar = edges.get(X);
		ar.add(new Integer(y));
		edges.put(X, ar);
//		e[x][y] = true;
	}
	

	// public void precalc(){
	// edgesInts.add(new int[0]);
	//
	// for(int v = 1; v < numE.length; v++){
	// int[] edgeList = v < edges.length ? edgesFromX(v) : edgesFromY(v);
	// edgesInts.add(edgeList);
	// }
	// }
	//
	// public int[] edges(int v){
	// // System.out.println(v);
	// // Testing.printIntArray(edgesInts.get(v));
	// return edgesInts.get(v);
	// }
	//
	// public int[] edgesFromX(int x) {
	// int[] E = new int[numE[x]];
	// int eAdded = 0;
	// for (int j = 1; j < edges[x].length; j++) {
	// // System.out.println(j + " " + edges[x].length);
	// if (edges[x][j]) {
	// E[eAdded] = j + (edges.length-1);
	// eAdded++;
	// }
	// }
	// // Testing.printIntArray(E);
	// return E;
	// }
	//
	// public int[] edgesFromY(int y) {
	// int[] E = new int[numE[y]];
	// int eAdded = 0;
	// for (int i = 1; i < edges.length; i++) {
	// if (edges[i][y - (edges.length-1)]) {
	// E[eAdded] = i;
	// eAdded++;
	// }
	// }
	// return E;
	// }
}
