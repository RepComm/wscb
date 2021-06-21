
# wscb

WebSocket callback wrapper

Implementing and using TooTallNate/Java-WebSocket throws multi-threading exceptions when using from GraalVM javascript context.
This aims to make WS more appropriate for that single threaded access standpoint.
