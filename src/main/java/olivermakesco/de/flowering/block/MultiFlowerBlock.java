package olivermakesco.de.flowering.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.ArrayList;
import java.util.List;

public class MultiFlowerBlock extends FlowerBlock {
	public static final IntProperty flowerNumber = IntProperty.of("flowers",2,5);

	public final FlowerBlock base;
	public MultiFlowerBlock(FlowerBlock base) {
		super(
				base.getEffectInStew(),
				base.getEffectInStewDuration(),
				QuiltBlockSettings.copy(base)
		);
		this.base = base;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(flowerNumber);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state.get(flowerNumber) == 5) return ActionResult.PASS;
		var heldItem = player.getStackInHand(hand);
		if (!(heldItem.getItem() instanceof BlockItem heldBlock)) return ActionResult.PASS;
		if (heldBlock.getBlock() != base) return ActionResult.PASS;
		world.setBlockState(pos, state.with(flowerNumber, state.get(flowerNumber)+1));
		if (!player.isCreative())
			player.getStackInHand(hand).decrement(1);
		return ActionResult.SUCCESS;
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return base.getPickStack(world, pos, state);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		List<ItemStack> stacks = new ArrayList<>();
		for (int i = 0; i < state.get(flowerNumber); i++)
			stacks.addAll(base.getDroppedStacks(base.getDefaultState(), builder));
		return stacks;
	}
}
