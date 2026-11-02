# Practical 3: To Simulate and Study the Stop & Wait ARQ Protocol and Its Behavior Under Different Situations

**Subject:** Networking and Web Technology (ESC-AIDS501)
**Course Outcomes:** CO1, CO2
**Tool Used:** Virtual Lab

---

## 1. Introduction

When data travels from a sender to a receiver across a network, it passes through physical media that are never perfect. Noise, interference, signal attenuation and congestion can corrupt bits, or cause entire frames to be lost. A reliable communication system must therefore be able to *detect* that a frame was damaged or lost and *recover* from it. This responsibility is handled by the **Data Link Layer** (and, end-to-end, by the Transport layer) through two closely related functions: **flow control** and **error control**.

**Stop-and-Wait ARQ (Automatic Repeat reQuest)** is the simplest protocol that provides both. It is the foundation on which more advanced sliding window protocols, such as Go-Back-N and Selective Repeat, are built.

---

## 2. Flow Control and Error Control

**Flow control** is a set of procedures that prevents a fast sender from overwhelming a slow receiver. The receiver has limited buffer space and processing speed; if frames arrive faster than they can be handled, they are dropped. Flow control makes the sender wait for permission before transmitting more data.

**Error control** deals with detecting and correcting errors during transmission. It involves:
- **Error detection:** using techniques such as parity bits, checksum or CRC (Cyclic Redundancy Check) to find out whether a received frame is corrupt.
- **Error correction by retransmission:** the damaged or lost frame is sent again. This is the principle behind ARQ.

**ARQ (Automatic Repeat reQuest)** is an error-control strategy in which the receiver sends acknowledgments to the sender, and the sender automatically retransmits any frame that is not acknowledged within a defined time. Three ARQ protocols are commonly studied: **Stop-and-Wait**, **Go-Back-N** and **Selective Repeat**.

---

## 3. The Stop-and-Wait ARQ Protocol

Stop-and-Wait is a **one-frame-at-a-time** protocol. The sender transmits a single frame, then *stops and waits* until it receives an acknowledgment (ACK) from the receiver before sending the next one.

### 3.1 Key Elements

| Element | Description |
|---------|-------------|
| **Data frame** | The unit of data sent by the sender, carrying a sequence number. |
| **ACK (Acknowledgment)** | A control frame sent by the receiver to confirm that a frame was received correctly. |
| **Timer** | The sender starts a timer when a frame is sent. If the ACK does not arrive before the timer expires, the frame is assumed lost. |
| **Timeout** | The waiting period after which the sender retransmits the frame. It is usually set longer than the round-trip time (RTT). |
| **Sequence number** | A label (0 or 1) given to each frame so that the receiver can detect duplicates. |
| **Retransmission** | Resending of a frame that was lost, corrupted, or not acknowledged. |

### 3.2 Why Sequence Numbers Are Needed

Suppose a frame reaches the receiver correctly but its ACK is lost. The sender will time out and retransmit the same frame, so the receiver now gets a **duplicate**. Without a way to tell the two apart, the receiver would accept the same data twice. Therefore, each frame carries a sequence number. In Stop-and-Wait only **two** sequence numbers (0 and 1) are required, since at most one frame is outstanding at any moment. The ACK carries the number of the **next frame expected**, so ACK 1 means "frame 0 received, send frame 1."

### 3.3 Working of the Protocol

1. The sender transmits frame 0 and starts its timer.
2. The receiver checks the frame. If it is correct and has the expected sequence number, it delivers the data upward and sends back an ACK.
3. When the sender gets the ACK before the timer expires, it stops the timer and sends the next frame (frame 1).
4. The process repeats, alternating sequence numbers 0, 1, 0, 1, and so on.

---

## 4. Behavior Under Different Situations

The protocol must work correctly in four situations, which are what the simulation demonstrates.

### Situation 1: Normal Operation (No Errors)
The data frame reaches the receiver intact, and the ACK returns to the sender before the timeout. The sender then moves on to the next frame. This is the ideal case, and transmission proceeds with no retransmission.

### Situation 2: Data Frame Lost or Corrupted
The frame is lost in transit, or arrives with errors and is discarded by the receiver after the CRC check. The receiver sends no ACK. The sender's timer expires, and the sender **retransmits** the same frame. Delivery then continues normally.

