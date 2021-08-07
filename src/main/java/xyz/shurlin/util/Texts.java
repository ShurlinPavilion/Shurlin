package xyz.shurlin.util;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import xyz.shurlin.cultivation.SpiritPropertyType;
import xyz.shurlin.cultivation.level.ConsciousnessLevel;
import xyz.shurlin.cultivation.level.SkillAttainments;
import xyz.shurlin.cultivation.level.SpiritRootLevel;
import xyz.shurlin.cultivation.level.WithLevel;

import java.util.Locale;

public class Texts {
    public static final TranslatableText REALM_SPIRIT = new TranslatableText("text.shurlin.realm_spirit");
    public static final TranslatableText REALM_EX = new TranslatableText("text.shurlin.realm_ex");
    public static final TranslatableText GONGFA = new TranslatableText("screen.shurlin.gongfa");
    public static final TranslatableText MAIN_GONGFA = new TranslatableText("screen.shurlin.main_gongfa");
    public static final TranslatableText ASSIST_GONGFA = new TranslatableText("screen.shurlin.assist_gongfa");
    public static final TranslatableText ATTACK_GONGFA = new TranslatableText("screen.shurlin.attack_gongfa");
    public static final TranslatableText ALL_GONGFA = new TranslatableText("screen.shurlin.all_gongfa");
    //    public static final TranslatableText SPIRIT_MERIDIANS = new TranslatableText("screen.shurlin.spirit_meridians");
    public static final TranslatableText SPIRIT_MERIDIANS_UNAWAKENED = new TranslatableText("screen.shurlin.spirit_meridians_unawakened");
    public static final TranslatableText SPIRIT_MERIDIANS_LEVEL = new TranslatableText("screen.shurlin.spirit_meridians_level");
    public static final TranslatableText ORIGIN_SPIRIT_ITEM = new TranslatableText("screen.shurlin.origin_spirit_item");

    public static Text getSpiritMeridiansText(SpiritPropertyType type) {
        return new TranslatableText("screen.shurlin.spirit_meridians_" + type.name().toLowerCase(Locale.ROOT));
    }

    public static Text getSpiritText(SpiritPropertyType type) {
        return new TranslatableText("type.shurlin." + type.name());
    }

    public static Text getCulBase(int ii) {
        return new TranslatableText("screen.shurlin.cul_base" + ii);
    }

    public static Text getAbility4(int id, WithLevel withLevel) {
        if (id == 0)
            return new TranslatableText("level.shurlin.consciousness_" + ((ConsciousnessLevel) withLevel).name().toLowerCase(Locale.ROOT));
        else {
            String s = ((SkillAttainments) withLevel).name().toLowerCase(Locale.ROOT), s1 = "";
            switch (id) {
                case 1 -> s1 = "al";
                case 2 -> s1 = "wf";
                case 3 -> s1 = "rn";
            }
            return new TranslatableText("level.shurlin.skill_" + s + '_' + s1);
        }
    }

    public static Text getSpiritRoot(SpiritRootLevel level) {
        return new TranslatableText("level.shurlin.root_" + level.name().toLowerCase(Locale.ROOT));
    }

}
