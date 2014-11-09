
public class Edge {

	public int y, c, x;
	public boolean inverse;
	
	public Edge(int a, int b){
		this.y = a;
		this.c = b;
	}
	
	public String toString(){
		return y + " " + c;
	}


	public Edge setInverse(boolean inverse) {
		this.inverse = inverse;
		return this;
	}
	public void setX(int x){
		this.x = x;
	}
	
}
