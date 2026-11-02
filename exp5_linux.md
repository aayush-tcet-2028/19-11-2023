# Experiment 5: Study of Linux Networking Commands

## Aim

To study Linux networking commands for inspecting interfaces, testing connectivity, checking name resolution, and examining routing and neighbor information.

## Environment

Ubuntu running under Windows Subsystem for Linux (WSL), as indicated by the supplied terminal transcript. Outputs below are captured observations from that transcript and may differ on another system.

## Commands, Use Cases, and Observed Outputs

### 1. Display Network Interface Configuration

**Command:**

```bash
ifconfig
```

**Purpose:** Displays network interfaces, IPv4/IPv6 addresses, MAC addresses, MTU, and packet statistics.

**Use case:** Use it to check the assigned IP address and inspect interface traffic or error counters.

**Observed output:**

```text
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1280
        inet 172.29.92.98  netmask 255.255.240.0  broadcast 172.29.95.255
        inet6 fe80::215:5dff:fe87:59d9  prefixlen 64  scopeid 0x20<link>
        ether 00:15:5d:87:59:d9  txqueuelen 1000  (Ethernet)
        RX packets 493  bytes 307623 (307.6 KB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 209  bytes 16612 (16.6 KB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        inet6 ::1  prefixlen 128  scopeid 0x10<host>
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 90  bytes 8540 (8.5 KB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 90  bytes 8540 (8.5 KB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
```

**Observation:** The eth0 interface is UP with IPv4 address 172.29.92.98 and netmask 255.255.240.0. The lo interface provides loopback connectivity.

### 2. Test Loopback Connectivity

**Command:**

```bash
ping -c 4 127.0.0.1
```

**Purpose:** Sends ICMP echo requests. The -c 4 option limits the test to four requests; 127.0.0.1 is the local loopback address.

**Use case:** Use it to verify that the local TCP/IP stack responds and measure round-trip time.

**Observed output:**

```text
PING 127.0.0.1 (127.0.0.1) 56(84) bytes of data.
64 bytes from 127.0.0.1: icmp_seq=1 ttl=64 time=0.028 ms
64 bytes from 127.0.0.1: icmp_seq=2 ttl=64 time=0.036 ms
64 bytes from 127.0.0.1: icmp_seq=3 ttl=64 time=0.037 ms
64 bytes from 127.0.0.1: icmp_seq=4 ttl=64 time=0.035 ms

--- 127.0.0.1 ping statistics ---
4 packets transmitted, 4 received, 0% packet loss, time 3164ms
rtt min/avg/max/mdev = 0.028/0.034/0.037/0.003 ms
```

**Observation:** All four packets received replies with 0% packet loss. This checks local connectivity; it does not test Internet access.

### 3. Trace the Packet Route

**Command:**

```bash
traceroute -n 127.0.0.1
```

**Purpose:** Displays the hops toward a destination. The -n option displays numerical IP addresses without reverse DNS lookups.

**Use case:** Use it to investigate routing paths and identify where responses stop along a path.

**Observed output:**

```text
traceroute to 127.0.0.1 (127.0.0.1), 30 hops max, 60 byte packets
 1  127.0.0.1  0.034 ms  0.005 ms  0.003 ms
```

**Observation:** The loopback destination is reached in one hop. This example does not traverse external routers.

### 4. Query DNS Records

**Command:**

```bash
nslookup localhost
```

**Purpose:** Queries the configured DNS server for records associated with localhost.

**Use case:** Use it to check DNS responses and identify the DNS server being queried.

**Observed output:**

```text
;; Got recursion not available from 10.255.255.254
Server:         10.255.255.254
Address:        10.255.255.254#53

Name:   localhost
Address: 127.0.0.1
;; Got recursion not available from 10.255.255.254
Name:   localhost
Address: ::1
```

**Observation:** Server 10.255.255.254 returns 127.0.0.1 and ::1. The recursion warning indicates that the responding server reports recursion as unavailable.

