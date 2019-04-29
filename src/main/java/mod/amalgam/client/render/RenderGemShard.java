package mod.amalgam.client.render;

import mod.amalgam.entity.EntityGemShard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderGemShard extends RenderLiving<EntityGemShard> {
    public RenderGemShard() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped() {
        	@Override
			public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        		return;
        	}
        }, 0.25F);
    }
    @Override
	public void doRender(EntityGemShard entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y  + Math.sin(entity.getIdleTime() * 50) / 50 + 1.2D, z);
        GlStateManager.scale(1.5D, 1.5D, 1.5D);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        Minecraft.getMinecraft().getItemRenderer().renderItem(entity, entity.getItem(), ItemCameraTransforms.TransformType.GROUND);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    @Override
	protected ResourceLocation getEntityTexture(EntityGemShard entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}