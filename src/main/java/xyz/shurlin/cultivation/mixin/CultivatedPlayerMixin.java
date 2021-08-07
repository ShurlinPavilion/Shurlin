package xyz.shurlin.cultivation.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.Shurlin;
import xyz.shurlin.cultivation.*;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.ConsciousnessLevel;
import xyz.shurlin.cultivation.level.SkillAttainments;
import xyz.shurlin.cultivation.level.WithLevel;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;
import xyz.shurlin.item.cultivation.DantianItem;
import xyz.shurlin.util.Utils;

import java.util.Random;
import java.util.UUID;
import java.util.Vector;

@Mixin(PlayerEntity.class)
public abstract class CultivatedPlayerMixin extends LivingEntity implements CultivatedPlayerAccessor {
    private static final UUID ATTACK_ID = UUID.fromString("f2f6e1ef-79f9-47b7-99d0-a073e258e656");
    private static final UUID RESISTANCE_ID = UUID.fromString("c18217b9-ca2b-4e5e-838e-c1c3a88c3609");
    private static final UUID HEALTH_ID = UUID.fromString("217f9144-9c20-4a66-8742-2f7432a4fdd7");

    protected CultivatedPlayerMixin(EntityType<? extends PlayerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow @Final private PlayerAbilities abilities;
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
    private double power;
    @Unique
    private double resistance;
    @Unique
    private double max_health;
    @Unique
    private double meridians_toughness;
    @Unique
    private Vector<WithLevel> ability4 = new Vector<>();
    @Unique
    private long[] ability4_experience = new long[4];
    @Unique
    private long[] ability4_max_experience = new long[4];
    @Unique
    private Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians = new Object2ObjectArrayMap<>();
    @Unique
    private short healTimes = 1;
    @Unique
    private NbtCompound tag;
    @Unique
    private Vector<AbstractSpiritManual> spiritManuals = new Vector<>();
    @Unique
    private AbstractSpiritManual mainSM;
    @Unique
    private Vector<AbstractSpiritManual> AssistSM = new Vector<>();
    @Unique
    private Vector<AbstractSpiritManual> AttackSM = new Vector<>();
    private float spirit_temp = 0;
    private int currentAttackSMIndex = -1;
    @Unique
    private boolean spirit_out = false;
    private boolean isPreparingGongfa;


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
    }

//    @Inject(method = "onDeath", at = @At("TAIL"))
//    private void onDeath(DamageSource source, CallbackInfo ci) {
//        if (!this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && source != ShurlinDamageSource.BODY_EXPLOSION) {
//            this.dropItem(toItemStack(), true, false);
//            origin();
//        }
//    }

    public void origin() {
        this.realm = null;
    }

    public void cultivate() {
        Random random = new Random();
        this.realm = CultivationRealms.SOLDIER;
        this.rating = 1;
        this.meridians_toughness = 1d;
        this.ability4.add(ConsciousnessLevel.WACKUP);
        this.ability4.add(SkillAttainments.NOVICE);
        this.ability4.add(SkillAttainments.NOVICE);
        this.ability4.add(SkillAttainments.NOVICE);
        init();
        this.experience = Shurlin.random.nextInt((int) this.maxSpirit);//TODO 这里值大了会有bug
        this.spirit = this.maxSpirit / 2;
        for (SpiritPropertyType type : SpiritPropertyType.GROUPS) {
            meridians.put(type, SpiritMeridians.random(type));
        }
        for (int i = 0; i < 4; i++)
            this.ability4_experience[i] = Shurlin.random.nextInt((int) this.ability4_max_experience[i]);

    }

    public void cultivate0() {
        this.realm = CultivationRealms.SOLDIER;
        this.rating = 1;
        init();
    }

