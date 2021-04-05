package io.github.seggan.liquid.items.fluids;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.reflection.ReflectionUtils;
import org.bukkit.inventory.meta.SkullMeta;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@AllArgsConstructor
public enum FluidTankTextures {
    EMPTY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjkxYjdiMjE3MjVmMTQ2ZDI5YzE5MmI3NDVkNzlkMjI2MDMyNjdjN2FkODkzYmFkZWI2NTQ2ZTc0NjYwMDA2MCJ9fX0="),
    FULL_16("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBmNzM3MDAwMjJiOGU4MDQzMTM5ZTFlMzBkZjE4Njk1ZjNkMDNmNjZkZDY2MmU3MGVlZGRjZGU4M2JkN2Y5NSJ9fX0="),
    FULL_33("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGJiM2ZhNTE1NTExYTNiYjhiYmEzOWYzNDIxZmUzM2IwOWFjYzMyMWEzMTAyYjJhM2E0Yjk0MTUwN2E5ZTQ1NCJ9fX0="),
    FULL_50("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQ4ZWVlY2RmYmE3OTVjMzJhZWIwMDFiZmRiMmI3OWRkNDVlNTYxODgzZjAzN2I2MDRlNjVjODU4ZjAyODQ3YSJ9fX0="),
    FULL_66("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU3OGUxM2ZiZmM1ZmUwNTg0MGVkZDc3MGNkOWI3NjQ3Mjc5NzM4ZTJmMWIwM2Q5MDhkN2ZjZDEyNTE4OWE5In19fQ=="),
    FULL_83("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjk2YmQzZTY3MTdiMTgyMTM5NGI1NDg5ZGViZjNmZjg1MDRmZWQ5ODkwNjgxZDMwNzUwMjJmYjE0ZDkyZjc4NyJ9fX0="),
    FULL_100("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E3MTE0MjcwNmMwODRmNzBhOTVmYTk5NTU3MTZiYWYyNjgwYWI3Y2ZmMzc3MWM0MjdmNWIxMTZhZGY0M2JmYSJ9fX0="),
    ;
    @Getter
    private final String texture;


    public static FluidTankTextures getTexture(double amount, double maxAmount) {
        int percent = intRound((amount / maxAmount) * 100);
        if (percent < 16) return EMPTY;
        else if (percent < 33) return FULL_16;
        else if (percent < 50) return FULL_33;
        else if (percent < 66) return FULL_50;
        else if (percent < 83) return FULL_66;
        else if (percent < 100) return FULL_83;
        else return FULL_100;
    }

    public void inject(SkullMeta meta) {
        GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(this.texture.getBytes(StandardCharsets.UTF_8)), "Liquid");
        profile.getProperties().put("textures", new Property("textures", this.texture));
        try {
            ReflectionUtils.setFieldValue(meta, "profile", profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int intRound(double d) {
        return (int) (d + 0.5);
    }
}