### 5. List Listening Network Sockets

**Command:**

```bash
netstat -tuln
```

**Purpose:** Lists TCP (-t) and UDP (-u) listening/server sockets (-l), showing numerical addresses and ports (-n).

**Use case:** Use it to check which local addresses and ports have services listening.

**Observed output:**

```text
Active Internet connections (only servers)
Proto Recv-Q Send-Q Local Address           Foreign Address         State
tcp        0      0 127.0.0.53:53           0.0.0.0:*               LISTEN
tcp        0      0 10.255.255.254:53       0.0.0.0:*               LISTEN
udp        0      0 127.0.0.53:53           0.0.0.0:*
udp        0      0 10.255.255.254:53       0.0.0.0:*
udp        0      0 127.0.0.1:323           0.0.0.0:*
udp6       0      0 ::1:323                 :::*
```

**Observation:** TCP and UDP sockets appear on port 53. UDP sockets also appear on port 323. UDP sockets do not have the TCP LISTEN state.

### 6. Display the ARP Cache

**Command:**

```bash
arp -a
```

**Purpose:** Displays cached mappings between IPv4 addresses and MAC addresses on local network links.

**Use case:** Use it to inspect the MAC address associated with a nearby IPv4 host, such as the gateway.

**Observed output:**

```text
Aayush.mshome.net (172.29.80.1) at 00:15:5d:3b:8a:49 [ether] on eth0
```

**Observation:** 172.29.80.1 maps to MAC address 00:15:5d:3b:8a:49 on eth0.

### 7. Display IP Addresses

**Command:**

```bash
ip addr show
```

**Purpose:** Displays interface addresses, prefix lengths, scope, and interface status.

**Use case:** Use it to verify IPv4/IPv6 addressing and subnet prefixes on each interface.

**Observed output:**

```text
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet 10.255.255.254/32 brd 10.255.255.254 scope global lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host
       valid_lft forever preferred_lft forever
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1280 qdisc mq state UP group default qlen 1000
    link/ether 00:15:5d:87:59:d9 brd ff:ff:ff:ff:ff:ff
    inet 172.29.92.98/20 brd 172.29.95.255 scope global eth0
       valid_lft forever preferred_lft forever
    inet6 fe80::215:5dff:fe87:59d9/64 scope link
       valid_lft forever preferred_lft forever
```

**Observation:** eth0 has address 172.29.92.98/20. The loopback interface includes 127.0.0.1/8, 10.255.255.254/32, and ::1/128.

### 8. Query a Specific DNS Server

**Command:**

```bash
dig @127.0.0.1 localhost
```

**Purpose:** Queries localhost using the DNS server explicitly specified by @127.0.0.1, normally on port 53.

**Use case:** Use it to test whether a DNS server at a particular address accepts queries and returns records.

**Observed output:**

```text
;; communications error to 127.0.0.1#53: connection refused
;; communications error to 127.0.0.1#53: connection refused
;; communications error to 127.0.0.1#53: connection refused

; <<>> DiG 9.18.39-0ubuntu0.22.04.7-Ubuntu <<>> @127.0.0.1 localhost
; (1 server found)
;; global options: +cmd
;; no servers could be reached
```

**Observation:** The query fails with connection refused and no servers could be reached. The supplied socket listing shows DNS on 127.0.0.53 and 10.255.255.254, not 127.0.0.1. The captured failure is retained as observed.

### 9. Display the IPv4 Routing Table

**Command:**

```bash
route -n
```

**Purpose:** Displays IPv4 routes using numerical addresses (-n).

**Use case:** Use it to identify the default gateway and directly connected networks when troubleshooting routing.

**Observed output:**

```text
Kernel IP routing table
Destination     Gateway         Genmask         Flags Metric Ref    Use Iface
0.0.0.0         172.29.80.1     0.0.0.0         UG    0      0        0 eth0
172.29.80.0     0.0.0.0         255.255.240.0   U     0      0        0 eth0
```

