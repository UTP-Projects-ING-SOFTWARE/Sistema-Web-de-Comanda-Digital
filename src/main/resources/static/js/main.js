import { httpClient } from './services/httpClient.js';

let currentUser = null;
const views = { login: document.getElementById('view-login'), app: document.getElementById('view-app') };
const modalRoot = document.getElementById('modal-root');

function showView(viewName) {
  Object.values(views).forEach(v => v.hidden = true);
  if (views[viewName]) views[viewName].hidden = false;
}

function showToast(message, type = 'info') {
  const container = document.getElementById('toast-container') || document.createElement('div');
  if (!container.id) {
    container.id = 'toast-container';
    container.className = 'toast-container';
    document.body.appendChild(container);
  }
  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.innerHTML = `<span>${message}</span>`;
  container.appendChild(toast);
  setTimeout(() => { toast.style.opacity = '0'; setTimeout(() => toast.remove(), 300); }, 3500);
}

async function init() {
  try { currentUser = await httpClient.get('/api/auth/me'); enterApp(); } 
  catch (e) { showView('login'); }
}

document.getElementById('form-login').addEventListener('submit', async (e) => {
  e.preventDefault();
  const errorEl = document.getElementById('login-error');
  errorEl.hidden = true;
  const codigo = document.getElementById('codigo').value;
  const password = document.getElementById('password').value;

  if (!codigo || !password) {
    errorEl.textContent = 'Por favor, complete ambos campos.';
    errorEl.hidden = false;
    return;
  }

  try {
    const response = await httpClient.post('/api/auth/login', { codigo, password });
    document.getElementById('password').value = '';
    currentUser = response;
    enterApp();
  } catch (err) {
    errorEl.textContent = err.message || 'Credenciales incorrectas.';
    errorEl.hidden = false;
  }
});

document.getElementById('btn-logout').addEventListener('click', async () => {
  try { await httpClient.post('/api/auth/logout'); } catch(e){}
  currentUser = null; showView('login');
});

document.getElementById('sidebar-home').addEventListener('click', () => {
  if (currentUser && currentUser.rol) enterApp();
});

window.addEventListener('session-expired', () => { currentUser = null; showView('login'); });

const icons = {
  list: `<svg viewBox="0 0 24 24"><path d="M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2zm0 4h14v-2H7v2zM7 7v2h14V7H7z"/></svg>`,
  grid: `<svg viewBox="0 0 24 24"><path d="M4 4h6v6H4V4zm10 0h6v6h-6V4zM4 14h6v6H4v-6zm10 0h6v6h-6v-6z"/></svg>`,
  book: `<svg viewBox="0 0 24 24"><path d="M18 2H6c-1.1 0-2 .9-2 2v16c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zM6 4h5v8l-2.5-1.5L6 12V4z"/></svg>`,
  users: `<svg viewBox="0 0 24 24"><path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"/></svg>`,
  cash: `<svg viewBox="0 0 24 24"><path d="M11.8 10.9c-2.27-.59-3-1.2-3-2.15 0-1.09 1.01-1.85 2.7-1.85 1.78 0 2.44.85 2.5 2.1h2.21c-.07-1.72-1.12-3.3-3.21-3.81V3h-3v2.16c-1.94.42-3.5 1.68-3.5 3.61 0 2.31 1.91 3.46 4.7 4.13 2.5.6 3 1.48 3 2.41 0 .69-.49 1.79-2.7 1.79-2.06 0-2.87-.92-2.98-2.1h-2.2c.12 2.19 1.76 3.42 3.68 3.83V21h3v-2.15c1.95-.37 3.5-1.5 3.5-3.55 0-2.84-2.43-3.81-4.7-4.4z"/></svg>`,
  chart: `<svg viewBox="0 0 24 24"><path d="M3.5 18.49l6-6.01 4 4L22 6.92l-1.41-1.41-7.09 7.97-4-4L2 16.99z"/></svg>`,
  add: `<svg viewBox="0 0 24 24"><path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/></svg>`,
  trendUp: `<svg viewBox="0 0 24 24"><path d="M16 6l2.29 2.29-4.88 4.88-4-4L2 16.59 3.41 18l6-6 4 4 6.3-6.29L22 12V6z"/></svg>`,
  edit: `<svg viewBox="0 0 24 24"><path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>`,
  delete: `<svg viewBox="0 0 24 24"><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/></svg>`,
  addBox: `<svg viewBox="0 0 24 24"><path d="M19 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-2 10h-4v4h-2v-4H7v-2h4V7h2v4h4v2z"/></svg>`
};