    void init() {
        maxSpirit = CultivationMathHelper.getMaxSpirit(realm.getGradation(), rating);
        experienceForUpgrade = CultivationMathHelper.getExperienceForUpgrade(realm.getGradation(), rating);
        this.power = CultivationMathHelper.getAttack(this.realm.getGradation(), this.rating);
        this.resistance = CultivationMathHelper.getResistance(this.realm.getGradation(), this.rating);
        this.max_health = CultivationMathHelper.getHealth(this.realm.getGradation(), this.rating);
        for (int i = 0; i < 4; i++)
            this.ability4_max_experience[i] = CultivationMathHelper.getMaxExperience(this.ability4.elementAt(i));
        EntityAttributeInstance instance = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (instance != null) {
            if (instance.getModifier(ATTACK_ID) != null) {
                instance.removeModifier(ATTACK_ID);
            }
            instance.addPersistentModifier(new EntityAttributeModifier(ATTACK_ID, "Cultivation Realm Base", power, EntityAttributeModifier.Operation.ADDITION));
        }
        EntityAttributeInstance instance1 = this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if (instance1 != null) {
            if (instance1.getModifier(RESISTANCE_ID) != null) {
                instance1.removeModifier(RESISTANCE_ID);
            }
            instance1.addPersistentModifier(new EntityAttributeModifier(RESISTANCE_ID, "Cultivation Realm Base", resistance, EntityAttributeModifier.Operation.ADDITION));
        }
        EntityAttributeInstance instance2 = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (instance2 != null) {
            if (instance2.getModifier(HEALTH_ID) != null) {
                instance2.removeModifier(HEALTH_ID);
            }
            instance2.addPersistentModifier(new EntityAttributeModifier(HEALTH_ID, "Cultivation Realm Base", max_health, EntityAttributeModifier.Operation.ADDITION));
        }
    }

