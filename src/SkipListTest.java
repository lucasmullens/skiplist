import java.util.ArrayList;

public class SkipListTest {
	public static void main(String[] args) {
		SkipList<Integer> list = new SkipList<Integer>();
		//ArrayList<String> list = new ArrayList<String>(); //FOR COMPARISON
		//TEST 1
		list.add(1);
		list.add(2);
		if (list.contains(1) && list.contains(2) && !list.contains(3)){
			System.out.println("Test 1 passed.");
		} else {
			System.out.println("Test 1 failed.");
		}
		//TEST 2 
		list.remove(1);
		if (list.contains(1)){
			System.out.println("Test 2 failed. Should not contain 1.");
		} else if (!list.contains(2)){
			System.out.println("Test 2 failed. 2 not found.");
		} else {
			System.out.println("Test 2 passed.");
		}
		//TEST 3
		list.clear();
		boolean failed = false;
		for (int i = 0; i < 100; i++){
			list.add(i);
		}
		for (int i = 0; i < 100; i+=2){
			list.remove(i);
		}
		for (int i = 1; i < 100; i+=2){
			if (!list.contains(i)){
				failed = true;
				System.out.println("Test 3 failed. Missing " + i);
			}
		}
		for (int i = 0; i < 100; i+=2){
			if (list.contains(i)){
				failed = true;
				System.out.println("Test 3 failed. Should not contain " + i);
			}
		}
		if (!failed){
			System.out.println("Test 3 passed.");
		}
	}
}
