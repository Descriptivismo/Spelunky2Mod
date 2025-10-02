package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.descriptivismo.spelunky2mod.block.entity.ai.SameLevelRandomStrollGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SpiderEntity extends SquishableMonster {

    public SpiderEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    public boolean shouldDropExperience() {
        return false;
    }

    protected boolean shouldDropLoot() {
        return false;
    }
}
