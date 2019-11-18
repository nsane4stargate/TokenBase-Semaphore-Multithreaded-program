package ajeffrey.teaching.dining;

import ajeffrey.teaching.debug.Debug;
import ajeffrey.teaching.test.TestPhilosopher;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A philosopher from the dining philosophers problem.
 * A philosopher thinks, picks up their left-hand fork,
 * picks up their right-hand fork, then eats.
 * Unfortunately, putting a collection of philosophers in a circle
 * can produce deadlock, if they all pick up their lh forks before any
 * of them have a chance to pick up their rh forks.
 * @author Alan Jeffrey and Lea Middleton
 * @version 1.0.1
 */

/* EDITOR: Lea Middleton

   EDITS:
   compareTo() method:  @Override
                        public int compareTo(OrderedPhilosopherImpl o) {
                            int i = String.valueOf(o.lhFork).compareTo(String.valueOf(o.rhFork));
                            switch(name){
                               case "Fred":
                               case "Wilma":
                               case "Barney":
                                            if(i < 0){  Debug.out.println (o.name + " desired pick fork order is correct "); return 1;}
                                            break;
                               case "Betty":
                                            if(i > 0) { Debug.out.println (o.name + " desired pick fork order is correct "); return 1;}
                                            break;
                            }
                            return 0;
                        }
   run() method: separated the nested synchronized locks
                 added control statement to check my order
                    /* To establish order */
                    /*if (compareTo(this) == 1)
 */

public interface OrderedPhilosopher {

    /**
     * A factory for building deadlocking philosophers.
     */
    PhilosopherFactory factory
	= new OrderedPhilosopherFactoryImpl();

}

class OrderedPhilosopherFactoryImpl implements PhilosopherFactory {

    public Philosopher build(final Comparable lhFork, final Comparable rhFork, final String name) {
		return new OrderedPhilosopherImpl(lhFork, rhFork, name);
    }
}

class OrderedPhilosopherImpl implements Runnable, Philosopher, Comparable<OrderedPhilosopherImpl>{

    final protected Comparable lhFork;
    final protected Comparable rhFork;
    final protected String name;
    final protected Thread thread;

    protected OrderedPhilosopherImpl(final Comparable lhFork, final Comparable rhFork, final String name) {
		this.lhFork = lhFork;
		this.rhFork = rhFork;
		this.name = name;
		this.thread = new Thread(this);
	}

    public void start () {
	    thread.start ();
    }

    public void run () {
	    Debug.out.breakPoint (name + " is starting");
	    try {
	        while (true) {
                Debug.out.println (name + " is thinking");
                delay ();
                Debug.out.println (name + " tries to pick up " + lhFork);

                synchronized (lhFork) {
                    /* To establish order */
                    if(compareTo(this) == 1) {
                        Debug.out.println(name + " picked up " + lhFork);
                        delay();
                        Debug.out.println(name + " tries to pick up " + rhFork);
                    }
                }
                synchronized (rhFork) {
                    /* To establish order */
                    if (compareTo(this) == 1) {
                        Debug.out.println(name + " picked up " + rhFork);
                        Debug.out.println(name + " starts eating");
                        delay();
                        Debug.out.println(name + " finishes eating");
                    }
                }
	        }

	    } catch (final InterruptedException ex) {
	        Debug.out.println (name + " is interrupted");
	    }
    } /* End of run method */

    protected void delay () throws InterruptedException {
	Thread.sleep((long)(1000*Math.random()));
    }

    @Override
    public int compareTo(OrderedPhilosopherImpl o) {
        int i = String.valueOf(o.lhFork).compareTo(String.valueOf(o.rhFork));
        switch(name){
            case "Fred":
            case "Wilma":
            case "Barney":
                if(i < 0){  Debug.out.println (o.name + " desired pick fork order is correct "); return 1;}
                break;
            case "Betty":
                if(i > 0) { Debug.out.println (o.name + " desired pick fork order is correct "); return 1;}
                break;
        }
        return 0;
    }
}
