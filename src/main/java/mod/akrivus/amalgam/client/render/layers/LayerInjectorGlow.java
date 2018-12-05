package mod.akrivus.amalgam.client.render.layers;

import mod.akrivus.amalgam.client.render.RenderInjector;
import mod.akrivus.amalgam.entity.EntityInjector;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerInjectorGlow implements LayerRenderer<EntityInjector> {
    private final RenderInjector renderer;
    public LayerInjectorGlow(RenderInjector renderer) {
        this.renderer = renderer;
    }
    public void doRenderLayer(EntityInjector entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        float[] rgb = EnumDyeColor.byDyeDamage(entity.getColor()).getColorComponentValues();
        if (!entity.isInvisible()) {
        	this.renderer.bindTexture(new ResourceLocation("amalgam:textures/entities/injector/glass.png"));
        	GlStateManager.enableBlend();
            GlStateManager.color(rgb[0], rgb[1], rgb[2]);
            GlStateManager.disableAlpha();
            this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
    public boolean shouldCombineTextures() {
        return false;
    }
}