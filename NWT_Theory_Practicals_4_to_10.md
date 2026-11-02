# Networking and Web Technology (ESC-AIDS501) – Theory for Practicals 4 to 10

---

## Practical 4: Illustrate Various Networking Devices Using Packet Tracer

**Introduction:** A computer network is a collection of interconnected devices that share data and resources. The devices that make this possible operate at different layers of the OSI model, and each one has a specific role in deciding how data is forwarded from a source to a destination.

**Hub:** A hub is a Layer 1 (Physical layer) device. It has no intelligence: when it receives a signal on one port, it regenerates it and broadcasts it to every other port. All devices connected to a hub share one collision domain and one broadcast domain, so as more devices are added, collisions increase and performance drops. Hubs operate in half-duplex mode and are now largely obsolete.

**Switch:** A switch is a Layer 2 (Data Link layer) device that forwards frames using MAC addresses. It builds a MAC address table (CAM table) by learning the source MAC address of each incoming frame and the port it arrived on. If the destination MAC is known, the frame is sent only out of that port (unicast forwarding); if it is unknown, the switch floods it to all ports except the incoming one. Every port of a switch is its own collision domain, which allows full-duplex communication, but all ports still belong to one broadcast domain (unless VLANs are used).

**Router:** A router is a Layer 3 (Network layer) device that forwards packets between different networks using IP addresses. It maintains a routing table and selects the best path to a destination network. Each router interface is a separate broadcast domain, so routers limit broadcast traffic and connect a LAN to other LANs or to the Internet.

**Other devices:** A *bridge* connects two LAN segments and filters traffic by MAC address. An *access point* gives wireless devices access to a wired network. A *repeater* regenerates weakened signals to extend the distance of a segment. A *gateway* connects networks that use different protocols.

**Collision and broadcast domains (comparison):**

| Device | OSI Layer | Forwards using | Collision domain | Broadcast domain |
|--------|-----------|----------------|------------------|------------------|
| Hub | 1 | Nothing (broadcasts) | One for all ports | One for all ports |
| Switch | 2 | MAC address | One per port | One (without VLANs) |
| Router | 3 | IP address | One per port | One per interface |

**Role of Packet Tracer:** Cisco Packet Tracer is a network simulation tool that lets learners build and test virtual networks without physical hardware. It provides a realistic model of devices, cabling and protocol behaviour, and its simulation mode lets us watch each PDU (Protocol Data Unit) move through the devices layer by layer. This makes the difference between how a hub, switch and router handle the same packet clearly visible.

**Conclusion:** Understanding the function of each device helps in choosing the right equipment when designing a network: hubs repeat, switches forward by MAC address within a LAN, and routers forward by IP address between networks.

---

## Practical 5: Study of Basic Network Commands and Network Configuration Commands (Linux)

**Introduction:** Network commands are command-line utilities used to configure network interfaces, test connectivity, and diagnose problems. They work with the TCP/IP protocol suite and give direct insight into how a host communicates on a network. Linux provides a powerful set of such tools.

**Interface configuration (`ifconfig` / `ip`):** Every network interface (e.g., `eth0`, `wlan0`, `lo`) has an IP address, subnet mask, MAC address and status. `ifconfig` displays and can modify these parameters, such as assigning an IP address or bringing an interface up or down. It is gradually replaced by the more modern `ip` command from the `iproute2` package.

**Connectivity testing (`ping`):** `ping` uses the **ICMP (Internet Control Message Protocol)**. It sends Echo Request messages and waits for Echo Reply messages. It reports whether the destination is reachable, the round-trip time (RTT) and packet loss, which helps judge latency and reliability.

**Path discovery (`traceroute` / `tracert`):** This command shows the route packets take to a destination. It works by sending packets with an increasing **TTL (Time To Live)** value starting from 1. Each router decrements TTL by one; when it reaches zero, the router discards the packet and returns an ICMP "Time Exceeded" message, revealing its address. Repeating this builds a hop-by-hop path and shows where delays occur. (`tracert` is the Windows equivalent of Linux `traceroute`.)

