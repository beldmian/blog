---
title: A small note on True Tech Hack 2023 participation
description: Everyone has heard about "epilepsy", sometimes they have come across a video marked "epilepsy warning". And it seems clear that it is something related to sharp flickering and color changes, but is it really that simple? At True Tech Hack, one of the tasks of the participants was "Adaptation of films for people with special needs", and one of these needs was the ability to filter or change scenes that could cause epileptic seizures. It is about my solution to this problem that I want to tell you further.
date: 06/28/2023
tags: golang, ffmpeg, epilepsy, hackathon
---

# A small note on True Tech Hack 2023 participation

Everyone has heard of "epilepsy", sometimes they have come across videos marked "epilepsy warning".
And it seems clear that it is something related to sharp flickering and color changes, but is it
really that simple? At True Tech Hack, one of the tasks of the participants was "Adaptation of
films for people with special needs", and one of these needs was the ability to filter or change
scenes that could cause epileptic seizures. It is about my solution to this problem that I want to tell you further.

## Attempt 0.5: What is this "epilepsy" of yours anyway
To begin with, I decided to figure out what problem I was dealing with, since I had no experience
interacting with people who had such a problem, as well as experience interacting with medical books.
The first thing I learned was that epilepsy comes in different forms, very different, so different that
in this case we are only touching on one of its variations - Photosensitive epilepsy (PSE). At its core,
PSE is characterized by sudden attacks triggered by visual stimuli, which include flashing lights, regular
patterns, or regular moving patterns. It was after reading this description that the first idea for solving the problem came to me.

## Attempts 1 and 2: Let's cut everything? Or almost everything?
The first thought was to cut all the places in the video where the frames next to each other are very different
in color. At first it sounded pretty good, but then I decided to watch the movie... And I realized that with this
method I would rather reduce the running time to 0 than fix any problem. The solution quickly turned to calculating
some quantitative "difference" between adjacent frames, and cutting them only if it crosses a certain boundary many
times in a row. Now this was already similar to what some of the hackathon finalists suggested, but I wanted to go further.

## Attempt 3: But there is ffmpeg
Some time after I started working on the hackathon, my sleepy brain suddenly decided to remember the existence
of such a wonderful utility as [ffmpeg](https://ffmpeg.org/). As the developers themselves write, this is
"A complete, cross-platform solution to record, convert and stream audio and video", that is, in essence,
just a "combine" for converting one video to another. After a short search on the ffmpeg documentation site, 
I found a filter called [photosensitivity](https://ffmpeg.org/ffmpeg-filters.html#photosensitivity).
The filter itself was created to remove abrupt color changes in video.
In fact, it takes and smooths the color change curve on each individual pixel, in case the brightness changes with
too high a frequency, which ultimately gives a smooth color transition throughout the video (for example, [here](https://imgur.com/CDwgKe9)).
After a few minutes of adjusting the commands, I got something like this:

```bash
ffmpeg -i video.mp4 -vf photosensitivity=30:0.6:20 out.mp4
```

However, even then I understood that such a simple command would not be enough, because VoD content was already waiting on the horizon.

## VoD, MPEG-DASH and everything-everything-everything: how to prevent content from being filtered normally

Having realized that I would have to use ffmpeg somewhere between the server and the client, I started googling about
how video players work in the modern world. As a result, I learned about [VoD, or Video on demand](https://en.wikipedia.org/wiki/Video_on_demand),
which is essentially a kind of paradigm - users receive a video by requesting it from a catalog. It sounds simple, but the implementation of
this paradigm can be quite complex. I think many people watch videos on various video hosting sites, but have you ever
thought about how it turns out that you can watch a video without downloading it completely or even starting from the middle?
The idea of segmenting the video and sending it to the client in parts comes to mind relatively quickly, but here you can run
into a difficulty: how to keep the volume of segments as small as possible? One of the solutions is [MPEG-DASH](https://ru.wikipedia.org/wiki/MPEG-DASH)
technology, which is now used in a huge number of video players. The essence of it is that we can put all the metadata and format
descriptions into a separate file, and then simply send the content of the segments directly over the network in order
to glue them with the format on the client.

It is the use of segmented video that prevents the ffmpeg solution described above from being applied, but it can be modified - ffmpeg can accept and output MPEG-DASH format. After some experiments, I came up with the following command:

```bash
ffmpeg -filter_complex photosensitivity=30:0.6:20 -re -i "$URL" \
    -f dash -seg_duration 1 -adaptation_sets id=0,streams=v id=1,streams=a \
    -ldash 1 -preset veryfast file.mpd
```

So I was able to accept video from a link to its mpd file and output a new segmented video that was generated "on the fly". The last problem was the need to somehow give this video to the client.

## Let's start developing
In essence, all that was left was to develop a solution that could be a kind of proxy between the client who wants to watch the video and the server that has this video. It was decided to write in Go, since this is my main language and it gives good performance in just such tasks. The architecture of the solution itself is not something special - we accept a link to a video as input, create a stream for processing it, and give a link to a new video to the client. As a result, the interaction between the client and the proxy looked something like this:

```
// Request
{
    "stream_url": "https://dash.akamaized.net/dash264/TestCasesHD/2b..."
}
// Response
{
    "new_url": "/stream/b1642bb4-e7fd-42ac-93dd-9334b4b74e35/file.mpd"
}
```

Then a frontend was also developed, but it was essentially even simpler, a couple of sliders and several input fields next to the player.

Then, for deployment and testing, the solution was packed into docker containers and hung with docker-compose and kubernetes manifests. Minimal documentation for launch and testing was written.

## A few results
We became finalists of the hackathon, went to a conference, where we screwed up the pitch a little.
We were not given any prizes, except for a minimal set of merch, but in general it is our own fault.
The hackathon gave me a lot of experience and new knowledge, which helps me develop further.
Actually, here is a [link to the solution](https://github.com/beldmian/colorblinder), if someone wants to poke it.
