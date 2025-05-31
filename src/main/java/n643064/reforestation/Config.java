package n643064.reforestation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.level.Level.OVERWORLD;

public record Config(
        int leavesHarvestingMaxDepth,
        int harvesterItemPickupRange,
        HashMap<String, String> frameToCombMap
)
{

    public static Config CONFIG = new Config(
            8,
            8,
        new HashMap<>(Map.of(
                "reforestation:amethyst_frame", "reforestation:amethyst_comb",
                "reforestation:coal_frame", "reforestation:coal_comb",
                "reforestation:copper_frame", "reforestation:copper_comb",
                "reforestation:diamond_frame", "reforestation:diamond_comb",
                "reforestation:emerald_frame", "reforestation:emerald_comb",
                "reforestation:gold_frame", "reforestation:gold_comb",
                "reforestation:iron_frame", "reforestation:iron_comb",
                "reforestation:lapis_frame", "reforestation:lapis_comb",
                "reforestation:redstone_frame", "reforestation:redstone_comb"
        ))
    );

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();
    static final String CONFIG_PATH = "config" + File.separator + "reforestation.json";

    public static final HashMap<Item, Item> CACHED_FRAME_TO_COMB = new HashMap<>();

    public static void generateCaches(MinecraftServer server, @Nullable ServerLevel level)
    {
        if (level != null && level.dimension() != OVERWORLD)
            return;

        CACHED_FRAME_TO_COMB.clear();
        CONFIG.frameToCombMap.forEach((s, s2) ->
        {
            final Item k = BuiltInRegistries.ITEM.get(ResourceLocation.parse(s));
            final Item v = BuiltInRegistries.ITEM.get(ResourceLocation.parse(s2));
            CACHED_FRAME_TO_COMB.put(k, v);
        });

    }

    static void onLoad()
    {

    }

    public static void create() throws IOException
    {
        Path p = Path.of("config");
        if (Files.exists(p))
        {
            if (Files.isDirectory(p))
            {
                FileWriter writer = new FileWriter(CONFIG_PATH);
                writer.write(GSON.toJson(CONFIG));
                writer.flush();
                writer.close();
            }
        } else
        {
            Files.createDirectory(p);
            create();
        }
    }

    public static <T> T read(String path, Class<T> clazz) throws IOException
    {
        final FileReader reader = new FileReader(path);
        T t = GSON.fromJson(reader, clazz);
        reader.close();
        return t;
    }

    public static void setup()
    {
        try
        {
            if (Files.exists(Path.of(CONFIG_PATH)))
            {
                CONFIG = read(CONFIG_PATH, Config.class);
            } else
            {
                create();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        onLoad();
    }
}
