package net.villagerzock.cus.modmenu.Entries;

import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import me.shedaniel.clothconfig2.impl.builders.AbstractListBuilder;
import me.shedaniel.math.Rectangle;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockListBuilder<T, E extends AbstractConfigListEntry<T>> extends AbstractListBuilder<T,BlockListListEntry<T,E>,BlockListBuilder<T,E>> {
    private final Function<T,E> createEntry;
    private T defaultValue;
    private final Rectangle defaultRectangle;
    public BlockListBuilder(Text fieldNameKey, Function<T,E> createEntry, T initialValue, Rectangle defaultRectangle) {
        super(Text.translatable("text.cloth-config.reset_value"), fieldNameKey);
        this.createEntry = createEntry;
        value = new ArrayList<>();
        this.defaultValue = initialValue;
        this.defaultRectangle = defaultRectangle;
    }



    public BlockListCell<T,E> createCell(T t, BlockListListEntry<T,E> entry){
        T value = t;
        if (value == null){
            value = entry.getInitialValue();
        }
        E constructedValue = createEntry.apply(value);
        constructedValue.setBounds(defaultRectangle);
        return new BlockListCell<>(constructedValue,entry,entry.getConfigScreen());
    }
    @Override
    public @NotNull BlockListListEntry<T,E> build() {
        return new BlockListListEntry<T,E>(getFieldNameKey(),value,isExpanded(),()-> getTooltipSupplier().apply(value),getSaveConsumer(),getDefaultValue(),getResetButtonKey(),requireRestart,isDeleteButtonEnabled(),isInsertInFront(), this::createCell,defaultValue);
    }
}
