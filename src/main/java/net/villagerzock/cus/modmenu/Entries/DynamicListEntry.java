package net.villagerzock.cus.modmenu.Entries;

import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.navigation.NavigationDirection;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public class DynamicListEntry<T,E extends AbstractConfigListEntry<T>> extends DynamicEntryListWidget<AbstractConfigEntry<T>> {
    private final BlockListCell<T,E> blockListListEntry;
    public DynamicListEntry(MinecraftClient client,BlockListCell<T,E> blockListListEntry) {
        super(client, 0, 0, 0, 0, null);
        this.blockListListEntry = blockListListEntry;
    }
    @Override
    public boolean isMouseOver(double double_1, double double_2) {
        if (blockListListEntry.isMouseOver(double_1,double_2)){
            System.out.println("Is Mouse Over: " + blockListListEntry.isMouseOver(double_1,double_2));
        }
        return blockListListEntry.isMouseOver(double_1,double_2);
    }
}
