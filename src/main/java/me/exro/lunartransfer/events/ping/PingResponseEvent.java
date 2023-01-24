package me.exro.lunartransfer.events.ping;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;

public class PingResponseEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Map<String, Long> responses;

    public PingResponseEvent(Player player, Map<String, Long> responses) {
        super(player);
        this.responses = responses;
    }

    public Map<String, Long> getResponses() {
        return responses;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
