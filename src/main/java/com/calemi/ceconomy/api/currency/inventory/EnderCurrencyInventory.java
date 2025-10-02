package com.calemi.ceconomy.api.currency.inventory;

import com.calemi.ccore.api.IEntityDataSaver;
import com.calemi.ceconomy.config.CEconomyConfig;
import com.calemi.ceconomy.util.AuditLog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class EnderCurrencyInventory extends CurrencyInventory {

    private final IEntityDataSaver player;

    public EnderCurrencyInventory(PlayerEntity player) {
        super(CEconomyConfig.COMMON.enderBankCurrencyCapacity);
        this.player = (IEntityDataSaver) player;
    }

    @Override
    public long getCurrency() {
        return player.getPersistentData().getLong("Currency");
    }

    @Override
    public void setCurrency(long amount) {
        long before = getCurrency();
        player.getPersistentData().putLong("Currency", amount);

        // Log only on the server to avoid client spam
        if (player instanceof ServerPlayerEntity sp) {
            long delta = amount - before;
            String kind = delta >= 0 ? "ENDER_CREDIT" : "ENDER_DEBIT";
            AuditLog.write(
                    sp.getUuid(),
                    sp.getGameProfile().getName(),
                    kind + " | delta=" + delta + " | before=" + before + " | after=" + amount
            );
        }
    }
}
