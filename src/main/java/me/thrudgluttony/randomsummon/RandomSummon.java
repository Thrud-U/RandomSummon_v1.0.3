package me.thrudgluttony.randomsummon;

import java.util.*;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.plugin.java.JavaPlugin;

import static net.md_5.bungee.api.ChatColor.*;
import static org.bukkit.entity.Fox.Type.SNOW;
import static org.bukkit.entity.MushroomCow.Variant.RED;
import static org.bukkit.entity.MushroomCow.Variant.BROWN;

public final class RandomSummon extends JavaPlugin implements Listener {

    //Random rnd = new Random();
    /*List<EntityType> mobsList = Arrays.asList(
            EntityType.CREEPER,
            EntityType.SKELETON,
            EntityType.WITHER_SKELETON,
            EntityType.STRAY,
            EntityType.SPIDER,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.HUSK,
            EntityType.SLIME,
            EntityType.GHAST,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ENDERMAN,
            EntityType.CAVE_SPIDER,
            EntityType.BLAZE,
            EntityType.MAGMA_CUBE,
            EntityType.BAT,
            EntityType.WITCH,
            EntityType.ENDERMITE,
            EntityType.DROWNED,
            EntityType.PILLAGER,
            EntityType.RAVAGER,
            EntityType.ZOGLIN,
            EntityType.PIG,
            EntityType.SHEEP,
            EntityType.COW,
            EntityType.CHICKEN,
            EntityType.WOLF,
            EntityType.MUSHROOM_COW,
            EntityType.OCELOT,
            EntityType.CAT,
            EntityType.HORSE,
            EntityType.DONKEY,
            EntityType.MULE,
            EntityType.ZOMBIE_HORSE,
            EntityType.SKELETON_HORSE,
            EntityType.RABBIT,
            EntityType.POLAR_BEAR,
            EntityType.LLAMA,
            EntityType.VILLAGER,
            EntityType.PARROT,
            EntityType.TURTLE,
            EntityType.FOX,
            EntityType.PANDA,
            EntityType.WANDERING_TRADER,
            EntityType.TRADER_LLAMA,
            EntityType.BEE,
            EntityType.STRIDER,
            EntityType.GOAT
    );*/
    @Override
    public void onEnable() {
        // Plugin startup logic

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Random random = new Random();

        System.out.println("RandomSummon has started.");

    }

    Ravager ravBossSloth;

