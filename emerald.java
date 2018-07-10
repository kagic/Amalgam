package Judacraz;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * emerald3 - Undefined
 * Created using Tabula 7.0.0
 */
public class emerald extends ModelBase {
    public ModelRenderer waist;
    public ModelRenderer body;
    public ModelRenderer hip;
    public ModelRenderer shoulder_left;
    public ModelRenderer shoulder_right;
    public ModelRenderer face;
    public ModelRenderer leg_right;
    public ModelRenderer leg_left;
    public ModelRenderer arm_left;
    public ModelRenderer arm_left_1;
    public ModelRenderer hair;
    public ModelRenderer face_1;

    public emerald() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.hair = new ModelRenderer(this, 24, 44);
        this.hair.setRotationPoint(0.0F, -0.2F, -3.3F);
        this.hair.addBox(-12.0F, -12.0F, 0.0F, 12, 12, 8, 0.0F);
        this.setRotateAngle(hair, -0.2617993877991494F, 0.2617993877991494F, 0.7853981633974483F);
        this.body = new ModelRenderer(this, 24, 0);
        this.body.setRotationPoint(0.0F, 2.6F, 0.0F);
        this.body.addBox(-8.0F, -8.0F, -3.5F, 7, 7, 7, 0.0F);
        this.setRotateAngle(body, 0.0F, 0.0F, 0.7853981633974483F);
        this.face = new ModelRenderer(this, 28, 14);
        this.face.setRotationPoint(0.0F, -7.0F, 0.3F);
        this.face.addBox(-4.0F, -8.0F, -4.7F, 8, 8, 8, 0.0F);
        this.face_1 = new ModelRenderer(this, 0, 50);
        this.face_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.face_1.addBox(-5.0F, -8.0F, 0.0F, 10, 4, 2, 0.0F);
        this.hip = new ModelRenderer(this, 0, 12);
        this.hip.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.hip.addBox(-4.0F, 0.0F, -3.0F, 8, 4, 6, 0.0F);
        this.shoulder_left = new ModelRenderer(this, 0, 22);
        this.shoulder_left.setRotationPoint(-3.0F, -4.5F, 0.0F);
        this.shoulder_left.addBox(0.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(shoulder_left, 0.0F, 0.0F, -2.6179938779914944F);
        this.leg_right = new ModelRenderer(this, 12, 30);
        this.leg_right.setRotationPoint(1.0F, 4.0F, 0.0F);
        this.leg_right.addBox(0.0F, 0.0F, -2.0F, 3, 16, 4, 0.0F);
        this.arm_left = new ModelRenderer(this, 0, 30);
        this.arm_left.setRotationPoint(2.0F, -2.0F, 0.0F);
        this.arm_left.addBox(-1.5F, -1.0F, -1.5F, 3, 13, 3, 0.0F);
        this.setRotateAngle(arm_left, 0.0F, 0.0F, 2.6179938779914944F);
        this.leg_left = new ModelRenderer(this, 12, 30);
        this.leg_left.setRotationPoint(-1.0F, 4.0F, 0.0F);
        this.leg_left.addBox(-3.0F, 0.0F, -2.0F, 3, 16, 4, 0.0F);
        this.shoulder_right = new ModelRenderer(this, 0, 22);
        this.shoulder_right.setRotationPoint(3.0F, -4.5F, 0.0F);
        this.shoulder_right.addBox(0.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(shoulder_right, 0.0F, 0.0F, -0.5235987755982988F);
        this.waist = new ModelRenderer(this, 0, 0);
        this.waist.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.waist.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.arm_left_1 = new ModelRenderer(this, 0, 30);
        this.arm_left_1.mirror = true;
        this.arm_left_1.setRotationPoint(2.0F, 2.0F, 0.0F);
        this.arm_left_1.addBox(-1.5F, -1.0F, -1.5F, 3, 13, 3, 0.0F);
        this.setRotateAngle(arm_left_1, 0.0F, 0.0F, 0.5235987755982988F);
        this.face.addChild(this.hair);
        this.waist.addChild(this.body);
        this.waist.addChild(this.face);
        this.face.addChild(this.face_1);
        this.waist.addChild(this.hip);
        this.waist.addChild(this.shoulder_left);
        this.hip.addChild(this.leg_right);
        this.shoulder_left.addChild(this.arm_left);
        this.hip.addChild(this.leg_left);
        this.waist.addChild(this.shoulder_right);
        this.shoulder_right.addChild(this.arm_left_1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.waist.offsetX, this.waist.offsetY, this.waist.offsetZ);
        GlStateManager.translate(this.waist.rotationPointX * f5, this.waist.rotationPointY * f5, this.waist.rotationPointZ * f5);
        GlStateManager.scale(1.1D, 1.1D, 1.1D);
        GlStateManager.translate(-this.waist.offsetX, -this.waist.offsetY, -this.waist.offsetZ);
        GlStateManager.translate(-this.waist.rotationPointX * f5, -this.waist.rotationPointY * f5, -this.waist.rotationPointZ * f5);
        this.waist.render(f5);
        GlStateManager.popMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
