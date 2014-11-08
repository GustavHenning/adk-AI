
public class AdkMaxFlow {

	public static Kattio io;
	
	public static void main(String[] args){
		long time = System.currentTimeMillis();
		io = new Kattio(System.in, System.out);
		FlowGraph fg = Main.readFlowProblem(io);
		EdKarpRes ekr = Main.edKarp(fg);
		ekr.printRes(io);
		io.close();
		System.err.println(System.currentTimeMillis() - time);
	}
	
}
