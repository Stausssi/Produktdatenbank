package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A slightly improved BufferedReader with the ability to get the lines read
 *
 * @see BufferedReader
 */
public class BetterBufferedReader extends BufferedReader {
    /**
     * Represents the lines read
     */
    private long lineCount = 0;

    /**
     * Creates a new instance of a {@link BufferedReader}
     *
     * @param in A reader
     *
     * @see BufferedReader
     */
    public BetterBufferedReader(Reader in) {
        super(in);
    }

    /**
     * Override {@linkplain BufferedReader#readLine() readLine()} to support lineCount
     */
    @Override
    public String readLine() throws IOException {
        lineCount++;
        return super.readLine();
    }

    /**
     * Get the count of lines read
     *
     * @return the number of lines read
     */
    public long getCurrentLine() {
        return lineCount;
    }
}
