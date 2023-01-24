package me.exro.lunartransfer.listener;

import me.exro.lunartransfer.LunarTransfer;
import me.exro.lunartransfer.events.ping.PingResponseEvent;
import me.exro.lunartransfer.events.transfer.TransferDeniedEvent;
import me.exro.lunartransfer.events.transfer.TransferSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler implements PluginMessageListener {

    private final LunarTransfer lunarTransfer;

    public ResponseHandler(final LunarTransfer lunarTransfer) {
        this.lunarTransfer = lunarTransfer;
    }

    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        // We're only interested in handling messages from "transfer:channel"
        if (!channel.equals(LunarTransfer.TRANSFER_CHANNEL)) return;

        // Create a ByteBuffer from the byte array and get the packet ID (first byte)
        final ByteBuffer buffer = ByteBuffer.wrap(message);
        final byte packet = buffer.get();

        switch (packet) {
            case LunarTransfer.TRANSFER_REPLY: {
                // Get the IP of the server the player was transferring to
                // and the byte which signals success
                final String ip = lunarTransfer.getLastTransfer(player);
                final byte success = buffer.get();

                // Call the appropriate events
                if (success == 1) {
                    Bukkit.getPluginManager().callEvent(new TransferSuccessEvent(player, ip));
                } else {
                    Bukkit.getPluginManager().callEvent(new TransferDeniedEvent(player, ip));
                }

                break;
            }
            case LunarTransfer.PING_REPLY: {
                // Create a new map for the responses and get the number of servers
                final Map<String, Long> responses = new HashMap<>();
                final int totalResponses = buffer.getInt();

                for (int i = 0; i < totalResponses; i++) {
                    // Get the length of the IP
                    final int ipLength = buffer.getInt();

                    // ...and get the IP
                    final byte[] ipArray = new byte[ipLength];
                    buffer.get(ipArray);

                    // Turn the IP bytes back into a String and get the ping
                    final String ip = new String(ipArray, StandardCharsets.UTF_8);
                    final long ping = buffer.getLong();

                    // Then put it into our responses map
                    responses.put(ip, ping);
                }

                // Call the response event with our map
                Bukkit.getPluginManager().callEvent(new PingResponseEvent(player, responses));

                break;
            }
        }
    }
}