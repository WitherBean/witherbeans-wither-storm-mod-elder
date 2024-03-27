package net.witherbean.wwsm.entity.wither_storm.head;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.witherbean.wwsm.entity.wither_storm.skull.WitherStormSkull;
import net.witherbean.wwsm.goals.WitherStormHeadAttackGoal;
import net.witherbean.wwsm.goals.YAffectedLookRandomlyGoal;
import org.jetbrains.annotations.Nullable;

public class WitherStormHead extends Monster {

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WitherStormHeadAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(0, new YAffectedLookRandomlyGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AmbientCreature.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Creeper.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 15f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 120)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.MAX_HEALTH, 700D)
                .add(Attributes.ATTACK_DAMAGE, 17.0f)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 500000f)
                .add(Attributes.MOVEMENT_SPEED, 0f);
    }
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(WitherStormHead.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHOOTING =
            SynchedEntityData.defineId(WitherStormHead.class, EntityDataSerializers.BOOLEAN);

    public WitherStormHead(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState shootAnimationState = new AnimationState();
    private int shootAnimationTimeout = 0;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if(this.isShooting() && shootAnimationTimeout <= 0) {
            shootAnimationTimeout = 80; // Length in ticks of your animation
            shootAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }
        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 20; // Length in ticks of your animation
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
        if(!this.isShooting()) {
            shootAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }
    public void setShooting(boolean shooting) {
        this.entityData.set(SHOOTING, shooting);
    }

    public boolean isShooting() {
        return this.entityData.get(SHOOTING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(SHOOTING, false);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }
}