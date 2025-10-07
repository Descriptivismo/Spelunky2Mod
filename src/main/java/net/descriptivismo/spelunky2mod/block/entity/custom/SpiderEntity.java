package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.descriptivismo.spelunky2mod.block.entity.ai.SameLevelRandomStrollGoal;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class SpiderEntity extends SquishableMonster {

    private static final EntityDataAccessor<Boolean> PERCHED =
            SynchedEntityData.defineId(SpiderEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FIRST_TICK =
            SynchedEntityData.defineId(SpiderEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> JUMPING =
            SynchedEntityData.defineId(SpiderEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIMER =
            SynchedEntityData.defineId(SpiderEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Vector3f> DIR =
            SynchedEntityData.defineId(SpiderEntity.class, EntityDataSerializers.VECTOR3);


    private final int JUMP_DELAY = 15;
    private final int IDLE_DELAY = 40;
    private final int AGGRO_RANGE = 12;
    private final double JUMP_STRENGTH_Y = 1.12d;
    private final double JUMP_STRENGTH_HORIZ = 1.05d;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState perchedAnimationState = new AnimationState();
    public final AnimationState fallAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();

    public SpiderEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(PERCHED, true);
        this.entityData.define(FIRST_TICK, true);
        this.entityData.define(JUMPING, false);
        this.entityData.define(TIMER, IDLE_DELAY);
        this.entityData.define(DIR, new Vector3f(0, 0, 0));
    }

    @Override
    public void tick() {
        super.tick();

        if (entityData.get(FIRST_TICK))
        {
            setAnim(perchedAnimationState);
            setNoGravity(true);
            this.setDeltaMovement(0, 0, 0);
            setPos(blockPosition().getX() + 0.5f,
                    blockPosition().getY() + 0.6875f,
                    blockPosition().getZ() + 0.5f);

            entityData.set(FIRST_TICK, false);
        }

        if (checkSquish() || this.getHealth() <= 0)
            return;

        List<Entity> entities = level().getEntities(this, getBoundingBox().inflate(0.1d));
        for (Entity entity : entities)
        {
            if (entity instanceof Player)
            {
                Player player = (Player)entity;
                player.hurt(damageSources().mobAttack(this),
                        (float)getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            }
        }

        if (entityData.get(PERCHED))
        {
            setAnim(perchedAnimationState);

            //if (!level().isClientSide())
            {
                if (this.getHealth() < this.getMaxHealth())
                {
                    fall();
                }
                else if (level().getBlockState(this.blockPosition().above()).isAir())
                {
                    fall();
                }
                else
                {
                    List<Entity> entities2 = level().getEntities(this,
                            getBoundingBox().setMinY(position().y - 8));

                    for (Entity entity : entities2)
                    {
                        if (entity instanceof Player) {
                            fall();
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            if (this.onGround())
            {
                entityData.set(TIMER, entityData.get(TIMER) - 1);
                if (entityData.get(JUMPING))
                {
                    Vector3f dir = entityData.get(DIR);
                    if (entityData.get(TIMER) <= 0)
                    {
                        jumpFromGround();
                        this.setDeltaMovement(
                                dir.x * JUMP_STRENGTH_HORIZ,
                                this.getDeltaMovement().y * JUMP_STRENGTH_Y,
                                dir.z * JUMP_STRENGTH_HORIZ);
                        level().playSeededSound(null, position().x, position().y, position().z,
                                ModSounds.SPIDER_JUMP.get(), SoundSource.HOSTILE, 1f, 1f, random.nextInt());
                    }
                    else
                    {
                        Vec3 target = new Vec3(
                                dir.x + position().x, dir.y + position().y, dir.z + position().z);
                        lookAt(EntityAnchorArgument.Anchor.FEET, target);
                    }
                }
                else
                {
                    if (entityData.get(TIMER) <= 0)
                    {
                        Player player = level().getNearestPlayer(this, AGGRO_RANGE);
                        if (player != null)
                        {
                            Vector3f dir = (player.position().subtract(position())).normalize().toVector3f();
                            entityData.set(DIR, dir);
                            startJumping();
                        }
                    }
                    else
                    {
                        setAnim(idleAnimationState);
                    }
                }
            }
            else
            {
                entityData.set(JUMPING, false);
                entityData.set(TIMER, IDLE_DELAY);
            }
        }
    }

    private void startJumping()
    {
        entityData.set(TIMER, JUMP_DELAY);
        entityData.set(JUMPING, true);
        setAnim(jumpAnimationState);
    }

    private void fall()
    {
        setNoGravity(false);
        setAnim(fallAnimationState);
        entityData.set(PERCHED, false);
        this.setDeltaMovement(getDeltaMovement().x, -0.25f, getDeltaMovement().z);
        level().playSeededSound(null, position().x, position().y, position().z,
                ModSounds.SPIDER_DROP.get(), SoundSource.HOSTILE, 1f, 1f, random.nextInt());
    }

    private void setAnim(AnimationState anim)
    {
        if (anim != idleAnimationState)
            idleAnimationState.stop();
        if (anim != perchedAnimationState)
            perchedAnimationState.stop();
        if (anim != fallAnimationState)
            fallAnimationState.stop();
        if (anim != jumpAnimationState)
            jumpAnimationState.stop();
        anim.startIfStopped(tickCount);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.FOLLOW_RANGE, 12)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.ATTACK_SPEED, 1)
                ;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SPIDER_DIE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.SPIDER_DIE.get();
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    public boolean shouldDropExperience() {
        return false;
    }

    protected boolean shouldDropLoot() {
        return false;
    }
}
