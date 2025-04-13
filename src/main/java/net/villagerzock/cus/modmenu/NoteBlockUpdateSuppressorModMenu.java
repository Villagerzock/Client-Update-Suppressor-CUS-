package net.villagerzock.cus.modmenu;

import com.google.gson.*;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ModifierKeyCode;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import me.shedaniel.math.Rectangle;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import net.villagerzock.cus.modmenu.Entries.BlockListBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NoteBlockUpdateSuppressorModMenu implements ModMenuApi {
    public static final List<Block> blockList = List.of(Blocks.NOTE_BLOCK,Blocks.TRIPWIRE);
    public static final List<String> serverList = new ArrayList<>();
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        File saveFile = new File(FabricLoader.getInstance().getConfigDir().toFile(),"cus.json");
        return parent ->{
            if (!saveFile.exists()){
                saveFile.getParentFile().mkdirs();
                try {
                    saveFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try (FileReader reader = new FileReader(saveFile)) {
                JsonObject object = new Gson().fromJson(reader,JsonObject.class);
                if (object != null){
                    if (object.has("serverList")){
                        for (JsonElement serverElement : object.get("serverList").getAsJsonArray()){
                            String server = serverElement.getAsString();
                            serverList.add(server);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("title.cus.config"))
                    ;
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.cus.general"));

            general.addEntry(entryBuilder.startStrList(Text.translatable("entry.cus.servers"),serverList)
                    .setDeleteButtonEnabled(true)
                    .build());

            ConfigCategory blocks = builder.getOrCreateCategory(Text.translatable("category.cus.blocks"));

            /*for (Identifier id : Registries.BLOCK.getIds()){
                blockList.add(id.toString());
            }*/
            BlockListBuilder<Block, DropdownBoxEntry<Block>> blockListBuilder = new BlockListBuilder<Block,DropdownBoxEntry<Block>>(Text.translatable("entry.cus.blocks"), (b) -> {
                return entryBuilder.startDropdownMenu(Text.empty(),
                                DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(b),
                                DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject()
                        )
                        .setSelections(Registries.BLOCK.stream().collect(Collectors.toSet()))
                        .setSaveConsumer(block -> {
                        })
                        .build();
            }, Blocks.NOTE_BLOCK,new Rectangle(42,347,42,132))
                    .setDeleteButtonEnabled(true);
            blocks.addEntry(blockListBuilder.build());
            blocks.addEntry(entryBuilder.startDropdownMenu(Text.empty(),
                            DropdownMenuBuilder.TopCellElementBuilder.ofBlockObject(Blocks.NOTE_BLOCK),
                            DropdownMenuBuilder.CellCreatorBuilder.ofBlockObject()
                    )
                    .setSelections(Registries.BLOCK.stream().collect(Collectors.toSet()))
                    .setSaveConsumer(block -> {
                    })
                    .build());
            builder.setSavingRunnable(()->{
                JsonObject object = new JsonObject();
                JsonArray serverListArray = new JsonArray();

                for (String server : serverList){
                    serverListArray.add(server);
                }

                object.add("serverList",serverListArray);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                try (FileWriter fileWriter = new FileWriter(saveFile)) {
                    gson.toJson(object,fileWriter);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return builder.build();
        };
    }
}
