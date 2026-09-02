import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Convertit le statut backend (ex: "LIVRE") vers le format attendu par le
// frontend (ex: "livre"), pour rester compatible avec StatusBadge, FilterBar, etc.
function normalizeShipment(shipment) {
  return {
    ...shipment,
    statut: shipment.statut ? shipment.statut.toLowerCase() : shipment.statut,
  };
}

export async function fetchShipments(filters = {}) {
  const params = {};

  if (filters.code) params.code = filters.code;
  if (filters.statut) params.statut = filters.statut;
  if (filters.pod) params.pod = filters.pod;
  if (filters.dateDepotFrom) params.dateDepotFrom = filters.dateDepotFrom;
  if (filters.dateDepotTo) params.dateDepotTo = filters.dateDepotTo;
  if (filters.dateStatutFrom) params.dateStatutFrom = filters.dateStatutFrom;
  if (filters.dateStatutTo) params.dateStatutTo = filters.dateStatutTo;

  const response = await api.get('/shipments', { params });
  return response.data.map(normalizeShipment);
}

export async function fetchShipmentByCode(code) {
  const response = await api.get(`/shipments/${code}`);
  return normalizeShipment(response.data);
}

export async function fetchStatistics(filters = {}) {
  const params = {};

  if (filters.code) params.code = filters.code;
  if (filters.statut) params.statut = filters.statut;
  if (filters.pod) params.pod = filters.pod;
  if (filters.dateDepotFrom) params.dateDepotFrom = filters.dateDepotFrom;
  if (filters.dateDepotTo) params.dateDepotTo = filters.dateDepotTo;
  if (filters.dateStatutFrom) params.dateStatutFrom = filters.dateStatutFrom;
  if (filters.dateStatutTo) params.dateStatutTo = filters.dateStatutTo;

  const response = await api.get('/shipments/statistics', { params });
  return response.data;
}

export default api;