**Observation:** The default route uses gateway 172.29.80.1 through eth0. The directly connected network is 172.29.80.0/20. U means the route is up; G indicates a gateway.

### 10. Inspect DNS Resolver Configuration

**Command:**

```bash
cat /etc/resolv.conf
```

**Purpose:** Uses cat to print the resolver configuration file, including nameserver and search-domain settings.

**Use case:** Use it to check which DNS server and domain search suffix the system is configured to use.

**Observed output:**

```text
# This file was automatically generated by WSL. To stop automatic generation of this file, add the following entry to /etc/wsl.conf:
# [network]
# generateResolvConf = false
nameserver 10.255.255.254
search taild37f91.ts.net
```

**Observation:** The file is generated by WSL. It specifies nameserver 10.255.255.254 and search domain taild37f91.ts.net.

### 11. Display Routes with ip

**Command:**

```bash
ip route show
```

**Purpose:** Displays the IPv4 routing table using the ip utility.

**Use case:** Use it to confirm the default gateway, outgoing interface, connected subnet, and preferred source address.

**Observed output:**

```text
default via 172.29.80.1 dev eth0 proto kernel
172.29.80.0/20 dev eth0 proto kernel scope link src 172.29.92.98
```

**Observation:** The default route uses 172.29.80.1 on eth0. The connected network is 172.29.80.0/20 with source address 172.29.92.98.

### 12. Display Link-Layer Interface Details

**Command:**

```bash
ip link show
```

**Purpose:** Displays interface link state, MTU, MAC address, and link flags.

**Use case:** Use it to inspect whether an interface is enabled and whether its underlying link is up.

**Observed output:**

```text
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1280 qdisc mq state UP mode DEFAULT group default qlen 1000
    link/ether 00:15:5d:87:59:d9 brd ff:ff:ff:ff:ff:ff
```

**Observation:** eth0 has UP and LOWER_UP flags, MTU 1280, and MAC address 00:15:5d:87:59:d9.

### 13. Display the Neighbor Cache

**Command:**

```bash
ip neigh show
```

**Purpose:** Displays neighbor entries, including IP-to-link-layer address mappings and reachability states.

**Use case:** Use it to inspect gateway or local-host address resolution and neighbor reachability.

**Observed output:**

```text
172.29.80.1 dev eth0 lladdr 00:15:5d:3b:8a:49 STALE
```

**Observation:** The gateway 172.29.80.1 maps to 00:15:5d:3b:8a:49. STALE means the cached entry has not recently been confirmed reachable; it does not by itself mean the neighbor is unreachable.

### 14. Display Local IP Addresses

**Command:**

```bash
hostname -I
```

**Purpose:** Prints configured host IP addresses, excluding loopback addresses. Uppercase -I differs from lowercase -i.

**Use case:** Use it to quickly find an address assigned to the machine for local network connections.

**Observed output:**

```text
172.29.92.98
```

**Observation:** The command prints 172.29.92.98.

### 15. Resolve a Host through System Name Services

**Command:**

```bash
getent hosts localhost
```

**Purpose:** Looks up localhost in the hosts database through the system Name Service Switch configuration.

**Use case:** Use it to check host resolution through configured system sources, such as /etc/hosts and DNS.

**Observed output:**

```text
::1             localhost
```

**Observation:** The lookup returns ::1 for localhost. Unlike a direct DNS query, getent follows the system name-service configuration.

## Transcript Notes

- `ip addr show` was executed twice with identical output; it is documented once above.
- `^C` represents pressing Ctrl+C to interrupt a foreground process or cancel input. It is a keyboard action, not a separate networking command.

## Result

Studied 15 distinct Linux networking commands using the supplied outputs. Local loopback connectivity succeeded, interface and routing details were identified, and the explicit DNS query to 127.0.0.1 failed because that endpoint refused the connection.
