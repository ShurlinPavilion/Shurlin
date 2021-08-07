package xyz.shurlin.cultivation.mixin;

import annotations.Nullable;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.Shurlin;
import xyz.shurlin.cultivation.*;
import xyz.shurlin.cultivation.spiritmanual.SpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;
import xyz.shurlin.entity.damage.ShurlinDamageSource;
import xyz.shurlin.item.cultivation.DantianItem;

import java.util.Random;
import java.util.UUID;
import java.util.Vector;


@Mixin(PlayerEntity.class)
public abstract class CultivatedPlayerMixin extends LivingEntity implements CultivatedPlayerAccessor {
    private static final UUID ATTACK_ID = UUID.fromString("f2f6e1ef-79f9-47b7-99d0-a073e258e656");
    private static final UUID RESISTANCE_ID = UUID.fromString("c18217b9-ca2b-4e5e-838e-c1c3a88c3609");

    protected CultivatedPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow
    @Nullable
    public abstract ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

    @Shadow
    public abstract Vec3d method_30951(float f);

    @Unique
    private CultivationRealms realm;
    @Unique
    private short rating;
    @Unique
    private long spirit;
    @Unique
    private long maxSpirit;
    @Unique
    private long experience;
    @Unique
    private long experienceForUpgrade;
    @Unique
    private long power;
    @Unique
    private long resistance;
    @Unique
    private Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians = new Object2ObjectArrayMap<>();
    @Unique
    private short healTimes = 1;
    @Unique
    private NbtCompound tag;
    @Unique
    private Vector<SpiritManual> spiritManuals = new Vector<>();
    @Unique
    private SpiritManual MainSM;
    @Unique
    private Vector<SpiritManual> AssistSM = new Vector<>();
    @Unique
    private Vector<SpiritManual> AttackSM = new Vector<>();
    @Unique
    private SpiritManual currentSpiritManual;
    private float spirit_temp = 0;

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbt(NbtCompound tag, CallbackInfo ci) {
        fromTagServer(tag.getCompound("cul"));
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbt(NbtCompound tag, CallbackInfo ci) {
        tag.put("cul", toTag());
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo ci) {
        if (spirit != maxSpirit) {
            spirit_temp += 0.2f;
            if (spirit_temp == 1.0f) {
                heal();
                spirit_temp = 0;
            }
        }

//        System.out.println(1);
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && source != ShurlinDamageSource.BODY_EXPLOSION) {
            this.dropItem(toItemStack(), true, false);
            origin();
        }
    }

    public void origin() {
        this.realm = null;
    }

    public void cultivate() {
        Random random = new Random();
        this.realm = CultivationRealms.SOLDIER;
        this.rating = 1;
        init();
        this.experience = Shurlin.random.nextInt((int) this.maxSpirit);
        this.spirit = this.maxSpirit / 2;
        int sm_cnt = 0;
        for (SpiritPropertyType type : SpiritPropertyType.GROUPS) {
            meridians.put(type, random.nextFloat() < 0.2 ? new SpiritMeridians(type) : null);
        }
    }

    public void cultivate0() {
        this.realm = CultivationRealms.SOLDIER;
        this.rating = 1;
        init();
    }

