package me.exro.lunartransfer.listener;

import me.exro.lunartransfer.LunarTransfer;
import me.exro.lunartransfer.events.PingResponseEvent;
import me.exro.lunartransfer.events.TransferDeniedEvent;
import me.exro.lunartransfer.events.TransferSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class PacketReceiver implements PluginMessageListener {
    private final LunarTransfer lunarTransfer;

    public PacketReceiver(final LunarTransfer lunarTransfer) {
        this.lunarTransfer = lunarTransfer;
    }

    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equals(lunarTransfer.TRANSFER_CHANNEL)) return;
        ByteBuffer buffer = ByteBuffer.wrap(message);
        byte packet = buffer.get();

        if (packet == lunarTransfer.TRANSFER_REPLY) {
            String ip = lunarTransfer.getLastTransfer(player);
            byte success = buffer.get();
            if (success == 1) {
                Bukkit.getPluginManager().callEvent(new TransferSuccessEvent(player, ip));
            } else {
                Bukkit.getPluginManager().callEvent(new TransferDeniedEvent(player, ip));
            }
        } else if (packet == lunarTransfer.PING_REPLY) {
            Map<String, Long> responses = new HashMap<>();
            int totalResponses = buffer.getInt();
            for (int i = 0; i < totalResponses; i++) {
                int ipLength = buffer.getInt();
                byte[] ipArray = new byte[ipLength];
                buffer.get(ipArray);
                String ip = new String(ipArray);
                long ping = buffer.getLong();

                responses.put(ip, ping);
            }

            Bukkit.getPluginManager().callEvent(new PingResponseEvent(player, responses));
        }
    }
}