package mod.akrivus.amalgam.client.model;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEmerald extends ModelGem {
	private ModelRenderer bipedLeftShoulder;
	private ModelRenderer bipedRightShoulder;
	private ModelRenderer bipedHair;
	private ModelRenderer bipedWaist;
	private ModelRenderer bipedHips;
    public ModelEmerald() {
		super(0.0F, 0.0F, 64, 64, false, -1F);
		this.bipedHead = new ModelRenderer(this, 28, 14);
		this.bipedHead.addBox(-4.0F, -14.0F, -4.0F, 8, 8, 8);
        this.bipedHead.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 50);
        this.bipedHeadwear.addBox(-5.0F, -14.0F, -5.0F, 10, 4, 2);
        this.bipedHeadwear.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.bipedHair = new ModelRenderer(this, 24, 44);
        this.bipedHair.setRotationPoint(0.0F, 0.0F, -3F);
        this.bipedHair.addBox(-17.0F, -17.0F, 0.0F, 12, 12, 8);
        this.bipedHair.rotateAngleX += -0.26F;
    	this.bipedHair.rotateAngleY +=  0.26F;
    	this.bipedHair.rotateAngleZ +=  0.76F;
        this.bipedBody = new ModelRenderer(this, 24, 0);
        this.bipedBody.setRotationPoint(0.0F, 2.6F, 0.0F);
        this.bipedBody.addBox(-8.0F, -8.0F, -3.5F, 7, 7, 7);
        this.bipedHips = new ModelRenderer(this, 0, 12);
        this.bipedHips.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.bipedHips.addBox(-4.0F, 1.0F, -3.0F, 8, 4, 6);
        this.bipedRightShoulder = new ModelRenderer(this, 0, 22);
        this.bipedRightShoulder.setRotationPoint(3.0F, -4.5F, 0.0F);
        this.bipedRightShoulder.addBox(0.0F, -2.0F, -2.0F, 6, 4, 4);
        this.bipedLeftShoulder = new ModelRenderer(this, 0, 22);
        this.bipedLeftShoulder.setRotationPoint(-3.0F, -4.5F, 0.0F);
        this.bipedLeftShoulder.addBox(0.0F, -2.0F, -2.0F, 6, 4, 4);
        this.bipedRightLeg = new ModelRenderer(this, 12, 30);
        this.bipedRightLeg.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.bipedRightLeg.addBox(0.0F, -4.0F, -2.0F, 3, 16, 4);
        this.bipedLeftLeg = new ModelRenderer(this, 12, 30);
        this.bipedLeftLeg.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.bipedLeftLeg.addBox(-3.0F, -4.0F, -2.0F, 3, 16, 4);
        this.bipedRightArm = new ModelRenderer(this, 0, 30);
        this.bipedRightArm.addBox(-1.5F, -2.0F, -1.5F, 3, 13, 3);
        this.bipedRightArm.setRotationPoint(2.0F, -2.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 0, 30);
        this.bipedLeftArm.addBox(-1.5F, -2.0F, -1.5F, 3, 13, 3);
        this.bipedLeftArm.setRotationPoint(2.0F, -2.0F, 0.0F);
        this.bipedWaist = new ModelRenderer(this, 0, 0);
        this.bipedWaist.addBox(-3.0F, -2.0F, -3.0F, 6, 6, 6);
        this.bipedWaist.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addChild(this.bipedHair);
    }
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale); 
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
    }
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		super.copyModelAngles(this.bipedHead, this.bipedHeadwear);
    	this.bipedBody.rotateAngleZ = 0.78F;
    	this.bipedRightShoulder.rotateAngleZ = -0.52F;
    	this.bipedLeftShoulder.rotateAngleZ = -2.62F;
	}
}