    void init() {
        maxSpirit = CultivationMathHelper.getMaxSpirit(realm.getGradation(), rating);
        experienceForUpgrade = CultivationMathHelper.getExperienceForUpgrade(realm.getGradation(), rating);
        EntityAttributeInstance instance = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (instance != null) {
            if (instance.getModifier(ATTACK_ID) != null) {
                instance.removeModifier(ATTACK_ID);
                instance.addPersistentModifier(new EntityAttributeModifier(ATTACK_ID, "Cultivation Realm Addition", CultivationMathHelper.getAttack(this.realm.getGradation(), this.rating), EntityAttributeModifier.Operation.ADDITION));
            }
        }
        EntityAttributeInstance instance1 = this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (instance1 != null) {
            if (instance1.getModifier(RESISTANCE_ID) != null) {
                instance1.removeModifier(RESISTANCE_ID);
                instance1.addPersistentModifier(new EntityAttributeModifier(RESISTANCE_ID, "Cultivation Realm Addition", CultivationMathHelper.getResistance(this.realm.getGradation(), this.rating), EntityAttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Unique
    public NbtCompound toTag() {
        NbtCompound tag1 = new NbtCompound();
        if (realm != null) {
            tag1.putBoolean("isCultivated", true);
            tag1.putShort("gradation", realm.getGradation());
            tag1.putShort("rating", rating);
            tag1.putLong("spirit", spirit);
            tag1.putLong("ex", experience);
            NbtCompound sma_tag = new NbtCompound();
            sma_tag.putInt("cnt", this.spiritManuals.size());
            sma_tag.putShort("c", SpiritManuals.getId(currentSpiritManual));
            for (int i = 0; i < this.spiritManuals.size(); i++) {
                sma_tag.putShort(String.valueOf(i), SpiritManuals.getId(spiritManuals.elementAt(i)));
            }
            tag1.put("sma", sma_tag);
            int sm_cnt = 0;
            NbtCompound sm_tag = new NbtCompound();
            for (SpiritPropertyType type : SpiritPropertyType.GROUPS) {
                if (meridians.get(type) != null)
                    sm_tag.put(String.valueOf(sm_cnt++), meridians.get(type).toTag());
            }
            sm_tag.putInt("cnt", sm_cnt);
            tag1.put("sm", sm_tag);
        } else
            tag1.putBoolean("isCultivated", false);
        return tag1;
    }

    @Unique
    private ItemStack toItemStack() {
        return new ItemStack(new DantianItem().init(this));
    }

    public void fromTagServer(NbtCompound tag) {
        fromTag(tag);
        this.tag = tag;
//        PacketByteBuf byteBuf = PacketByteBufs.create();
//        byteBuf.writeNbt(tag);
//        ServerPlayNetworking.send((ServerPlayerEntity) this.inventory.player, Utils.CULTIVATION_DATA, byteBuf);
    }


    @Override
    public void fromTag(NbtCompound tag) {
        if (!tag.getBoolean("isCultivated"))
            return;
        short gradation = tag.getShort("gradation");
        rating = tag.getShort("rating");
        realm = CultivationRealms.getRealmByGradation(gradation);
//        System.out.println(rating);
//        System.out.println(realm);
//        System.out.println(this.gameProfile.getName());
//        System.out.println(this.getDisplayName());
        spirit = tag.getLong("spirit");
        experience = tag.getLong("ex");
        NbtCompound sma_tag = tag.getCompound("sma_tag");
        this.currentSpiritManual = SpiritManuals.getSpiritManual(sma_tag.getShort("c"));
        for (int i = 0; i < sma_tag.getInt("cnt"); i++) {
            this.spiritManuals.add(SpiritManuals.getSpiritManual(sma_tag.getShort(String.valueOf(i))));
        }
        NbtCompound sm_tag = tag.getCompound("sm");
        for (int sm_cnt = 0; sm_cnt < sm_tag.getInt("cnt"); sm_cnt++) {
            SpiritMeridians.fromTag(meridians, sm_tag.getCompound(String.valueOf(sm_cnt++)));
        }


        init();
    }

    public void upgrade() {
        if (best())
            return;
        if (rating == realm.getMaxRating()) {
            this.realm = realm.getNextGradation();
            this.rating = 1;
        } else rating++;
    }

    private void heal() {
        long spirit = (this.maxSpirit >> 8) * healTimes;
        if (spirit + this.spirit > maxSpirit) {
            this.spirit = maxSpirit;
        } else {
            this.spirit += spirit;
        }
    }

    public TranslatableText getDescribeText() {
        return new TranslatableText("realm.shurlin." + this.realm.getName() + ".rating.k0", this.rating);
//        return new TranslatableText("realm.shurlin.soldier.rating", this.rating);
    }

    @Override
    public String getSpiritText() {
        return SpiritConsistences.getDescribe(this.spirit) + '/' + SpiritConsistences.getDescribe(this.maxSpirit);
    }

    @Override
    public String getExperimentText() {
        return SpiritConsistences.getDescribe(this.experience) + '/' + SpiritConsistences.getDescribe(this.experienceForUpgrade);
    }

    public boolean best() {
        return this.realm.getGradation() == 12 && this.rating == 18;
    }

    public void up(long spirit) {
        if (spirit + this.spirit > 2 * maxSpirit && !this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            System.out.println(3);
            this.damage(ShurlinDamageSource.BODY_EXPLOSION, Float.MAX_VALUE);
            System.out.println(4);
            return;
        }
        if (spirit + this.spirit > maxSpirit) {
            this.experience += spirit + this.spirit - this.maxSpirit;
            if (this.experience >= this.experienceForUpgrade) {
                this.experience = 0;
                upgrade();
                init();
                this.spirit = maxSpirit;
            }
        } else {
            this.spirit += spirit;
        }
    }

    public void healWithDantian(long spirit) {
        cultivate0();
        while (spirit >= this.experienceForUpgrade) {
            spirit -= experienceForUpgrade;
            upgrade();
            init();
        }
        this.experience = spirit;
        this.spirit = spirit;
    }

    @Override
    public CultivationRealms getRealm() {
        return realm;
    }

    @Override
    public void setRealm(CultivationRealms realm) {
        this.realm = realm;
    }

    @Override
    public short getRating() {
        return rating;
    }

    @Override
    public void setRating(short rating) {
        this.rating = rating;
    }

    @Override
    public long getSpirit() {
        return spirit;
    }

    @Override
    public void setSpirit(long spirit) {
        this.spirit = spirit;
    }

    @Override
    public long getMaxSpirit() {
        return maxSpirit;
    }

    @Override
    public void setMaxSpirit(long maxSpirit) {
        this.maxSpirit = maxSpirit;
    }

    @Override
    public long getExperience() {
        return experience;
    }

    @Override
    public void setExperience(long experience) {
        this.experience = experience;
    }

    @Override
    public long getExperienceForUpgrade() {
        return experienceForUpgrade;
    }

    @Override
    public void setExperienceForUpgrade(long experienceForUpgrade) {
        this.experienceForUpgrade = experienceForUpgrade;
    }

    @Override
    public long getPower() {
        return power;
    }

    @Override
    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public long getResistance() {
        return resistance;
    }

    @Override
    public void setResistance(long resistance) {
        this.resistance = resistance;
    }

    @Override
    public Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> getMeridians() {
        return meridians;
    }

    @Override
    public void setMeridians(Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians) {
        this.meridians = meridians;
    }

    @Override
    public String toString() {
        return String.valueOf(this.experience);
    }

    @Override
    public boolean consume(long spirit) {
        if (this.spirit - spirit < 0)
            return false;
        else {
            this.spirit -= spirit;
            return true;
        }
    }

    @Override
    public NbtCompound getTag() {
        return this.tag;
    }

    @Override
    public long getTotalSpirit() {
        long total = 0;
        for (short i = CultivationRealms.SOLDIER.getGradation(); i <= this.realm.getGradation(); i++) {
            for (short j = 1; j <= ((i == this.realm.getGradation()) ? this.rating : CultivationRealms.getRealmByGradation(i).getMaxRating()); j++) {
                total += CultivationMathHelper.getMaxSpirit(i, j);
            }
        }
        return total;
    }

    public boolean appendSpiritManual(SpiritManual spiritManual) {
        if (this.spiritManuals.contains(spiritManual) || this.realm == null)
            return false;
        else {
            this.spiritManuals.add(spiritManual);
            this.currentSpiritManual = spiritManual;
            return true;
        }
    }

    @Override
    public SpiritManual getCurrentSpiritManual() {
        return this.currentSpiritManual;
    }
}