function buildSidebar(rol) {
  const nav = document.getElementById('nav-menu');
  let html = '';
  if (rol === 'ADMINISTRADOR') {
    html = `
      <div class="nav-category">Operaciones del Salón</div>
      <div class="nav-item" data-view="dashboard">${icons.list.replace('<svg', '<svg class="icon-cyan"')} Control de Pedidos</div>
      <div class="nav-item" data-view="mesas">${icons.grid.replace('<svg', '<svg class="icon-pink"')} Mapa de Mesas</div>
      <div class="nav-category">Gestión de Catálogo</div>
      <div class="nav-item" data-view="productos">${icons.book.replace('<svg', '<svg class="icon-orange"')} Carta y Productos</div>
      <div class="nav-category">Administración</div>
      <div class="nav-item" data-view="usuarios">${icons.users.replace('<svg', '<svg class="icon-green"')} Directorio de Personal</div>
      <div class="nav-category">Análisis Comercial</div>
      <div class="nav-item" data-view="caja">${icons.cash.replace('<svg', '<svg class="icon-yellow"')} Historial de Caja</div>
      <div class="nav-item" data-view="stats">${icons.chart.replace('<svg', '<svg class="icon-purple"')} Estadísticas del Local</div>
    `;
  } else if (rol === 'MESERO') {
    html = `
      <div class="nav-category">Atención al Cliente</div>
      <div class="nav-item" data-view="mesas">${icons.grid.replace('<svg', '<svg class="icon-pink"')} Mesas y Comandas</div>
      <div class="nav-item" data-view="dashboard">${icons.list.replace('<svg', '<svg class="icon-cyan"')} Mis Pedidos Abiertos</div>
      <div class="nav-category">Facturación</div>
      <div class="nav-item" data-view="caja">${icons.cash.replace('<svg', '<svg class="icon-yellow"')} Registrar Cobro</div>
    `;
  }
  nav.innerHTML = html;
  nav.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('click', (e) => {
      nav.querySelectorAll('.nav-item').forEach(i => i.classList.remove('active'));
      e.currentTarget.classList.add('active');
      const view = e.currentTarget.getAttribute('data-view');
      if (view === 'mesas') loadMesasView();
      else loadAdminDashboard();
    });
  });
}

function enterApp() {
  if (!currentUser || !currentUser.rol) return;
  document.getElementById('user-nombre').textContent = `${currentUser.nombre} ${currentUser.apellido}`;
  document.getElementById('user-rol').textContent = currentUser.rol;
  document.getElementById('user-avatar').textContent = currentUser.nombre.charAt(0).toUpperCase();
  document.getElementById('current-date').textContent = new Date().toLocaleDateString('es-PE', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });
  showView('app');
  buildSidebar(currentUser.rol);
  if (currentUser.rol === 'ADMINISTRADOR') loadAdminDashboard();
  else if (currentUser.rol === 'MESERO') loadMesasView();
}

function loadAdminDashboard() {
  document.getElementById('page-title').textContent = 'Resumen Operativo';
  document.getElementById('content-area').innerHTML = `
    <div class="quick-actions">
      <button class="btn-action" onclick="loadMesasView()">${icons.grid} Abrir Mesa</button>
      <button class="btn-action">${icons.list} Nueva Comanda</button>
      <button class="btn-action">${icons.cash} Cobrar Mesa</button>
    </div>
    <div class="stats-grid">
      <div class="stat-card"><div class="stat-label">Ingresos del Día</div><div class="stat-value">S/ 3,450</div><div class="stat-sub">${icons.trendUp} +15% vs ayer</div></div>
      <div class="stat-card"><div class="stat-label">Ocupación de Salón</div><div class="stat-value">75%</div><div class="stat-sub" style="color: var(--text-muted)">18 de 24 mesas</div></div>
    </div>
    <h2 class="section-title">Actividad Reciente</h2>
    <table class="data-table">
      <thead><tr><th>N° Pedido</th><th>Mesa</th><th>Total</th><th>Estado</th></tr></thead>
      <tbody><tr><td>#0045</td><td>Mesa 12</td><td>S/ 120.50</td><td><span class="status-badge completado">Pagado</span></td></tr></tbody>
    </table>
  `;
}

