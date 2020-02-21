package org.faucetmc.network.type;

import io.netty.buffer.ByteBuf;

public class VarInteger {

    public static int read(ByteBuf in) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = in.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new VarIntParseException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static int read(byte[] in) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = in[numRead];
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new VarIntParseException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static void write(ByteBuf out, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            out.writeByte(temp);
        } while (value != 0);
    }

    public static void write(byte[] out, int index, int value) {
        do {
            byte temp = (byte) (value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            out[index] = temp;
            index++;
        } while (value != 0);
    }

    public static int getSize(int value) {
        int size = 0;
        do {
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            size++;
        } while (value != 0);
        return size;
    }

    public static class VarIntParseException extends RuntimeException {
        public VarIntParseException() {
        }

        public VarIntParseException(String message) {
            super(message);
        }

        public VarIntParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public VarIntParseException(Throwable cause) {
            super(cause);
        }

        public VarIntParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
