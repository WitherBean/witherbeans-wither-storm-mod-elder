package net.witherbean.wwsm.entity.wither_storm.main;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.witherbean.wwsm.entity.wither_storm.skull.WitherStormSkull;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class WitherStorm extends Monster implements RangedAttackMob, PowerableMob {

    int phase = this.getPhase();
    public static int maxHealth = (int) 400;
    public static int followRange = (int) 1200;
    public static int attackKnockback = (int) 5;
    public static int armorToughness = (int) 1;
    public static int speed = (int) 6;
    public static int attackDamage = (int) 14;
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (p_31504_) -> {
        return p_31504_.getMobType() != MobType.UNDEAD && p_31504_.attackable();
    };
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forCombat().range(20.0D).selector(LIVING_ENTITY_SELECTOR);

    private static final EntityDataAccessor<Integer> DATA_TARGET_A = SynchedEntityData.defineId(WitherStorm.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_TARGET_B = SynchedEntityData.defineId(WitherStorm.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_TARGET_C = SynchedEntityData.defineId(WitherStorm.class, EntityDataSerializers.INT);
    private static final List<EntityDataAccessor<Integer>> DATA_TARGETS = ImmutableList.of(DATA_TARGET_A, DATA_TARGET_B, DATA_TARGET_C);
    private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(WitherStorm.class, EntityDataSerializers.INT);
    private static final int INVULNERABLE_TICKS = 220;
    private final float[] xRotHeads = new float[2];
    private final float[] yRotHeads = new float[2];
    private final float[] xRotOHeads = new float[2];
    private final float[] yRotOHeads = new float[2];
    private final int[] nextHeadUpdate = new int[2];
    private final int[] idleHeadUpdates = new int[2];
    private int destroyBlocksTick;
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(WitherStorm.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EVOLUTION_POINTS = SynchedEntityData.defineId(WitherStorm.class, EntityDataSerializers.INT);
    public WitherStorm(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setHealth(this.getMaxHealth());
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.xpReward = 150;
    }
    protected PathNavigation createNavigation(Level p_186262_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_186262_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState phase0 = new AnimationState();
    public final AnimationState phase1 = new AnimationState();
    public final AnimationState phase2 = new AnimationState();
    public final AnimationState phase3 = new AnimationState();
    public final AnimationState phase4 = new AnimationState();
    public final AnimationState phase5 = new AnimationState();
    public final AnimationState phase6 = new AnimationState();
    public final AnimationState phase7 = new AnimationState();
    public final AnimationState phase8 = new AnimationState();
    public final AnimationState phase9 = new AnimationState();
    public final AnimationState phase10 = new AnimationState();
    public final AnimationState phase11 = new AnimationState();
    public final AnimationState phase12 = new AnimationState();
    public final AnimationState evolving1 = new AnimationState();
    public final AnimationState evolving2 = new AnimationState();
    public final AnimationState evolving3 = new AnimationState();
    public final AnimationState evolving4 = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int phase0AnimationTimeout = 0;
    private int phase1AnimationTimeout = 0;
    private int phase2AnimationTimeout = 0;
    private int phase3AnimationTimeout = 0;
    private int phase4AnimationTimeout = 0;
    private int phase5AnimationTimeout = 0;
    private int phase6AnimationTimeout = 0;
    private int phase7AnimationTimeout = 0;
    private int phase8AnimationTimeout = 0;
    private int phase9AnimationTimeout = 0;
    private int phase10AnimationTimeout = 0;
    private int phase11AnimationTimeout = 0;
    private int evolving1AnimationTimeout = 0;
    private int evolving2AnimationTimeout = 0;
    private int evolving3AnimationTimeout = 0;
    private int evolving4AnimationTimeout = 0;

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TARGET_A, 0);
        this.entityData.define(DATA_TARGET_B, 0);
        this.entityData.define(DATA_TARGET_C, 0);
        this.entityData.define(DATA_ID_INV, 0);
        this.entityData.define(EVOLUTION_POINTS, 0);
        this.entityData.define(PHASE, 0);

    }
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        compound.putInt("Invul", this.getInvulnerableTicks());
        compound.putInt("Phase", this.getPhase());
        compound.putInt("EvolutionPoints", this.getEvolutionPoints());
    }
    public void readAdditionalSaveData(CompoundTag p_31474_) {
        super.readAdditionalSaveData(p_31474_);
        this.setInvulnerableTicks(p_31474_.getInt("Invul"));
        this.setPhase(p_31474_.getInt("Phase"));
        this.setEvolutionPoints(p_31474_.getInt("EvolutionPoints"));
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }

    }
    public void setCustomName(@javax.annotation.Nullable Component p_31476_) {
        super.setCustomName(p_31476_);
        this.bossEvent.setName(this.getDisplayName());
    }
    public void aiStep() {
        if (this.getPhase() <= 5) {
            maxHealth = 400;
        }
        if (this.getPhase() >= 6) {
            maxHealth = 600;
        }
        if (this.getEvolutionPoints() == 0) {
            this.setPhase(0);
        }
        if (this.getEvolutionPoints() == 1) {
            this.setPhase(1);
        }
        if (this.getEvolutionPoints() == 2) {
            this.setPhase(2);
        }
        if (this.getEvolutionPoints() == 3) {
            this.setPhase(3);
        }
        if (this.getEvolutionPoints() == 4) {
            this.setPhase(4);
        }
        if (this.getEvolutionPoints() == 5) {
            this.setPhase(5);
        }
        if (this.getEvolutionPoints() == 8) {
            this.setPhase(6);
        }
        if (this.getEvolutionPoints() == 11) {
            this.setPhase(7);
        }
        if (this.getEvolutionPoints() == 20) {
            this.setPhase(8);
        }
        if (this.getEvolutionPoints() == 24) {
            this.setPhase(9);
        }
        if (this.getEvolutionPoints() == 27) {
            this.setPhase(10);
        }
        if (this.getEvolutionPoints() == 35) {
            this.setPhase(11);
        }
        if (this.getEvolutionPoints() == 40) {
            this.setPhase(12);
        }
        if (this.getEvolutionPoints() == 50) {
            this.setPhase(13);
        }
        if (this.getEvolutionPoints() == 60) {
            this.setPhase(14);
        }
        if (this.getEvolutionPoints() == 70) {
            this.setPhase(15);
        }
        Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D);
        if (!this.level().isClientSide && this.getAlternativeTarget(0) > 0) {
            Entity entity = this.level().getEntity(this.getAlternativeTarget(0));
            if (entity != null) {
                double d0 = vec3.y;
                if (this.getY() < entity.getY() || !this.isPowered() && this.getY() < entity.getY() + 5.0D) {
                    d0 = Math.max(0.0D, d0);
                    d0 += 0.3D - d0 * (double)0.6F;
                }

                vec3 = new Vec3(vec3.x, d0, vec3.z);
                Vec3 vec31 = new Vec3(entity.getX() - this.getX(), 0.0D, entity.getZ() - this.getZ());
                if (vec31.horizontalDistanceSqr() > 9.0D) {
                    Vec3 vec32 = vec31.normalize();
                    vec3 = vec3.add(vec32.x * 0.3D - vec3.x * 0.6D, 0.0D, vec32.z * 0.3D - vec3.z * 0.6D);
                }
            }
        }

        this.setDeltaMovement(vec3);
        if (vec3.horizontalDistanceSqr() > 0.05D) {
            this.setYRot((float) Mth.atan2(vec3.z, vec3.x) * (180F / (float)Math.PI) - 90.0F);
        }

        super.aiStep();

        for(int i = 0; i < 2; ++i) {
            this.yRotOHeads[i] = this.yRotHeads[i];
            this.xRotOHeads[i] = this.xRotHeads[i];
        }

        for(int j = 0; j < 2; ++j) {
            int k = this.getAlternativeTarget(j + 1);
            Entity entity1 = null;
            if (k > 0) {
                entity1 = this.level().getEntity(k);
            }

            if (entity1 != null) {
                double d9 = this.getHeadX(j + 1);
                double d1 = this.getHeadY(j + 1);
                double d3 = this.getHeadZ(j + 1);
                double d4 = entity1.getX() - d9;
                double d5 = entity1.getEyeY() - d1;
                double d6 = entity1.getZ() - d3;
                double d7 = Math.sqrt(d4 * d4 + d6 * d6);
                float f = (float)(Mth.atan2(d6, d4) * (double)(180F / (float)Math.PI)) - 90.0F;
                float f1 = (float)(-(Mth.atan2(d5, d7) * (double)(180F / (float)Math.PI)));
                this.xRotHeads[j] = this.rotlerp(this.xRotHeads[j], f1, 40.0F);
                this.yRotHeads[j] = this.rotlerp(this.yRotHeads[j], f, 10.0F);
            } else {
                this.yRotHeads[j] = this.rotlerp(this.yRotHeads[j], this.yBodyRot, 10.0F);
            }
        }

        boolean flag = this.isPowered();

        for(int l = 0; l < 3; ++l) {
            double d8 = this.getHeadX(l);
            double d10 = this.getHeadY(l);
            double d2 = this.getHeadZ(l);
            this.level().addParticle(ParticleTypes.SMOKE, d8 + this.random.nextGaussian() * (double)0.3F, d10 + this.random.nextGaussian() * (double)0.3F, d2 + this.random.nextGaussian() * (double)0.3F, 0.0D, 0.0D, 0.0D);
            if (flag && this.level().random.nextInt(4) == 0) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, d8 + this.random.nextGaussian() * (double)0.3F, d10 + this.random.nextGaussian() * (double)0.3F, d2 + this.random.nextGaussian() * (double)0.3F, (double)0.7F, (double)0.7F, 0.5D);
            }
        }

        if (this.getInvulnerableTicks() > 0) {
            for(int i1 = 0; i1 < 3; ++i1) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + this.random.nextGaussian(), this.getY() + (double)(this.random.nextFloat() * 3.3F), this.getZ() + this.random.nextGaussian(), (double)0.7F, (double)0.7F, (double)0.9F);
            }
        }

    }

    protected void customServerAiStep() {
        if (this.getInvulnerableTicks() > 0) {
            int k1 = this.getInvulnerableTicks() - 1;
            this.bossEvent.setProgress(1.0F - (float)k1 / 220.0F);
            if (k1 <= 0) {
                this.level().explode(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, Level.ExplosionInteraction.MOB);
                if (!this.isSilent()) {
                    this.level().globalLevelEvent(1023, this.blockPosition(), 0);
                }
            }

            this.setInvulnerableTicks(k1);
            if (this.tickCount % 10 == 0) {
                this.heal(10.0F);
            }

        } else {
            super.customServerAiStep();

            for(int i = 1; i < 3; ++i) {
                if (this.tickCount >= this.nextHeadUpdate[i - 1]) {
                    this.nextHeadUpdate[i - 1] = this.tickCount + 10 + this.random.nextInt(10);
                    if (this.level().getDifficulty() == Difficulty.NORMAL || this.level().getDifficulty() == Difficulty.HARD) {
                        int i3 = i - 1;
                        int j3 = this.idleHeadUpdates[i - 1];
                        this.idleHeadUpdates[i3] = this.idleHeadUpdates[i - 1] + 1;
                        if (j3 > 15) {
                            float f = 10.0F;
                            float f1 = 5.0F;
                            double d0 = Mth.nextDouble(this.random, this.getX() - 10.0D, this.getX() + 10.0D);
                            double d1 = Mth.nextDouble(this.random, this.getY() - 5.0D, this.getY() + 5.0D);
                            double d2 = Mth.nextDouble(this.random, this.getZ() - 10.0D, this.getZ() + 10.0D);
                            this.performRangedAttack(i + 1, d0, d1, d2, true);
                            this.idleHeadUpdates[i - 1] = 0;
                        }
                    }

                    int l1 = this.getAlternativeTarget(i);
                    if (l1 > 0) {
                        LivingEntity livingentity = (LivingEntity)this.level().getEntity(l1);
                        if (livingentity != null && this.canAttack(livingentity) && !(this.distanceToSqr(livingentity) > 900.0D) && this.hasLineOfSight(livingentity)) {
                            this.performRangedAttack(i + 1, livingentity);
                            this.nextHeadUpdate[i - 1] = this.tickCount + 40 + this.random.nextInt(20);
                            this.idleHeadUpdates[i - 1] = 0;
                        } else {
                            this.setAlternativeTarget(i, 0);
                        }
                    } else {
                        List<LivingEntity> list = this.level().getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(20.0D, 8.0D, 20.0D));
                        if (!list.isEmpty()) {
                            LivingEntity livingentity1 = list.get(this.random.nextInt(list.size()));
                            this.setAlternativeTarget(i, livingentity1.getId());
                        }
                    }
                }
            }

            if (this.getTarget() != null) {
                this.setAlternativeTarget(0, this.getTarget().getId());
            } else {
                this.setAlternativeTarget(0, 0);
            }

            if (this.destroyBlocksTick > 0) {
                --this.destroyBlocksTick;
                if (this.destroyBlocksTick == 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
                    int j1 = Mth.floor(this.getY());
                    int i2 = Mth.floor(this.getX());
                    int j2 = Mth.floor(this.getZ());
                    boolean flag = false;

                    for(int j = -1; j <= 1; ++j) {
                        for(int k2 = -1; k2 <= 1; ++k2) {
                            for(int k = 0; k <= 3; ++k) {
                                int l2 = i2 + j;
                                int l = j1 + k;
                                int i1 = j2 + k2;
                                BlockPos blockpos = new BlockPos(l2, l, i1);
                                BlockState blockstate = this.level().getBlockState(blockpos);
                                if (blockstate.canEntityDestroy(this.level(), blockpos, this) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                                }
                            }
                        }
                    }

                    if (flag) {
                        this.level().levelEvent((Player)null, 1022, this.blockPosition(), 0);
                    }
                }
            }
            if (this.getPhase() == 0) {
                if (this.tickCount % 20 == 0) {
                    this.heal(3.0F);
                }
            }
            if (this.getPhase() >= 7) {
                if (this.tickCount % 40 == 0) {
                    this.heal(3.0F);
                }
            }

            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Deprecated
    public static boolean canDestroy(BlockState p_31492_) {
        return !p_31492_.isAir() && !p_31492_.is(BlockTags.WITHER_IMMUNE);
    }
    public float getHeadYRot(int p_31447_) {
        return this.yRotHeads[p_31447_];
    }

    public float getHeadXRot(int p_31481_) {
        return this.xRotHeads[p_31481_];
    }

    public int getInvulnerableTicks() {
        return this.entityData.get(DATA_ID_INV);
    }
    public int getPhase() {
        return this.entityData.get(PHASE);
    }
    public int getEvolutionPoints() {
        return this.entityData.get(EVOLUTION_POINTS);
    }

    public void setInvulnerableTicks(int p_31511_) {
        this.entityData.set(DATA_ID_INV, p_31511_);
    }
    public void setPhase(int p_31511_) {
        this.entityData.set(PHASE, p_31511_);
    }
    public void setEvolutionPoints(int p_31511_) {
        this.entityData.set(EVOLUTION_POINTS, p_31511_);
    }

    public int getAlternativeTarget(int p_31513_) {
        return this.entityData.get(DATA_TARGETS.get(p_31513_));
    }

    public void setAlternativeTarget(int p_31455_, int p_31456_) {
        this.entityData.set(DATA_TARGETS.get(p_31455_), p_31456_);
    }

    public boolean isPowered() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    protected boolean canRide(Entity p_31508_) {
        return false;
    }

    public boolean canChangeDimensions() {
        return false;
    }

    public boolean canBeAffected(MobEffectInstance p_31495_) {
        return p_31495_.getEffect() == MobEffects.WITHER ? false : super.canBeAffected(p_31495_);
    }

    class WitherDoNothingGoal extends Goal {
        public WitherDoNothingGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return WitherStorm.this.getInvulnerableTicks() > 0;
        }
    }

    public void makeInvulnerable() {
        this.setInvulnerableTicks(220);
        this.bossEvent.setProgress(0.0F);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    public void makeStuckInBlock(BlockState p_31471_, Vec3 p_31472_) {
    }

    public void startSeenByPlayer(ServerPlayer p_31483_) {
        super.startSeenByPlayer(p_31483_);
        this.bossEvent.addPlayer(p_31483_);
    }

    public void stopSeenByPlayer(ServerPlayer p_31488_) {
        super.stopSeenByPlayer(p_31488_);
        this.bossEvent.removePlayer(p_31488_);
    }

    private double getHeadX(int p_31515_) {
        if (p_31515_ <= 0) {
            return this.getX();
        } else {
            float f = (this.yBodyRot + (float)(180 * (p_31515_ - 1))) * ((float)Math.PI / 180F);
            float f1 = Mth.cos(f);
            return this.getX() + (double)f1 * 1.3D;
        }
    }

    private double getHeadY(int p_31517_) {
        return p_31517_ <= 0 ? this.getY() + 3.0D : this.getY() + 2.2D;
    }

    private double getHeadZ(int p_31519_) {
        if (p_31519_ <= 0) {
            return this.getZ();
        } else {
            float f = (this.yBodyRot + (float)(180 * (p_31519_ - 1))) * ((float)Math.PI / 180F);
            float f1 = Mth.sin(f);
            return this.getZ() + (double)f1 * 1.3D;
        }
    }

    private float rotlerp(float p_31443_, float p_31444_, float p_31445_) {
        float f = Mth.wrapDegrees(p_31444_ - p_31443_);
        if (f > p_31445_) {
            f = p_31445_;
        }

        if (f < -p_31445_) {
            f = -p_31445_;
        }

        return p_31443_ + f;
    }

    private void performRangedAttack(int p_31458_, LivingEntity p_31459_) {
        this.performRangedAttack(p_31458_, p_31459_.getX(), p_31459_.getY() + (double)p_31459_.getEyeHeight() * 0.5D, p_31459_.getZ(), p_31458_ == 0 && this.random.nextFloat() < 0.001F);
    }

    private void performRangedAttack(int p_31449_, double p_31450_, double p_31451_, double p_31452_, boolean p_31453_) {
        if (!this.isSilent()) {
            this.level().levelEvent((Player)null, 1024, this.blockPosition(), 0);
        }
        this.setEvolutionPoints(this.getEvolutionPoints() + 1);

        if (this.tickCount % 20 == 0) {
            double d0 = this.getHeadX(p_31449_);
            double d1 = this.getHeadY(p_31449_);
            double d2 = this.getHeadZ(p_31449_);
            double d3 = p_31450_ - d0;
            double d4 = p_31451_ - d1;
            double d5 = p_31452_ - d2;
            WitherStormSkull witherskull = new WitherStormSkull(this.level(), this, d3, d4, d5);
            witherskull.setOwner(this);
            if (p_31453_) {
                witherskull.setDangerous(true);
            }

            witherskull.setPosRaw(d0, d1, d2);
            this.level().addFreshEntity(witherskull);
        } else {
            double d0 = this.getHeadX(p_31449_);
            double d1 = this.getHeadY(p_31449_);
            double d2 = this.getHeadZ(p_31449_);
            double d3 = p_31450_ - d0;
            double d4 = p_31451_ - d1;
            double d5 = p_31452_ - d2;
            WitherSkull witherskull = new WitherSkull(this.level(), this, d3, d4, d5);
            witherskull.setOwner(this);
            if (p_31453_) {
                witherskull.setDangerous(true);
            }

            witherskull.setPosRaw(d0, d1, d2);
            this.level().addFreshEntity(witherskull);
        }
    }

    public void performRangedAttack(LivingEntity p_31468_, float p_31469_) {
        this.performRangedAttack(0, p_31468_);
    }

    public boolean hurt(DamageSource p_31461_, float p_31462_) {
        if (this.isInvulnerableTo(p_31461_)) {
            return false;
        } else if (!p_31461_.is(DamageTypeTags.WITHER_IMMUNE_TO) && !(p_31461_.getEntity() instanceof WitherBoss)) {
            if (this.getInvulnerableTicks() > 0 && !p_31461_.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return false;
            } else {
                if (this.isPowered()) {
                    Entity entity = p_31461_.getDirectEntity();
                    if (entity instanceof AbstractArrow) {
                        return false;
                    }
                }

                Entity entity1 = p_31461_.getEntity();
                if (entity1 != null && !(entity1 instanceof Player) && entity1 instanceof LivingEntity && ((LivingEntity)entity1).getMobType() == this.getMobType()) {
                    return false;
                } else {
                    if (this.destroyBlocksTick <= 0) {
                        this.destroyBlocksTick = 20;
                    }

                    for(int i = 0; i < this.idleHeadUpdates.length; ++i) {
                        this.idleHeadUpdates[i] += 3;
                    }

                    return super.hurt(p_31461_, p_31462_);
                }
            }
        } else {
            return false;
        }
    }

    protected void dropCustomDeathLoot(DamageSource p_31464_, int p_31465_, boolean p_31466_) {
        super.dropCustomDeathLoot(p_31464_, p_31465_, p_31466_);
        ItemEntity itementity = this.spawnAtLocation(Items.NETHER_STAR);
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }

    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    public boolean addEffect(MobEffectInstance p_182397_, @javax.annotation.Nullable Entity p_182398_) {
        return false;
    }


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide() && this.getPhase() == 0) {
            setupAnimationStatesPhase0();
        }
        if(this.level().isClientSide() && this.getPhase() == 1) {
            setupAnimationStatesPhase1();
        }
        if(this.level().isClientSide() && this.getPhase() == 2) {
            setupAnimationStatesPhase2();
        }
        if(this.level().isClientSide() && this.getPhase() == 3) {
            setupAnimationStatesPhase3();
        }
        if(this.level().isClientSide() && this.getPhase() == 4) {
            setupAnimationStatesPhase4();
        }
        if(this.level().isClientSide() && this.getPhase() == 5) {
            setupAnimationStatesPhase5();
        }
        if(this.level().isClientSide() && this.getPhase() == 6) {
            setupAnimationStatesPhase6();
        }
        if(this.level().isClientSide() && this.getPhase() == 7) {
            setupAnimationStatesPhase7();
        }
        if(this.level().isClientSide() && this.getPhase() == 8) {
            setupAnimationStatesPhase8();
        }
        if(this.level().isClientSide() && this.getPhase() == 9) {
            setupAnimationStatesPhase9();
        }
        if(this.level().isClientSide() && this.getPhase() == 10) {
            setupAnimationStatesPhase10();
        }
        if(this.level().isClientSide() && this.getPhase() == 11) {
            setupAnimationStatesPhase11();
        }
        if(this.level().isClientSide() && this.getPhase() == 12) {
            setupAnimationStatesPhase12();
        }
        if(this.level().isClientSide() && this.getPhase() == 13) {
            setupAnimationStatesPhaseEvolve1();
        }
        if(this.level().isClientSide() && this.getPhase() == 14) {
            setupAnimationStatesPhaseEvolve2();
        }
        if(this.level().isClientSide() && this.getPhase() == 15) {
            setupAnimationStatesPhaseEvolve3();
        }
    }

    private void setupAnimationStatesPhase0() {
        if (this.getPhase() == 0) {
            if(this.phase0AnimationTimeout <= 0) {
                this.phase0AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase0.start(this.tickCount);
            } else {
                --this.phase0AnimationTimeout;
            }
            this.phase0.start(this.tickCount);
        } else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase1() {
        if (this.getPhase() == 1) {
            if(this.phase1AnimationTimeout <= 0) {
                this.phase1AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase1.start(this.tickCount);
            } else {
                --this.phase1AnimationTimeout;
            }
            this.phase1.start(this.tickCount);
        } else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase2() {
        if (this.getPhase() == 2) {
            if(this.phase2AnimationTimeout <= 0) {
                this.phase2AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase2.start(this.tickCount);
            } else {
                --this.phase2AnimationTimeout;
            }
            this.phase2.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase3() {
        if (this.getPhase() == 3) {
            if(this.phase3AnimationTimeout <= 0) {
                this.phase3AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase3.start(this.tickCount);
            } else {
                --this.phase3AnimationTimeout;
            }
            this.phase3.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase4() {
        if (this.getPhase() == 4) {
            if(this.phase4AnimationTimeout <= 0) {
                this.phase4AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase4.start(this.tickCount);
            } else {
                --this.phase4AnimationTimeout;
            }
            this.phase4.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase5() {
        if (this.getPhase() == 5) {
            if(this.phase5AnimationTimeout <= 0) {
                this.phase5AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase5.start(this.tickCount);
            } else {
                --this.phase5AnimationTimeout;
            }
            this.phase5.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase6() {
        if (this.getPhase() == 6) {
            if(this.phase6AnimationTimeout <= 0) {
                this.phase6AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase6.start(this.tickCount);
            } else {
                --this.phase6AnimationTimeout;
            }
            this.phase6.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase7() {
        if (this.getPhase() == 7) {
            if(this.phase7AnimationTimeout <= 0) {
                this.phase7AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase7.start(this.tickCount);
            } else {
                --this.phase7AnimationTimeout;
            }
            this.phase7.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase8() {
        if (this.getPhase() == 8) {
            if(this.phase8AnimationTimeout <= 0) {
                this.phase8AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase8.start(this.tickCount);
            } else {
                --this.phase8AnimationTimeout;
            }
            this.phase8.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase9() {
        if (this.getPhase() == 9) {
            if(this.phase9AnimationTimeout <= 0) {
                this.phase9AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase9.start(this.tickCount);
            } else {
                --this.phase9AnimationTimeout;
            }
            this.phase9.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase10() {
        if (this.getPhase() == 10) {
            if(this.phase10AnimationTimeout <= 0) {
                this.phase10AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase10.start(this.tickCount);
            } else {
                --this.phase10AnimationTimeout;
            }
            this.phase10.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase11() {
        if (this.getPhase() == 11) {
            if(this.phase11AnimationTimeout <= 0) {
                this.phase11AnimationTimeout = this.random.nextInt(40) + 80;
                this.phase11.start(this.tickCount);
            } else {
                --this.phase11AnimationTimeout;
            }
            this.phase11.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhase12() {
        if (this.getPhase() == 12) {
            if(this.evolving1AnimationTimeout <= 0) {
                this.evolving1AnimationTimeout = this.random.nextInt(40) + 80;
                this.evolving1.start(this.tickCount);
            } else {
                --this.evolving1AnimationTimeout;
            }
            this.evolving1.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhaseEvolve1() {
        if (this.getPhase() == 13) {
            if(this.evolving2AnimationTimeout <= 0) {
                this.evolving2AnimationTimeout = this.random.nextInt(40) + 80;
                this.evolving2.start(this.tickCount);
            } else {
                --this.evolving2AnimationTimeout;
            }
            this.evolving2.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhaseEvolve2() {
        if (this.getPhase() == 14) {
            if(this.evolving3AnimationTimeout <= 0) {
                this.evolving3AnimationTimeout = this.random.nextInt(40) + 80;
                this.evolving3.start(this.tickCount);
            } else {
                --this.evolving3AnimationTimeout;
            }
            this.evolving3.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }
    private void setupAnimationStatesPhaseEvolve3() {
        if (this.getPhase() == 15) {
            if(this.evolving4AnimationTimeout <= 0) {
                this.evolving4AnimationTimeout = this.random.nextInt(40) + 80;
                this.evolving4.start(this.tickCount);
            } else {
                --this.evolving4AnimationTimeout;
            }
            this.evolving4.start(this.tickCount);
        }
        else {
            this.phase0.stop();
            this.phase1.stop();
            this.phase2.stop();
            this.phase3.stop();
            this.phase4.stop();
            this.phase5.stop();
            this.phase6.stop();
            this.phase7.stop();
            this.phase8.stop();
            this.phase9.stop();
            this.phase10.stop();
            this.phase11.stop();
            this.phase12.stop();
            this.evolving1.stop();
            this.evolving2.stop();
            this.evolving3.stop();
            this.evolving4.stop();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MOVEMENT_SPEED, speed / 10)
                .add(Attributes.FLYING_SPEED, speed / 10)
                .add(Attributes.MAX_HEALTH, maxHealth)
                .add(Attributes.FOLLOW_RANGE, followRange)
                .add(Attributes.MOVEMENT_SPEED, speed / 10)
                .add(Attributes.ARMOR_TOUGHNESS, armorToughness / 10)
                .add(Attributes.ATTACK_KNOCKBACK, attackKnockback / 10)
                .add(Attributes.ATTACK_DAMAGE, attackDamage);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }
}
