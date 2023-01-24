package me.exro.lunartransfer.listener;

import me.exro.lunartransfer.LunarTransfer;
import me.exro.lunartransfer.events.TransferDeniedEvent;
import me.exro.lunartransfer.events.TransferSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PacketReceiver implements PluginMessageListener {
    private final LunarTransfer lunarTransfer;

    public PacketReceiver(final LunarTransfer lunarTransfer) {
        this.lunarTransfer = lunarTransfer;
    }

    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equals(lunarTransfer.TRANSFER_CHANNEL)) return;
        String ip = lunarTransfer.getLastTransfer(player);

        if (message[0] == lunarTransfer.TRANSFER_REPLY) {
            if (message[1] == 1) {
                Bukkit.getPluginManager().callEvent(new TransferSuccessEvent(player, ip));
            } else {
                Bukkit.getPluginManager().callEvent(new TransferDeniedEvent(player, ip));
            }
        }
    }
}
