package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreasureBlock extends FallingBlock {
    public static final EnumProperty<TreasureType> TREASURE_TYPE = EnumProperty.create("treasure_type",
            TreasureType.class);
    public static final IntegerProperty ROTATION_16 = BlockStateProperties.ROTATION_16;

    public enum TreasureType implements StringRepresentable {
        BAR(1, "bar"),
        BARS(3, "bars"),
        RED(5, "red"),
        BLUE(5, "blue"),
        GREEN(5, "green");

        private final int goldCount;
        private final String name;

        TreasureType(int goldCount, String name) { this.goldCount = goldCount; this.name = name; }

        public int getGoldCount() { return goldCount; }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public TreasureBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TREASURE_TYPE, TreasureType.BARS)
                .setValue(ROTATION_16, 0));
    }

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D));

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        double rand = Math.random();
        TreasureType type;
        if (rand < 0.4d) type = TreasureType.BAR;
        else if (rand < 0.7d) type = TreasureType.BARS;
        else if (rand < 0.8d) type = TreasureType.RED;
        else if (rand < 0.9d) type = TreasureType.BLUE;
        else type = TreasureType.GREEN;

        return this.defaultBlockState().setValue(TREASURE_TYPE, type)
                .setValue(ROTATION_16, (int)(Math.random() * 16));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TREASURE_TYPE, ROTATION_16);
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
        pLevel.scheduleTick(pPos, this, 1);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.tick(pState, pLevel, pPos, pRandom);

        List<Player> collidingPlayers = pLevel.getEntitiesOfClass(Player.class,
                SHAPE.bounds().move(pPos), EntitySelector.NO_SPECTATORS);
        if (!collidingPlayers.isEmpty())
        {
            int goldCount = pState.getValue(TREASURE_TYPE).getGoldCount();
            ItemStack itemStack = new ItemStack(Items.GOLD_NUGGET, goldCount);
            Inventory inventory = collidingPlayers.get(0).getInventory();

            if (inventory.getFreeSlot() > -1 || inventory.getSlotWithRemainingSpace(itemStack) > -1)
            {
                inventory.add(itemStack);
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        ModSounds.GOLD_COLLECT.get(), SoundSource.BLOCKS, 1f, 1f, pRandom.nextInt());
                pLevel.destroyBlock(pPos, false);
            }
        }

        pLevel.scheduleTick(pPos, this, 1);
    }
}
