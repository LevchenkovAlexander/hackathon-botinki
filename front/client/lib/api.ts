export async function postTask(task: any) {
  // Preferred RESTful endpoint for creating/updating tasks on backend
  try {
    const res = await fetch('/api/tasks', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ task }),
    });
    if (!res.ok) {
      // fallback to legacy endpoint used by local dev server
      const fallback = await fetch('/api/submit-task', {
        method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ task })
      });
      if (!fallback.ok) throw new Error('Request failed');
      return await fallback.json();
    }
    return await res.json();
  } catch (e) {
    // still attempt fallback
    try {
      const fallback = await fetch('/api/submit-task', {
        method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ task })
      });
      return await fallback.json();
    } catch (err) {
      throw err;
    }
  }
}

export async function postFreeHours(hours: number) {
  try {
    const res = await fetch('/api/free-hours', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ hours }) });
    if (!res.ok) {
      // fallback to posting as a task
      return await postTask({ id: `${Date.now()}`, name: `Free hours: ${hours}`, hours });
    }
    return await res.json();
  } catch (e) {
    // fallback
    return await postTask({ id: `${Date.now()}`, name: `Free hours: ${hours}`, hours });
  }
}

export async function postResult(payload: any) {
  try {
    const res = await fetch('/api/results', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
    if (!res.ok) {
      // fallback to submitTask
      return await postTask(payload);
    }
    return await res.json();
  } catch (e) {
    return await postTask(payload);
  }
}

export async function generateOrderApi(body: any) {
  const res = await fetch('/api/generate-order', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
  return res;
}
