
public class SkipListTestThread extends Thread {  
	private int offset;
    public SkipListTestThread(int i) {
    	offset = i;
	}

	public void run(){
    	try {
    	       for (int i = offset; i < 10000; i+=SkipListTest.threadCount){
    	           SkipListTest.list.add(i);
    	       }
    	       for (int i = offset; i < 10000; i+=SkipListTest.threadCount){
    	           if (!SkipListTest.list.contains(i)){
	    	   			if (SkipListTest.test5passed1 == true){
	    					SkipListTest.test5passed1 = false;
	    					System.err.println("!!! Test 5 failed. " + i + " not found.");
	    	   			}
    	           }
    	       }
    	       for (int i = offset; i < 10000; i+=SkipListTest.threadCount){
    	           SkipListTest.list.remove(i);
    	       }
    	       for (int i = offset; i < 10000; i+=SkipListTest.threadCount){
    	           if (SkipListTest.list.contains(i)){
	    	   			if (SkipListTest.test5passed2 == true){
	    					SkipListTest.test5passed2 = false;
	    					System.err.println("!!! Test 5 failed. " + i + " was found.");
	    	   			}
    	           }
    	       }
    	       SkipListTest.incThreadsFinished();
		} catch (NullPointerException e){
			if (SkipListTest.test5passed3 == true){
				SkipListTest.test5passed3 = false;
				System.err.println("!!! Test 5 failed. Concurrency error.");
			}
		} 
    }
  }