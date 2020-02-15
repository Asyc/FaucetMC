package org.faucetmc.world.parse;

public class LocationTableEntry {

	private int offset;
	private short size;

	public LocationTableEntry(int offset, short size) {
		this.offset = offset;
		this.size = size;
	}

	public int getOffset() {
		return offset;
	}

	public short getSize() {
		return size;
	}
}
