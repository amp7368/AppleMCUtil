package apple.mc.utilities.data.serialize;

public record EntitySerialOptions(boolean removeLocation, boolean removeUniqueData) {

    public static final EntitySerialOptions DEFAULT = new EntitySerialOptions(false, false);
}
