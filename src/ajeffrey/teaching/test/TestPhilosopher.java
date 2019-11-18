package ajeffrey.teaching.test;

import ajeffrey.teaching.debug.Debug;

import ajeffrey.teaching.debug.StepDebugStream;
import ajeffrey.teaching.dining.*;
import net.jcip.annotations.GuardedBy;

import java.util.concurrent.Semaphore;

/**
 * A ajeffery.teaching.test of the dining philosophers, with four philosophers.
 * @author Alan Jeffrey and Lea Middleton
 * @version 1.0.1
 * @see Philosopher
 */
public class TestPhilosopher {
    @GuardedBy("this")public static int tokensOut = 0;
    @GuardedBy("this")public final static int maxTokens = 3;
    @GuardedBy("this")public final static Semaphore sem = new Semaphore(3);

    public static void main(String[] args) {
        // Switch on step debugging
        Debug.out.addFactory(StepDebugStream.factory);
        // Send debugging to stderr
        Debug.out.addPrintStream(System.err);
        // Create the forks
        final Comparable fork1 = "Fork 1";
        final Comparable fork2 = "Fork 2";
        final Comparable fork3 = "Fork 3";
        final Comparable fork4 = "Fork 4";

        // Which philosopher factory to use: you may want to edit this!
        final PhilosopherFactory factory = TokenPhilosopher.factory;

        // Create the philosophers
        final Philosopher fred = factory.build(fork1, fork2, "Fred");
        final Philosopher wilma = factory.build(fork2, fork3, "Wilma");
        final Philosopher barney = factory.build(fork3, fork4, "Barney");
        final Philosopher betty = factory.build(fork4, fork1, "Betty");

        // Start the philosophers
        fred.start();
        wilma.start();
        barney.start();
        betty.start();
    }
}


