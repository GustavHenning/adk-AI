


public class Testing {

	public static void printIntArray(int[] a){
		
		System.out.print("index = [");
		for(int i = 0; i < a.length; i++){
			System.out.print((i == 0 ? "" : ",") + i);
		}
		System.out.print("]");
		System.out.println();
		
		System.out.print("array = [");
		for(int i = 0; i < a.length; i++){
			System.out.print((i == 0 ? "" : ",") + a[i]);
		}
		System.out.print("]");
		System.out.println();
	}
	
}
