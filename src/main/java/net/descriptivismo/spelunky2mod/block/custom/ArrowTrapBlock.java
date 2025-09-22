package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class ArrowTrapBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public ArrowTrapBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());//.getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TRIGGERED);
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        pLevel.scheduleTick(pPos, this, 1);
    }

    /*@Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        System.out.println("pleegis");
        fireArrow(pState, pLevel, pPos);
        return InteractionResult.SUCCESS;
    }*/

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(TRIGGERED))
            return;

        Direction dir = pState.getValue(FACING);
        AABB detectionBox = new AABB(
                dir.getStepZ() * -0.25d,
                -0.25d,
                dir.getStepX() * -0.25d,
                dir.getStepZ() * 0.25d + dir.getStepX() * 6.5d,
                0.25d,
                dir.getStepX() * 0.25d + dir.getStepZ() * 6.5d)
                .move(pPos)
                .move(new Vec3(0.5d, 0.5d, 0.5d));

        if (!pLevel.getEntitiesOfClass(Entity.class, detectionBox, EntitySelector.NO_SPECTATORS).isEmpty())
        {
            pState.setValue(TRIGGERED, Boolean.valueOf(true));
            fireArrow(pState, pLevel, pPos);
        }
        else
        {
            pLevel.scheduleTick(pPos, this, 1);
        }
    }

    private void fireArrow(BlockState pState, Level pLevel, BlockPos pPos) {
        Direction dir = pState.getValue(FACING);

        Arrow arrow = new Arrow(pLevel,
                pPos.getX() + 0.5f + dir.getStepX() * 0.6f,
                pPos.getY() + 0.5f,
                pPos.getZ() + 0.5f + dir.getStepZ() * 0.6f);
        arrow.shoot(dir.getStepX(),0.0d, dir.getStepZ(),2.0f,1.0f);
        pLevel.addFreshEntity(arrow);

        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                ModSounds.ARROW_TRAP_FIRE.get(), SoundSource.BLOCKS, 1f, 1f, 0);
    }
}
