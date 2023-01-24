package me.exro.lunartransfer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class TransferEvent extends PlayerEvent {
    private final String ip;

    public TransferEvent(Player player, String ip) {
        super(player);
        this.ip = ip;
    }

    public final String getIp() {
        return this.ip;
    }
}
