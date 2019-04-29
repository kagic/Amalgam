package mod.amalgam.client.render;

import mod.amalgam.client.model.ModelPalanquin;
import mod.amalgam.client.render.layers.LayerPalanquinColor;
import mod.amalgam.client.render.layers.LayerPalanquinHighlights;
import mod.amalgam.client.render.layers.LayerPalanquinVeil;
import mod.amalgam.entity.EntityPalanquin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderPalanquin extends RenderLiving<EntityPalanquin> {
	public RenderPalanquin() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelPalanquin(), 0.5F);
		this.layerRenderers.add(new LayerPalanquinColor(this));
		this.layerRenderers.add(new LayerPalanquinHighlights(this));
		this.layerRenderers.add(new LayerPalanquinVeil(this));
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityPalanquin entity) {
		return new ResourceLocation("amalgam:textures/entities/white.png");
	}
}
