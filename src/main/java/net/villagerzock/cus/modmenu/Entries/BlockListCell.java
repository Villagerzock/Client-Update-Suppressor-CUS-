package net.villagerzock.cus.modmenu.Entries;

import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.gui.AbstractConfigScreen;
import me.shedaniel.clothconfig2.gui.entries.AbstractListListEntry;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BlockListCell<T, E extends AbstractConfigListEntry<T>> extends AbstractListListEntry.AbstractListCell<T, BlockListCell<T,E>, BlockListListEntry<T,E>> {
    private final E value;

    public<P extends DynamicEntryListWidget.Entry<P>> BlockListCell(E value, BlockListListEntry<T, E> listListEntry,AbstractConfigScreen screen) {
        super(value.getValue(), listListEntry);
        this.value = value;
        value.setParent(new DynamicListEntry<>(MinecraftClient.getInstance(),this));
        value.setScreen(screen);

    }
    @Override
    public T getValue() {
        return value.getValue();
    }

    @Override
    public Optional<Text> getError() {
        return value.getError();
    }

    @Override
    public int getCellHeight() {
        return value.getItemHeight();
    }

    @Override
    public void render(DrawContext graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        value.render(graphics,index,y,x,entryWidth,entryHeight,mouseX,mouseY,isHovered,delta);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public List<? extends Element> children() {
        return value.children();
    }

    @Override
    public SelectionType getType() {
        return value.getType();
    }
}
