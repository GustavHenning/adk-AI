
public class AdkMaxFlow {

	public static Kattio io;
	
	public static void main(String[] args){
		io = new Kattio(System.in, System.out);
		FlowGraph fg = Main.readFlowProblem(io);
		EdKarpRes ekr = Main.edKarp(fg);
		ekr.printRes(io);
	}
	
}
