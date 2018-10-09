package fabflix;

public class Pair<F, S> {
		private F id; //first member of pair
	    private S count; //second member of pair

	    public Pair(F id, S count) {
	        this.id = id;
	        this.count = count;
	    }

	    public void setFirst(F id) {
	        this.id = id;
	    }

	    public void setSecond(S count) {
	        this.count = count;
	    }

	    public F getFirst() {
	        return id;
	    }

	    public S getSecond() {
	        return count;
	    }
}
