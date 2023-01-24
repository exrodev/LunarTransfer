package me.exro.lunartransfer.events.transfer;

import me.exro.lunartransfer.events.TransferEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PostTransferPlayerEvent extends TransferEvent {

    private static final HandlerList handlers = new HandlerList();

    public PostTransferPlayerEvent(Player player, String ip) {
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
