package me.exro.lunartransfer;

import me.exro.lunartransfer.events.transfer.PostTransferPlayerEvent;
import me.exro.lunartransfer.events.transfer.PreTransferPlayerEvent;
import me.exro.lunartransfer.listener.ResponseHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class LunarTransfer {

    private final JavaPlugin plugin;

    public static final String TRANSFER_CHANNEL = "transfer:channel";

    public static final byte INCOMING_TRANSFER = 0;
    public static final byte TRANSFER_REPLY = 1;
    public static final byte PING_SERVERS = 2;
    public static final byte PING_REPLY = 3;

    private final Map<UUID, String> playerTransfers;

    public LunarTransfer(final JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerTransfers = new HashMap<>();

        // Set up the outgoing and incoming plugin channels on "transfer:channel"
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, TRANSFER_CHANNEL);
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, TRANSFER_CHANNEL, new ResponseHandler(this));
    }

    /**
     * Sends a transfer request to the client for the specified IP.
     *
     * @param player the Bukkit Player object
     * @param ip the IP of the server you wish to transfer the client to
     */
    public void transferPlayer(final Player player, final String ip) {
        // 5 bytes for the packet type and length, plus the length of the IP
        final byte[] ipBytes = ip.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer buffer = ByteBuffer.allocate(5 + ipBytes.length);

        buffer.put(INCOMING_TRANSFER); // Insert the packet type
        buffer.putInt(ip.length()); // The length of the IP string
        buffer.put(ipBytes); // and the IP bytes

        // Turn the ByteBuffer back into a byte array
        final byte[] data = buffer.array();

        // Store the pending transfer
        playerTransfers.put(player.getUniqueId(), ip);

        // Call the corresponding events and send the request on "transfer:channel"
        Bukkit.getPluginManager().callEvent(new PreTransferPlayerEvent(player, ip));
        player.sendPluginMessage(plugin, TRANSFER_CHANNEL, data);
        Bukkit.getPluginManager().callEvent(new PostTransferPlayerEvent(player, ip));
    }

    /**
     * Sends a ping request to the client for the specified IPs.
     *
     * @param player the Bukkit Player object
     * @param ips array of the IPs you want to ping
     */
    public void getPing(final Player player, String... ips) {
        getPing(player, Arrays.asList(ips));
    }

    /**
     * Sends a ping request to the client for the specified IPs.
     *
     * @param player the Bukkit Player object
     * @param ips list of the IPs you want to ping
     */
    public void getPing(final Player player, List<String> ips) {
        // Lunar Client doesn't support sending more than 10 IPs
        if (ips.size() > 10) {
            ips = ips.subList(0, 9);
        }

        // Set up the buffer size
        int bufferSize = 5;
        for (String ip : ips) {
            bufferSize += 4;
            bufferSize += ip.length();
        }

        // Create a ByteBuffer with the size we set above
        final ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

        buffer.put(PING_SERVERS); // Add packet type
        buffer.putInt(ips.size()); // and the size of the array

        for (String ip : ips) {
            buffer.putInt(ip.length()); // For each IP, add the length of the IP
            buffer.put(ip.getBytes(StandardCharsets.UTF_8)); // and the actual IP string
        }

        // Turn the ByteBuffer back into a byte array
        final byte[] data = buffer.array();

        // Send the ping request on "transfer:channel"
        player.sendPluginMessage(plugin, TRANSFER_CHANNEL, data);
    }

    public String getLastTransfer(Player player) {
        return getLastTransfer(player.getUniqueId());
    }

    public String getLastTransfer(UUID uuid) {
        return playerTransfers.get(uuid);
    }
}
