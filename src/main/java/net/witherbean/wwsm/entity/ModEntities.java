package net.witherbean.wwsm.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.witherbean.wwsm.WWSM;
import net.witherbean.wwsm.entity.wither_storm.head.WitherStormHead;
import net.witherbean.wwsm.entity.wither_storm.main.WitherStorm;
import net.witherbean.wwsm.entity.wither_storm.skull.WitherStormSkull;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WWSM.MODID);

    public static final RegistryObject<EntityType<WitherStormHead>> WITHER_STORM_HEAD =
            ENTITY_TYPES.register("wither_storm_head", () -> EntityType.Builder.of(WitherStormHead::new, MobCategory.MONSTER)
                    .clientTrackingRange(100)
                    .setTrackingRange(100)
                    .sized(1f, 1f)
                    .build("wither_storm_head"));
    public static final RegistryObject<EntityType<WitherStorm>> COMMAND_WITHER =
            ENTITY_TYPES.register("command_wither", () -> EntityType.Builder.of(WitherStorm::new, MobCategory.MONSTER)
                    .clientTrackingRange(100)
                    .setTrackingRange(100)
                    .sized(0.75f, 1.5f)
                    .build("command_wither"));
    public static final RegistryObject<EntityType<WitherStormSkull>> WITHER_STORM_SKULL = ENTITY_TYPES.register("wither_storm_skull",
            () -> EntityType.Builder.<WitherStormSkull>of(WitherStormSkull::new, MobCategory.MISC)
                    .clientTrackingRange(100)
                    .setTrackingRange(100)
                    .sized(0.3F, 0.3F)
                    .build(new ResourceLocation(WWSM.MODID, "wither_storm_skull")
                            .toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}