async function loadMesasView() {
  document.getElementById('page-title').textContent = 'Mapa de Mesas';
  const content = document.getElementById('content-area');
  content.innerHTML = '<p style="color: var(--text-muted)">Cargando mapa...</p>';

  try {
    const [zonas, mesas] = await Promise.all([
      httpClient.get('/api/admin/zonas'),
      httpClient.get('/api/admin/mesas')
    ]);

    const mesasPorZona = mesas.reduce((acc, m) => {
      if (!acc[m.zonaId]) acc[m.zonaId] = [];
      acc[m.zonaId].push(m);
      return acc;
    }, {});

    let html = `
      <div class="map-header-container">
        <div class="map-title-group">
          <h1>Distribución del Local</h1>
          <p>Administra las zonas, áreas y mesas disponibles en Liverpool Restobar.</p>
        </div>
        <button class="btn-new-category" onclick="openZonaModal()">
          ${icons.add} Nueva Zona
        </button>
      </div>
    `;

    zonas.forEach(zona => {
      const mesasEnZona = mesasPorZona[zona.id] || [];
      html += `
        <div class="category-section">
          <div class="category-header">
            <h2 class="category-title">
              ${zona.nombre}
              <span class="category-badge">${mesasEnZona.length} Mesas</span>
            </h2>
            <div class="category-actions">
              <button class="btn-icon-small" onclick="openZonaModal(${zona.id}, '${zona.nombre.replace(/'/g, "\\'")}')" title="Editar Zona">${icons.edit}</button>
              <button class="btn-icon-small delete" onclick="confirmDeleteZona(${zona.id}, '${zona.nombre.replace(/'/g, "\\'")}')" title="Eliminar Zona">${icons.delete}</button>
            </div>
          </div>
          <div class="mesas-grid">
      `;

      mesasEnZona.forEach(m => {
        const estadoClass = m.estado === 'LIBRE' ? 'libre' : 'ocupada';
        html += `
          <div class="mesa-card ${estadoClass}">
            <div class="mesa-info">
              <div class="mesa-numero">${m.numero}</div>
              <div class="mesa-estado">${m.estado}</div>
            </div>
            <div class="mesa-controls">
              <button class="btn-control edit" onclick="openMesaModal(${zona.id}, ${m.id}, '${m.numero}')">
                ${icons.edit} Editar
              </button>
              <button class="btn-control delete" onclick="confirmDeleteMesa(${m.id}, '${m.numero}')">
                ${icons.delete} Eliminar
              </button>
            </div>
          </div>
        `;
      });

      html += `
          <div class="add-mesa-card" onclick="openMesaModal(${zona.id})">
            ${icons.addBox}
            <span>Nueva Mesa</span>
          </div>
        </div>
      </div>
      `;
    });
    
    content.innerHTML = html;
  } catch (error) {
    showToast("Error al cargar mapa: " + error.message, 'error');
    content.innerHTML = `<div class="empty-state" style="color: var(--danger)">Error de conexión.</div>`;
  }
}

function showModal(title, contentHTML, confirmText, onConfirm, isDanger = false) {
  modalRoot.innerHTML = `
    <div class="modal-overlay active" id="modal-overlay">
      <div class="modal-box">
        <h3 class="modal-title">${title}</h3>
        <div class="modal-body">${contentHTML}</div>
        <div class="modal-footer">
          <button class="btn-modal cancel" onclick="closeModal()">Cancelar</button>
          <button class="btn-modal ${isDanger ? 'danger' : 'confirm'}" id="modal-confirm-btn">${confirmText}</button>
        </div>
      </div>
    </div>
  `;
  document.getElementById('modal-confirm-btn').addEventListener('click', onConfirm);
  document.getElementById('modal-overlay').addEventListener('click', (e) => {
    if (e.target.id === 'modal-overlay') closeModal();
  });
}

