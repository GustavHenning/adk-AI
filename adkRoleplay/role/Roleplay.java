package role;

import java.util.ArrayList;

public class Roleplay {
	int roles, stages, actors, numE;
	ArrayList<Edge>[] edges;


	/*
	 * 1. Takes input 2. Reduces input to GraphColor input 3. Returns solution
	 */
	@SuppressWarnings("unchecked")
	public Roleplay() {
		Kattio io = new Kattio(System.in, System.out);
		roles = io.getInt();
		stages = io.getInt();
		actors = io.getInt(); // playas
		edges = new ArrayList[roles + 2];

		for (int i = 0; i < roles; i++) {
			/* ??? */
			int rMax = io.getInt();
			for (int j = 0; j < rMax; j++) {
				io.getInt();
			}
			io.flush();
		}
		for (int i = 0; i < stages; i++) {
			int[] n = new int[io.getInt()];
			for (int j = 0; j < n.length; j++) {
				n[j] = io.getInt();
			}
			io.flush();
			for (int j = 0; j < n.length; j++) {
				int x = n[j];
				for (int k = 0; k < n.length; k++) {
					if (j != k) {
						int y = n[k];
						addEdge(x, y);
					}
				}
			}
		}
		for (int i = 1; i < edges.length - 1; i++) {
			addEdge(i, edges.length - 1);
		}
		/* Graffärgning? */
		// System.err.println("n " + roles + " s " + stages + " k " + actors);
		// System.err.println(solvable());
		printKattis();
		io.close();
	}

	public Roleplay(String[] input) {
		for (int i = 0; i < input.length; i++) {
			String[] split = input[i].split(" ");
			if (i == 0) {
				roles = Integer.parseInt(split[0]);
				stages = Integer.parseInt(split[1]);
				actors = Integer.parseInt(split[2]);
			}
			edges = new ArrayList[roles + 2];
			for (int j = 1; j < split.length; j++) {
				if (i < roles) {
					continue;
				} else {
					int k = Integer.parseInt(split[j]);
					for (int l = 1; l < split.length; l++) {
						if (l != j) {
							addEdge(k, Integer.parseInt(split[l]));
						}
					}
				}
			}
		}
		for (int i = 1; i < edges.length - 1; i++) {
			addEdge(i, edges.length - 1);
		}
	}

	boolean solvable() {
		return new GraphColor().solvableGraph(roles + 1, edges, actors);
	}

	void addEdge(int x, int y) {
		if (edges[x] == null)
			edges[x] = new ArrayList<Edge>();
		for (int i = 0; i < edges[x].size(); i++) {
			if (y == edges[x].get(i).y)
				return;
		}
		numE++;
		edges[x].add(new Edge(x, y));
		if (edges[y] == null)
			edges[y] = new ArrayList<Edge>();
		edges[y].add(new Edge(y, x).inverse());
	}

	public void printKattis() {
		System.out.println(roles + 1);
		System.out.println(numE);
		System.out.println(actors);
		for (int i = 0; i < edges.length; i++) {
			if (edges[i] == null)
				continue;
			for (int j = 0; j < edges[i].size(); j++) {
				Edge e = edges[i].get(j);
				if (!e.inverse) {
					System.out.println(i + " " + e.y);
				}
			}
		}
	}

	public static void main(String[] args) {
		new Roleplay();
	}
}
