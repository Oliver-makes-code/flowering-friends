package olivermakesco.de.flowering.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class MultiFlowerBlock extends FlowerBlock {
	public static final IntProperty flowerNumber = IntProperty.of("flowers",2,5);

	public final FlowerBlock base;
	public MultiFlowerBlock(FlowerBlock base) {
		super(base.getEffectInStew(), base.getEffectInStewDuration(), QuiltBlockSettings.copy(base));
		this.base = base;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(flowerNumber);
	}
}
