
int roles, stages, actors;
ArrayList<Edge>[] edges;
/* 1. Takes input
 * 2. Reduces input to GraphColor input
 * 3. Returns solution
 * 
 *  */
public Roleplay(){
	Kattio io = new Kattio(System.in, System.out);
	roles = io.getInt();
	stages = io.getInt();
	actors = io.getInt(); //playas
	
	for(int i = 0; i < roles; i++){
		/* ??? */
	}
	for(int i = 0; i < stages; i++){
		edges = new ArrayList<Edge>[roles];
		int[] n = new int[io.getInt()];
		for(int j = 0; j < n.length; j++){
			n[i] = io.getInt();
		}
		for(int j = 0; j < n.length; j++){
			int x = n[j];
			for(int k = 0; k < n.length; k++){
				if(j != k){
					int y = n[k];
					addEdge(x,y);
				}					
			}
		}
		/* Graffärgning? */
	}
}
void addEdge(int x, int y){
	if(edges[x] == null)
		edges[x] = new ArrayList<Edge>();
	edges[x].add(new Edge(x,y));
	if(edges[y] == null)
		edges[y] = new ArrayList<Edge>();
	edges[y].add(new Edge(y,x));
}