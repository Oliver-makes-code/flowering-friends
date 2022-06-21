package olivermakesco.de.flowering.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.BlockRenderView;
import olivermakesco.de.flowering.block.MultiFlowerBlock;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class BakedMultiFlowerBlockModel extends ForwardingBakedModel {
	public final int flowers;
	public BakedMultiFlowerBlockModel(BakedModel baseModel, int flowers) {
		this.wrapped = baseModel;
		this.flowers = flowers;
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

		context.pushTransform(quad -> {
			Vec3f vec = null;
			for (int i = 0; i < 4; i++) {
				vec = quad.copyPos(i, vec);
				vec.multiplyComponentwise(0.75f, 0.75f, 0.75f);
				quad.pos(i, vec);
			}
			quad.material(RendererAccess.INSTANCE.getRenderer().materialFinder().find());
			return true;
		});

		for (int i = 2; i <= flowers; i++) {
			float offsetx = switch (i) {
				case 2 -> -0.25f;
				case 3 -> -0.3f;
				case 4 -> 0.65f;
				default -> 0.5f;
			};
			float offsetz = switch (i) {
				case 2 -> -0.3f;
				case 3 -> 0.5f;
				case 4 -> -0.25f;
				default -> 0.65f;
			};
			draw(blockView, state, pos, randomSupplier, context, offsetx, -0.1f, offsetz);
		}

		context.popTransform();
	}

	private void draw(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<RandomGenerator> randomSupplier, RenderContext context, float x, float y, float z) {
		context.pushTransform(quad -> {
			Vec3f vec = null;
			for (int i = 0; i < 4; i++) {
				vec = quad.copyPos(i, vec);
				vec.add(x,y,z);
				quad.pos(i, vec);
			}
			quad.material(RendererAccess.INSTANCE.getRenderer().materialFinder().find());
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}
}
