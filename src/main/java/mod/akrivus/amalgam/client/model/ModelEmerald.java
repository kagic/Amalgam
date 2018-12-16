package mod.akrivus.amalgam.client.model;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelEmerald extends ModelGem {
	private ModelRenderer bipedLeftShoulder;
	private ModelRenderer bipedRightShoulder;
	private ModelRenderer bipedHair;
	private ModelRenderer bipedWaist;
	private ModelRenderer bipedHips;
    public ModelEmerald() {
		super(0F, 0F, 64, 64, false, -1F);
		this.bipedHead = new ModelRenderer(this, 28, 14);
		this.bipedHead.addBox(-4F, -14F, -4F, 8, 8, 8);
        this.bipedHead.setRotationPoint(0F, -8F, 0F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 50);
        this.bipedHeadwear.addBox(-5F, -14F, -5F, 10, 4, 2);
        this.bipedHeadwear.setRotationPoint(0F, -8F, 0F);
        this.bipedHair = new ModelRenderer(this, 24, 44);
        this.bipedHair.addBox(-17F, -17F, 0F, 12, 12, 8);
        this.bipedHair.setRotationPoint(0F, 0F, -3F);
        this.bipedBody = new ModelRenderer(this, 24, 0);
        this.bipedBody.addBox(-8F, -8F, -3.5F, 7, 7, 7);
        this.bipedBody.setRotationPoint(0F, 2.6F, 0F);
        this.bipedHips = new ModelRenderer(this, 0, 12);
        this.bipedHips.addBox(-4F, 1F, -3F, 8, 4, 6);
        this.bipedHips.setRotationPoint(0F, 3F, 0F);
        this.bipedRightShoulder = new ModelRenderer(this, 0, 22);
        this.bipedRightShoulder.addBox(0F, -2F, -2F, 6, 4, 4);
        this.bipedRightShoulder.setRotationPoint(3F, -4.5F, 0F);
        this.bipedLeftShoulder = new ModelRenderer(this, 0, 22);
        this.bipedLeftShoulder.addBox(0F, -2F, -2F, 6, 4, 4);
        this.bipedLeftShoulder.setRotationPoint(-3F, -4.5F, 0F);
        this.bipedRightArm = new ModelRenderer(this, 0, 30);
        this.bipedRightArm.addBox(-1.5F, -2F, -1.5F, 3, 13, 3);
        this.bipedRightArm.setRotationPoint(2F, -2F, 0F);
        this.bipedLeftArm = new ModelRenderer(this, 0, 30);
        this.bipedLeftArm.addBox(-1.5F, -2F, -1.5F, 3, 13, 3);
        this.bipedLeftArm.setRotationPoint(2F, -2F, 0F);
        this.bipedRightLeg = new ModelRenderer(this, 12, 30);
        this.bipedRightLeg.addBox(1F, -4F, -2F, 3, 16, 4);
        this.bipedRightLeg.setRotationPoint(0F, 16F, 0F);
        this.bipedLeftLeg = new ModelRenderer(this, 12, 30);
        this.bipedLeftLeg.addBox(-4F, -4F, -2F, 3, 16, 4);
        this.bipedLeftLeg.setRotationPoint(0F, 16F, 0F);
        this.bipedWaist = new ModelRenderer(this, 0, 0);
        this.bipedWaist.addBox(-3F, -2F, -3F, 6, 6, 6);
        this.bipedWaist.setRotationPoint(0F, 0F, 0F);
        this.bipedHeadwear.addChild(this.bipedHair);
    }
    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	GlStateManager.pushMatrix();
        GlStateManager.translate(this.bipedWaist.offsetX, this.bipedWaist.offsetY, this.bipedWaist.offsetZ);
        GlStateManager.translate(this.bipedWaist.rotationPointX * scale, this.bipedWaist.rotationPointY * scale, this.bipedWaist.rotationPointZ * scale);
        GlStateManager.scale(1.1D, 1.1D, 1.1D);
        GlStateManager.translate(-this.bipedWaist.offsetX, -this.bipedWaist.offsetY, -this.bipedWaist.offsetZ);
        GlStateManager.translate(-this.bipedWaist.rotationPointX * scale, -this.bipedWaist.rotationPointY * scale, -this.bipedWaist.rotationPointZ * scale);
    	super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale); 
    	this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBody.render(scale);
		this.bipedHips.render(scale);
		this.bipedRightShoulder.render(scale);
		this.bipedLeftShoulder.render(scale);
		this.bipedRightArm.mirror = true;
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.mirror = false;
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
		this.bipedWaist.render(scale);
        GlStateManager.popMatrix();
    }
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		super.copyModelAngles(this.bipedHead, this.bipedHeadwear);
    	this.bipedBody.rotateAngleZ = 0.7853981633974483F;
    	this.bipedRightShoulder.rotateAngleZ = -0.5235987755982988F;
    	this.bipedLeftShoulder.rotateAngleZ = -2.6179938779914944F;
        this.bipedHair.rotateAngleX = -0.2617993877991494F;
    	this.bipedHair.rotateAngleY =  0.2617993877991494F;
    	this.bipedHair.rotateAngleZ =  0.7853981633974483F;
    }
}