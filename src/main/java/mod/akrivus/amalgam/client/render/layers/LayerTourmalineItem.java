package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.gem.EntityTourmaline;
import mod.kagic.client.render.RenderGemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerTourmalineItem implements LayerRenderer<EntityTourmaline> {
	protected final RenderGemBase<EntityTourmaline> renderer;
	public LayerTourmalineItem(RenderGemBase<EntityTourmaline> renderer) {
		this.renderer = renderer;
	}
	@Override
	public void doRenderLayer(EntityTourmaline tourmaline, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		boolean flag = tourmaline.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack mainhand = flag ? tourmaline.getHeldItemOffhand() : tourmaline.getHeldItemMainhand();
		ItemStack offhand = flag ? tourmaline.getHeldItemMainhand() : tourmaline.getHeldItemOffhand();
		if (!mainhand.isEmpty() || !offhand.isEmpty()) {
			GlStateManager.pushMatrix();
			this.renderHeldItem(tourmaline, offhand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			this.renderHeldItem(tourmaline, mainhand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
			GlStateManager.popMatrix();
		}
	}
	private void renderHeldItem(EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			if (entity.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}
			this.setSide(handSide);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = handSide == EnumHandSide.LEFT;
			GlStateManager.translate((flag ? -1 : 1) / 16.0F, 0.125F, -0.7F);
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