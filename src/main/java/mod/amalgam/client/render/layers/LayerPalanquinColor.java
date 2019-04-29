package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderPalanquin;
import mod.amalgam.entity.EntityPalanquin;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPalanquinColor implements LayerRenderer<EntityPalanquin> {
    private final RenderPalanquin renderer;
    public LayerPalanquinColor(RenderPalanquin renderer) {
        this.renderer = renderer;
    }
    @Override
	public void doRenderLayer(EntityPalanquin entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        float[] rgb = EnumDyeColor.byDyeDamage(entity.getBodyColor()).getColorComponentValues();
        if (!entity.isInvisible()) {
        	this.renderer.bindTexture(new ResourceLocation("amalgam:textures/entities/palanquin/palanquin.png"));
            GlStateManager.color(rgb[0], rgb[1], rgb[2], 1.0F);
            this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    		GlStateManager.disableBlend();
        }
    }
    @Override
	public boolean shouldCombineTextures() {
        return false;
    }
}