
public class AdkReduceToFlow {
	static Kattio io;
	
	public static void main(String[] args){
		
		io = new Kattio(System.in, System.out);
		BipGraph bg = null;
		FlowGraph fg = null;
		bg = Main.readMatchingProblem(io);
		fg = Main.matchingToFlow(bg);
		Main.writeFlowProblem(io, fg);
		bg = Main.readFlowToMatching(io, fg);
		bg.printProblem(io);
	}
	
}
