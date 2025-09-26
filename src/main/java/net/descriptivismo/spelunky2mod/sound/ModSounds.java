package net.descriptivismo.spelunky2mod.sound;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Spelunky2Mod.MODID);

    public static final RegistryObject<SoundEvent> UNLOCK = registerSoundEvents("unlock");

    public static final RegistryObject<SoundEvent> BONE_BLOCK_BREAK = registerSoundEvents("bone_block_break");
    public static final RegistryObject<SoundEvent> BONE_BLOCK_STEP = registerSoundEvents("bone_block_step");
    public static final RegistryObject<SoundEvent> BONE_BLOCK_FALL = registerSoundEvents("bone_block_fall");
    public static final RegistryObject<SoundEvent> BONE_BLOCK_PLACE = registerSoundEvents("bone_block_place");
    public static final RegistryObject<SoundEvent> BONE_BLOCK_HIT = registerSoundEvents("bone_block_hit");

    public static final RegistryObject<SoundEvent> ARROW_TRAP_FIRE = registerSoundEvents("arrow_trap_fire");

    public static final RegistryObject<SoundEvent> CRATE_BREAK = registerSoundEvents("crate_break");

    public static final RegistryObject<SoundEvent> SNAKE_DIE = registerSoundEvents("snake_die");
    public static final RegistryObject<SoundEvent> SNAKE_ATTACK = registerSoundEvents("snake_attack");

    public static final RegistryObject<SoundEvent> GOLD_COLLECT = registerSoundEvents("gold_collect");

    public static final RegistryObject<SoundEvent> NO_SOUND = registerSoundEvents("no_sound");

    public static final RegistryObject<SoundEvent> PICK_UP = registerSoundEvents("pick_up");
    public static final RegistryObject<SoundEvent> TOSS = registerSoundEvents("toss");

    public static final RegistryObject<SoundEvent> FUSE = registerSoundEvents("fuse");

    public static final RegistryObject<SoundEvent> RESOURCE_GET = registerSoundEvents("resource_get");

    public static final RegistryObject<SoundEvent> ROPE_ATTACH = registerSoundEvents("rope_attach");
    public static final RegistryObject<SoundEvent> ROPE_TOSS = registerSoundEvents("rope_toss");

    public static final ForgeSoundType BONE_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.BONE_BLOCK_BREAK, ModSounds.BONE_BLOCK_STEP, ModSounds.BONE_BLOCK_PLACE,
            ModSounds.BONE_BLOCK_HIT, ModSounds.BONE_BLOCK_FALL);
    public static final ForgeSoundType CRATE_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.CRATE_BREAK,
            ForgeRegistries.SOUND_EVENTS.getDelegateOrThrow(SoundEvents.WOOD_STEP),
            ForgeRegistries.SOUND_EVENTS.getDelegateOrThrow(SoundEvents.WOOD_PLACE),
            ForgeRegistries.SOUND_EVENTS.getDelegateOrThrow(SoundEvents.WOOD_HIT),
            ForgeRegistries.SOUND_EVENTS.getDelegateOrThrow(SoundEvents.WOOD_FALL)
            );

    public static final ForgeSoundType NO_BREAK_SOUNDS = new ForgeSoundType (1f, 1f,
            ModSounds.NO_SOUND, ModSounds.NO_SOUND,
            ForgeRegistries.SOUND_EVENTS.getDelegateOrThrow(SoundEvents.STONE_PLACE),
            ModSounds.NO_SOUND, ModSounds.NO_SOUND);


    private static RegistryObject<SoundEvent> registerSoundEvents(String name)
    {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(Spelunky2Mod.MODID, name)));
    }

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }

}
