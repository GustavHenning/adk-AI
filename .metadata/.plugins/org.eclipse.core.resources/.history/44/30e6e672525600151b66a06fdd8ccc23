import java.util.ArrayDeque;
import java.util.ArrayList;

public class MaxFlow {

	public Kattio io;

	public int V, source, sink, E, flowC, from, to, cap, minFlowFound, totFlow, currentCap;
	public Edge currEdge, inverseEdge;
	public Edge[] edgeList, inverseEdgeList;

	public ArrayList<Edge>[] edges;
	public Edge[] path;

	public MaxFlow() {
		io = new Kattio(System.in, System.out);

		V = io.getInt();
		source = io.getInt();
		sink = io.getInt();
		E = io.getInt();
		io.flush();

		edges = new ArrayList[V + 1];
		path = new Edge[V + 1];

		readGraph();

		findFlow();

		writeFlow();

		io.close();

	}

	void readGraph() {
		for (int i = 0; i < E; i++) {
			/* Read edge */
			from = io.getInt();
			to = io.getInt();
			cap = io.getInt();
			io.flush();

			/* Init neighbor list */
			if (edges[from] == null)
				edges[from] = new ArrayList<Edge>();
			/* Backwards too */
			if (edges[to] == null)
				edges[to] = new ArrayList<Edge>();
			/* Create edges */
			currEdge = new Edge(from, to, cap);
			inverseEdge = new Edge(to, from, 0);
			/* Refer them */
			currEdge.inverse = inverseEdge;
			inverseEdge.inverse = currEdge;
			/* Add the new edges */
			edges[from].add(currEdge);
			edges[to].add(inverseEdge);

		}
	}

	public void findFlow() {

		Edge e;
		while (BreadthFirstSearch(source, sink)) {

			/* Save the BFS path : for backtracking etc */
			ArrayList<Edge> localPath = new ArrayList<Edge>();
			/* Find the last edge */
			Edge localEdge = path[sink];
			/* init min flow */
			minFlowFound = localEdge.capFlowDif;

			/* Find the flow while iterating over the path */
			while (localEdge.x != source) {
				minFlowFound = Math.min(minFlowFound, localEdge.capFlowDif);
				localPath.add(localEdge);
				localEdge = path[localEdge.x];
			}
			/* First edge */
			minFlowFound = Math.min(minFlowFound, localEdge.capFlowDif);
			localPath.add(localEdge);

			/* min flow actually found */
			totFlow += minFlowFound;

			/* Update all the edges */
			for (int i = 0; i < localPath.size(); i++) {
				e = localPath.get(i);

				e.flow += minFlowFound;
				e.inverse.flow = -1 * e.flow;
				e.capFlowDif = e.cap - e.flow;
				e.inverse.capFlowDif = e.inverse.cap - e.inverse.flow;

			}
		}
	}

	boolean BreadthFirstSearch(int source, int sink) {

		/* Check impossible paths */
		if (edges[sink] == null || edges[sink].size() == 0 || source == sink)
			return false;

		ArrayDeque<Edge> queue = new ArrayDeque<Edge>();

		boolean[] visited = new boolean[V + 1];

		/* Create a fictional source node */
		Edge sourceEdge = new Edge(0, source, 0);
		queue.add(sourceEdge);

		Edge currentEdge;
		Edge nextEdge;

		// BFS...
		while (!queue.isEmpty()) {
			currentEdge = queue.poll();
			ArrayList<Edge> neighbors = edges[currentEdge.y];

			if (neighbors == null)
				return false;

			for (int i = 0; i < neighbors.size(); i++) {
				nextEdge = neighbors.get(i);
				if (nextEdge.capFlowDif > 0 && visited[nextEdge.y] != true) {

					/* Check if done */
					if (nextEdge.y == sink) {
						visited[nextEdge.y] = true;
						path[sink] = nextEdge;
						return true;
					}
					/* Keep going */
					visited[nextEdge.y] = true;
					path[nextEdge.y] = nextEdge;
					queue.add(nextEdge);

				}

			}
		}
		/* Queue empty */
		return false;

	}

	void writeFlow() {

		io.println(V);
		io.println(source + " " + sink + " " + totFlow);

		ArrayList<Edge> numEdgesToPrint = new ArrayList<Edge>();
		
		/* Print only positive flow */
		for (int i = 0; i < edges.length; i++) {
			if (edges[i] != null) {
				for (int j = 0; j < edges[i].size(); j++) {
					if (edges[i].get(j).flow > 0)
						numEdgesToPrint.add(edges[i].get(j));
				}
			}
		}

		io.println(numEdgesToPrint.size());
		io.flush();

		/* Print em */
		for (int i = 0; i < numEdgesToPrint.size(); i++) {
			io.println(numEdgesToPrint.get(i).x + " " + numEdgesToPrint.get(i).y + " "
					+ numEdgesToPrint.get(i).flow);
			io.flush();
		}
	}

	public static void main(String args[]) {
		MaxFlow mf = new MaxFlow();
	}
}