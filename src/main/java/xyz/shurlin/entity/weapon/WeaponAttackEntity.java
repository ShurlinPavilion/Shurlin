package xyz.shurlin.entity.weapon;

import com.google.common.base.MoreObjects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.UUID;

public abstract class WeaponAttackEntity extends Entity {
    @Nullable
    private UUID ownerUuid;
    @Nullable
    private Entity owner;
    private boolean leftOwner;
    private boolean shot;
    public double powerX;
    public double powerY;
    public double powerZ;

    public WeaponAttackEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public WeaponAttackEntity(EntityType<? extends WeaponAttackEntity> type, double x, double y, double z, double directionX, double directionY, double directionZ, World world) {
        this(type, world);
        this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
        this.refreshPosition();
        double d = Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);
        if (d != 0.0D) {
            this.powerX = directionX / d * 0.1D;
            this.powerY = directionY / d * 0.1D;
            this.powerZ = directionZ / d * 0.1D;
        }

    }

    public WeaponAttackEntity(EntityType<? extends WeaponAttackEntity> type, LivingEntity owner, double directionX, double directionY, double directionZ, World world) {
        this(type, owner.getX(), owner.getY(), owner.getZ(), directionX, directionY, directionZ, world);
        this.setOwner(owner);
        this.setRotation(owner.getYaw(), owner.getPitch());
    }

    @Override
    protected void initDataTracker() {

    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUuid = entity.getUuid();
            this.owner = entity;
        }

    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null) {
            return this.owner;
        } else if (this.ownerUuid != null && this.world instanceof ServerWorld) {
            this.owner = ((ServerWorld)this.world).getEntity(this.ownerUuid);
            return this.owner;
        } else {
            return null;
        }
    }

    protected boolean isOwner(Entity entity) {
        return entity.getUuid().equals(this.ownerUuid);
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (this.world.isClient || (entity == null || !entity.isRemoved()) && this.world.isChunkLoaded(this.getBlockPos())) {
            if (!this.shot) {
                this.emitGameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner(), this.getBlockPos());
                this.shot = true;
            }

            if (!this.leftOwner) {
                this.leftOwner = this.shouldLeaveOwner();
            }

            super.tick();
            if (this.isBurning()) {
                this.setOnFireFor(1);
            }

            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }

            this.checkBlockCollision();
            Vec3d vec3d = this.getVelocity();
            double d = this.getX() + vec3d.x;
            double e = this.getY() + vec3d.y;
            double f = this.getZ() + vec3d.z;
            ProjectileUtil.method_7484(this, 0.2F);
            float g = this.getDrag();
            this.setVelocity(vec3d.add(this.powerX, this.powerY, this.powerZ).multiply(g));
            this.setPosition(d, e, f);
        } else {
            this.discard();
        }
    }

    protected boolean isBurning() {
        return false;
    }

    protected float getDrag() {
        return 0.95F;
    }

    protected ParticleEffect getParticleType() {
        return ParticleTypes.SMOKE;
    }

    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 4.0D;
        if (Double.isNaN(d)) {
            d = 4.0D;
        }

        d *= 64.0D;
        return distance < d * d;
    }


    private boolean shouldLeaveOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            Iterator var2 = this.world.getOtherEntities(this, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), (entityx) -> {
                return !entityx.isSpectator() && entityx.collides();
            }).iterator();

            while(var2.hasNext()) {
                Entity entity2 = (Entity)var2.next();
                if (entity2.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double)divergence, this.random.nextGaussian() * 0.007499999832361937D * (double)divergence, this.random.nextGaussian() * 0.007499999832361937D * (double)divergence).multiply((double)speed);
        this.setVelocity(vec3d);
        double d = vec3d.horizontalLength();
        this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
        this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875D));
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
    }

    public void setProperties(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.setVelocity((double)f, (double)g, (double)h, modifierZ, modifierXYZ);
        Vec3d vec3d = user.getVelocity();
        this.setVelocity(this.getVelocity().add(vec3d.x, user.isOnGround() ? 0.0D : vec3d.y, vec3d.z));
    }

    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            this.onBlockHit((BlockHitResult)hitResult);
        }

        if (type != HitResult.Type.MISS) {
            this.emitGameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
//        BlockState blockState = this.world.getBlockState(blockHitResult.getBlockPos());
//        blockState.onProjectileHit(this.world, blockState, blockHitResult, this);
    }

    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(x, y, z);
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = Math.sqrt(x * x + z * z);
            this.setPitch((float)(MathHelper.atan2(y, d) * 57.2957763671875D));
            this.setYaw((float)(MathHelper.atan2(x, z) * 57.2957763671875D));
            this.prevPitch = this.getPitch();
            this.prevYaw = this.getYaw();
            this.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
        }

    }

    protected void updateRotation() {
        Vec3d vec3d = this.getVelocity();
        double d = vec3d.horizontalLength();
        this.setPitch(updateRotation(this.prevPitch, (float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875D)));
        this.setYaw(updateRotation(this.prevYaw, (float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D)));
    }

    protected static float updateRotation(float prevRot, float newRot) {
        while(newRot - prevRot < -180.0F) {
            prevRot -= 360.0F;
        }

        while(newRot - prevRot >= 180.0F) {
            prevRot += 360.0F;
        }

        return MathHelper.lerp(0.2F, prevRot, newRot);
    }

    public boolean canModifyAt(World world, BlockPos pos) {
        Entity entity = this.getOwner();
        if (entity instanceof PlayerEntity) {
            return entity.canModifyAt(world, pos);
        } else {
            return entity == null || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        }
    }

    protected boolean canHit(Entity entity) {
        boolean b;
        if (!entity.isSpectator() && entity.isAlive() && entity.collides()) {
            Entity entity2 = this.getOwner();
            b = entity2 == null || this.leftOwner || !entity2.isConnectedThroughVehicle(entity);
        } else {
            b = false;
        }
        return b && !entity.noClip;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }

        if (this.leftOwner) {
            nbt.putBoolean("LeftOwner", true);
        }

        nbt.putBoolean("HasBeenShot", this.shot);
        nbt.put("power", this.toNbtList(new double[]{this.powerX, this.powerY, this.powerZ}));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }
        this.leftOwner = nbt.getBoolean("LeftOwner");
        this.shot = nbt.getBoolean("HasBeenShot");
        if (nbt.contains("power", 9)) {
            NbtList nbtList = nbt.getList("power", 6);
            if (nbtList.size() == 3) {
                this.powerX = nbtList.getDouble(0);
                this.powerY = nbtList.getDouble(1);
                this.powerZ = nbtList.getDouble(2);
            }
        }

    }

    public boolean collides() {
        return true;
    }

    public float getTargetingMargin() {
        return 1.0F;
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.scheduleVelocityUpdate();
            Entity entity = source.getAttacker();
            if (entity != null) {
                Vec3d vec3d = entity.getRotationVector();
                this.setVelocity(vec3d);
                this.powerX = vec3d.x * 0.1D;
                this.powerY = vec3d.y * 0.1D;
                this.powerZ = vec3d.z * 0.1D;
                this.setOwner(entity);
                return true;
            } else {
                return false;
            }
        }
    }

    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    public Packet<?> createSpawnPacket() {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        return new EntitySpawnS2CPacket(this.getId(), this.getUuid(), this.getX(), this.getY(), this.getZ(), this.getPitch(), this.getYaw(), this.getType(), i, new Vec3d(this.powerX, this.powerY, this.powerZ));
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        Entity entity = this.world.getEntityById(packet.getEntityData());
        if (entity != null) {
            this.setOwner(entity);
        }
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();
        double g = Math.sqrt(d * d + e * e + f * f);
        if (g != 0.0D) {
            this.powerX = d / g * 0.1D;
            this.powerY = e / g * 0.1D;
            this.powerZ = f / g * 0.1D;
        }

    }
}
