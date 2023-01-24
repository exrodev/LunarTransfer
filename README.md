# LunarTransfer
A library that allows you to send Lunar Client's transfer packets to a player. This allows you to make a multi-region server without having to log-in via a subdomain.

## Usage
Create your plugin and create a new `LunarTransfer` like so:
```java
import me.exro.lunartransfer.LunarTransfer;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    public LunarTransfer lunarTransfer;

    @Override
    public void onEnable() {
        lunarTransfer = new LunarTransfer(this);
        //...
    }
}
```

Whenever you would like to transfer a player to a server, you can do it like so:
```java
// player = Player object
// ip = String of the ip you would like the client to connect to
lunarTransfer.transferPlayer(player, ip);
```

There are multiple events that the library will set off when a player will be sent a transfer packet, and when the server receives a response about one. Don't forget to register your listener!
```java
import me.exro.lunartransfer.events.PostTransferPlayerEvent;
import me.exro.lunartransfer.events.PreTransferPlayerEvent;
import me.exro.lunartransfer.events.TransferDeniedEvent;
import me.exro.lunartransfer.events.TransferSuccessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExampleListener implements Listener {
    @EventHandler
    public void onPreTransfer(PreTransferPlayerEvent event) {
        System.out.println("Transferring " + event.getPlayer().getName() + " to " + event.getIp());
        //...
    }

    @EventHandler
    public void onPostTransfer(PostTransferPlayerEvent event) {
        //...
    }

    @EventHandler
    public void onSuccessfulTransfer(TransferSuccessEvent event) {
        //...
    }

    @EventHandler
    public void onDeniedTransfer(TransferDeniedEvent event) {
        //...
    }
}
```

## License
LunarTransfer is licensed under the [MIT](https://choosealicense.com/licenses/mit/) License. Have fun!

## Credits
This wouldn't have been able to be possible without the [Lunar Mapping Project](https://github.com/Lunar-Mapping-Project/mappings).

## Authors
 - [exro](https://github.com/exrodev)
 - [Hydrogen](https://github.com/thehydrogen)