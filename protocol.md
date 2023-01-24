# Lunar Client Transfer Protocol

## Incoming Transfer (server->client)
```java
byte packetType = 0
int ipLength // length of the ip string
byte[] ip // ip string
```
Example: The server sends a request for the client to join `hypixel.net`
```java
// Data: [(byte)0, (int)14, "hypixel.net"]
byte packetType = 0
int ipLength = 11
byte[] ip = "hypixel.net"
```

## Transfer Reply (client->server)
```java
byte packetType = 1
byte accepted // whether the player accepted the transfer request (0 = denied, 1 = accepted)
```
Example: The player didn't accept the transfer request
```java
// Data: [(byte)1, (byte)0]
byte packetType = 1
byte accepted = 0
```

## Ping Servers (server->client)
```java
byte packetType = 2
int servers // total amount of servers
for server:
  int ipLength // length of ip string
  byte[] ipBytes // ip string
```
Example: The server requested the client's ping to `mc.hypixel.net`, `mineplex.com`, and `blockmania.com`
```java
// Data: [(byte)2, (int)3, (int)14, "mc.hypixel.net", (int)12, "mineplex.com", (int)14, blockmania.com]
byte packetType = 2
int servers = 3
int length = 14
byte[] ip = "mc.hypixel.net"
int length = 12
byte[] ip = "mineplex.com"
int length = 14
byte[] ip = "blockmania.com"
```

## Ping Response (client->server)
```java
byte packetType = 3
int servers // total amount of servers
for server:
  int ipLength // length of ip string
  byte[] ipBytes // ip string
  long ping // player's ping to server
```
Example: The client replies with their ping to `mc.hypixel.net`, `mineplex.com`, and `blockmania.com`
```java
// Data: [(byte)2, (int)3, (int)14, "mc.hypixel.net", (long)256, (int)12, "mineplex.com", (long)220, (int)14, blockmania.com, (long)180]
byte packetType = 2
int servers = 3
int length = 14
byte[] ip = "mc.hypixel.net"
long ping = 256
int length = 12
byte[] ip = "mineplex.com"
long ping = 220
int length = 14
byte[] ip = "blockmania.com"
long ping = 180
```