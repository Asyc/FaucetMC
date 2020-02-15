package org.faucetmc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FlippableByteArrayOutStream extends ByteArrayOutputStream {

	public FlippableByteArrayOutStream() {}

	public FlippableByteArrayOutStream(int size) {
		super(size);
	}

	public ByteArrayInputStream toInputStream() {
		return new ByteArrayInputStream(super.buf);
	}

}
