package olivermakesco.de.flowering;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import olivermakesco.de.flowering.block.MultiFlowerBlock;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.api.event.RegistryMonitor;

import java.util.HashMap;
import java.util.Map;

public class Mod implements ModInitializer {
	public static final String modid = "floweringfriends";
	public static Map<FlowerBlock, MultiFlowerBlock> flowers = new HashMap<>();
	@Override
	public void onInitialize(ModContainer mod) {
		RegistryMonitor.create(Registry.BLOCK).forAll(blockCtx -> {
			var block = blockCtx.value();
			if (block instanceof FlowerBlock flowerBlock && !(flowerBlock instanceof MultiFlowerBlock)) {
				var id = blockCtx.id();
				String name;
				if (id.getNamespace().equals("minecraft"))
					name = "multiflower/"+id.getPath();
				else name = "multiflower/"+id.getNamespace()+"/"+id.getPath();
				var newBlock = new MultiFlowerBlock(flowerBlock);
				flowers.put(flowerBlock, newBlock);
				blockCtx.register(
						new Identifier(modid, name),
						newBlock
				);
			}
		});
		UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
			var handItem = player.getStackInHand(hand).getItem();
			if (!(handItem instanceof BlockItem blockItem)) return ActionResult.PASS;
			var hitState = world.getBlockState(blockHitResult.getBlockPos());
			if (!(hitState.getBlock() instanceof PlantBlock plantBlock)) return ActionResult.PASS;
			var nextState = flowers.get(plantBlock);
			if (nextState == null) return ActionResult.PASS;
			if (blockItem.getBlock() != hitState.getBlock()) return ActionResult.PASS;
			world.setBlockState(blockHitResult.getBlockPos(), nextState.getDefaultState());
			if (!player.isCreative())
				player.getStackInHand(hand).decrement(1);
			return ActionResult.SUCCESS;
		});
	}
}
