// com/calemi/ceconomy/util/EnderBalance.java
package com.calemi.ceconomy.util;

import com.calemi.ccore.api.IEntityDataSaver;
import net.minecraft.server.network.ServerPlayerEntity;

public final class EnderBalance {
    public static long get(ServerPlayerEntity p) {
        return ((IEntityDataSaver)p).getPersistentData().getLong("Currency");
    }
    public static void set(ServerPlayerEntity p, long value) {
        ((IEntityDataSaver)p).getPersistentData().putLong("Currency", value);
    }
    private EnderBalance() {}
}
