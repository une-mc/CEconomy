package com.calemi.ceconomy.event.listener;

import com.calemi.ccore.api.IEntityDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public final class KeepCurrencyOnDeath {

    public static void register() {
        ServerPlayerEvents.COPY_FROM.register((ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) -> {
            // Only on death respawns
            if (alive) return;

            NbtCompound oldData = ((IEntityDataSaver) oldPlayer).getPersistentData();
            NbtCompound newData = ((IEntityDataSaver) newPlayer).getPersistentData();

            if (oldData != null && oldData.contains("Currency")) {
                long balance = oldData.getLong("Currency");
                newData.putLong("Currency", balance);

                System.out.println("[CEconomy] " + oldPlayer.getGameProfile().getName()
                        + " died. Carrying over EnderBank balance: " + balance);
            } else {
                System.out.println("[CEconomy] " + oldPlayer.getGameProfile().getName()
                        + " died. No Currency field found to carry over.");
            }
        });
    }
}
