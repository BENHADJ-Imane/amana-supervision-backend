import { useState, useEffect } from 'react';
import { fetchShipments } from '../../services/api';
import { EMPTY_FILTERS } from '../../utils/filterShipments';
import FilterBar from '../../components/filters/FilterBar/FilterBar';
import ShipmentsTable from '../../components/table/ShipmentsTable/ShipmentsTable';
import Pagination from '../../components/table/Pagination/Pagination';

function Shipments() {
  const [filters, setFilters] = useState(EMPTY_FILTERS);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [shipments, setShipments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    fetchShipments(filters)
      .then((data) => setShipments(data))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [filters]);

  const totalPages = Math.max(1, Math.ceil(shipments.length / pageSize));
  const safePage = Math.min(currentPage, totalPages);
  const paginatedShipments = shipments.slice(
    (safePage - 1) * pageSize,
    safePage * pageSize
  );

  const handleFiltersChange = (newFilters) => {
    setFilters(newFilters);
    setCurrentPage(1);
  };

  const handlePageSizeChange = (newSize) => {
    setPageSize(newSize);
    setCurrentPage(1);
  };

  return (
    <div>
      <h1>Mes envois</h1>
      <FilterBar
        filters={filters}
        onChange={handleFiltersChange}
        onReset={handleFiltersChange}
      />

      {loading && <p className="results-count">Chargement...</p>}
      {error && <p className="results-count">Erreur : {error}</p>}

      {!loading && !error && (
        <>
          <p className="results-count">{shipments.length} Colis</p>
          <ShipmentsTable shipments={paginatedShipments} />
          <Pagination
            currentPage={safePage}
            totalPages={totalPages}
            pageSize={pageSize}
            onPageChange={setCurrentPage}
            onPageSizeChange={handlePageSizeChange}
          />
        </>
      )}
    </div>
  );
}

export default Shipments;