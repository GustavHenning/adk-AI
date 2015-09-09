
public class MaxBip {

	public MaxBip(){
		ToFlow f = new ToFlow();
		MaxFlow max = new MaxFlow(f.io, f.V, f.source, f.sink, f.E, f.edges);
	}
	
	public static void main(String[] args){
		new MaxBip();
	}
	
}
