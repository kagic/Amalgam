package mod.amalgam.client.render.layers;

import mod.amalgam.gem.EntityEmerald;
import mod.kagic.client.render.RenderGemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEmeraldItem implements LayerRenderer<EntityEmerald> {
	protected final RenderGemBase<EntityEmerald> renderer;
	public LayerEmeraldItem(RenderGemBase<EntityEmerald> render) {
		this.renderer = render;
	}
	@Override
	public void doRenderLayer(EntityEmerald gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		boolean flag = gem.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack offhand = flag ? gem.getHeldItemOffhand() : gem.getHeldItemMainhand();
		ItemStack mainhand = flag ? gem.getHeldItemMainhand() : gem.getHeldItemOffhand();
		if (!offhand.isEmpty() || !mainhand.isEmpty()) {
			GlStateManager.pushMatrix();
			this.renderHeldItem(gem, mainhand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			this.renderHeldItem(gem, offhand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
			GlStateManager.popMatrix();
		}
	}
	private void renderHeldItem(EntityEmerald entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			if (entity.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}
			this.setSide(handSide);
			GlStateManager.rotate(-95.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = handSide == EnumHandSide.LEFT;
			GlStateManager.translate((flag ? -1 : 1) / 9.0F, 0.125F, -0.65F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
			GlStateManager.popMatrix();
		}
	}
	protected void setSide(EnumHandSide side) {
		((ModelBiped)(this.renderer.getMainModel())).postRenderArm(0.04F, side);
	}
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}