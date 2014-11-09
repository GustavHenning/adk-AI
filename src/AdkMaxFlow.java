
public class AdkMaxFlow {

	public static Kattio io;
	
	public static void main(String[] args){
		long time = System.currentTimeMillis();
		io = new Kattio(System.in, System.out);
		FlowGraph fg = Main.readFlowProblem(io);
		System.err.println("time to read: " + (System.currentTimeMillis() - time));
		EdKarpRes ekr = Main.edKarp(fg);
		System.err.println("adkarp res after: " + (System.currentTimeMillis() - time));
		ekr.printRes(io);
		io.close();
		System.err.println(System.currentTimeMillis() - time);
	}
	
}
