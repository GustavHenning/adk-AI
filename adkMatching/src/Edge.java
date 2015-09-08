
public class Edge {

	public int a, b;
	public boolean counted = false;
	public boolean inverse = false;
	
	public Edge(int a, int b){
		this.a = a;
		this.b = b;
	}
	
	public String toString(){
		return a + " " + b;
	}
	public boolean equals(Object o){
		Edge e = (Edge) o;
		return e.a == this.a && e.b == this.b;
		
	}

	public Edge setInverse(boolean inverse) {
		this.inverse = inverse;
		return this;
	}
}
