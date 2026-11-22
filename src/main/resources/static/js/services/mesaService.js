import { httpClient } from './httpClient.js';

export const mesaService = {
  listarLibres: () => httpClient.get('/api/mesero/mesas/libres'),
  listarOcupadas: () => httpClient.get('/api/mesero/mesas/ocupadas'),
  liberar: (mesaId) => httpClient.post(`/api/mesero/mesas/${mesaId}/liberar`)
};
