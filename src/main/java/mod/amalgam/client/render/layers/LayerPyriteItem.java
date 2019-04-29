package mod.amalgam.client.render.layers;

import mod.amalgam.gem.EntityPyrite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPyriteItem implements LayerRenderer<EntityPyrite> {
    protected final RenderLivingBase<EntityPyrite> renderer;
    public LayerPyriteItem(RenderLivingBase<EntityPyrite> renderer) {
        this.renderer = renderer;
    }
    @Override
    public void doRenderLayer(EntityPyrite pyrite, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = pyrite.getPrimaryHand() == EnumHandSide.LEFT;
        ItemStack offhand = flag ? pyrite.getHeldItemOffhand() : pyrite.getHeldItemMainhand();
        ItemStack mainhand = flag ? pyrite.getHeldItemMainhand() : pyrite.getHeldItemOffhand();
        if (!offhand.isEmpty() || !mainhand.isEmpty()) {
            GlStateManager.pushMatrix();
            this.renderHeldItem(pyrite, mainhand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(pyrite, offhand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }
    private void renderHeldItem(EntityPyrite entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
    	if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            this.setSide(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16F, 0.125F, -0.5F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
            GlStateManager.popMatrix();
        }
    }
    protected void setSide(EnumHandSide side) {
        ((ModelBiped)(this.renderer.getMainModel())).postRenderArm(0.0625F, side);
    }
    public boolean shouldCombineTextures() {
        return false;
    }
}
