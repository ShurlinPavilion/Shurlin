package xyz.shurlin.entity.projecttile;

import net.minecraft.block.FacingBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import xyz.shurlin.block.Blocks;
import xyz.shurlin.block.BrightMoonPollenBlock;
import xyz.shurlin.entity.EntityTypes;
import xyz.shurlin.item.Items;

public class BrightMoonPollenEntity extends ThrownItemEntity {

    public BrightMoonPollenEntity(EntityType<? extends BrightMoonPollenEntity> entityType, World world) {
            super(entityType, world);
    }

    public BrightMoonPollenEntity(double x, double y, double z, World world) {
        super(EntityTypes.BRIGHT_MOON_POLLEN_ENTITY_TYPE, x, y, z, world);
    }

    public BrightMoonPollenEntity(LivingEntity owner, World world) {
        super(EntityTypes.BRIGHT_MOON_POLLEN_ENTITY_TYPE, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BRIGHT_MOON_POLLEN;
    }

    @Override
    public void tick() {
        super.tick();
        if(world.isClient)
        world.addParticle(ParticleTypes.LIGHT, this.getX(), this.getY(), this.getZ(), this.getVelocity().x / 2,this.getVelocity().y / 2,this.getVelocity().z / 2);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        Direction direction = blockHitResult.getSide();
        BlockPos pos = blockHitResult.getBlockPos();
        if(BrightMoonPollenBlock.canPlaceAt(world, pos, direction)){
            world.setBlockState(pos.add(direction.getVector()), Blocks.BRIGHT_MOON_POLLEN.getDefaultState().with(FacingBlock.FACING, blockHitResult.getSide()), 3);
        }
        this.discard();
    }


}
