---
title: Creating yastation — a console client for managing Yandex Station
description: One day, while sitting at work and listening to music on my Yandex Station, I discovered something interesting - I couldn't simply skip the track. This unfortunate situation prompted me to create my own solution.
date: 03/27/2022
tags: golang, api, yandex, cli
---

# Creating yastation — a console client for managing Yandex Station

One day, while sitting at work and listening to music on my Yandex Station (speaker device from Yandex company, something like Home Pod from Apple), I discovered something interesting - I couldn't simply skip the track. You might ask, "How could that happen?" I'd reply that yesterday I lost my voice during a heated political debate, and my phone, inconveniently, was further away than I could reach. Ultimately, this unfortunate situation prompted me to create my own solution (yet another "bicycle" [a Russian idiom for reinventing the wheel]).

## Interaction with the Station
First, as I imagine anyone else in my position would, I started Googling. I searched for information on both existing solutions and any available documentation and API descriptions. In the end, my search only led me to a module for Home Assistant, which, unfortunately, I don't use. However, this implementation significantly helped me understand how to control the station programmatically. Firstly, there are two methods of interaction:

1.  The WebSocket API, which isn't the most convenient (IMHO)
2.  A private and poorly documented HTTP API

After some consideration, I chose the HTTP API because it's simpler to implement and can work from any network.

## The Implementation Itself
From there, it wasn't particularly complex. As with any other private API, I had to implement login using a username and password, followed by extracting cookies from the session. After logging in, a list of devices was retrieved, and for the selected speaker, a set of scenarios was created for further interaction. As a result, the following basic actions are currently possible:

* Start (starts playing favorite music)
* Stop
* Next track
* Previous track
* Volume Down
* Volume Up
* Play song (plays a song after entering its title)

![](/public/assets/yastation/image.png)

Future plans include adding functionality that even the station itself doesn't support, such as a playback queue. You can find the source code on [github](https://github.com/beldmian/yastation).
