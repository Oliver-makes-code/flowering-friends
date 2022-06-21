package olivermakesco.de.flowering.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import olivermakesco.de.flowering.block.MultiFlowerBlock;
import olivermakesco.de.flowering.client.model.MultiFloweModelVariantProvider;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.registry.api.event.RegistryMonitor;

@Environment(EnvType.CLIENT)
public class ClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> new MultiFloweModelVariantProvider());

		RegistryMonitor.create(Registry.BLOCK).forAll(blockCtx -> {
			if (blockCtx.value() instanceof MultiFlowerBlock multiFlowerBlock) {
				BlockRenderLayerMap.put(RenderLayer.getCutout(), multiFlowerBlock);
			}
		});
	}
}
