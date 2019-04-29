package mod.amalgam.client.render;

import mod.amalgam.client.model.ModelSteven;
import mod.amalgam.client.render.layers.LayerCheeseburgerBackpack;
import mod.amalgam.client.render.layers.LayerStevenItem;
import mod.amalgam.human.EntitySteven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderSteven extends RenderLiving<EntitySteven> {
	public RenderSteven() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSteven(), 0.25F);
        this.addLayer(new LayerStevenItem(this));
        this.addLayer(new LayerCheeseburgerBackpack(this));
        this.addLayer(new LayerArrow(this));
    }
	@Override
	protected ResourceLocation getEntityTexture(EntitySteven entity) {
		return new ResourceLocation("amalgam:textures/entities/steven/steven.png");
	}
}