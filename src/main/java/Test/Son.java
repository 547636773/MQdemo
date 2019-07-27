package Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

public class Son extends Father {
    public Son(){
        super();
        show();
    }
    @Override
    public void show(){
        System.out.println("son");
    }

    public static void main(String[] args) {
        Son son = new Son();

        //list
        ArrayList<Integer> ai = new ArrayList<>();
        ai.add(1);
        ai.add(2);
        Integer[] integers = ai.toArray(new Integer[0]);

        Integer [] b = new Integer[]{12,1};
        ReentrantLock
    }
}
