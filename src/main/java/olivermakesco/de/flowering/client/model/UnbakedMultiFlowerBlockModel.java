package olivermakesco.de.flowering.client.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class UnbakedMultiFlowerBlockModel implements UnbakedModel {
	public final UnbakedModel baseModel;
	public final int flowers;

	public UnbakedMultiFlowerBlockModel(UnbakedModel baseModel, int flowers) {
		this.baseModel = baseModel;
		this.flowers = flowers;
	}


	@Override
	public Collection<Identifier> getModelDependencies() {
		return baseModel.getModelDependencies();
	}

	@Override
	public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
		return baseModel.getTextureDependencies(unbakedModelGetter, unresolvedTextureReferences);
	}

	@Nullable
	@Override
	public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
		return new BakedMultiFlowerBlockModel(baseModel.bake(loader, textureGetter, rotationContainer, modelId), flowers);
	}
}
