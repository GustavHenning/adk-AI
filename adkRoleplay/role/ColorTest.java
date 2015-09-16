package role;

import java.util.ArrayList;

public class ColorTest {

	public ColorTest(){
		ArrayList<Edge>[] edges = new ArrayList[3];
		edges[1] = new ArrayList<Edge>();
		edges[2] = new ArrayList<Edge>();
		edges[1].add(new Edge(1,2));
		edges[2].add(new Edge(2,1));
		System.out.println("true: " + new GraphColor().solvableGraph(3, edges, 2));
		System.out.println("false: " + new GraphColor().solvableGraph(3, edges, 1));
		for(int i = 1; i < 10; i++){
			testComplete(i);
		}
	}
	public void testComplete(int V){
		ArrayList<Edge>[] edges = new ArrayList[V+1];
		for(int i = 1; i <= V; i++){
			for(int j = 1; j <= V; j++){
				if(i == j)
					continue;
				if(edges[i] == null)
					edges[i] = new ArrayList<Edge>();
				edges[i].add(new Edge(i, j));
			}
		}
		System.out.println("Test complete: " + V + " " + new GraphColor().solvableGraph(V, edges, V));
	}
	
	public static void main(String[] args){
		System.out.println("Color test");
		new ColorTest();
	}
}
