import { RequestHandler } from "express";
import { Task, GenerateOrderRequest, GenerateOrderResponse, SubmitTaskRequest, SubmitTaskResponse } from "@shared/api";

// Simple in-memory store (dev only)
const savedTasks: Record<string, Task> = {};

export const handleGenerateOrder: RequestHandler = (req, res) => {
  const body = req.body as GenerateOrderRequest;
  const tasks = Array.isArray(body.tasks) ? body.tasks.slice() : [];

  // Normalize dates: try parse DD:MM:YYYY or ISO
  const parseDate = (d?: string | null) => {
    if (!d) return null;
    const isoMatch = /^\d{4}-\d{2}-\d{2}/.test(d);
    if (isoMatch) return new Date(d);
    const parts = d.split(":").join("-").split("-");
    // Accept DD-MM-YYYY or DD/MM/YYYY
    if (parts.length === 3) {
      const [p1, p2, p3] = parts;
      // if first looks like day
      if (p1.length === 2) return new Date(`${p3}-${p2}-${p1}`);
    }
    const parsed = new Date(d);
    return isNaN(parsed.getTime()) ? null : parsed;
  };

  tasks.sort((a, b) => {
    const da = parseDate(a.deadline);
    const db = parseDate(b.deadline);
    if (da && db) {
      const diff = da.getTime() - db.getTime();
      if (diff !== 0) return diff;
    } else if (da && !db) {
      return -1;
    } else if (!da && db) {
      return 1;
    }

    // then by percent completed ascending (less completed first)
    const pa = typeof a.percent === "number" ? a.percent : 0;
    const pb = typeof b.percent === "number" ? b.percent : 0;
    if (pa !== pb) return pa - pb;

    // then by complexity/hours ascending
    const ca = typeof a.complexityHours === "number" ? a.complexityHours : (typeof a.hours === "number" ? a.hours : 0);
    const cb = typeof b.complexityHours === "number" ? b.complexityHours : (typeof b.hours === "number" ? b.hours : 0);
    return ca - cb;
  });

  const response: GenerateOrderResponse = {
    orderedTasks: tasks,
    message: "Ordered tasks generated",
  };

  res.json(response);
};

export const handleSubmitTask: RequestHandler = (req, res) => {
  const body = req.body as SubmitTaskRequest;
  if (!body || !body.task) {
    res.status(400).json({ ok: false } as SubmitTaskResponse);
    return;
  }

  const id = body.task.id ?? `${Date.now()}`;
  const task: Task = { ...body.task, id };
  savedTasks[id] = task;

  const response: SubmitTaskResponse = { ok: true, taskId: id };
  res.json(response);
};
