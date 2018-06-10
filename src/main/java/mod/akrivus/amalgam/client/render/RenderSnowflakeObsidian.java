package mod.akrivus.amalgam.client.render;

import mod.akrivus.amalgam.client.model.ModelObsidian;
import mod.akrivus.amalgam.client.render.layers.LayerObsidianItem;
import mod.akrivus.amalgam.gem.EntitySnowflakeObsidian;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderSnowflakeObsidian extends RenderGemBase<EntitySnowflakeObsidian> {
	public RenderSnowflakeObsidian() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelObsidian(), 0.25F);
        this.addLayer(new LayerObsidianItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
    }
	protected ResourceLocation getEntityTexture(EntitySnowflakeObsidian entity) {
		return new ResourceLocation("amalgam:textures/entities/snowflake_obsidian/obsidian.png");
	}
}