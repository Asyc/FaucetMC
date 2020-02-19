package org.faucetmc.block;

public class Block {




    public static class SoundType {
        public final String soundName;
        public final float volume;
        public final float frequency;

        public SoundType(String soundName, float volume, float frequency) {
            this.soundName = soundName;
            this.volume = volume;
            this.frequency = frequency;
        }

        public float getVolume() {
            return this.volume;
        }

        public float getFrequency() {
            return this.frequency;
        }

        public String getDigResourcePath() {
            return "dig." + this.soundName;
        }

        public String getStepResourcePath() { return "step." + this.soundName; }
    }

}
