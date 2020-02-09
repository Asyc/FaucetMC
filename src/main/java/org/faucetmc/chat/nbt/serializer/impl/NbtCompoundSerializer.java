package org.faucetmc.chat.nbt.serializer.impl;

import org.faucetmc.chat.nbt.NbtParser;
import org.faucetmc.chat.nbt.serializer.NbtSerializer;
import org.faucetmc.chat.nbt.tag.NbtTag;
import org.faucetmc.chat.nbt.tag.impl.NbtTagCompound;
import org.faucetmc.chat.nbt.type.NbtTagType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class NbtCompoundSerializer extends NbtSerializer<NbtTagCompound> {

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void serialize(OutputStream out, NbtTagCompound value) throws IOException {
		for (Map.Entry<String, NbtTag<?>> compound : value.getPayload().entrySet()) {
			NbtSerializer serializer = NbtParser.getSerializer(compound.getValue().getType().getID());
			serializer.writeTagType(out);
			serializer.writeTagName(out, compound.getKey());
			serializer.serialize(out, compound.getValue());
		}
		out.write(NbtTagType.TAG_END.getID());
	}

	@Override
	public NbtTagCompound deserialize(InputStream in, boolean hasName) throws IOException {
		HashMap<String, NbtTag<?>> entries = new HashMap<>();
		for (; ; ) {
			int read = in.read();
			if (read == NbtTagType.TAG_END.getID()) break;
			NbtSerializer<?> serializer = NbtParser.getSerializer(read);
			entries.put(serializer.readTagName(in), serializer.deserialize(in, true));
		}
		return new NbtTagCompound(entries);
	}

	@Override
	public NbtTagType getType() {
		return NbtTagType.TAG_COMPOUND;
	}
}
