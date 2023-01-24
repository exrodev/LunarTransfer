package me.exro.lunartransfer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PreTransferPlayerEvent extends TransferEvent {
    private static final HandlerList handlers = new HandlerList();

    public PreTransferPlayerEvent(Player player, String ip) {
        super(player, ip);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
