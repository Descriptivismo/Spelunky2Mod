package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class SquishableMonster extends Monster {

    protected SquishableMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boolean checkSquish() {

        if (this.getHealth() <= 0)
            return false;

        List<Entity> entities = level().getEntities(this, getBoundingBox().inflate(0.1d));
        boolean ret = false;

        for (Entity entity : entities) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                double playerY = player.getBoundingBox().minY;
                double monsterY = getBoundingBox().maxY;

                if (monsterY - playerY < 0.2d && player.getDeltaMovement().y < this.getDeltaMovement().y) {
                    ret = true;
                    this.hurt(damageSources().playerAttack(player), 4);
                    player.jumpFromGround();
                }
            }
        }

        return ret;
    }
}
