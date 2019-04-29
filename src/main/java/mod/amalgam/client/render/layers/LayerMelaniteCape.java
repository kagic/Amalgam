package mod.amalgam.client.render.layers;

import mod.amalgam.gem.EntityMelanite;
import mod.kagic.client.model.ModelGem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerMelaniteCape implements LayerRenderer<EntityMelanite> {
	private final RenderLivingBase<EntityMelanite> renderer;
	private final boolean isBack;
	private final boolean useInsigniaColors;
	public LayerMelaniteCape(RenderLivingBase<EntityMelanite> renderer, boolean isBack, boolean useInsigniaColors) {
		this.renderer = renderer;
		this.isBack = isBack;
		this.useInsigniaColors = useInsigniaColors;
	}
	@Override
	public void doRenderLayer(EntityMelanite melanite, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!melanite.isInvisible() && melanite.hasCape())	{
			this.renderer.bindTexture(this.getTexture(melanite));
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 0.125F);
			double d0 = melanite.prevChasingPosX + (melanite.chasingPosX - melanite.prevChasingPosX) * partialTicks - (melanite.prevPosX + (melanite.posX - melanite.prevPosX) * partialTicks);
			double d1 = melanite.prevChasingPosY + (melanite.chasingPosY - melanite.prevChasingPosY) * partialTicks - (melanite.prevPosY + (melanite.posY - melanite.prevPosY) * partialTicks);
			double d2 = melanite.prevChasingPosZ + (melanite.chasingPosZ - melanite.prevChasingPosZ) * partialTicks - (melanite.prevPosZ + (melanite.posZ - melanite.prevPosZ) * partialTicks);
			float f = melanite.prevRenderYawOffset + (melanite.renderYawOffset - melanite.prevRenderYawOffset) * partialTicks;
			double d3 = MathHelper.sin(f * 0.017453292F);
			double d4 = (-MathHelper.cos(f * 0.017453292F));
			float f1 = (float)d1 * 10.0F;
			f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
			float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
			float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
			if (f2 < 0.0F) {
				f2 = 0.0F;
			}
			f1 = f1 + MathHelper.sin((melanite.prevDistanceWalkedModified + (melanite.distanceWalkedModified - melanite.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F;
			if (melanite.isSneaking()) {
				f1 += 25.0F;
			}
			GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			float[] afloat;
			if (this.useInsigniaColors) {
				afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[melanite.getInsigniaColor()]);
			}
			else {
				afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[melanite.getUniformColor()]);
			}
			GlStateManager.color(afloat[0] * 2, afloat[1] * 2, afloat[2] * 2);
			((ModelGem)(this.renderer.getMainModel())).renderCape(0.0625F);
			GlStateManager.popMatrix();
		}
	}
	public ResourceLocation getTexture(EntityMelanite melanite) {
		if (this.isBack) {
			return new ResourceLocation("amalgam:textures/entities/melanite/cape_back.png");
		} else {
			return new ResourceLocation("amalgam:textures/entities/melanite/cape.png");
		}
	}
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
