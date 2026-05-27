# Java TCP State Machine Server

A Java-based TCP server application that processes incoming client commands using a strict internal state machine. 

## Overview

The server listens for incoming TCP connections on port **1337**. It utilizes Java Virtual Threads to handle multiple clients concurrently without blocking. 

Communication strictly depends on the server's current state. 
* Sending a valid command advances the state and returns a specific reply.
* Sending an **invalid** command for the current state immediately prints an error to the server console and **closes the connection**.

## The State Machine (Current Logic)

The server begins in the `Init` state. Below is the exact command flow based on the current server logic:

| Current State | Client Command | Server Reply | New State |
| :--- | :--- | :--- | :--- |
| **Init** | `Left!` | `WentLeft.` | **SLeft** |
| **Init** | `Right!` | `WentRight.\n` | **SRight** |
| **SLeft** | `GoOn1!` | `WentOn1.\n` | **Sfinal** |
| **SRight** | `GoOn2!` | `WentOn2.\n` | **Sfinal** |
| **Sfinal** | `Back!` | `WentBack.\n` | **Init** |
| **Sfinal** | `OnceMore!`| `DidOnceMore.\n`| **SLeft** |

## Project Files

* `TcpServer.java`: Main server application containing the port listener and state machine switch logic.

## How to Run & Test

### 1. Start the Server
Compile and run the server first so it can listen for connections:
```bash
javac src/*.java
java -cp src TcpServer
```
### 2. Connect via Netcat from the Terminal
Because there is no custom client application, you will use `netcat` to act as the client. Open a **new, separate terminal window** and run:

```bash
nc localhost 1337
