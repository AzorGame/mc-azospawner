package io.github.azorimor.azospawner.utils;

import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;

public class PickaxeItemTagType implements ItemTagType<String, Boolean> {

    public Class<String> getPrimitiveType() {
        return String.class;
    }

    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    public String toPrimitive(Boolean aBoolean, ItemTagAdapterContext itemTagAdapterContext) {
        return String.valueOf(aBoolean);
    }

    public Boolean fromPrimitive(String s, ItemTagAdapterContext itemTagAdapterContext) {
        return Boolean.valueOf(s);
    }
}
