package net.witherbean.wwsm.entity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.witherbean.wwsm.WWSM;
import net.witherbean.wwsm.entity.wither_storm.head.WitherStormHead;
import net.witherbean.wwsm.entity.wither_storm.main.WitherStorm;

@Mod.EventBusSubscriber(modid = WWSM.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WITHER_STORM_HEAD.get(), WitherStormHead.createAttributes().build());
        event.put(ModEntities.COMMAND_WITHER.get(), WitherStorm.createAttributes().build());
    }
}