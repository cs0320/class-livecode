# Demo Server

A minimal HTTP-only server that lets us mock grids from the Internet.

```
npx tsx server/server.ts
```

It listens on `http://127.0.0.1:4000` only (not reachable from other
machines). Every request, regardless of path or method, gets the same
JSON body back.
