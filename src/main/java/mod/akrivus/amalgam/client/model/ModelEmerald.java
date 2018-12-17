package mod.akrivus.amalgam.client.model;

import mod.akrivus.kagic.client.model.ModelGem;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelRenderer;
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
		this.bipedHead.addBox(-4F, -15F, -4F, 8, 8, 8);
        this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}
		
        this.bipedHeadwear = new ModelRenderer(this, 0, 50);
        this.bipedHeadwear.addBox(-5F, -15F, -4F, 10, 4, 2);
        this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
        
        this.bipedHair = new ModelRenderer(this, 24, 44);
        this.bipedHair.addBox(-18.5F, -18F, 0F, 12, 12, 8);
        this.bipedHair.setRotationPoint(0F, 0F, -3F);
        
        this.bipedBody = new ModelRenderer(this, 24, 0);
        this.bipedBody.addBox(-8F, -8F, -3.5F, 7, 7, 7);
        this.bipedBody.setRotationPoint(0F, 2.6F, 0F);
        
        this.bipedHips = new ModelRenderer(this, 0, 12);
        this.bipedHips.addBox(-4F, 4F, -3F, 8, 4, 6);
        this.bipedHips.setRotationPoint(0F, 0F, 0F);
        
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
    	this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
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
    	this.bipedRightLeg.rotationPointY = 16.0F;
    	this.bipedRightLeg.offsetY = -0.25F;
    	this.bipedLeftLeg.rotationPointY = 16.0F;
    	this.bipedLeftLeg.offsetY = -0.25F;
    }
}