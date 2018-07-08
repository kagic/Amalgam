package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelSardonyx;
import mod.akrivus.amalgam.gem.EntitySardonyx;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderSardonyx extends RenderGemBase<EntitySardonyx> {
	public RenderSardonyx() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSardonyx(), 0.5F);
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySardonyx entity) {
		return new ResourceLocation("amalgam:textures/entities/sardonyx/sardonyx.png");
	}
}