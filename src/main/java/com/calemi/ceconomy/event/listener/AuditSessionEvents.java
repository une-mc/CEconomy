// com/calemi/ceconomy/event/listener/AuditSessionEvents.java
package com.calemi.ceconomy.event.listener;

import com.calemi.ceconomy.util.AuditLog;
import com.calemi.ceconomy.util.EnderBalance;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public final class AuditSessionEvents {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity p = handler.player;
            AuditLog.write(p.getUuid(), p.getGameProfile().getName(),
                    "LOGIN | balance=" + EnderBalance.get(p));
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity p = handler.player;
            AuditLog.write(p.getUuid(), p.getGameProfile().getName(),
                    "LOGOUT | balance=" + EnderBalance.get(p));
        });
    }
}
