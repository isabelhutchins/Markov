import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class EfficientWordMarkov implements MarkovInterface<WordGram>{
	private String [] myText;
	private Random myRandom;
	private int myOrder;
	private Map<WordGram, ArrayList<String>> kgrams;
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;

	public EfficientWordMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
public void setTraining(String text){
	myText = text.split("\\s+");
	kgrams = new HashMap<>();
	WordGram key;

	for (int i=0; i<=myText.length-myOrder;i++){
		key = new WordGram(myText, i, myOrder);
		if (kgrams.containsKey(key)){
			if (i+myOrder>=myText.length){
				kgrams.get(key).add(PSEUDO_EOS);
				break;
			}
			kgrams.get(key).add(myText[i+myOrder]);
		}else{
			kgrams.put(key, new ArrayList<String>());
			if (i+myOrder>=myText.length){
				kgrams.get(key).add(PSEUDO_EOS);
				break;
			}
			kgrams.get(key).add(myText[i+myOrder]);
		}
	}
}
 
public String getRandomText(int numWords){
	StringBuilder sb = new StringBuilder();
	int index = myRandom.nextInt(myText.length - myOrder);
	WordGram current = new WordGram(myText, index, myOrder);
	//System.out.printf("first random %d for '%s'\n",index,current);
	sb.append(current);
	
	for(int k=0; k < numWords-myOrder; k++){
		ArrayList<String> follows = getFollows(current);
		if (follows.size() == 0){
			break;
		}
		index = myRandom.nextInt(follows.size());
		
		String nextItem = follows.get(index);
	
		if (nextItem.equals(PSEUDO_EOS)) {
			//System.out.println("PSEUDO");
			break;
		}
		sb.append(" ");
		sb.append(nextItem);
		current = current.shiftAdd(nextItem);
	}
	
	return sb.toString();
	
}

public ArrayList<String> getFollows(WordGram key){
	return kgrams.get(key);
}

@Override
public int getOrder() {
	return myOrder;
}
}