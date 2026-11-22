export const httpClient = {
  async request(method, url, body = null) {
    const options = { method, headers: { 'Content-Type': 'application/json' }, credentials: 'same-origin' };
    if (body) options.body = JSON.stringify(body);
    try {
      const response = await fetch(url, options);
      let data = null;
      try { data = await response.json(); } catch (e) {}
      if (!response.ok) {
        if (response.status === 401 && !url.startsWith('/api/auth/')) window.dispatchEvent(new Event('session-expired'));
        throw new Error(data?.mensaje || `Error ${response.status}`);
      }
      return data;
    } catch (error) {
      console.error(`[HTTP] Error en ${method} ${url}:`, error);
      throw error;
    }
  },
  get(url) { return this.request('GET', url); },
  post(url, body) { return this.request('POST', url, body); },
  put(url, body) { return this.request('PUT', url, body); },
  delete(url) { return this.request('DELETE', url); }
};
