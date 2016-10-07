import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class WordGramTester {

	private WordGram[] myGrams;

	@Before
	public void setUp(){
		String str = "aa bb cc aa bb cc aa bb cc aa bb dd ee ff gg hh ii jj";
		String[] array = str.split("\\s+");
		myGrams= new WordGram[array.length-2];
		for(int k=0; k < array.length-2; k++){
			myGrams[k] = new WordGram(array,k,3);
		}
	}

	@Test
	public void testHashEquals(){
		assertEquals("hash fail on equals 0,3",myGrams[0].hashCode(),myGrams[3].hashCode());
		assertEquals("hash fail on equals 0,3",myGrams[0].hashCode(),myGrams[6].hashCode());
		assertEquals("hash fail on equals 0,3",myGrams[1].hashCode(),myGrams[4].hashCode());
		assertEquals("hash fail on equals 0,3",myGrams[2].hashCode(),myGrams[8].hashCode());
		assertEquals("hash fail on equals 0,3",myGrams[2].hashCode(),myGrams[5].hashCode());
	}

	@Test
	public void testEquals(){

		assertEquals("eq fail on 0,3",myGrams[0].equals(myGrams[3]),true);
		assertEquals("eq fail on 0,6",myGrams[0].equals(myGrams[6]),true);
		assertEquals("eq fail on 1,4",myGrams[1].equals(myGrams[4]),true);
		assertEquals("eq fail on 2,5",myGrams[2].equals(myGrams[5]),true);
		assertEquals("eq fail on 2,8",myGrams[2].equals(myGrams[8]),true);
		assertEquals("eq fail on 0,2",myGrams[0].equals(myGrams[2]),false);
		assertEquals("eq fail on 0,4",myGrams[0].equals(myGrams[2]),false);
		assertEquals("eq fail on 2,3",myGrams[2].equals(myGrams[3]),false);
		assertEquals("eq fail no 2,6",myGrams[2].equals(myGrams[6]),false);
		assertEquals("eq fail no 7,8",myGrams[7].equals(myGrams[8]),false);
	}

	@Test
	public void testHash(){
		Set<Integer> set = new HashSet<Integer>();
		for(WordGram w : myGrams) {
			set.add(w.hashCode());
		}

		assertTrue("hash code test", set.size() > 9);
	}
	
	@Test
	public void testCompare(){
		String[] words = {"apple", "zebra", "mongoose", "hat"};
		WordGram a = new WordGram(words,0,4);
		WordGram b = new WordGram(words,0,4);
		WordGram a2 = new WordGram(words,0,3);
		WordGram b2 = new WordGram(words,2,0);
		
		assertEquals("comp fail self",a.compareTo(a) == 0, true);
		assertEquals("comp fail copy",a.compareTo(b) == 0, true);
		assertEquals("fail sub", a2.compareTo(a) < 0, true);
		assertEquals("fail super",a.compareTo(a2) > 0, true);
		assertEquals("fail empty",b2.compareTo(a2) < 0, true);
	}
	
	@Test
	public void testToString(){
	String[] words = {"crown", "rats", "fox", "d", " ", "computer", "", "tacos", "kakkasdka"};
		WordGram one = new WordGram(words, 2, 3); //fox, d, (space)
		WordGram two = new WordGram(words, 0, 4); // crown, rats, fox, d, (space)
		WordGram three = new WordGram(words, 5, 1); //computer
		WordGram four = new WordGram(words, 6, 3); // (nothing), tacos, kakkasdka
		
		//to help user realize they left out the end bracket
		assertEquals("words, 2, 3", one.toString().equals("{fox,d, "), false);
		//to help user realize they added a comma to the end of the last string
		assertEquals("words, 0, 4", two.toString().equals("{crown,rats,fox,d,}"), false);
		assertEquals("words, 5, 1", three.toString().equals("{computer}"), true);
		assertEquals("words, 6, 3", four.toString().equals("{,tacos,kakkasdka}"), true);
	}
	
	
	
	@Test
	public void testShiftAdd(){
		String [] array = {"tomorrow", "i", "am", "", "going", "to", "be", "happy!"};
		String [] lastWords = {"ending", "HELLO", " ", "happy."};
		
		WordGram one = new WordGram(array, 2, 5);
		WordGram two = new WordGram(array, 0, 1);
		WordGram three = new WordGram(array, 1, 0);
		WordGram four = new WordGram(array, 1, 7);
		
		one = one.shiftAdd(lastWords[0]);
		two = two.shiftAdd(lastWords[1]);
		three = three.shiftAdd(lastWords[2]);
		four = four.shiftAdd(lastWords[3]);
		
		assertEquals("Gram: words, 2, 5. Last index: 0", one.toString().equals("{,going,to,be,ending}"), true);
		assertEquals("Gram: words, 0, 1. Last index: 1", two.toString().equals("{HELLO}"), true);
		assertEquals("Gram: words, 1, 0. Last index: 2", three.toString().equals("{}"), true);
		assertEquals("Gram: words, 1, 7. Last index: 3", four.toString().equals("{am,,going,to,be,happy!,happy.}"), true);
		
	}

}
