package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.render.layers.LayerLowerHalf;
import mod.akrivus.amalgam.gem.EntityWatermelonTourmaline;
import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderWatermelonTourmaline extends RenderGemBase<EntityWatermelonTourmaline> {
	public RenderWatermelonTourmaline() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.5F);
		this.addLayer(new LayerQuartzItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerLowerHalf(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerQuartzCape(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
		LayerBipedArmor armor = new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelQuartz(0.5F, true);
				this.modelArmor = new ModelQuartz(1F, true);
			}
		};
		this.addLayer(armor);
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityWatermelonTourmaline entity) {
		return new ResourceLocation("amalgam:textures/entities/watermelon_tourmaline/watermelon_tourmaline.png");
	}
}