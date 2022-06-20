package olivermakesco.de.flowering.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockRenderView;
import olivermakesco.de.flowering.block.MultiFlowerBlock;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class BakedMultiFlowerBlockModel extends ForwardingBakedModel {
	public BakedMultiFlowerBlockModel(BakedModel baseModel) {
		wrapped = baseModel;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}


	@Override
	public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<RandomGenerator> randomSupplier, RenderContext context) {
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);

		if (!(state.getBlock() instanceof MultiFlowerBlock multiFlowerBlock))
			return;


	}
}
