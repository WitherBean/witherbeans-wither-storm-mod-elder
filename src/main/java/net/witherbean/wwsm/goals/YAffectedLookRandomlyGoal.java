package net.witherbean.wwsm.goals;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class YAffectedLookRandomlyGoal extends Goal {
    private final Mob entity;
    private final int randomLookTime;
    private final int Xmin;
    private final int Xmax;
    private final int Ymin;
    private final int Ymax;
    protected double relX;
    protected double relY;
    protected double relZ;
    protected int lookTime;

    public YAffectedLookRandomlyGoal(Mob entity) {
        this(entity, -140, -30, -80, 80, 20);
    }

    public YAffectedLookRandomlyGoal(Mob entity, int lookTime) {
        this(entity, -140, -30, -80, 80, lookTime);
    }

    public YAffectedLookRandomlyGoal(Mob entity, int Xmin, int Xmax, int Ymin, int Ymax, int lookTime) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.Xmin = Xmin;
        this.Xmax = Xmax;
        this.Ymin = Ymin;
        this.Ymax = Ymax;
        this.randomLookTime = lookTime;
    }

    public boolean canUse() {
        return this.entity.getRandom().nextFloat() < 0.02F;
    }

    public boolean canContinueToUse() {
        return this.lookTime >= 0;
    }

    public void start() {
        double xRot = (double)((float)Mth.clamp(-this.entity.getRandom().nextInt(180), this.Xmin, this.Xmax) * 0.017453292F);
        double yRot = (double)((Mth.wrapDegrees(this.entity.yBodyRot) + 90.0F + (float)Mth.clamp(this.entity.getRandom().nextInt(360) - 180, this.Ymin, this.Ymax)) * 0.017453292F);
        this.relX = Math.cos(yRot) * 30.0;
        this.relY = Math.sin(xRot) * 30.0;
        this.relZ = Math.sin(yRot) * 30.0;
        this.lookTime = this.getRandomLookTime() + this.entity.getRandom().nextInt(20);
    }

    protected int getRandomLookTime() {
        return this.randomLookTime;
    }

    public void tick() {
        --this.lookTime;
        this.look();
    }

    protected void look() {
        this.entity.getLookControl().setLookAt(this.getPos());
    }

    public Vec3 getPos() {
        return new Vec3(this.entity.getX() + this.relX, this.entity.getEyeY() + this.relY, this.entity.getZ() + this.relZ);
    }
}
