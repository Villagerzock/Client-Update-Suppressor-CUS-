package net.villagerzock.cus.modmenu.Entries;

import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.AbstractListListEntry;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.navigation.NavigationDirection;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockListListEntry<T, E extends AbstractConfigListEntry<T>> extends AbstractListListEntry<T, BlockListCell<T,E>, BlockListListEntry<T,E>> {
    private final T initialValue;
    public BlockListListEntry(Text fieldName, List<T> value, boolean defaultExpanded, Supplier<Optional<Text[]>> tooltipSupplier, Consumer<List<T>> saveConsumer, Supplier<List<T>> defaultValue, Text resetButtonKey, boolean requiresRestart, boolean deleteButtonEnabled, boolean insertInFront, BiFunction<T, BlockListListEntry<T, E>, BlockListCell<T, E>> createNewCell, T initialValue) {
        super(fieldName, value, defaultExpanded, tooltipSupplier, saveConsumer, defaultValue, resetButtonKey, requiresRestart, deleteButtonEnabled, insertInFront, createNewCell);

        this.initialValue = initialValue;
    }

    public T getInitialValue() {
        return initialValue;
    }

    private Consumer<Element> focusChangedListener = (e) ->{};
    public void setFocusChangedListener(Consumer<Element> focusChangedListener) {
        this.focusChangedListener = focusChangedListener;
    }

    @Override
    public void setFocused(@Nullable Element guiEventListener) {
        super.setFocused(guiEventListener);
        focusChangedListener.accept(guiEventListener);
    }

    @Override
    public BlockListListEntry<T, E> self() {
        return this;
    }
}
