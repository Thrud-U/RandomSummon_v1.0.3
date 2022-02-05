package me.thrudgluttony.randomsummon;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class loot_table_ravagerBossSloth implements LootTable {

    private Collection<ItemStack> items = new ArrayList<ItemStack>();

    double looting_modifier_netherstar;
    double looting_modifier_leather;
    double looting_modifier_popped_chorus_fruit;
    double looting_modifier_end_stone;

    int netherstar_count;
    int leather_count;
    int leather_count_min;
    int leather_count_max;
    int popped_chorus_fruit_count;
    int popped_chorus_fruit_count_min = 2;
    int popped_chorus_fruit_count_max;
    int end_stone_count;
    int end_stone_count_max;

    int randomDrop_endstone;
    int randomDrop_leather;

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {

        int random_modifier = random.nextInt(100);



        int looting_level = context.getLootingModifier();
        if (looting_level > 0) {
            looting_modifier_netherstar = (looting_level * 0.005D) + 0.005D;
            looting_modifier_leather = 100;
            looting_modifier_popped_chorus_fruit = 100;
        } else {
            looting_modifier_netherstar = 0.0025D;
            looting_modifier_leather = 100;
            looting_modifier_popped_chorus_fruit = 100;
        }

        popped_chorus_fruit_count_max = (looting_level * 1) + 4;


        if (context.getKiller() == null) {
            int end_stone_count_max = 3 + (looting_level * 1);
            int leather_count_max = 8 + (looting_level * 2);
            looting_modifier_end_stone = 50;
            int leather_count_min = 4;
        } else {
            int leather_count_min = 2;
        }


        if (random.nextInt() <= looting_modifier_leather) {
            leather_count = random.nextInt(leather_count_min, leather_count_max);
        } else {
            leather_count = 0;
        }

        if (random.nextInt() <= looting_modifier_end_stone) {
            end_stone_count = random.nextInt(0, end_stone_count_max);
        } else {
            end_stone_count = 0;
        }

        if (random.nextInt() <= looting_modifier_popped_chorus_fruit) {
            popped_chorus_fruit_count = random.nextInt(popped_chorus_fruit_count_min, popped_chorus_fruit_count_max);
        } else {
            popped_chorus_fruit_count = 0;
        }

        if (random.nextInt() <= looting_modifier_netherstar) {
            netherstar_count = 1;
        } else {
            netherstar_count = 0;
        }

        ItemStack netherstarIS = null;
        ItemStack leatherIS = null;
        ItemStack popped_chorus_fruitIS = null;
        ItemStack end_stoneIS = null;
        if (netherstar_count >= 1) {
            netherstarIS = new ItemStack(Material.NETHER_STAR, netherstar_count);
        }

        if (leather_count >= 1) {
             leatherIS = new ItemStack(Material.LEATHER, leather_count);
        }

        if (popped_chorus_fruit_count >= 1) {
            popped_chorus_fruitIS = new ItemStack(Material.POPPED_CHORUS_FRUIT, popped_chorus_fruit_count);
        }

        if (end_stone_count >= 1) {
            end_stoneIS = new ItemStack(Material.END_STONE, end_stone_count);
        }
        ItemStack slothenergy = new ItemStack(Material.LIGHT, 1);
        ItemMeta slothMeta = slothenergy.getItemMeta();
        slothMeta.setDisplayName("&l&#FF00FFEnergy of &#BD9168S&#BD9A7Al&#D4B37Fo&#BD9A7At&#BD9168h");
        List<String> loresList = new ArrayList<String>();
        loresList.add("&#A020F0A fragment of the energy used by &#BD9168S&#BD9A7Al&#D4B37Fo&#BD9A7At&#B9168h&#A020F0.");
        slothMeta.setLore(loresList);

        if (netherstar_count > 0) {
            items.add(netherstarIS);
        }
        if (leather_count > 0) {
            items.add(leatherIS);
        }
        if (popped_chorus_fruit_count > 0) {
            items.add(popped_chorus_fruitIS);
        }
        if (end_stone_count > 0) {
            items.add(end_stoneIS);
        }

        return items;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext context) {

    }


    @Override
    public NamespacedKey getKey() {
        return null;
    }
}