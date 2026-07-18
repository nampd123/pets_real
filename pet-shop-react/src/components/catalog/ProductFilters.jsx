export default function ProductFilters({ filter, onChange }) {
  const filters = ['all', 'thucung', 'thucan', 'phukien'];

  return (
    <div className="filters">
      {filters.map((key) => (
        <button key={key} className={filter === key ? 'filter active' : 'filter'} onClick={() => onChange(key)}>
          {key === 'all' ? 'Tất cả' : key === 'thucung' ? 'Thú cưng' : key === 'thucan' ? 'Thức ăn' : 'Phụ kiện'}
        </button>
      ))}
    </div>
  );
}