**Address resolution (`arp`):** Devices on a LAN communicate using MAC addresses, while applications use IP addresses. **ARP (Address Resolution Protocol)** maps an IP address to its MAC address. The `arp` command displays the ARP cache, the table of recently resolved IP-to-MAC mappings.

**Connection and port information (`netstat` / `ss`):** These display active TCP/UDP connections, listening ports, routing tables and interface statistics, which is useful for monitoring and detecting unusual activity.

**Name resolution (`nslookup` / `dig`):** These query the **DNS (Domain Name System)** to translate domain names into IP addresses and show which DNS server answered.

**Other useful commands:** `hostname` (shows host name), `route` / `ip route` (views or edits the routing table), and `whois` (domain registration details).

**Importance:** These commands are the first tools a network administrator uses for troubleshooting: `ping` checks reachability, `traceroute` locates the failing hop, `arp` verifies local address mapping, `nslookup` checks DNS, and `ifconfig` confirms interface settings.

**Conclusion:** Mastery of basic network commands gives a practical understanding of the working of the network and transport layers and enables quick diagnosis and configuration of networks.

---

## Practical 6: Create a Local Area Network Using Static and Dynamic Routing Protocols

**Introduction:** Routing is the process of selecting the best path for packets to travel from a source network to a destination network. Routers store this information in a **routing table**, which can be filled in manually (static routing) or automatically (dynamic routing).

**Static routing:** The administrator manually enters routes to each remote network, specifying the destination network, subnet mask and next-hop address or exit interface. *Advantages:* no routing protocol overhead, low CPU and bandwidth usage, predictable and more secure. *Disadvantages:* does not scale to large networks, requires manual updating, and does not adapt automatically to link failures. It suits small or stub networks. A **default route** (0.0.0.0/0) is a special static route used when no specific match exists.

**Dynamic routing:** Routers run a routing protocol to exchange information with neighbours and automatically build and update routing tables. When the topology changes, routes are recalculated (convergence). Dynamic routing scales well but uses more CPU, memory and bandwidth.

**Types of dynamic routing protocols:**

1. **Distance Vector** (e.g., RIP): Routers share their entire routing table with neighbours periodically. RIP uses *hop count* as its metric with a maximum of 15 hops (16 means unreachable). Simple, but slow to converge and prone to routing loops (mitigated by split horizon and hold-down timers).
2. **Link State** (e.g., OSPF): Each router floods information about its links so all routers in an area build an identical map of the topology, then runs Dijkstra's Shortest Path First algorithm. The metric is *cost*, based on bandwidth. It converges fast and scales well using areas.
3. **Hybrid / Advanced Distance Vector** (e.g., EIGRP): A Cisco protocol combining features of both types, using the DUAL algorithm and a composite metric.

**Administrative Distance (AD):** When a router learns of the same network from multiple sources, it prefers the route with the lowest AD (connected = 0, static = 1, EIGRP = 90, OSPF = 110, RIP = 120), which indicates trustworthiness.

**Comparison:**

| Feature | Static | Dynamic |
|---------|--------|---------|
| Configuration | Manual | Automatic |
| Scalability | Poor | Good |
| Fault adaptation | None | Automatic |
| Resource usage | Very low | Higher |

**Conclusion:** Static routing is simple and efficient for small networks, while dynamic routing protocols provide scalability and automatic recovery for larger, changing infrastructures. Real networks often combine both.

---

## Practical 7: JSP/Servlet Program for Session Handling (URL Rewriting, Hidden Form Fields, Cookies, Sessions)

**Introduction:** HTTP is a **stateless** protocol: each request is treated independently and the server does not remember previous requests from the same client. Yet web applications such as shopping carts and logins need to recognise a user across multiple requests. **Session tracking (session management)** is the set of techniques used to maintain state between requests.

