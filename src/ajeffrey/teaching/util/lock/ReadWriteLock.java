package ajeffrey.teaching.util.lock;

public interface ReadWriteLock {

    void acquireReadLock() throws InterruptedException;
    void acquireWriteLock() throws InterruptedException;
    boolean attemptReadLock(long delay) throws InterruptedException;
    boolean attemptWriteLock(long delay) throws InterruptedException;
    void releaseReadLock();
    void releaseWriteLock();

    ReadWriteLockFactory factory = new ReadWriteLockFactoryImpl ();

}

class ReadWriteLockFactoryImpl implements ReadWriteLockFactory {

    public ReadWriteLock build () { return new ReadWriteLockImpl (); }

}

class ReadWriteLockImpl implements ReadWriteLock {

    public void acquireReadLock () throws InterruptedException {
    }
    public void acquireWriteLock () throws InterruptedException {
    }

    public boolean attemptReadLock (long delay) throws InterruptedException {
	return true;
    }
    public boolean attemptWriteLock (long delay) throws InterruptedException {
	return true;
    }
    public void releaseReadLock () {
    }
    public void releaseWriteLock () {
    }

}
