import java.util.Random;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EfficientMarkov implements MarkovInterface<String> {
	private String myText;
	private Random myRandom;
	private int myOrder;
	private Map<String, ArrayList<String>> kgrams;
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	
	public EfficientMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order; 
	}
	
	public EfficientMarkov() {
		this(3);
	}

	@Override
	public void setTraining(String text) {
		myText = text;
		kgrams = new HashMap<>();
		String sub;
		
		for (int i=0; i<=myText.length()-myOrder;i++){
			sub = myText.substring(i, i+myOrder);
			if (kgrams.containsKey(sub)){
				if (i+myOrder>=myText.length()){
					kgrams.get(sub).add(PSEUDO_EOS);
					break;
				}
				kgrams.get(sub).add(String.valueOf(myText.charAt(i+myOrder)));
			}else{
				kgrams.put(sub, new ArrayList<String>());
				if (i+myOrder>=myText.length()){
					kgrams.get(sub).add(PSEUDO_EOS);
					break;
				}
				kgrams.get(sub).add(String.valueOf(myText.charAt(i+myOrder)));
			}
		}
	}

	@Override
	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length() - myOrder);
		
		String current = myText.substring(index, index + myOrder);
		//System.out.printf("first random %d for '%s'\n",index,current);
		sb.append(current);
		for(int k=0; k < length-myOrder; k++){
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
			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}
		return sb.toString();
	}

	@Override
	public ArrayList<String> getFollows(String key) {
		return kgrams.get(key);	
	}

	@Override
	public int getOrder() {
		return myOrder;
	}
	
	
	
	
	
	
}
