import java.util.ArrayList;

public class SkipListTest {
	static SkipList<Integer> list;
	//booleans to make each error show up only once
	public final static int threadCount = 1000;
	public static boolean test5passed1 = true;
	public static boolean test5passed2 = true;
	public static boolean test5passed3 = true;
	public static int test5count = 0;
	public static void main(String[] args) {
		list = new SkipList<Integer>();
		//ArrayList<String> list = new ArrayList<String>(); //FOR COMPARISON
		test1(); //creating items test
		test2(); //creating and deleting items test
		test3(); //creating and removing many items test
		test4(); //checks for O(log(n)) lookups
		test5(); //concurrency safe test
	}
	public static void test1(){
		list.add(1);
		list.add(2);
		if (list.contains(1) && list.contains(2) && !list.contains(3)){
			System.out.println("Test 1 passed. Items added successfully.");
		} else {
			System.out.println("!!! Test 1 failed.");
		}
		
	}
	public static void test2(){
		list.clear();
		list.add(1);
		list.add(2);
		list.remove(1);
		if (list.contains(1)){
			System.out.println("!!! Test 2 failed. Should not contain 1.");
		} else if (!list.contains(2)){
			System.out.println("!!! Test 2 failed. 2 not found.");
		} else {
			System.out.println("Test 2 passed. Item removed successfully.");
		}
		
	}
	public static void test3(){

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
				System.out.println("!!! Test 3 failed. Should not contain " + i);
			}
		}
		if (!failed){
			System.out.println("Test 3 passed. All items are correct.");
		}
	}
	public static void test4(){
		list.clear();
		for (int i = 0; i < 10000; i++){
			list.add(i);
		}
		int sum = 0;
		for (int i = 0; i < 10000; i++){
			sum += list.debug(i);
		}
		double averageReads = sum/10000.0;
		if (averageReads < 100) {//effectively, if O(log(n))
			System.out.println("Test 4 passed. Required " + averageReads + " reads on average lookup for 10000 values.");
		} else {
			System.out.println("!!! Test 4 failed. Required " + averageReads + " reads on average.");			
		}
		
	}
	public static void test5(){
		list.clear();
		ArrayList<SkipListTestThread> threads = new ArrayList<SkipListTestThread>();
		SkipListTestThread t;
		for (int i = 0; i < threadCount; i++){
			t = new SkipListTestThread(i);
			t.start();
			threads.add(t);
		}
		//wait for threads to finish
		for (Thread e : threads){
			try {
				e.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		boolean pass = true;
		if (test5count != threadCount){
			System.err.println("!!! Test 5 failed. Only " + test5count + " threads finished.");
			pass = false;
		} else {
			System.out.println("All threads completed");
		}
		if (!test5passed1) {
			pass = false;
		}
		if (!test5passed2) {
			pass = false;
		}
		if (!test5passed3) {
			pass = false;
		} 
		if (pass){
			System.out.println("Test 5 passed. Concurrency successful.");
		}
	}
	public static synchronized void incThreadsFinished(){
		test5count++;
	}
}
