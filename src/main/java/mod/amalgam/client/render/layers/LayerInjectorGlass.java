package mod.amalgam.client.render.layers;

import mod.amalgam.client.render.RenderInjector;
import mod.amalgam.entity.EntityInjector;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerInjectorGlass implements LayerRenderer<EntityInjector> {
    private final RenderInjector renderer;
    public LayerInjectorGlass(RenderInjector renderer) {
        this.renderer = renderer;
    }
    @Override
	public void doRenderLayer(EntityInjector entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        float[] rgb = EnumDyeColor.byDyeDamage(entity.getColor()).getColorComponentValues();
        if (!entity.isInvisible()) {
        	this.renderer.bindTexture(new ResourceLocation("amalgam:textures/entities/injector/glass.png"));
            GlStateManager.color(rgb[0], rgb[1], rgb[2], 0.5F);
    		GlStateManager.enableBlend();
    		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
    @Override
	public boolean shouldCombineTextures() {
        return false;
    }
}