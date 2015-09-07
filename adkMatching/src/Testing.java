


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

	public static void printIntArray(int[][] a) {
		System.out.print("index = [");
		for(int i = 1; i < a.length; i++){
			System.out.print((i <= 1 ? "" : ",") + i);
		}
		System.out.print("]");
		System.out.println();
		for(int i = 1; i < a.length; i++){
		System.out.print("array = [");
		for(int j = 1; j < a[i].length; j++){
			System.out.print((j <= 1 ? "" : ",") + a[i][j]);
		}
		System.out.print("]");
		System.out.println();
		}
	}
	
}