window.closeModal = function() {
  const overlay = document.getElementById('modal-overlay');
  if (overlay) {
    overlay.classList.remove('active');
    setTimeout(() => { modalRoot.innerHTML = ''; }, 300);
  }
};

window.openZonaModal = function(id = null, nombreActual = '') {
  const isEdit = id !== null;
  showModal(isEdit ? 'Editar Zona' : 'Nueva Zona', `
    <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1rem;">Ingresa el nombre de la zona o área del local.</p>
    <input type="text" id="input-zona" class="modal-input" placeholder="Ej. Salón VIP, Terraza, Patio..." value="${nombreActual}">
  `, isEdit ? 'Guardar Cambios' : 'Crear Zona', async () => {
    const nombre = document.getElementById('input-zona').value.trim();
    if (!nombre) return showToast("El nombre de la zona es obligatorio.", 'error');

    try {
      if (isEdit) await httpClient.put(`/api/admin/zonas/${id}`, { nombre });
      else await httpClient.post('/api/admin/zonas', { nombre });
      loadMesasView();
      closeModal();
      showToast(isEdit ? "Zona actualizada" : "Zona creada exitosamente", 'success');
    } catch (error) {
      showToast(error.message, 'error');
    }
  });
};

window.confirmDeleteZona = function(id, nombre) {
  showModal('Eliminar Zona', `
    <p style="color: var(--text-main); font-size: 1rem; text-align: center;">¿Eliminar la zona <strong>${nombre}</strong>?</p>
    <p style="color: var(--danger); font-size: 0.85rem; text-align: center; margin-top: 0.5rem;">Se eliminarán todas las mesas asociadas a esta zona.</p>
  `, 'Sí, Eliminar', async () => {
    try {
      await httpClient.delete(`/api/admin/zonas/${id}`);
      loadMesasView();
      closeModal();
      showToast("Zona eliminada", 'success');
    } catch (error) {
      showToast("Error al eliminar: " + error.message, 'error');
    }
  }, true);
};

window.openMesaModal = function(zonaId, mesaId = null, numeroActual = '') {
  const isEdit = mesaId !== null;
  showModal(isEdit ? 'Editar Mesa' : 'Nueva Mesa', `
    <p style="color: var(--text-muted); font-size: 0.9rem; margin-bottom: 1rem;">Ingresa el número de la mesa.</p>
    <input type="text" id="input-mesa-num" class="modal-input" placeholder="Ej. M1, M2..." value="${numeroActual}">
  `, isEdit ? 'Guardar Cambios' : 'Crear Mesa', async () => {
    const numero = document.getElementById('input-mesa-num').value.trim();
    if (!numero) return showToast("El número de mesa es obligatorio.", 'error');

    try {
      if (isEdit) await httpClient.put(`/api/admin/mesas/${mesaId}`, { numero, zonaId });
      else await httpClient.post('/api/admin/mesas', { numero, zonaId });
      loadMesasView();
      closeModal();
      showToast(isEdit ? "Mesa actualizada" : "Mesa creada exitosamente", 'success');
    } catch (error) {
      showToast(error.message, 'error');
    }
  });
};

window.confirmDeleteMesa = function(mesaId, numeroMesa) {
  showModal('Eliminar Mesa', `
    <p style="color: var(--text-main); font-size: 1rem; text-align: center;">¿Estás seguro de que deseas eliminar la <strong>Mesa ${numeroMesa}</strong>?</p>
    <p style="color: var(--danger); font-size: 0.85rem; text-align: center; margin-top: 0.5rem;">Esta acción no se puede deshacer.</p>
  `, 'Sí, Eliminar', async () => {
    try {
      await httpClient.delete(`/api/admin/mesas/${mesaId}`);
      loadMesasView();
      closeModal();
      showToast("Mesa eliminada", 'success');
    } catch (error) {
      showToast("Error al eliminar: " + error.message, 'error');
    }
  }, true);
};

init();
