package xyz.shurlin.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import xyz.shurlin.inventory.SquareInventory;

import java.util.Map;
import java.util.Set;

public class WeaponForgingTableRecipe extends CustomRecipe<SquareInventory> {
    final int width;
    final int height;
    final DefaultedList<Ingredient> input;

    public WeaponForgingTableRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> input, ItemStack output) {
        super(RecipeTypes.WEAPON_FORGING, id, group, output);
        this.width = width;
        this.height = height;
        this.input = input;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    public boolean fits(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    public boolean matches(SquareInventory inventory, World world) {
        for(int i = 0; i <= inventory.getWidth() - this.width; ++i) {
            for(int j = 0; j <= inventory.getHeight() - this.height; ++j) {
                if (this.matchesPattern(inventory, i, j, true)) {
                    return true;
                }

                if (this.matchesPattern(inventory, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack craft(SquareInventory inventory) {
        return this.getOutput().copy();
    }

    private boolean matchesPattern(SquareInventory inv, int offsetX, int offsetY, boolean flipped) {
        for(int i = 0; i < inv.getWidth(); ++i) {
            for(int j = 0; j < inv.getHeight(); ++j) {
                int k = i - offsetX;
                int l = j - offsetY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (flipped) {
                        ingredient = this.input.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.input.get(k + l * this.width);
                    }
                }

                if (!ingredient.test(inv.getStack(i + j * inv.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }

    static Map<String, Ingredient> readSymbols(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    private static int findFirstSymbol(String line) {
        int i;
        for(i = 0; i < line.length() && line.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int findLastSymbol(String pattern) {
        int i;
        for(i = pattern.length() - 1; i >= 0 && pattern.charAt(i) == ' '; --i) {
        }

        return i;
    }

    @VisibleForTesting
    static String[] removePadding(String... pattern) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int m = 0; m < pattern.length; ++m) {
            String string = pattern[m];
            i = Math.min(i, findFirstSymbol(string));
            int n = findLastSymbol(string);
            j = Math.max(j, n);
            if (n < 0) {
                if (k == m) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (pattern.length == l) {
            return new String[0];
        } else {
            String[] strings = new String[pattern.length - l - k];

            for(int o = 0; o < strings.length; ++o) {
                strings[o] = pattern[o + k].substring(i, j + 1);
            }

            return strings;
        }
    }

    static String[] getPattern(JsonArray json) {
        String[] strings = new String[json.size()];
        if (strings.length > 5) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 5 is maximum");
        } else if (strings.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < strings.length; ++i) {
                String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
                if (string.length() > 5) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, 5 is maximum");
                }

                if (i > 0 && strings[0].length() != string.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                strings[i] = string;
            }

            return strings;
        }
    }

    static DefaultedList<Ingredient> createPatternMatrix(String[] pattern, Map<String, Ingredient> symbols, int width, int height) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet((Iterable)symbols.keySet());
        set.remove(" ");

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String string = pattern[i].substring(j, j + 1);
                Ingredient ingredient = symbols.get(string);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }

                set.remove(string);
                defaultedList.set(j + width * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return defaultedList;
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.WEAPON_FORGING;
    }

    public static class Serializer implements RecipeSerializer<WeaponForgingTableRecipe> {

        @Override
        public WeaponForgingTableRecipe read(Identifier id, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Map<String, Ingredient> map = readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = removePadding(getPattern(JsonHelper.getArray(jsonObject, "pattern")));
            int i = strings[0].length();
            int j = strings.length;
            DefaultedList<Ingredient> defaultedList = createPatternMatrix(strings, map, i, j);
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new WeaponForgingTableRecipe(id, string, i, j, defaultedList, itemStack);
        }

        @Override
        public WeaponForgingTableRecipe read(Identifier id, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < defaultedList.size(); ++k) {
                defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            return new WeaponForgingTableRecipe(id, string, i, j, defaultedList, itemStack);
        }


        @Override
        public void write(PacketByteBuf packetByteBuf, WeaponForgingTableRecipe recipe) {
            packetByteBuf.writeVarInt(recipe.width);
            packetByteBuf.writeVarInt(recipe.height);
            packetByteBuf.writeString(recipe.group);

            for (Ingredient ingredient : recipe.input) {
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(recipe.output);
        }
    }
}
