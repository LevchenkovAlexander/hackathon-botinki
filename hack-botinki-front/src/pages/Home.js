import React, { useState } from "react";

function Home() {
  const [text, setText] = useState("");

  const handleClick = () => {
    alert(`Вы ввели: ${text}`);
  };

  return (
    <div>
      <h1>Главная страница</h1>
      <input
        type="text"
        placeholder="Введите текст..."
        value={text}
        onChange={(e) => setText(e.target.value)}
        style={{ padding: "8px", width: "300px", marginRight: "10px" }}
      />
      <button
        onClick={handleClick}
        style={{
          padding: "8px 16px",
          background: "#0078d7",
          color: "#fff",
          border: "none",
          borderRadius: "6px",
          cursor: "pointer",
        }}
      >
        Отправить
      </button>
    </div>
  );
}

export default Home;
