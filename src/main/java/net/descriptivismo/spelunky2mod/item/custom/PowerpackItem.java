package net.descriptivismo.spelunky2mod.item.custom;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.client.ModModelLayers;
import net.descriptivismo.spelunky2mod.block.entity.client.PowerpackModel;
import net.descriptivismo.spelunky2mod.item.ModItems;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public class PowerpackItem extends BackItem {

    public PowerpackItem(Properties pProperties) {
        super(pProperties);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
                        "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "body", new PowerpackModel(
                                Minecraft.getInstance().getEntityModels().
                                        bakeLayer(ModModelLayers.POWERPACK_LAYER)).root(),
                        "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                )));
                return armorModel;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return Spelunky2Mod.MODID + ":textures/entity/powerpack.png";
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event)
    {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        if (player.getInventory().getArmor(2).is(ModItems.POWERPACK.get())
            && player.getItemInHand(player.swingingArm).isEmpty())
        {
            event.setCanceled(true);
            Entity entity = event.getTarget();
            entity.hurt(player.damageSources().playerAttack(player), 2);
            entity.setSecondsOnFire(1);
            player.level().playSeededSound(null, player, Holder.direct(SoundEvents.PLAYER_ATTACK_STRONG),
                    player.getSoundSource(), 1.0f, 1.0f, player.level().random.nextInt());
        }
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);

        if (!level.isClientSide() && slotIndex == 38 && player.isOnFire())
        {
            level.playSound(null, player.position().x, player.position().y, player.position().z,
                    ModSounds.BACKPACK_WARN.get(), SoundSource.PLAYERS, 2.0f, 1.0f);
            level.explode(null, player.position().x, player.position().y, player.position().z,
                    6.0f, Level.ExplosionInteraction.TNT);
            player.getInventory().setItem(38, ItemStack.EMPTY);
        }
    }
}
