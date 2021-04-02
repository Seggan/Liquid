package io.github.seggan.liquid.objects;

import io.github.seggan.liquid.items.Liquid;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.nustaq.serialization.FSTConfiguration;

import javax.annotation.Nonnull;

public class PersistentLiquid implements PersistentDataType<byte[], Liquid> {

    public static final PersistentLiquid TYPE = new PersistentLiquid();

    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    private PersistentLiquid() {}

    @Nonnull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Nonnull
    @Override
    public Class<Liquid> getComplexType() {
        return Liquid.class;
    }

    @Nonnull
    @Override
    public byte[] toPrimitive(@Nonnull Liquid liquid, @Nonnull PersistentDataAdapterContext context) {
        return liquid.getHash();
    }

    @Nonnull
    @Override
    public Liquid fromPrimitive(@Nonnull byte[] bytes, @Nonnull PersistentDataAdapterContext context) {
        return Liquid.getByHash(bytes);
    }
}
