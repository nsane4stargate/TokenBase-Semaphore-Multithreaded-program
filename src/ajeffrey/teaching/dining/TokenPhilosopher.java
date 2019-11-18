package ajeffrey.teaching.dining;

import ajeffrey.teaching.debug.Debug;
import ajeffrey.teaching.test.TestPhilosopher;

import java.util.concurrent.Semaphore;

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
   In public void run () modified the while statement:
   							while (true) {
								Debug.out.println(name + " is thinking");
								delay();
								if(receivedToken()) {
									eat();
									releaseToken();
								}
							}
   In protected void eat(), moved the synchronized() critical section from run() to this function
						Debug.out.println (name + " tries to pick up " + lhFork);
						synchronized (lhFork) {
							Debug.out.println (name + " picked up " + lhFork);
							delay ();
							Debug.out.println (name + " tries to pick up " + rhFork);
							synchronized (rhFork) {
								Debug.out.println (name + " picked up " + rhFork);
								Debug.out.println (name + " starts eating");
								delay ();
								Debug.out.println (name + " finishes eating");
							}
						}
	Implemented functions: receivedTokens(), releasedTokens(), incrementTokens(), decrementTokens()

 */
public interface TokenPhilosopher {

    /**
     * A factory for building token philosophers.
     */
	PhilosopherFactory factory
	= new TokenPhilosopherFactoryImpl ();

}

class TokenPhilosopherFactoryImpl implements PhilosopherFactory {

    public Philosopher build (final Comparable lhFork, final Comparable rhFork, final String name) {
		return new TokenPhilosopherImpl (lhFork, rhFork, name);
    }
}

class TokenPhilosopherImpl implements Runnable, Philosopher {

    final protected Object lhFork;
    final protected Object rhFork;
    final protected String name;
    final protected Thread thread;

    protected TokenPhilosopherImpl(final Object lhFork, final Object rhFork, final String name) {
		this.lhFork = lhFork;
		this.rhFork = rhFork;
		this.name = name;
		this.thread = new Thread (this);
    }

    public void start () {
	thread.start ();
    }

    public void run () {
		Debug.out.breakPoint (name + " is starting");
		try {
	    	while (true) {
				Debug.out.println(name + " is thinking");
				delay();
				if(receivedToken()) {
					eat();
					releaseToken();
				}
			}
		} catch (final InterruptedException ex) {
		    Debug.out.println (name + " is interrupted");
		}
    }

	protected void eat() throws InterruptedException {
		Debug.out.println (name + " tries to pick up " + lhFork);
		synchronized (lhFork) {
			Debug.out.println (name + " picked up " + lhFork);
			delay ();
			Debug.out.println (name + " tries to pick up " + rhFork);
			synchronized (rhFork) {
				Debug.out.println (name + " picked up " + rhFork);
				Debug.out.println (name + " starts eating");
				delay ();
				Debug.out.println (name + " finishes eating");
			}
		}
	}

	synchronized boolean receivedToken() throws InterruptedException{
    	synchronized ("this"){
    		boolean gotToken = false;
    		try{
				if(TestPhilosopher.tokensOut < TestPhilosopher.maxTokens)
    				gotToken = incrementTokens();
					if(gotToken) {
						TestPhilosopher.sem.acquire();
					}
			}finally{
    			if(!gotToken)
					Debug.out.println ("No Tokens available !!");
    				TestPhilosopher.sem.release();
			}
			Debug.out.println ("receivedToken(): Current number of tokens checked out: " + String.valueOf(TestPhilosopher.tokensOut));
			Debug.out.println ("receivedToken(): Tokens available: " + String.valueOf(TestPhilosopher.maxTokens - TestPhilosopher.tokensOut));
    		return gotToken;
		}
	}

	synchronized void releaseToken() {
    	synchronized ("this") {
			boolean releasedToken = decrementTokens();
			if(releasedToken) {
				TestPhilosopher.sem.release();
				Debug.out.println("releaseToken(): Number of tokens available: " + String.valueOf(TestPhilosopher.maxTokens - TestPhilosopher.tokensOut));
				Debug.out.println("releaseToken(): Tokens available: " + String.valueOf(TestPhilosopher.maxTokens - TestPhilosopher.tokensOut));
			}
		}
	}

	synchronized boolean incrementTokens(){
    	synchronized ("this") {
			TestPhilosopher.tokensOut++;
		}
    	return true;
	}

	synchronized boolean decrementTokens(){
    	synchronized ("this") {
			TestPhilosopher.tokensOut--;
		}
		return true;
	}

	protected void delay () throws InterruptedException {
		Thread.sleep((long)(1000*Math.random()));
    }
}
