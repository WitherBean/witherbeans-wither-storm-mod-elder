package net.witherbean.wwsm.client.renderer.wither_storm.head;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.witherbean.wwsm.WWSM;
import net.witherbean.wwsm.client.model.layers.WitherStormHeadModelLayers;
import net.witherbean.wwsm.client.model.wither_storm.head.WitherStormHeadModel;
import net.witherbean.wwsm.entity.wither_storm.head.WitherStormHead;

public class WitherStormHeadRenderer extends MobRenderer<WitherStormHead, WitherStormHeadModel<WitherStormHead>> {
    public WitherStormHeadRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WitherStormHeadModel<>(pContext.bakeLayer(WitherStormHeadModelLayers.ModModelLayers.WITHER_STORM_HEAD_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherStormHead pEntity) {
        return new ResourceLocation(WWSM.MODID, "textures/entity/wither_storm/head/wither_storm_head.png");
    }

    @Override
    public void render(WitherStormHead pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.scale(4f, 4f, 4f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}