### Situation 3: ACK Lost
The receiver gets the frame correctly and sends an ACK, but the ACK is lost on the way back. The sender cannot tell this apart from a lost frame, so after the timeout it retransmits. The receiver now sees a frame with a sequence number it has already accepted, recognises it as a **duplicate**, discards it, and sends the ACK again. This is why sequence numbers are essential.

### Situation 4: Delayed ACK / Premature Timeout
The ACK is not lost, but it arrives after the timer has expired. The sender has already retransmitted the frame, which the receiver discards as a duplicate. The late ACK may be ignored by the sender. This shows the importance of choosing a proper timeout value: too short causes unnecessary retransmissions, while too long wastes time when a frame is really lost.

---

## 5. Performance Analysis

### 5.1 Efficiency (Link Utilization)

The main weakness of Stop-and-Wait is that the sender sits **idle** while waiting for each ACK, so the channel is poorly used.

Let:
- **Tt** = transmission time of a frame (frame size ÷ bandwidth)
- **Tp** = propagation delay
- **RTT** = round-trip time = 2 × Tp
- **a** = Tp / Tt

Time to send one frame and receive its ACK (ignoring processing delay and ACK transmission time) = Tt + 2Tp.

**Efficiency (η) = Tt / (Tt + 2Tp) = 1 / (1 + 2a)**

When the propagation delay is large compared with the transmission time (a long link, such as a satellite link, or a high bandwidth link with small frames), `a` becomes large and efficiency becomes very low.

### 5.2 Effect of Errors

If the probability that a frame is in error or lost is **P**, then the average number of transmissions needed per frame is 1 / (1 − P), and the efficiency becomes:

**η = (1 − P) / (1 + 2a)**

So efficiency drops further as the error rate increases.

### 5.3 Throughput

Throughput is the number of useful bits delivered per unit time, equal to the efficiency multiplied by the link bandwidth.

---

## 6. Advantages and Disadvantages

**Advantages**
- Very simple to design and implement.
- Needs minimal buffer space: only one frame is stored at a time.
- Provides both flow control and error control.
- Works well on short, low-delay links.

**Disadvantages**
- Low efficiency: the channel stays idle during each round trip.
- Poor performance on long-delay or high-bandwidth links.
- Only one frame is in transit at a time, so bandwidth is wasted.
- A single lost frame or ACK stalls the entire transmission until the timeout.

---

## 7. Comparison with Sliding Window Protocols

Stop-and-Wait is a sliding window protocol with a window size of 1. The limitations above are overcome by allowing several frames to be outstanding at once.

| Feature | Stop-and-Wait | Go-Back-N | Selective Repeat |
|---------|---------------|-----------|------------------|
| Sender window size | 1 | N (up to 2ⁿ − 1) | Up to 2ⁿ⁻¹ |
| Receiver window size | 1 | 1 | Same as sender |
| Frames sent before ACK | 1 | Many | Many |
| Retransmission on error | Only the lost frame | The lost frame and all that follow | Only the lost frame |
| Sequence numbers needed | 2 | Larger range | Larger range |
| Efficiency | Low | Higher | Highest |
| Complexity | Very low | Moderate | High |

---

## 8. Applications and Relevance

Although inefficient for high-speed links, the ideas behind Stop-and-Wait are used everywhere in networking: acknowledgments, timers, retransmission and sequence numbers appear in **TCP**, wireless link layers such as **IEEE 802.11 (Wi-Fi)**, and simple protocols such as **TFTP**, which uses a lock-step, stop-and-wait style exchange. Studying it gives a clear base for understanding reliable data transfer.

---

## 9. Conclusion

Stop-and-Wait ARQ ensures reliable delivery over an unreliable channel by combining acknowledgments, timers, sequence numbers and retransmission. The simulation shows that the protocol handles normal operation, lost frames, lost acknowledgments and delayed acknowledgments correctly, with sequence numbers preventing duplicate delivery. However, because only one frame is outstanding at a time, its efficiency falls sharply as the link delay grows, which motivates sliding window protocols such as Go-Back-N and Selective Repeat.
