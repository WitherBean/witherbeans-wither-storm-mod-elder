package net.witherbean.wwsm.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.phys.Vec3;
import net.witherbean.wwsm.entity.wither_storm.head.WitherStormHead;
import net.witherbean.wwsm.entity.wither_storm.skull.WitherStormSkull;

public class WitherStormHeadShootGoal extends TargetGoal {
        private int cooldown = 80;
        public WitherStormHeadShootGoal(WitherStormHead attacker, boolean mustSee) {
            super(attacker, mustSee);
        }
        @Override
        public boolean canUse() {
            if (this.cooldown != 0) {
                return true;
            }
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return false;

            } else if (!target.isAlive()) {
                return false;
            }
            else {
                return true;
            }
        }

        @Override
        public void tick() {
            if (this.cooldown > 0) {
                this.cooldown--;
            }
            LivingEntity target = this.mob.getTarget();
            this.mob.getLookControl().setLookAt(target);
            Vec3 mobPosition = this.mob.position();
            Vec3 targetPosition = target.position();
            Vec3 arrowToTarget = targetPosition.subtract(mobPosition);
            Vec3 direction = arrowToTarget.normalize();
            Double distance = arrowToTarget.length();
            if (distance > 32) {
                this.mob.getNavigation().stop();
                this.mob.getNavigation().moveTo(target, 1.5);
            } else {
                if (this.mob.getRandom().nextInt(8) == 0) {
                    int i = 1;
                    if (this.mob.getRandom().nextInt(32) == 0) {
                        i = 4;
                    }

                    for(int j = 0; j < i; ++j) {
                        WitherStormSkull witherStormSkull = new WitherStormSkull(this.mob.level(), this.mob, direction.x, direction.y, direction.z);
                        if (witherStormSkull != null) {
//                            if(isTimeToStartAttackAnimation()) {
//                                this.mob.setShooting(true);
//                            }
                            witherStormSkull.setPos(this.mob.getEyePosition());
                            this.mob.level().addFreshEntity(witherStormSkull);
                        }
                    }
                }
                this.stop();
                this.cooldown = 1000;
            }
        }

    }