**Java Servlets and JSP:** A *servlet* is a Java class running on a web container (e.g., Apache Tomcat) that handles requests and generates dynamic responses. *JSP (JavaServer Pages)* lets developers embed Java in HTML; it is compiled into a servlet. Servlets follow a life cycle of `init()`, `service()` (`doGet()`/`doPost()`) and `destroy()`.

**Techniques of session tracking:**

1. **Cookies:** A cookie is a small piece of data (name-value pair) sent by the server and stored by the browser, which returns it with every subsequent request to that site. They can have an expiry time (persistent) or last until the browser closes. *Limitation:* the user can disable cookies, and storing sensitive data in them is insecure.
2. **Hidden Form Fields:** The server embeds invisible `<input type="hidden">` fields in the HTML form carrying the state data, which is sent back on submission. It works even if cookies are disabled, but only for form-based navigation and the data is visible in page source.
3. **URL Rewriting:** Session information is appended to the URL as a query string (e.g., `page?user=ravi`) or as a session ID. It works when cookies are disabled, but makes URLs long, exposes data, and must be added to every link.
4. **HttpSession:** The most robust method. The container creates a unique **session ID** for each user (typically sent via the `JSESSIONID` cookie, falling back to URL rewriting) and stores the data on the *server side* as attributes (`setAttribute`/`getAttribute`). Sessions can be configured with a timeout and destroyed with `invalidate()`.

**Comparison:**

| Technique | Data stored at | Needs cookies? | Security |
|-----------|----------------|----------------|----------|
| Cookies | Client | Yes | Low |
| Hidden fields | Client (in page) | No | Low |
| URL rewriting | Client (in URL) | No | Low |
| HttpSession | Server | Usually | High |

**Conclusion:** Because HTTP is stateless, session tracking is essential for dynamic web applications. Client-side techniques are simple but less secure, while `HttpSession` keeps data on the server and is preferred for sensitive information.

---

## Practical 8: Create VLAN in Packet Tracer

**Introduction:** A **VLAN (Virtual Local Area Network)** is a logical grouping of devices within a switched network that behave as if they are on the same physical LAN, regardless of their physical location. VLANs are defined by the IEEE **802.1Q** standard and operate at Layer 2.

**Need for VLANs:** In a normal switched network, all ports belong to a single broadcast domain, so broadcast traffic grows with the number of devices, wasting bandwidth and reducing performance. VLANs divide one large broadcast domain into several smaller ones.

**Benefits:**
- **Reduced broadcast traffic:** broadcasts remain inside their own VLAN.
- **Improved security:** sensitive departments (e.g., Finance, Admin) are isolated from others.
- **Logical grouping:** users are grouped by function or department, not by location.
- **Easier management and flexibility:** moving a user needs only a port reassignment, not re-cabling.
- **Cost saving:** fewer routers and physical switches are needed.

**Types of VLAN ports:**
- **Access port:** belongs to one VLAN and carries untagged traffic; connects to end devices.
- **Trunk port:** carries traffic of multiple VLANs between switches (or switch and router) by adding an **802.1Q tag** containing the VLAN ID to each frame.

**VLAN ID range:** Normal range is 1–1005 (VLAN 1 is the default VLAN, and extended range goes up to 4094). The **native VLAN** is the one whose traffic is sent untagged on a trunk.

**Inter-VLAN routing:** Devices in different VLANs cannot communicate directly, since each VLAN is a separate subnet and broadcast domain. A Layer 3 device is needed: a router using the "router-on-a-stick" method (sub-interfaces on one trunk link) or a Layer 3 switch using SVIs.

**VTP and DTP:** VLAN Trunking Protocol (VTP) propagates VLAN information across switches, and Dynamic Trunking Protocol (DTP) negotiates trunk links automatically.

**Conclusion:** VLANs provide network segmentation without extra hardware, improving performance, security and manageability. Communication between VLANs requires routing at Layer 3.

---

## Practical 9: Create a Web Page Using HTML5, CSS3 and JavaScript with Events and Buttons

**Introduction:** Modern front-end web pages are built from three cooperating technologies, each with a distinct responsibility: **HTML** for structure, **CSS** for presentation, and **JavaScript** for behaviour.