    @EventHandler
    public void slothDeath (EntityDeathEvent event) {
        if (!event.getEntityType().equals(ravBossSloth)) {
            return;
        }
        if (event.getEntity().getKiller() == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        Location location = entity.getLocation();
        Player player = entity.getKiller();
        int looting_mod = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

        LootContext.Builder builder = new LootContext.Builder(location);
        builder.lootedEntity(event.getEntity());
        builder.lootingModifier(looting_mod);
        builder.killer(player);
        LootContext lootContext = builder.build();

        loot_table_ravagerBossSloth slothLoot = new loot_table_ravagerBossSloth();
        Collection<ItemStack> drops = slothLoot.populateLoot(new Random(), lootContext);
        ArrayList<ItemStack> items = (ArrayList<ItemStack>) drops;

        event.getDrops().clear();

        for (int a = 0; a < 4; a++) {
            if (items.get(a).getAmount() > 0) {
                location.getWorld().dropItemNaturally(location, items.get(a));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player p = (Player) sender;
        if(p.hasPermission("RandomSummon.srmPerms")) {
            p.sendMessage(GREEN + "Random mob summoned successfully");

            int upperbound = getConfig().getInt("RandomUpperbound");
            Random random = new Random();
            int rnd = random.nextInt(0, upperbound);

            Location loc = p.getLocation();
            //World pworld = p.getWorld();
            double rotation = (p.getLocation().getYaw() - 180) % 360;

            if (rotation < 0) {
                rotation += 360.0;
            }
            if (0 <= rotation && rotation < 22.5) {
                loc.add(0, 0, -5);
            } else if (22.5 <= rotation && rotation < 67.5) {
                loc.add(3, 0, -3);
            } else if (67.5 <= rotation && rotation < 112.5) {
                loc.add(5, 0, 0);
            } else if (112.5 <= rotation && rotation < 157.5) {
                loc.add(3, 0, 3);
            } else if (157.5 <= rotation && rotation < 202.5) {
                loc.add(0, 0, 5);
            } else if (202.5 <= rotation && rotation < 247.5) {
                loc.add(-3, 0, 3);
            } else if (247.5 <= rotation && rotation < 292.5) {
                loc.add(-5, 0, 0);
            } else if (292.5 <= rotation && rotation < 337.5) {
                loc.add(-3, 0, -3);
            } else if (337.5 <= rotation && rotation < 360.0) {
                loc.add(0, 0, -5);
            } else {
                loc.add(0, 0, 5);
            }

            while (
                    !p.getWorld().getBlockAt(loc).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 1, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 2, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 0, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 0, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 0, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 0, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 0, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 0, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 0, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 0, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 1, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 1, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 1, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 1, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 1, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 1, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 1, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 1, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 2, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 2, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 2, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(1, 2, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(0, 2, -1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 2, 0)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 2, 1)).isEmpty() &&
                    !p.getWorld().getBlockAt(loc.add(-1, 2, -1)).isEmpty()

            ) {
                loc.add(0, 1, 0);

                /*for(int i = 0; i <= 26; i++) {
                    if(!p.getWorld().getBlockAt(loc.add(1 , 0, 0)).isEmpty() && )
                }*/
            }

            if(cmd.getName().equalsIgnoreCase("summonrandommob") || cmd.getName().equalsIgnoreCase("srm")){
                if(rnd <= getConfig().getInt("weightCreeper")) {
                    p.getWorld().spawnEntity(loc, EntityType.CREEPER);
                } else if(rnd >= getConfig().getInt("weightCreeper") && rnd <= getConfig().getInt("weightChargedCreeper")) {
                    Creeper creeper = (Creeper) p.getWorld().spawnEntity(loc, EntityType.CREEPER);
                    creeper.setPowered(true);
                } else if(rnd >= getConfig().getInt("weightChargedCreeper") && rnd <= getConfig().getInt("weightSkeleton")) {
                    p.getWorld().spawnEntity(loc, EntityType.SKELETON);
                } else if(rnd >= getConfig().getInt("weightSkeleton") + 1 && rnd <= getConfig().getInt("weightWitherSkeleton")) {
                    p.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                } else if(rnd >= getConfig().getInt("weightWitherSkeleton") + 1 && rnd <= getConfig().getInt("weightStray")) {
                    p.getWorld().spawnEntity(loc, EntityType.STRAY);
                } else if(rnd >= getConfig().getInt("weightStray") + 1 && rnd <= getConfig().getInt("weightSpider")) {
                    p.getWorld().spawnEntity(loc, EntityType.SPIDER);
                } else if(rnd >= getConfig().getInt("weightSpider") + 1 && rnd <= getConfig().getInt("weightSpiderJockey")) {
                    Spider spider = (Spider) p.getWorld().spawnEntity(loc, EntityType.SPIDER);
                    Skeleton skeleton = (Skeleton) p.getWorld().spawnEntity(loc, EntityType.SKELETON);
                    spider.addPassenger(skeleton);
                } else if(rnd >= getConfig().getInt("weightSpiderJockey") + 1 && rnd <= getConfig().getInt("weightZombie")) {
                    p.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                } else if(rnd >= getConfig().getInt("weightZombie") + 1 && rnd <= getConfig().getInt("weightZombieVillager")) {
                    p.getWorld().spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);
                } else if(rnd >= getConfig().getInt("weightZombieVillager") + 1 && rnd <= getConfig().getInt("weightHusk")) {
                    p.getWorld().spawnEntity(loc, EntityType.HUSK);
                } else if(rnd >= getConfig().getInt("weightHusk") + 1 && rnd <= getConfig().getInt("weightChickenJockey")) {
                    Chicken chicken = (Chicken) p.getWorld().spawnEntity(loc, EntityType.CHICKEN);
                    Zombie bzombie = (Zombie) p.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    bzombie.setBaby();
                    chicken.addPassenger(bzombie);
                } else if(rnd >= getConfig().getInt("weightChickenJockey") + 1 && rnd <= getConfig().getInt("weightSlime")) {
                    p.getWorld().spawnEntity(loc, EntityType.SLIME);
                } else if(rnd >= getConfig().getInt("weightSlime") + 1 && rnd <= getConfig().getInt("weightGhast")) {
                    p.getWorld().spawnEntity(loc, EntityType.GHAST);
                } else if(rnd >= getConfig().getInt("weightGhast") + 1 && rnd <= getConfig().getInt("weightZombiePigman")) {
                    p.getWorld().spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);
                } else if(rnd >= getConfig().getInt("weightZombiePigman") + 1 && rnd <= getConfig().getInt("weightChickenJockey2")) {
                    Chicken chicken = (Chicken) p.getWorld().spawnEntity(loc, EntityType.CHICKEN);
                    PigZombie bzombiepigman = (PigZombie) p.getWorld().spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);
                    bzombiepigman.setBaby();
                    chicken.addPassenger(bzombiepigman);
                } else if(rnd >= getConfig().getInt("weightChickenJockey2") + 1 && rnd <= getConfig().getInt("weightEnderman")) {
                    p.getWorld().spawnEntity(loc, EntityType.ENDERMAN);
                } else if(rnd >= getConfig().getInt("weightEnderman") + 1 && rnd <= getConfig().getInt("weightCaveSpider")) {
                    p.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
                } else if(rnd >= getConfig().getInt("weightCaveSpider") + 1 && rnd <= getConfig().getInt("weightBlaze")) {
                    p.getWorld().spawnEntity(loc, EntityType.BLAZE);
                } else if(rnd >= getConfig().getInt("weightBlaze") + 1 && rnd <= getConfig().getInt("weightMagmaCube")) {
                    p.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
                } else if(rnd >= getConfig().getInt("weightMagmaCube") + 1 && rnd <= getConfig().getInt("weightRavagerBossSloth")) {
                    Ravager ravBossSloth = (Ravager) p.getWorld().spawnEntity(loc, EntityType.RAVAGER);
                    ravBossSloth.setCustomName("&l&#BD9168S&#BD9A7Al&#D4B37Fo&#BD9A7At&#BD9168h");
                    ravBossSloth.setCustomNameVisible(true);
                    ravBossSloth.setGlowing(true);
                    ravBossSloth.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300);
                    ravBossSloth.setHealth(300);
                    ravBossSloth.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(64);
                    ravBossSloth.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1f);
                    ravBossSloth.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
                    ravBossSloth.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, 2));


                    EnderCrystal enderCrystal = (EnderCrystal) p.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
                    enderCrystal.setGlowing(true);
                    ravBossSloth.addPassenger(enderCrystal);

                } else if(rnd >= getConfig().getInt("weightRavagerBossSloth") + 1 && rnd <= getConfig().getInt("weightBat")) {
                    p.getWorld().spawnEntity(loc, EntityType.BAT);
                } else if(rnd >= getConfig().getInt("weightBat") + 1 && rnd <= getConfig().getInt("weightWitch")) {
                    p.getWorld().spawnEntity(loc, EntityType.WITCH);
                } else if(rnd >= getConfig().getInt("weightWitch") + 1 && rnd <= getConfig().getInt("weightEndermite")) {
                    p.getWorld().spawnEntity(loc, EntityType.ENDERMITE);
                } else if(rnd >= getConfig().getInt("weightEndermite") + 1 && rnd <= getConfig().getInt("weightDrowned")) {
                    p.getWorld().spawnEntity(loc, EntityType.DROWNED);
                } else if(rnd >= getConfig().getInt("weightDrowned") + 1 && rnd <= getConfig().getInt("weightPillager")) {
                    p.getWorld().spawnEntity(loc, EntityType.PILLAGER);
                } else if(rnd >= getConfig().getInt("weightPillager") + 1 && rnd <= getConfig().getInt("weightRavager")) {
                    p.getWorld().spawnEntity(loc, EntityType.RAVAGER);
                } else if(rnd >= getConfig().getInt("weightRavager") + 1 && rnd <= getConfig().getInt("weightZoglin")) {
                    p.getWorld().spawnEntity(loc, EntityType.ZOGLIN);
                } else if(rnd >= getConfig().getInt("weightZoglin") + 1 && rnd <= getConfig().getInt("weightPig")) {
                    p.getWorld().spawnEntity(loc, EntityType.PIG);
                } else if(rnd >= getConfig().getInt("weightPig") + 1 && rnd <= getConfig().getInt("weightSheep")) {
                    p.getWorld().spawnEntity(loc, EntityType.SHEEP);
                } else if(rnd >= getConfig().getInt("weightSheep") + 1 && rnd <= getConfig().getInt("weightCow")) {
                    p.getWorld().spawnEntity(loc, EntityType.COW);
                } else if(rnd >= getConfig().getInt("weightCow") + 1 && rnd <= getConfig().getInt("weightChicken")) {
                    p.getWorld().spawnEntity(loc, EntityType.CHICKEN);
                } else if(rnd >= getConfig().getInt("weightChicken") + 1 && rnd <= getConfig().getInt("weightWolf")) {
                    p.getWorld().spawnEntity(loc, EntityType.WOLF);
                } else if(rnd >= getConfig().getInt("weightWolf") + 1 && rnd <= getConfig().getInt("weightRedMooshroom")) {
                    MushroomCow redmooshroom = (MushroomCow) p.getWorld().spawnEntity(loc, EntityType.MUSHROOM_COW);
                    redmooshroom.setVariant(RED);
                } else if(rnd >= getConfig().getInt("weightRedMooshroom") + 1 && rnd <= getConfig().getInt("weightBrownMooshroom")) {
                    MushroomCow brownmooshroom = (MushroomCow) p.getWorld().spawnEntity(loc, EntityType.MUSHROOM_COW);
                    brownmooshroom.setVariant(BROWN);
                } else if(rnd >= getConfig().getInt("weightBrownMooshroom") + 1 && rnd <= getConfig().getInt("weightOcelot")) {
                    p.getWorld().spawnEntity(loc, EntityType.OCELOT);
                } else if(rnd >= getConfig().getInt("weightOcelot") + 1 && rnd <= getConfig().getInt("weightCat")) {
                    p.getWorld().spawnEntity(loc, EntityType.CAT);
                } else if(rnd >= getConfig().getInt("weightCat") + 1 && rnd <= getConfig().getInt("weightHorse")) {
                    p.getWorld().spawnEntity(loc, EntityType.HORSE);
                } else if(rnd >= getConfig().getInt("weightHorse") + 1 && rnd <= getConfig().getInt("weightDonkey")) {
                    p.getWorld().spawnEntity(loc, EntityType.DONKEY);
                } else if(rnd >= getConfig().getInt("weightDonkey") + 1 && rnd <= getConfig().getInt("weightMule")) {
                    p.getWorld().spawnEntity(loc, EntityType.MULE);
                } else if(rnd >= getConfig().getInt("weightMule") + 1 && rnd <= getConfig().getInt("weightZombieHorse")) {
                    p.getWorld().spawnEntity(loc, EntityType.ZOMBIE_HORSE);
                } else if(rnd >= getConfig().getInt("weightZombieHorse") + 1 && rnd <= getConfig().getInt("weightSkeletonHorse")) {
                    p.getWorld().spawnEntity(loc, EntityType.SKELETON_HORSE);
                } else if(rnd >= getConfig().getInt("weightSkeletonHorse") + 1 && rnd <= getConfig().getInt("weightRabbit")) {
                    p.getWorld().spawnEntity(loc, EntityType.RABBIT);
                } else if(rnd >= getConfig().getInt("weightRabbit") + 1 && rnd <= getConfig().getInt("weightPolarBear")) {
                    p.getWorld().spawnEntity(loc, EntityType.POLAR_BEAR);
                } else if(rnd >= getConfig().getInt("weightPolarBear") + 1 && rnd <= getConfig().getInt("weightLlama")) {
                    p.getWorld().spawnEntity(loc, EntityType.LLAMA);
                } else if(rnd >= getConfig().getInt("weightLlama") + 1 && rnd <= getConfig().getInt("weightVillager")) {
                    p.getWorld().spawnEntity(loc, EntityType.VILLAGER);
                } else if(rnd >= getConfig().getInt("weightVillager") + 1 && rnd <= getConfig().getInt("weightParrot")) {
                    p.getWorld().spawnEntity(loc, EntityType.PARROT);
                } else if(rnd >= getConfig().getInt("weightParrot") + 1 && rnd <= getConfig().getInt("weightTurtle")) {
                    p.getWorld().spawnEntity(loc, EntityType.TURTLE);
                } else if(rnd >= getConfig().getInt("weightTurtle") + 1 && rnd <= getConfig().getInt("weightFox")) {
                    Fox fox = (Fox) p.getWorld().spawnEntity(loc, EntityType.FOX);
                    fox.setFoxType(Fox.Type.RED);
                } else if(rnd >= getConfig().getInt("weightFox") + 1 && rnd <= getConfig().getInt("weightArcticFox")) {
                    Fox fox = (Fox) p.getWorld().spawnEntity(loc, EntityType.FOX);
                    fox.setFoxType(SNOW);
                } else if(rnd >= getConfig().getInt("weightArcticFox") + 1 && rnd <= getConfig().getInt("weightPanda")) {
                    p.getWorld().spawnEntity(loc, EntityType.PANDA);
                } else if(rnd >= getConfig().getInt("weightPanda") + 1 && rnd <= getConfig().getInt("weightWanderingTrader")) {
                    p.getWorld().spawnEntity(loc, EntityType.WANDERING_TRADER);
                } else if(rnd >= getConfig().getInt("weightWanderingTrader") + 1 && rnd <= getConfig().getInt("weightTraderLlama")) {
                    p.getWorld().spawnEntity(loc, EntityType.TRADER_LLAMA);
                } else if(rnd >= getConfig().getInt("weightTraderLlama") + 1 && rnd <= getConfig().getInt("weightBee")) {
                    p.getWorld().spawnEntity(loc, EntityType.BEE);
                } else if(rnd >= getConfig().getInt("weightBee") + 1 && rnd <= getConfig().getInt("weightStrider")) {
                    p.getWorld().spawnEntity(loc, EntityType.STRIDER);
                } else if(rnd >= getConfig().getInt("weightStrider") + 1 && rnd <= getConfig().getInt("weightStriderJockey")) {
                    Strider strider = (Strider) p.getWorld().spawnEntity(loc, EntityType.STRIDER);
                    PigZombie zombiepigman = (PigZombie) p.getWorld().spawnEntity(loc, EntityType.ZOMBIFIED_PIGLIN);
                    zombiepigman.setBaby();
                    strider.addPassenger(zombiepigman);
                } else if(rnd >= getConfig().getInt("weightStriderJockey") + 1 && rnd <= getConfig().getInt("weightGoat")) {
                    p.getWorld().spawnEntity(loc, EntityType.GOAT);
                } else  {
                    System.out.println("Something went wrong, rnd = " + rnd);

                }
            } else {
            p.sendMessage(ChatColor.RED.toString() + BOLD + "You do not have the required permissions to run this command.");
            }

        }
        return true; //???
    };
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RandomSummon has stopped.");
    }
}
