package net.villagerzock.cus.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.villagerzock.cus.modmenu.NoteBlockUpdateSuppressorModMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(AbstractBlock.class)
public class BlockMixin {
    @Inject(method = "getStateForNeighborUpdate",cancellable = true, at = @At("HEAD"))
    public void neighborUpdateInject(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random, CallbackInfoReturnable<BlockState> cir){
        if (world.isClient()){
            if (MinecraftClient.getInstance().getServer() != null)
                if (MinecraftClient.getInstance().getServer().isDedicated())
                    if (NoteBlockUpdateSuppressorModMenu.serverList.contains(MinecraftClient.getInstance().getServer().getServerIp()))
                        if (NoteBlockUpdateSuppressorModMenu.blockList.contains(state.getBlock()))
                            cir.setReturnValue(Blocks.AIR.getDefaultState());
        }
    }
    @Inject(method = "onUse", cancellable = true,at = @At("HEAD"))
    public void onUseInject(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (world.isClient){
            MinecraftClient.getInstance().player.sendMessage(Text.literal(Objects.toString(MinecraftClient.getInstance().getServer())),false);
            if (MinecraftClient.getInstance().getServer() != null){
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Server is not Null"),false);
                if (MinecraftClient.getInstance().getServer().isDedicated()) {
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("Server is Dedicated"),false);
                    if (NoteBlockUpdateSuppressorModMenu.serverList.contains(MinecraftClient.getInstance().getServer().getServerIp())){
                        MinecraftClient.getInstance().player.sendMessage(Text.literal("Server is on the List"),false);
                        if (NoteBlockUpdateSuppressorModMenu.blockList.contains(state.getBlock())){
                            MinecraftClient.getInstance().player.sendMessage(Text.literal("Block is on the List"),false);
                            cir.setReturnValue(ActionResult.FAIL);
                        }
                    }
                }
            }

        }
    }
}
