package com.leclowndu93150.carbort.content.entities;

import com.google.common.annotations.VisibleForTesting;
import com.leclowndu93150.carbort.registries.CBEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Objects;

public class BeanEntity extends Mob {
    private static final EntityDataAccessor<Integer> ID_SIZE;
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 127;
    public static final int MAX_NATURAL_SIZE = 4;
    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;

    public BeanEntity(EntityType<BeanEntity> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
        this.moveControl = new BeanEntity.BeanEntityMoveControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BeanEntity.BeanEntityFloatGoal(this));
        this.goalSelector.addGoal(2, new BeanEntity.BeanEntityAttackGoal(this));
        this.goalSelector.addGoal(3, new BeanEntity.BeanEntityRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new BeanEntity.BeanEntityKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_352812_) -> Math.abs(p_352812_.getY() - this.getY()) <= (double)4.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_SIZE, 1);
    }

    @VisibleForTesting
    public void setSize(int size, boolean resetHealth) {
        int i = Mth.clamp(size, 1, 127);
        this.entityData.set(ID_SIZE, i);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(i * i));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i);
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }

        this.xpReward = i;
    }

    public int getSize() {
        return (Integer)this.entityData.get(ID_SIZE);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Size", this.getSize() - 1);
        compound.putBoolean("wasOnGround", this.wasOnGround);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        this.setSize(compound.getInt("Size") + 1, false);
        super.readAdditionalSaveData(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
    }

    public boolean isTiny() {
        return this.getSize() <= 1;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.ITEM_SLIME;
    }

    protected boolean shouldDespawnInPeaceful() {
        return this.getSize() > 0;
    }

    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;
        super.tick();
        if (this.onGround() && !this.wasOnGround) {
            float f = this.getDimensions(this.getPose()).width() * 2.0F;
            float f1 = f / 2.0F;
            if (!this.spawnCustomParticles()) {
                for(int i = 0; (float)i < f * 16.0F; ++i) {
                    float f2 = this.random.nextFloat() * ((float)Math.PI * 2F);
                    float f3 = this.random.nextFloat() * 0.5F + 0.5F;
                    float f4 = Mth.sin(f2) * f1 * f3;
                    float f5 = Mth.cos(f2) * f1 * f3;
                    this.level().addParticle(this.getParticleType(), this.getX() + (double)f4, this.getY(), this.getZ() + (double)f5, (double)0.0F, (double)0.0F, (double)0.0F);
                }
            }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetSquish = -0.5F;
        } else if (!this.onGround() && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }

        this.wasOnGround = this.onGround();
        this.decreaseSquish();
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (ID_SIZE.equals(key)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.onSyncedDataUpdated(key);
    }

    public void remove(Entity.RemovalReason reason) {
        int i = this.getSize();
        if (!this.level().isClientSide && i > 1 && this.isDeadOrDying()) {
            Component component = this.getCustomName();
            boolean flag = this.isNoAi();
            float f = this.getDimensions(this.getPose()).width();
            float f1 = f / 2.0F;
            int j = i / 2;
            int k = 2 + this.random.nextInt(3);
            ArrayList<Mob> children = new ArrayList();

            for(int l = 0; l < k; ++l) {
                float f2 = ((float)(l % 2) - 0.5F) * f1;
                float f3 = ((float)(l / 2) - 0.5F) * f1;
                BeanEntity BeanEntity = (BeanEntity)this.getType().create(this.level());
                if (BeanEntity != null) {
                    if (this.isPersistenceRequired()) {
                        BeanEntity.setPersistenceRequired();
                    }

                    BeanEntity.setCustomName(component);
                    BeanEntity.setNoAi(flag);
                    BeanEntity.setInvulnerable(this.isInvulnerable());
                    BeanEntity.setSize(j, true);
                    BeanEntity.moveTo(this.getX() + (double)f2, this.getY() + (double)0.5F, this.getZ() + (double)f3, this.random.nextFloat() * 360.0F, 0.0F);
                    children.add(BeanEntity);
                }
            }

            if (!EventHooks.onMobSplit(this, children).isCanceled()) {
                Level var10001 = this.level();
                Objects.requireNonNull(var10001);
                children.forEach(var10001::addFreshEntity);
            }
        }

        super.remove(reason);
    }

    public void push(Entity entity) {
        super.push(entity);
        if (entity instanceof IronGolem && this.isDealsDamage()) {
            this.dealDamage((LivingEntity)entity);
        }

    }

    public void playerTouch(Player entity) {
        if (this.isDealsDamage()) {
            this.dealDamage(entity);
        }

    }

    protected void dealDamage(LivingEntity livingEntity) {
        if (this.isAlive() && this.isWithinMeleeAttackRange(livingEntity) && this.hasLineOfSight(livingEntity)) {
            DamageSource damagesource = this.damageSources().mobAttack(this);
            if (livingEntity.hurt(damagesource, this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                Level var4 = this.level();
                if (var4 instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)var4;
                    EnchantmentHelper.doPostAttackEffects(serverlevel, livingEntity, damagesource);
                }
            }
        }

    }

    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        return new Vec3((double)0.0F, (double)dimensions.height() - (double)0.015625F * (double)this.getSize() * (double)partialTick, (double)0.0F);
    }

    protected boolean isDealsDamage() {
        return !this.isTiny() && this.isEffectiveAi();
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.SLIME_SQUISH_SMALL;
    }

    protected float getSoundVolume() {
        return 0.4F * (float)this.getSize();
    }

    public int getMaxHeadXRot() {
        return 0;
    }

    protected boolean doPlayJumpSound() {
        return this.getSize() > 0;
    }

    public void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, (double)this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
        CommonHooks.onLivingJump(this);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        int i = randomsource.nextInt(3);
        if (i < 2 && randomsource.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
            ++i;
        }

        int j = 1 << i;
        this.setSize(j, true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    float getSoundPitch() {
        float f = this.isTiny() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP_SMALL;
    }

    public EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).scale((float)this.getSize());
    }

    protected boolean spawnCustomParticles() {
        return false;
    }

    static {
        ID_SIZE = SynchedEntityData.defineId(BeanEntity.class, EntityDataSerializers.INT);
    }

    static class BeanEntityAttackGoal extends Goal {
        private final BeanEntity bean;
        private int growTiredTimer;

        public BeanEntityAttackGoal(BeanEntity bean) {
            this.bean = bean;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.bean.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return !this.bean.canAttack(livingentity) ? false : this.bean.getMoveControl() instanceof BeanEntity.BeanEntityMoveControl;
            }
        }

        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.bean.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return !this.bean.canAttack(livingentity) ? false : --this.growTiredTimer > 0;
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.bean.getTarget();
            if (livingentity != null) {
                this.bean.lookAt(livingentity, 10.0F, 10.0F);
            }

            MoveControl var3 = this.bean.getMoveControl();
            if (var3 instanceof BeanEntity.BeanEntityMoveControl BeanEntity$BeanEntitymovecontrol) {
                BeanEntity$BeanEntitymovecontrol.setDirection(this.bean.getYRot(), this.bean.isDealsDamage());
            }

        }
    }

    static class BeanEntityFloatGoal extends Goal {
        private final BeanEntity BeanEntity;

        public BeanEntityFloatGoal(BeanEntity BeanEntity) {
            this.BeanEntity = BeanEntity;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
            BeanEntity.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return (this.BeanEntity.isInWater() || this.BeanEntity.isInLava()) && this.BeanEntity.getMoveControl() instanceof BeanEntity.BeanEntityMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.BeanEntity.getRandom().nextFloat() < 0.8F) {
                this.BeanEntity.getJumpControl().jump();
            }

            MoveControl var2 = this.BeanEntity.getMoveControl();
            if (var2 instanceof BeanEntity.BeanEntityMoveControl BeanEntity$BeanEntitymovecontrol) {
                BeanEntity$BeanEntitymovecontrol.setWantedMovement(1.2);
            }

        }
    }

    static class BeanEntityKeepOnJumpingGoal extends Goal {
        private final BeanEntity BeanEntity;

        public BeanEntityKeepOnJumpingGoal(BeanEntity BeanEntity) {
            this.BeanEntity = BeanEntity;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        public boolean canUse() {
            return !this.BeanEntity.isPassenger();
        }

        public void tick() {
            MoveControl var2 = this.BeanEntity.getMoveControl();
            if (var2 instanceof BeanEntity.BeanEntityMoveControl BeanEntity$BeanEntitymovecontrol) {
                BeanEntity$BeanEntitymovecontrol.setWantedMovement((double)1.0F);
            }

        }
    }

    static class BeanEntityMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final BeanEntity BeanEntity;
        private boolean isAggressive;

        public BeanEntityMoveControl(BeanEntity BeanEntity) {
            super(BeanEntity);
            this.BeanEntity = BeanEntity;
            this.yRot = 180.0F * BeanEntity.getYRot() / (float)Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
            this.isAggressive = aggressive;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.BeanEntity.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.BeanEntity.getJumpControl().jump();
                        if (this.BeanEntity.doPlayJumpSound()) {
                            this.BeanEntity.playSound(this.BeanEntity.getJumpSound(), this.BeanEntity.getSoundVolume(), this.BeanEntity.getSoundPitch());
                        }
                    } else {
                        this.BeanEntity.xxa = 0.0F;
                        this.BeanEntity.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }

        }
    }

    static class BeanEntityRandomDirectionGoal extends Goal {
        private final BeanEntity BeanEntity;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public BeanEntityRandomDirectionGoal(BeanEntity BeanEntity) {
            this.BeanEntity = BeanEntity;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            return this.BeanEntity.getTarget() == null && (this.BeanEntity.onGround() || this.BeanEntity.isInWater() || this.BeanEntity.isInLava() || this.BeanEntity.hasEffect(MobEffects.LEVITATION)) && this.BeanEntity.getMoveControl() instanceof BeanEntity.BeanEntityMoveControl;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.BeanEntity.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.BeanEntity.getRandom().nextInt(360);
            }

            MoveControl var2 = this.BeanEntity.getMoveControl();
            if (var2 instanceof BeanEntity.BeanEntityMoveControl BeanEntity$BeanEntitymovecontrol) {
                BeanEntity$BeanEntitymovecontrol.setDirection(this.chosenDegrees, false);
            }

        }
    }
}
