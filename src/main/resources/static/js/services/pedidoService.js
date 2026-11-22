import { httpClient } from './httpClient.js';

export const pedidoService = {
  iniciar: (mesaId) => httpClient.post('/api/mesero/pedidos', { mesaId }),
  obtener: (id) => httpClient.get(`/api/mesero/pedidos/${id}`),
  agregarItem: (pedidoId, productoId, cantidad) => httpClient.post(`/api/mesero/pedidos/${pedidoId}/items`, { productoId, cantidad }),
  enviar: (pedidoId) => httpClient.post(`/api/mesero/pedidos/${pedidoId}/enviar`),
  cobrar: (pedidoId, tipo) => httpClient.post(`/api/mesero/pedidos/${pedidoId}/pagar`, { tipo }),
  listarPendientes: (area) => httpClient.get(`/api/${area}/pedidos/pendientes`),
  marcarPreparacion: (area, pedidoId) => httpClient.post(`/api/${area}/pedidos/${pedidoId}/preparacion`),
  marcarListo: (area, pedidoId) => httpClient.post(`/api/${area}/pedidos/${pedidoId}/listo`)
};
