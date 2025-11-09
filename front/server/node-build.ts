import path from "path";
import { createServer } from "./index";
import * as express from "express";
import { createProxyMiddleware } from "http-proxy-middleware";

const app = createServer();
const port = process.env.PORT || 3000;

const JAVA_SERVER_URL = process.env.JAVA_SERVER_URL || "http://localhost:8080";

// Проксирование всех API-запросов на Java-сервер
app.use(
  "/api",
  createProxyMiddleware({
    target: JAVA_SERVER_URL,
    changeOrigin: true,
    pathRewrite: {
      "^/api": "/api", // Можно изменить путь, если нужно
    },
    onProxyReq: (proxyReq, req, res) => {
      console.log(`📡 Proxying ${req.method} ${req.path} -> ${JAVA_SERVER_URL}`);
    },
    onError: (err, req, res) => {
      console.error("❌ Proxy error:", err);
      res.status(500).json({ error: "Connection to backend failed" });
    },
  })
);

// In production, serve the built SPA files
const __dirname = import.meta.dirname;
const distPath = path.join(__dirname, "../spa");

// Serve static files
app.use(express.static(distPath));

// Handle React Router - serve index.html for all non-API routes
app.use((req, res, next) => {
  // Don't serve index.html for API routes
  if (req.path.startsWith("/api/") || req.path.startsWith("/health")) {
    return next();
  }
  
  // Only handle GET requests for SPA routing
  if (req.method === "GET") {
    res.sendFile(path.join(distPath, "index.html"));
  } else {
    next();
  }
});

app.listen(port, () => {
  console.log(`🚀 Fusion Starter server running on port ${port}`);
  console.log(`📱 Frontend: http://localhost:${port}`);
  console.log(`🔧 API: http://localhost:${port}/api`);
  console.log(`☕ Java Backend: ${JAVA_SERVER_URL}`);
});

// Graceful shutdown
process.on("SIGTERM", () => {
  console.log("🛑 Received SIGTERM, shutting down gracefully");
  process.exit(0);
});

process.on("SIGINT", () => {
  console.log("🛑 Received SIGINT, shutting down gracefully");
  process.exit(0);
});
