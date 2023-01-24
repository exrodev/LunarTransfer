package me.exro.lunartransfer;

import me.exro.lunartransfer.events.PostTransferPlayerEvent;
import me.exro.lunartransfer.events.PreTransferPlayerEvent;
import me.exro.lunartransfer.listener.PacketReceiver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class LunarTransfer {
    private final JavaPlugin plugin;

    public final String TRANSFER_CHANNEL = "transfer:channel";
    public final byte INCOMING_TRANSFER = 0;
    public final byte TRANSFER_REPLY = 1;
    public final byte PING_SERVERS = 2;
    public final byte PING_REPLY = 3;

    private final Map<UUID, String> playerTransfers;

    public LunarTransfer(final JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerTransfers = new HashMap<>();
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, TRANSFER_CHANNEL);
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, TRANSFER_CHANNEL, new PacketReceiver(this));
    }

    public void transferPlayer(final Player player, final String ip) {
        final ByteBuffer buffer = ByteBuffer.allocate(5 + ip.length());
        buffer.put(INCOMING_TRANSFER); // packet type
        buffer.putInt(ip.length());
        buffer.put(ip.getBytes(StandardCharsets.UTF_8));
        final byte[] data = buffer.array();

        playerTransfers.put(player.getUniqueId(), ip);

        Bukkit.getPluginManager().callEvent(new PreTransferPlayerEvent(player, ip));
        player.sendPluginMessage(plugin, TRANSFER_CHANNEL, data);
        Bukkit.getPluginManager().callEvent(new PostTransferPlayerEvent(player, ip));
    }

    public String getLastTransfer(Player player) {
        return getLastTransfer(player.getUniqueId());
    }

    public String getLastTransfer(UUID uuid) {
        return playerTransfers.get(uuid);
    }
}
