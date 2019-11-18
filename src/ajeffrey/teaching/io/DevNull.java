package ajeffrey.teaching.io;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * An output stream which discards messages
 * @author Alan Jeffrey
 * @version 1.0.3
 */
public interface DevNull {

    /**
     * An output stream which discards messages
     */
    OutputStream out = new DevNullOutputStream ();

    /**
     * A print stream which discards messages
     */
    PrintStream printStream = new PrintStream (out);

    /**
     * A writer which discards messages
     */
    Writer writer = new OutputStreamWriter (out);

    /**
     * A print writer which discards messages
     */
    PrintWriter printWriter = new PrintWriter (writer);

}

class DevNullOutputStream extends OutputStream {

    public void write (final byte[] b) {}

    public void write (final byte[] b, final int off, final int len) {}
	    
    public void write (int b) {}

}
