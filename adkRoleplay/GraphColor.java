
int[] C;
int V;

public GraphColor(){
	
}

boolean solvableGraph(int V, ArrayList<Edge>[] edges, int m){
	C = new int[V+1];
	int next = -1;
	
	while((next = notColored()) != -1){
		int nextColor = 1;
		boolean colorUnique = false;
		
		while(!colorUnique){
			for(Edge e: edges[next]){
				if(C[e.y] == nextColor){
					nextColor++;
					if(nextColor > m){
						return false;
					}
					break;
				}
			}
			colorUnique = true;
		}
		C[next] = nextColor;
	}
	return true;
}
/* Should choose by number of edges */
private int notColored(){
	for(int i = 1; i < C.length; i++){
		if(C[i] == 0){
			return i;
		}
	}
	return -1;
}