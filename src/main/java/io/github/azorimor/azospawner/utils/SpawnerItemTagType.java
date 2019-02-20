package io.github.azorimor.azospawner.utils;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;

public class SpawnerItemTagType implements ItemTagType<String, EntityType> {

    public Class<String> getPrimitiveType() {

        return String.class;
    }

    public Class<EntityType> getComplexType() {
        return EntityType.class;
    }

    public String toPrimitive(EntityType entityType, ItemTagAdapterContext itemTagAdapterContext) {

        return entityType.toString();
    }

    public EntityType fromPrimitive(String s, ItemTagAdapterContext itemTagAdapterContext) {
        return EntityType.valueOf(s);
    }
}
