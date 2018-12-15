package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelTourmaline;
import mod.akrivus.amalgam.client.render.layers.LayerTourmalineHalf;
import mod.akrivus.amalgam.client.render.layers.LayerTourmalineItem;
import mod.akrivus.amalgam.gem.EntityTourmaline;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderTourmaline extends RenderGemBase<EntityTourmaline> {
	public RenderTourmaline() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTourmaline(), 0.25F);
		this.addLayer(new LayerTourmalineItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerTourmalineHalf(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
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
	protected ResourceLocation getEntityTexture(EntityTourmaline entity) {
		return new ResourceLocation("amalgam:textures/entities/wtourmaline/tourmaline.png");
	}
}