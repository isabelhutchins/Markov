
public class WordGram implements Comparable<WordGram>{
	  private String[] myWords;
	    private int myHash;
	    
	public WordGram(String[] source, int start, int size){
		myWords = new String [size];
		for (int i=0; i<myWords.length; i++){
			myWords[i]=source[i+start];
		}
	
	}

	public int hashCode(){
		int hash = 0;
		for (int i=0; i<this.myWords.length; i++){
			hash += (this.myWords[i].hashCode()*Math.pow(i, 2)/19);			
		}
		this.myHash = hash; 
		return hash;
	}
	
	public String toString(){
		String returnString = "{";
		
		for (int i=0; i<this.myWords.length; i++){
			if (i==this.myWords.length-1)
			returnString = returnString + this.myWords[i];
			else{
			returnString = returnString + this.myWords[i] + ",";	
			}
	}
		return returnString + "}";
	}
	
	public boolean equals(Object other){
		if (! (other instanceof WordGram))
			return false;
		
		WordGram wg = (WordGram) other;
		
		if (wg.myWords.length!=this.myWords.length)
			return false;
		
		for (int i=0; i<myWords.length; i++){
			if (! (this.myWords[i].equals(wg.myWords[i])))
				return false;	
		}
		
		return true;
	}
	
	public int compareTo(WordGram other){
		WordGram shorter = this;
		if (this.myWords.length>other.myWords.length)
			shorter = other;
		
		if (this.myWords.length!=other.myWords.length){
			if (this.myWords.length==0)
			return -1;
		else if (other.myWords.length==0)
			return 1;
		}
		
		for (int i=0; i<shorter.myWords.length; i++){
			if (this.myWords[i].compareTo(other.myWords[i])>0)
				return 1;
			if (this.myWords[i].compareTo(other.myWords[i])<0)
				return -1;
			int check = i+1;
			if ((check)>=this.myWords.length&&(check)<other.myWords.length){
				return -1;
			}
			if ((check)>=other.myWords.length&&(check)<this.myWords.length){
				return 1;
			}
			
		}
		return 0;
	}
	
	public WordGram shiftAdd(String last){
		String [] shifted = new String [this.myWords.length];
		
		if (myWords.length==0){
			return new WordGram(shifted, 0, 0);
		}
		
		for (int i=0; i<this.myWords.length-1; i++){
			shifted[i]=this.myWords[i+1];
		}
		shifted[shifted.length-1]=last;
		 
		return new WordGram(shifted, 0, shifted.length);
		
	}
	
	public int length(){
		return this.myWords.length;
	}
	
	
}
