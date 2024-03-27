package net.witherbean.wwsm.entity.wither_storm.skull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.witherbean.wwsm.entity.ModEntities;

public class WitherStormSkull extends AbstractHurtingProjectile {
    private static final EntityDataAccessor<Boolean> DATA_DANGEROUS = SynchedEntityData.defineId(WitherStormSkull.class, EntityDataSerializers.BOOLEAN);

    public WitherStormSkull(EntityType<? extends WitherStormSkull> p_37598_, Level p_37599_) {
        super(p_37598_, p_37599_);
    }

    public WitherStormSkull(Level p_37609_, LivingEntity p_37610_, double p_37611_, double p_37612_, double p_37613_) {
        super(ModEntities.WITHER_STORM_SKULL.get(), p_37610_, p_37611_, p_37612_, p_37613_, p_37609_);
    }

    protected float getInertia() {
        return this.isDangerous() ? 1.2F : super.getInertia();
    }

    public boolean isOnFire() {
        return false;
    }

    public float getBlockExplosionResistance(Explosion p_37619_, BlockGetter p_37620_, BlockPos p_37621_, BlockState p_37622_, FluidState p_37623_, float p_37624_) {
        return this.isDangerous() && p_37622_.canEntityDestroy(p_37620_, p_37621_, this) ? Math.min(0.8F, p_37624_) : p_37624_;
    }

    protected void onHitEntity(EntityHitResult p_37626_) {
        super.onHitEntity(p_37626_);
        if (!this.level().isClientSide) {
            Entity entity = p_37626_.getEntity();
            Entity entity1 = this.getOwner();
            boolean flag;
            if (entity1 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity1;
            } else {
                flag = entity.hurt(this.damageSources().magic(), 5.0F);
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 40;
                }

                if (i > 0) {
                    livingentity1.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * i, 1), this.getEffectSource());
                }
            }

        }
    }

    protected void onHit(HitResult p_37628_) {
        super.onHit(p_37628_);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, true, Level.ExplosionInteraction.MOB);
            this.discard();
        }

    }

    public boolean isPickable() {
        return true;
    }

    public boolean hurt(DamageSource p_37616_, float p_37617_) {
        return true;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_DANGEROUS, false);
    }

    public boolean isDangerous() {
        return this.entityData.get(DATA_DANGEROUS);
    }

    public void setDangerous(boolean p_37630_) {
        this.entityData.set(DATA_DANGEROUS, p_37630_);
    }

    protected boolean shouldBurn() {
        return false;
    }
}