    public NbtCompound toTag() {
        NbtCompound tag1 = new NbtCompound();
        if (realm != null) {
            tag1.putBoolean("isCultivated", true);
            tag1.putShort("gradation", realm.getGradation());
            tag1.putShort("rating", rating);
            tag1.putLong("spirit", spirit);
            tag1.putLong("ex", experience);
            tag1.putDouble("mt", meridians_toughness);
            NbtCompound a4_tag = new NbtCompound();
            for (int i = 0; i < 4; i++) {
                a4_tag.putShort(i + "1", this.ability4.elementAt(i).getLevel());
                a4_tag.putLong(i + "2", this.ability4_experience[i]);
            }
            tag1.put("a4", a4_tag);
            NbtCompound sma_tag = new NbtCompound();
            sma_tag.putInt("cnt", this.spiritManuals.size());
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
    }


    @Override
    public void fromTag(NbtCompound tag) {
        if (tag == null || !tag.getBoolean("isCultivated"))
            return;
        short gradation = tag.getShort("gradation");
        rating = tag.getShort("rating");
        realm = CultivationRealms.getRealmByGradation(gradation);
        spirit = tag.getLong("spirit");
        experience = tag.getLong("ex");
        meridians_toughness = tag.getDouble("mt");
        NbtCompound a4_tag = tag.getCompound("a4");
        for (int i = 0; i < 4; i++) {
            if (i == 0)
                this.ability4.add(ConsciousnessLevel.getByLevel(a4_tag.getShort(i + "1")));
            else
                this.ability4.add(SkillAttainments.getByLevel(a4_tag.getShort(i + "1")));
            this.ability4_experience[i] = a4_tag.getLong(i + "2");
        }
        NbtCompound sma_tag = tag.getCompound("sma");
//        this.currentSpiritManual = SpiritManuals.getSpiritManual(sma_tag.getShort("c"));
        for (int i = 0; i < sma_tag.getInt("cnt"); i++) {
            storageSpiritManual(SpiritManuals.getSpiritManual(sma_tag.getShort(String.valueOf(i))));
        }
        NbtCompound sm_tag = tag.getCompound("sm");
        for (int sm_cnt = 0; sm_cnt < sm_tag.getInt("cnt"); sm_cnt++) {
            SpiritMeridians.fromTag(meridians, sm_tag.getCompound(String.valueOf(sm_cnt)));
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
//        if (spirit + this.spirit > 2 * maxSpirit && !this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
//            System.out.println(3);
//            this.damage(ShurlinDamageSource.BODY_EXPLOSION, Float.MAX_VALUE);
//            System.out.println(4);
//            return;
//        }//TODO
        if (spirit + this.spirit > maxSpirit) {
            this.experience += spirit + this.spirit - this.maxSpirit;
            if (this.experience >= this.experienceForUpgrade) {
                this.experience = 0;
                upgrade();
                init();
            }
            this.spirit = maxSpirit;
        } else {
            this.spirit += spirit;
        }
    }

    public void healWithDantian(long spirit) {//补4abilities
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
    public long getSpirit() {
        return spirit;
    }

    @Override
    public long getMaxSpirit() {
        return maxSpirit;
    }

    @Override
    public long getExperience() {
        return experience;
    }

    @Override
    public long getExperienceForUpgrade() {
        return experienceForUpgrade;
    }

    @Override
    public double getPower() {
        return this.power;
    }

    @Override
    public double getResistance() {
        return this.resistance;
    }

    @Override
    public double getMaxHealth1() {
        return this.max_health;
    }

    @Override
    public double getMeridiansToughness() {
        return this.meridians_toughness;
    }

    @Override
    public WithLevel getAbility(int id) {
        return this.ability4.elementAt(id);
    }

    @Override
    public long getAbilityExperience(int id) {
        return this.ability4_experience[id];
    }

    @Override
    public long getAbilityMaxExperience(int id) {
        return this.ability4_max_experience[id];
    }

    @Override
    public Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> getMeridians() {
        return meridians;
    }

    @Override
    public SpiritMeridians getMeridian(SpiritPropertyType type) {
        return this.meridians.get(type);
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

    public boolean appendSpiritManual(AbstractSpiritManual spiritManual) {
        if (this.spiritManuals.contains(spiritManual) || this.realm == null)
            return false;
        else {
            storageSpiritManual(spiritManual);
            return true;
        }
    }

    @Override
    public AbstractSpiritManual getCurrentSpiritManual() {
        if (this.world.isClient || this.AssistSM.size() > 0)
            return AttackSM.elementAt(currentAttackSMIndex);
        else
            return null;
    }

    @Override
    public void changeSpiritOut() {
        this.spirit_out = !this.spirit_out;
        if (this.world.isClient) {
            sendSO();
        }
    }

    @Environment(EnvType.CLIENT)
    private void sendSO() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(this.spirit_out);
        ClientPlayNetworking.send(Utils.SPIRIT_OUT, buf);
    }

    @Override
    public boolean getSpiritOut() {
        return this.spirit_out;
    }

    @Override
    public Vector<AbstractSpiritManual> getAttackSM() {
        return AttackSM;
    }

    @Override
    public Vector<AbstractSpiritManual> getAllSM() {
        return spiritManuals;
    }

    @Override
    public Vector<AbstractSpiritManual> getAssistSM() {
        return AssistSM;
    }

    @Override
    public AbstractSpiritManual getMainSM() {
        return mainSM;
    }

    private void storageSpiritManual(AbstractSpiritManual spiritManual) {
        this.spiritManuals.add(spiritManual);
        switch (spiritManual.getType()) {
            case ATTACK :
                if(this.AttackSM.isEmpty())
                    this.currentAttackSMIndex = 0;
                this.AttackSM.add(spiritManual);
            case ASSIST : this.AssistSM.add(spiritManual);
            case MAIN : this.mainSM = spiritManual;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getCASMI() {
        return this.currentAttackSMIndex;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setCASMI(int currentAttackSMIndex) {
        this.currentAttackSMIndex = currentAttackSMIndex;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void nextCASMI() {
        if (this.currentAttackSMIndex + 1 == this.AttackSM.size())
            this.currentAttackSMIndex = 0;
        else this.currentAttackSMIndex++;
    }

    @Override
    public boolean isPreparingGongfa() {
        return this.isPreparingGongfa;
    }

    @Override
    public void setPreparingGongfa(boolean b) {
        this.isPreparingGongfa = b;
    }
}
