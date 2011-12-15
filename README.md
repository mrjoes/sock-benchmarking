What is this?
-------------

This is a fork of the [socket.io-benchmarking](https://github.com/drewww/socket.io-benchmarking) project written by Drew Harry.

Why it is not a proper fork?
----------------------------

Because this project can benchmark [socket.io](http://socket.io/) and [SockJS](http://github.com/sockjs/) using same core.
There's no handy way to switch protocol to socket.io or to sockjs without recompiling, but patches are always welcome.

What was changed?
-----------------

* Support of the SockJS protocol
* Fixed bugs: concurrency issues, improper packet scheduling, etc.
* Changed conditions when to stop


Graphs
------

I also adopted original Mathlab graph plotting scripts to GNU Octave, so you don't have to have Mathlab to plot them.

Have fun.
