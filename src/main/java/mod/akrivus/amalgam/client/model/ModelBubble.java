package mod.akrivus.amalgam.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelBubble extends ModelBase {
	public ModelRenderer block;
	public ModelRenderer side1;
	public ModelRenderer side2;
	public ModelRenderer side3;
	public ModelRenderer side4;
	public ModelRenderer side5;
	public ModelRenderer side6;
	public ModelBubble() {
		this.textureHeight = 24; this.textureWidth = 48;
		this.block = new ModelRenderer(this, 0, 0);
		this.block.addBox(-6, -6, -6, 12, 12, 12);
		this.block.setRotationPoint(0, 0, 0);
		this.side1 = new ModelRenderer(this, 0, 0);
		this.side1.addBox(-5, -5,  6, 10, 10, 1);
		this.side1.setRotationPoint(0, 0, 0);
		this.side2 = new ModelRenderer(this, 0, 0);
		this.side2.addBox(-5, -5, -7, 10, 10, 1);
		this.side2.setRotationPoint(0, 0, 0);
		this.side3 = new ModelRenderer(this, 0, 0);
		this.side3.addBox( 6, -5, -5,  1, 10, 10);
		this.side3.setRotationPoint(0, 0, 0);
		this.side4 = new ModelRenderer(this, 0, 0);
		this.side4.addBox(-7, -5, -5,  1, 10, 10);
		this.side4.setRotationPoint(0, 0, 0);
		this.side5 = new ModelRenderer(this, 0, 0);
		this.side5.addBox(-5,  6, -5,  10, 1,  10);
		this.side5.setRotationPoint(0, 0, 0);
		this.side6 = new ModelRenderer(this, 0, 0);
		this.side6.addBox(-5, -7, -5,  10, 1,  10);
		this.side6.setRotationPoint(0, 0, 0);
	}
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 1.28, 0);
        GlStateManager.scale(0.5, 0.5, 0.5);
		this.block.render(scale);
		this.block.render(scale);
		this.side1.render(scale);
		this.side2.render(scale);
		this.side3.render(scale);
		this.side4.render(scale);
		this.side5.render(scale);
		this.side6.render(scale);
        GlStateManager.popMatrix();
	}
}