package me.exro.lunartransfer.events.transfer;

import me.exro.lunartransfer.events.TransferEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class TransferSuccessEvent extends TransferEvent {

    private static final HandlerList handlers = new HandlerList();

    public TransferSuccessEvent(Player player, String ip) {
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
