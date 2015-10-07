
public class Kattis1 {

	public static void main(String[] args){
		Kattio io = new Kattio(System.in, System.out);
		
		double[][] transM = HMM.getMatrix(io),
				emisM = HMM.getMatrix(io),
				initM = HMM.getMatrix(io);
		
		HMM hmm = new HMM(transM, emisM, initM);
		HMM.printVector(hmm.nextEmissionProbabilities());
		
		io.close();
		
	}
	
}
