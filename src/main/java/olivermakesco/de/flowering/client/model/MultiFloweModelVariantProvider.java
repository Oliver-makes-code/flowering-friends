package olivermakesco.de.flowering.client.model;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import olivermakesco.de.flowering.Mod;
import olivermakesco.de.flowering.block.MultiFlowerBlock;
import org.jetbrains.annotations.Nullable;

public class MultiFloweModelVariantProvider implements ModelVariantProvider {
	@Override
	public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) throws ModelProviderException {
		if (modelId.getNamespace().equals(Mod.modid) && modelId.getPath().startsWith("multiflower/")) {
			if (Registry.BLOCK.get(new Identifier(modelId.getNamespace(), modelId.getPath())) instanceof MultiFlowerBlock multiFlowerBlock) {
				for (int i = 2; i < 6; i++) {
					var state = multiFlowerBlock.getDefaultState().with(MultiFlowerBlock.flowerNumber, i);
					if (modelId.equals(BlockModels.getModelId(state))) {
						var flowerState = multiFlowerBlock.base.getDefaultState();
						var flowerModel = context.loadModel(BlockModels.getModelId(flowerState));
						return new UnbakedMultiFlowerBlockModel(flowerModel, i);
					}
				}
			}
		}
		return null;
	}
}
