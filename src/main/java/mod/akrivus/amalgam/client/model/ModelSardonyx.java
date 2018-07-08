package mod.akrivus.amalgam.client.model;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSardonyx extends ModelGem {
    public ModelRenderer bipedNeck;
    public ModelRenderer bipedNose;
    public ModelRenderer bipedWaist;
    public ModelRenderer bipedLeftShoulder;
    public ModelRenderer bipedRightShoulder;
    public ModelRenderer bipedLeftArmLower;
    public ModelRenderer bipedRightArmLower;
    public ModelRenderer bipedLeftCalve;
    public ModelRenderer bipedRightCalve;
    public ModelRenderer bipedLeftFoot;
    public ModelRenderer bipedRightFoot;

    public ModelSardonyx() {
		this(0, false);
	}
	public ModelSardonyx(float modelSize, boolean isArmor) {
		super(modelSize, 0.0F, 64, 64, false, -2F);
		this.bipedRightArm = new ModelRenderer(this, 8, 41);
        this.bipedRightArm.setRotationPoint(-2.3F, 1.0F, 0.0F);
        this.bipedRightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.2617993877991494F);
        this.bipedLeftCalve = new ModelRenderer(this, 16, 16);
        this.bipedLeftCalve.setRotationPoint(0.0F, 3.4F, 0.5F);
        this.bipedLeftCalve.addBox(0.5F, -11.5F, -2.5F, 4, 9, 4, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 16, 29);
        this.bipedRightLeg.setRotationPoint(-0.7F, 9.0F, -0.6F);
        this.bipedRightLeg.addBox(-3.0F, 1.0F, -1.5F, 3, 16, 3, 0.0F);
        this.bipedRightFoot = new ModelRenderer(this, 41, 26);
        this.bipedRightFoot.setRotationPoint(-1.5F, 15.0F, 0.0F);
        this.bipedRightFoot.addBox(-1.5F, -2.5F, -8.0F, 3, 1, 8, 0.0F);
        this.bipedBody = new ModelRenderer(this, 0, 0);
        this.bipedBody.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.bipedBody.addBox(-3.0F, -3.0F, -3.0F, 6, 5, 6, 0.0F);
        this.bipedLeftShoulder = new ModelRenderer(this, 42, 0);
        this.bipedLeftShoulder.setRotationPoint(2.1F, -1.0F, 0.0F);
        this.bipedLeftShoulder.addBox(0.0F, -2.0F, -2.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(bipedLeftShoulder, 0.0F, 0.0F, -0.2617993877991494F);
        this.bipedHead = new ModelRenderer(this, 34, 12);
        this.bipedHead.setRotationPoint(0.0F, -0.6F, -0.7F);
        this.bipedHead.addBox(-10.0F, -10.0F, -1.0F, 10, 10, 4, 0.0F);
        this.setRotateAngle(bipedHead, 0.0F, 0.0F, 0.7853981633974483F);
        this.bipedLeftFoot = new ModelRenderer(this, 41, 35);
        this.bipedLeftFoot.setRotationPoint(1.5F, 15.0F, 0.0F);
        this.bipedLeftFoot.addBox(-1.5F, -2.5F, -8.0F, 3, 1, 8, 0.0F);
        this.bipedRightArmLower = new ModelRenderer(this, 0, 24);
        this.bipedRightArmLower.setRotationPoint(-2.5F, 2.0F, 0.0F);
        this.bipedRightArmLower.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
        this.setRotateAngle(bipedRightArmLower, 0.0F, 0.0F, 0.5235987755982988F);
        this.bipedLeftLeg = new ModelRenderer(this, 29, 26);
        this.bipedLeftLeg.setRotationPoint(0.6F, 9.0F, -0.6F);
        this.bipedLeftLeg.addBox(0.0F, 1.0F, -1.5F, 3, 16, 3, 0.0F);
        this.bipedNose = new ModelRenderer(this, 0, 0);
        this.bipedNose.setRotationPoint(-3.4F, -3.4F, -2.0F);
        this.bipedNose.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 2, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 0, 41);
        this.bipedLeftArm.setRotationPoint(2.3F, 1.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
        this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.2617993877991494F);
        this.bipedNeck = new ModelRenderer(this, 18, 0);
        this.bipedNeck.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.bipedNeck.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
        this.bipedWaist = new ModelRenderer(this, 20, 8);
        this.bipedWaist.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.bipedWaist.addBox(-2.5F, 0.0F, -2.0F, 5, 4, 4, 0.0F);
        this.bipedRightShoulder = new ModelRenderer(this, 24, 0);
        this.bipedRightShoulder.setRotationPoint(-2.1F, -1.0F, 0.0F);
        this.bipedRightShoulder.addBox(-5.0F, -2.0F, -2.0F, 5, 4, 4, 0.0F);
        this.setRotateAngle(bipedRightShoulder, 0.0F, 0.0F, 0.2617993877991494F);
        this.bipedRightCalve = new ModelRenderer(this, 0, 11);
        this.bipedRightCalve.setRotationPoint(0.0F, 3.5F, 0.5F);
        this.bipedRightCalve.addBox(-1.0F, -11.5F, -2.5F, 4, 9, 4, 0.0F);
        this.bipedLeftArmLower = new ModelRenderer(this, 8, 24);
        this.bipedLeftArmLower.setRotationPoint(2.5F, 2.0F, 0.0F);
        this.bipedLeftArmLower.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
        this.setRotateAngle(bipedLeftArmLower, 0.0F, 0.0F, -0.5235987755982988F);
        this.bipedRightShoulder.addChild(this.bipedRightArm);
        this.bipedWaist.addChild(this.bipedLeftLeg);
        this.bipedRightLeg.addChild(this.bipedRightCalve);
        this.bipedRightCalve.addChild(this.bipedRightFoot);
        this.bipedBody.addChild(this.bipedLeftShoulder);
        this.bipedNeck.addChild(this.bipedHead);
        this.bipedLeftCalve.addChild(this.bipedLeftFoot);
        this.bipedWaist.addChild(this.bipedRightArmLower);
        this.bipedLeftLeg.addChild(this.bipedLeftCalve);
        this.bipedHead.addChild(this.bipedNose);
        this.bipedLeftShoulder.addChild(this.bipedLeftArm);
        this.bipedBody.addChild(this.bipedNeck);
        this.bipedBody.addChild(this.bipedWaist);
        this.bipedBody.addChild(this.bipedRightShoulder);
        this.bipedWaist.addChild(this.bipedRightLeg);
        this.bipedWaist.addChild(this.bipedLeftArmLower);
    }
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.bipedBody.render(f5);
    }
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}