package net.witherbean.wwsm.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.witherbean.wwsm.WWSM;
import net.witherbean.wwsm.client.model.layers.CommandWitherModelLayers;
import net.witherbean.wwsm.client.model.layers.WitherStormHeadModelLayers;
import net.witherbean.wwsm.client.model.layers.WitherStormSkullModelLayers;
import net.witherbean.wwsm.client.model.wither_storm.command_wither.CommandWitherModel;
import net.witherbean.wwsm.client.model.wither_storm.head.WitherStormHeadModel;
import net.witherbean.wwsm.client.model.wither_storm.skull.WitherStormSkullModel;

@Mod.EventBusSubscriber(modid = WWSM.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelLayerEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WitherStormHeadModelLayers.ModModelLayers.WITHER_STORM_HEAD_LAYER, WitherStormHeadModel::createBodyLayer);
        event.registerLayerDefinition(CommandWitherModelLayers.ModModelLayers.COMMAND_WITHER_LAYER, CommandWitherModel::createBodyLayer);
        event.registerLayerDefinition(WitherStormSkullModelLayers.ModModelLayers.WITHER_STORM_SKULL_LAYER, WitherStormSkullModel::createBodyLayer);
    }
}