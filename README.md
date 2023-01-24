# LunarTransfer
This allows you to send transfer packets to a player who is using Lunar Client,
in the way the Lunar Network operates. 

## Usage
You can add the library to your `pom.xml` like so:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.exrodev</groupId>
        <artifactId>LunarTransfer</artifactId>
        <version>-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Create your plugin and create a new `LunarTransfer` like so:
```java
import me.exro.lunartransfer.LunarTransfer;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    
    private LunarTransfer lunarTransfer;

    @Override
    public void onEnable() {
        lunarTransfer = new LunarTransfer(this);
        //...
    }
}
```

Whenever you would like to transfer a player to a server, you can do it like so:
```java
// player = the Bukkit Player object
// ip = String of the IP you would like the client to connect to
lunarTransfer.transferPlayer(player, ip);
```

There are multiple events that the library will set off when a player will is sent a transfer packet, and when the server receives a response about one. Don't forget to register your listener!

```java
public class ExampleListener implements Listener {

    @EventHandler
    public void onPreTransfer(PreTransferPlayerEvent event) {
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

---

In addition, Lunar Client supports asking clients to ping multiple IPs and get their result.
Here's how you can implement that using LunarTransfer:
```java
lunarTransfer.getPing(player, "hypixel.net", "mineplex.com");
```

Then, listen for the `PingResponseEvent` to get your result.
```java
public class ExampleListener implements Listener {
    
    @EventHandler
    public void onPingResponse(PingResponseEvent event) {
        //...
    }
}
```

## License
LunarTransfer is licensed under the [MIT License](https://choosealicense.com/licenses/mit/). Have fun!

## Credits
This wouldn't have been able to be possible without the [Lunar Mapping Project](https://github.com/Lunar-Mapping-Project/mappings).

## Authors
 - [exro](https://github.com/exrodev)
 - [Hydrogen](https://github.com/thehydrogen)