**HTML5:** HyperText Markup Language defines the content and structure of a page using elements (tags) arranged in a tree. HTML5 introduced semantic elements (`<header>`, `<nav>`, `<section>`, `<article>`, `<footer>`) that give meaning to content, native multimedia (`<audio>`, `<video>`), the `<canvas>` element for graphics, and new form input types (`email`, `date`, `range`) with built-in validation.

**CSS3:** Cascading Style Sheets control how elements look: colours, fonts, spacing, layout and responsiveness. CSS works through *selectors* (element, class, ID) and *properties*. Key concepts include the **box model** (content, padding, border, margin), the **cascade and specificity** rules that decide which style wins, and styles applied as inline, internal or external. CSS3 added rounded corners, shadows, gradients, transitions, animations, **Flexbox**, **Grid**, and **media queries** for responsive design.

**JavaScript:** A lightweight, interpreted scripting language that runs in the browser and makes pages interactive. It manipulates the **DOM (Document Object Model)**, the tree representation of the HTML document, to change content, styles and structure dynamically without reloading the page.

**Events:** An *event* is an action or occurrence detected by the browser, such as a click, keypress, mouse hover, form submission or page load. JavaScript uses **event handlers/listeners** (e.g., `onclick`, `addEventListener`) to run code in response. Events *bubble* up through the DOM tree from the target element to its ancestors. Buttons (`<button>` elements) are the common trigger for events, linking user actions to program logic.

**Separation of concerns:** Keeping HTML, CSS and JS separate (in different files) makes pages easier to maintain, reuse and cache.

**Conclusion:** HTML, CSS and JavaScript together deliver structured, attractive and interactive web pages. The event-driven model lets a page respond to user actions in real time, which is the foundation of modern web applications.

---

## Practical 10: Creating a Simple Web Application Using Ruby on Rails

**Introduction:** **Ruby on Rails (Rails)** is an open-source, server-side web application framework written in Ruby. It simplifies web development by providing structure and ready-made components for common tasks, allowing rapid development of database-backed applications.

**Guiding principles:**
- **Convention over Configuration (CoC):** Rails assumes sensible defaults (e.g., a model `Student` maps to a table `students`), reducing the amount of configuration code.
- **Don't Repeat Yourself (DRY):** each piece of knowledge should exist in a single place in the system.

**MVC architecture:** Rails is built on the **Model–View–Controller** pattern, which separates an application into three layers:
1. **Model:** represents data and business logic. It talks to the database through **Active Record**, an ORM (Object-Relational Mapping) layer that maps database tables to Ruby classes and rows to objects. It also handles validations and associations.
2. **View:** the presentation layer, usually HTML mixed with embedded Ruby (`.erb` templates), that shows data to the user.
3. **Controller:** receives the browser's request, interacts with the model, and chooses the view to render. Public methods in a controller are called *actions*.

**Request flow:** Browser request → **Router** (`routes.rb`) → **Controller action** → **Model** (database) → **View** → HTTP response to browser.

**CRUD operations:** CRUD stands for Create, Read, Update and Delete, the four basic operations on stored data. Rails maps them to HTTP verbs and RESTful controller actions:

| Operation | HTTP verb | Controller action |
|-----------|-----------|-------------------|
| Create | POST | `new`, `create` |
| Read | GET | `index`, `show` |
| Update | PATCH/PUT | `edit`, `update` |
| Delete | DELETE | `destroy` |

**REST (Representational State Transfer):** Rails encourages RESTful design, where resources are identified by URLs and manipulated using standard HTTP methods. A single `resources :students` line in the routes file generates all of these routes.

**Other key features:** *Migrations* version-control database schema changes, *scaffolding* auto-generates model, views and controller for a resource, and *RubyGems* provides reusable libraries.

**Conclusion:** Ruby on Rails speeds up development through conventions and the MVC pattern, which separates concerns and makes applications organised, maintainable and easy to extend with CRUD functionality.
