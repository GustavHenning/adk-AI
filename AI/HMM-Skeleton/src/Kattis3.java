
public class Kattis3 {

	public static void main(String[] args){
		Kattio io = new Kattio(System.in, System.out);
		double[][] transM = HMM.getMatrix(io), emisM = HMM.getMatrix(io), initM = HMM.getMatrix(io);
		
		double[] seq = HMM.getVector(io);
		
		HMM hmm = new HMM(transM, emisM, initM);
		
		int[] statePath = hmm.estimateStateFromSequence(seq);
		
		for(int i = 0; i < statePath.length; i++){
			System.out.print(statePath[i] + " ");
		}
		System.out.println();
		io.close();
	}
}
