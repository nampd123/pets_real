import { useEffect, useState } from 'react';
import { fetchCatalog } from '../services/api';

export function useCatalog() {
  const [catalog, setCatalog] = useState([]);
  const [loadingCatalog, setLoadingCatalog] = useState(true);
  const [catalogError, setCatalogError] = useState('');
  const [filter, setFilter] = useState('all');

  function reloadCatalog() {
    let active = true;
    setLoadingCatalog(true);

    fetchCatalog()
      .then((items) => {
        if (active) {
          setCatalog(items);
          setCatalogError('');
        }
      })
      .catch((error) => {
        if (active) {
          setCatalogError(error.message);
        }
      })
      .finally(() => {
        if (active) {
          setLoadingCatalog(false);
        }
      });

    return () => {
      active = false;
    };
  }

  useEffect(() => {
    const cleanup = reloadCatalog();
    return cleanup;
  }, []);

  return {
    catalog,
    setCatalog,
    reloadCatalog,
    loadingCatalog,
    catalogError,
    setCatalogError,
    filter,
    setFilter
  };
}
