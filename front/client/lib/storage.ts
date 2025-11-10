const STORAGE_PREFIX = "mobile_task_app_v1";

function generateUuid() {
  // simple RFC4122 v4-like uuid
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
    const r = (Math.random() * 16) | 0;
    const v = c === 'x' ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}

export function getUserId(): string {
  if (typeof window === 'undefined') return 'server';
  const explicit = localStorage.getItem('user_id');
  if (explicit) return explicit;
  let uuid = localStorage.getItem('user_uuid');
  if (!uuid) {
    uuid = `guest_${generateUuid()}`;
    try {
      localStorage.setItem('user_uuid', uuid);
    } catch (e) {}
  }
  return uuid;
}

export function setUserId(userId: string) {
  if (typeof window === 'undefined') return;
  if (!userId) return;
  try {
    const prevKey = getStorageKey();
    const prevRaw = localStorage.getItem(prevKey);
    localStorage.setItem('user_id', userId);
    const newKey = `${STORAGE_PREFIX}_${userId}`;
    if (prevRaw && !localStorage.getItem(newKey)) {
      localStorage.setItem(newKey, prevRaw);
    }
  } catch (e) {
    console.error('setUserId error', e);
  }
}

export function getStorageKey() {
  return `${STORAGE_PREFIX}_${getUserId()}`;
}

export function loadState() {
  try {
    const raw = localStorage.getItem(getStorageKey());
    if (!raw) return {};
    return JSON.parse(raw);
  } catch (e) {
    return {};
  }
}

export function saveState(state: any) {
  try {
    localStorage.setItem(getStorageKey(), JSON.stringify(state));
  } catch (e) {}
}
