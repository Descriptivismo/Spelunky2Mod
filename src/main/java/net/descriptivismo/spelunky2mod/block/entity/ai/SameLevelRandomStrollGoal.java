package net.descriptivismo.spelunky2mod.block.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.ToDoubleFunction;

public class SameLevelRandomStrollGoal extends RandomStrollGoal {

    public SameLevelRandomStrollGoal(PathfinderMob pMob, double pSpeedModifier, int pInterval) {
        super(pMob, pSpeedModifier, pInterval);
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3 == null ? super.getPosition() : vec3;
        } else {
            System.out.println("glee");
            return getPos(this.mob, 10, 0);
        }
    }

    @Nullable
    public static Vec3 getPos(PathfinderMob pMob, int pRadius, int pVerticalRange) {
        return getPos(pMob, pRadius, pVerticalRange, pMob::getWalkTargetValue);
    }

    @Nullable
    public static Vec3 getPos(PathfinderMob pMob, int pRadius, int pYRange, ToDoubleFunction<BlockPos> pToDoubleFunction) {
        boolean flag = GoalUtils.mobRestricted(pMob, pRadius);
        return RandomPos.generateRandomPos(() -> {
            BlockPos blockpos = RandomPos.generateRandomDirection(pMob.getRandom(), pRadius, pYRange);
            BlockPos blockpos1 = generateRandomPosTowardDirection(pMob, pRadius, flag, blockpos);

            if (blockpos1 == null || GoalUtils.isSolid(pMob, blockpos1))
                return null;

            Path path = pMob.getNavigation().createPath(blockpos1, 0);
            if (path == null) return null;
            for (int i = 0; i < path.getNodeCount(); i++)
            {
                Node node = path.getNode(i);
                if (node.y != blockpos1.getY())
                    return null;
            }

            return blockpos1;
        }, pToDoubleFunction);
    }

    @Nullable
    public static BlockPos generateRandomPosTowardDirection(PathfinderMob pMob, int pRadius, boolean pShortCircuit, BlockPos pPos) {
        BlockPos blockpos = RandomPos.generateRandomPosTowardDirection(pMob, pRadius, pMob.getRandom(), pPos);
        return !GoalUtils.isOutsideLimits(blockpos, pMob) && !GoalUtils.isRestricted(pShortCircuit, pMob, blockpos) && !GoalUtils.isNotStable(pMob.getNavigation(), blockpos) ? blockpos : null;
    }
}
