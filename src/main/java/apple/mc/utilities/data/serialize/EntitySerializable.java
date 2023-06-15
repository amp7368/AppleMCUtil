package apple.mc.utilities.data.serialize;

import com.google.gson.JsonParseException;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import java.util.Objects;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntitySerializable {


    private transient ReadWriteNBT entityTag;
    private String nbt;
    private NamespacedKey entityTypeKey;

    public EntitySerializable(ItemStack item) {
        entityTag = new NBTItem(item).getCompound("EntityTag");
        this.nbt = entityTag.toString();
        String[] type = entityTag.getString("id").split(":");
        this.entityTypeKey = new NamespacedKey(type[0], type[1]);
    }

    public EntitySerializable(String nbt, NamespacedKey entityTypeKey) {
        this.nbt = nbt;
        this.entityTypeKey = entityTypeKey;
    }

    public EntitySerializable(String inputNBT) {
        NBTContainer inputContainer = new NBTContainer(inputNBT);
        boolean hasEntityTag = inputContainer.hasKey("EntityTag");
        this.entityTag = hasEntityTag ? inputContainer.getCompound("EntityTag") : inputContainer;
        String[] type = entityTag.getString("id").split(":");
        this.entityTypeKey = new NamespacedKey(type[0], type[1]);
        this.nbt = entityTag.toString();
    }

    public EntitySerializable(Entity entity) {
        this(entity, EntitySerialOptions.DEFAULT);
    }

    public EntitySerializable(Entity entity, EntitySerialOptions options) {
        if (options.removeUniqueData()) {
            removeUniqueAttributeModifiers(entity);
        }
        this.entityTypeKey = entity.getType().getKey();
        NBTContainer nbt = new NBTContainer();
        nbt.mergeCompound(new NBTEntity(entity));
        this.entityTag = nbt;
        if (options.removeLocation()) removeLocationData();
        if (options.removeUniqueData()) removeUniqueData();
        this.nbt = nbt.toString();
    }

    public EntitySerializable() {
    }

    public static void removeUniqueAttributeModifiers(Entity entity) {
        if (entity instanceof Attributable mob) {
            for (Attribute attribute : Attribute.values()) {
                AttributeInstance inst = mob.getAttribute(attribute);
                if (inst == null) continue;
                inst.getModifiers().stream()
                    .filter(modifier -> modifier.getName().equals("Random spawn bonus"))
                    .forEach(inst::removeModifier);
                removeAttribute(mob, Attribute.ZOMBIE_SPAWN_REINFORCEMENTS);
            }
        }
    }

    private static void removeAttribute(Attributable mob, Attribute attribute) {
        AttributeInstance inst = mob.getAttribute(attribute);
        if (inst == null) return;
        inst.setBaseValue(0);
        inst.getModifiers().forEach(inst::removeModifier);
    }

    public void removeLocationData() {
        entityTag.removeKey("Paper.Origin");
        entityTag.removeKey("Pos");
        entityTag.removeKey("Rotation");
    }

    public void mergeData(Entity entity) {
        NBT.modify(entity, (c) -> {
            c.mergeCompound(getEntityTag());
        });
    }

    public void removeUniqueData() {
        entityTag.removeKey("UUID");
        entityTag.removeKey("Brain");
        entityTag.removeKey("Spigot.ticksLived");
    }

    @NotNull
    public Component getDisplayName() {
        String name = this.getEntityTag().getString("CustomName");
        if (name == null || name.isBlank()) return Component.text(entityTypeKey.toString());
        try {
            return GsonComponentSerializer.gson().deserializeOrNull(name);
        } catch (JsonParseException e) {
            return Component.text("NULL?");
        }
    }

    @Nullable
    public Entity spawn(Location location) {
        return spawn(location, null);
    }

    @Nullable
    public Entity spawn(Location location, @Nullable Consumer<Entity> handleSpawn) {
        EntityType entityType = getEntityType();
        if (entityType == null)
            return null;
        Class<? extends Entity> entityClass = entityType.getEntityClass();
        if (entityClass == null) return null;
        return location.getWorld()
            .spawn(location, entityClass, false, e -> {
                this.mergeData(e);
                if (handleSpawn != null)
                    handleSpawn.accept(e);
            });

    }

    public ReadWriteNBT getEntityTag() {
        if (entityTag == null)
            entityTag = NBT.parseNBT(this.nbt);
        return entityTag;
    }

    public void saveEntityTag() {
        this.nbt = this.entityTag.toString();
    }

    @Nullable
    public EntityType getEntityType() {
        return Registry.ENTITY_TYPE.get(this.entityTypeKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nbt, this.entityTypeKey);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntitySerializable other) {
            return Objects.equals(this.nbt, other.nbt) &&
                Objects.equals(this.entityTypeKey, other.entityTypeKey);
        }
        return false;
    }
}
