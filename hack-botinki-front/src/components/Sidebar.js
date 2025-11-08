import React from "react";
import { Link, useLocation } from "react-router-dom";

function Sidebar() {
  const location = useLocation();

  const linkStyle = (path) => ({
    display: "block",
    padding: "12px 16px",
    textDecoration: "none",
    color: location.pathname === path ? "#fff" : "#ddd",
    backgroundColor: location.pathname === path ? "#0078d7" : "transparent",
    borderRadius: "6px",
    margin: "4px 0",
  });

  return (
    <div
      style={{
        width: "200px",
        backgroundColor: "#1e1e1e",
        padding: "20px",
        color: "#fff",
        display: "flex",
        flexDirection: "column",
        borderRadius: "0 8px 8px 0",
        height: "100%"
      }}
    >
      <h2 style={{ marginBottom: "20px", color: "#61dafb" }}>Мой сайт</h2>
      <Link to="/" style={linkStyle("/")}>🏠 Home</Link>
      <Link to="/page1" style={linkStyle("/page1")}>📄 Page1</Link>
      <Link to="/page2" style={linkStyle("/page2")}>⚙️ Page2</Link>
    </div>
  );
}

export default Sidebar;
