package io.github.seggan.liquid.liquidapi;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class PersistentLiquid implements PersistentDataType<String, Liquid> {

    public static final PersistentLiquid TYPE = new PersistentLiquid();

    private PersistentLiquid() {}

    @Nonnull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Nonnull
    @Override
    public Class<Liquid> getComplexType() {
        return Liquid.class;
    }

    @Nonnull
    @Override
    public String toPrimitive(@Nonnull Liquid complex, @Nonnull PersistentDataAdapterContext context) {
        return complex.getId();
    }

    @Nonnull
    @Override
    public Liquid fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        return Liquid.getById(primitive);
    }
}
