package org.sableccsupport.lexer;

import java.io.IOException;
import org.netbeans.spi.lexer.LexerInput;
import org.sableccsupport.scclexer.lexer.IPushbackReader;

/**
 *
 * @author phucluoi
 */
public class NBPushbackReader implements IPushbackReader{

	LexerInput input;
	char[] buf;
	private int pos;
	//private PushbackReader reader;
	Object lock;
	
	public NBPushbackReader(LexerInput input, int bufferSize) {
		//super(new StringReader(""));
		//reader = new PushbackReader(new StringReader(""));
		if(bufferSize < 0){
			throw new IllegalArgumentException("size <= 0");
		}
		this.buf = new char[bufferSize];
		this.pos = bufferSize;
		this.input = input;
		lock = this;
	}



	@Override
	public int read() throws IOException
	{
	synchronized (lock) {
	    if (pos < buf.length) {
			return buf[pos++];
		}else {
			return input.read();
		}
	}
	}


	@Override
	public void unread(int c) throws IOException {
	synchronized (lock) {
	    if (pos == 0) {
			throw new IOException("Pushback buffer overflow");
		}
	    buf[--pos] = (char) c;
	}
	}
}
