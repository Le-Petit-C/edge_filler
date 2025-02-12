package lpctools.tools.fillingassistant;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

import java.util.HashSet;

public class HandRestock {
    public static void restock(){
        if(!Data.enabled()) return;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        PlayerInventory inventory = player.getInventory();
        if(isStackOk(inventory.getMainHandStack())) return;
        int i;
        for (i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if(!isStackOk(stack)) continue;
            ClientPlayerInteractionManager itm = MinecraftClient.getInstance().interactionManager;
            if(itm == null) break;
            if(i < 9) inventory.selectedSlot = i;
            else itm.clickSlot(player.currentScreenHandler.syncId, i, inventory.selectedSlot, SlotActionType.SWAP, player);
            break;
        }
        if(i >= inventory.size())
            Data.disableTool(" 可用填充物耗尽");
    }
    private static boolean isStackOk(ItemStack stack){
        if(stack.isEmpty()) return false;
        HashSet<String> placeableItems = FillingAssistant.getPlaceableItemIds();
        if(placeableItems == null) return false;
        return placeableItems.contains(stack.getItem().toString());
    }
}
