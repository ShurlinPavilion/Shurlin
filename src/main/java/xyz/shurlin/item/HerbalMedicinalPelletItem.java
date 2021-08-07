package xyz.shurlin.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import xyz.shurlin.item.ItemGroups;

public class HerbalMedicinalPelletItem extends Item {

    HerbalMedicinalPelletItem(FoodComponent component) {
        super(new Settings().maxCount(16).food(component).group(ItemGroups.SHURLIN));
    }

    HerbalMedicinalPelletItem(){
        this(new FoodComponent.Builder().hunger(0).snack().saturationModifier(0.0f).build());
    }

    HerbalMedicinalPelletItem(StatusEffectInstance instance){
        this(new FoodComponent.Builder().hunger(0).snack().saturationModifier(0.0f).statusEffect(instance, 1.0f).build());
    }



}
