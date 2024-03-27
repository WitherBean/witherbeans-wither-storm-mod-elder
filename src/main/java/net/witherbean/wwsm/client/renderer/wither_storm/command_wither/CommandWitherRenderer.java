package net.witherbean.wwsm.client.renderer.wither_storm.command_wither;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.witherbean.wwsm.WWSM;
import net.witherbean.wwsm.client.model.layers.CommandWitherModelLayers;
import net.witherbean.wwsm.client.model.wither_storm.command_wither.CommandWitherModel;
import net.witherbean.wwsm.entity.wither_storm.main.WitherStorm;

public class CommandWitherRenderer extends MobRenderer<WitherStorm, CommandWitherModel<WitherStorm>> {
    public CommandWitherRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CommandWitherModel<>(pContext.bakeLayer(CommandWitherModelLayers.ModModelLayers.COMMAND_WITHER_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherStorm pEntity) {
        return new ResourceLocation(WWSM.MODID, "textures/entity/wither_storm/main/mini/wither_command_block.png");
    }

    @Override
    public void render(WitherStorm pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
//        pMatrixStack.scale(2f, 2f, 2f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
