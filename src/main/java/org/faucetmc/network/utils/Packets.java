package org.faucetmc.network.utils;

import org.faucetmc.network.packet.abstraction.InboundPacket;
import org.faucetmc.network.packet.impl.inbound.handshake.PacketInHandshake;
import org.faucetmc.network.packet.impl.inbound.login.PacketInEncryptionResponse;
import org.faucetmc.network.packet.impl.inbound.login.PacketInLoginStart;
import org.faucetmc.network.packet.impl.inbound.query.PacketInPing;
import org.faucetmc.network.packet.impl.inbound.query.PacketInStatusRequest;

public class Packets {

    public enum Handshake {
        HANDSHAKE(new PacketInHandshake());

        private InboundPacket packet;

        Handshake(InboundPacket packet) {
            this.packet = packet;
        }

        public InboundPacket getInstance() {
            return packet;
        }
    }

    public enum Query {
        STATUS_REQUEST(new PacketInStatusRequest()),
        PING(new PacketInPing()),
        ;
        private InboundPacket packet;

        Query(InboundPacket packet) {
            this.packet = packet;
        }

        public InboundPacket getInstance() {
            return packet;
        }
    }

    public enum Login {
        LOGIN_START(new PacketInLoginStart()),
        ENCRYPTION_RESPONSE(new PacketInEncryptionResponse())
        ;
        private InboundPacket packet;

        Login(InboundPacket packet) {
            this.packet = packet;
        }

        public InboundPacket getInstance() {
            return packet;
        }
    }

    public enum Play {
        ;
        private InboundPacket packet;

        Play(InboundPacket packet) {
            this.packet = packet;
        }

        public InboundPacket getInstance() {
            return packet;
        }
    }
}
