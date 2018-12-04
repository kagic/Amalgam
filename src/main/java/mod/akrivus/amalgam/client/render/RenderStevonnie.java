package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.render.layers.LayerStevonnieClothing;
import mod.akrivus.amalgam.client.render.layers.LayerStevonnieHair;
import mod.akrivus.amalgam.client.render.layers.LayerStevonnieItem;
import mod.akrivus.amalgam.gem.EntityStevonnie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderStevonnie extends RenderLivingBase<EntityStevonnie> {
	public RenderStevonnie() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPlayer(0.0F, true), 0.25F);
        this.addLayer(new LayerStevonnieItem(this));
        this.addLayer(new LayerStevonnieClothing(this));
        this.addLayer(new LayerStevonnieHair(this));
        this.addLayer(new LayerArrow(this));
    }
	@Override
	protected ResourceLocation getEntityTexture(EntityStevonnie entity) {
		return new ResourceLocation("amalgam:textures/entities/stevonnie/stevonnie.png");
	}
}