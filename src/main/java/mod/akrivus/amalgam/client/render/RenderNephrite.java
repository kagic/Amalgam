package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelNephrite;
import mod.akrivus.amalgam.client.render.layers.LayerNephriteItem;
import mod.akrivus.amalgam.gem.EntityNephrite;
import mod.kagic.client.render.RenderGemBase;
import mod.kagic.client.render.layers.LayerBirthdayHat;
import mod.kagic.client.render.layers.LayerGemPlacement;
import mod.kagic.client.render.layers.LayerHair;
import mod.kagic.client.render.layers.LayerInsignia;
import mod.kagic.client.render.layers.LayerSantaHat;
import mod.kagic.client.render.layers.LayerSkin;
import mod.kagic.client.render.layers.LayerUniform;
import mod.kagic.client.render.layers.LayerWitchHat;
import mod.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderNephrite extends RenderGemBase<EntityNephrite> {
	public RenderNephrite() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelNephrite(), 0.5F);
        this.addLayer(new LayerNephriteItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }	
	@Override
	protected ResourceLocation getEntityTexture(EntityNephrite entity) {
		return new ResourceLocation("amalgam:textures/entities/nephrite/nephrite.png");
	}
}