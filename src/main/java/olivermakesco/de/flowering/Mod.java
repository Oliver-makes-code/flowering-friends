package olivermakesco.de.flowering;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import olivermakesco.de.flowering.block.MultiFlowerBlock;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.api.event.RegistryEvents;

import java.util.HashMap;
import java.util.Map;

public class Mod implements ModInitializer {
	public static final String modid = "floweringfriends";
	public static Map<FlowerBlock, MultiFlowerBlock> flowers = new HashMap<>();
	@Override
	public void onInitialize(ModContainer mod) {
		RegistryEvents.getEntryAddEvent(Registry.BLOCK).register(blockCtx -> {
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
			var hitState = world.getBlockState(blockHitResult.getBlockPos());
			if (!(hitState.getBlock() instanceof PlantBlock plantBlock) || hitState.getBlock() instanceof MultiFlowerBlock) return ActionResult.PASS;
			var nextState = flowers.get(plantBlock);
			if (nextState == null) return ActionResult.PASS;
			world.setBlockState(blockHitResult.getBlockPos(), nextState.getDefaultState());
			return ActionResult.SUCCESS;
		});
	}
}
