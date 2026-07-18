export function classifyProduct(product) {
  const speciesName = (product.species?.name || '').toLowerCase();
  const text = `${product.name || ''} ${product.description || ''}`.toLowerCase();

  if (speciesName.includes('thức ăn') || speciesName.includes('food')) {
    return 'thucan';
  }
  if (speciesName.includes('phụ kiện') || speciesName.includes('accessory')) {
    return 'phukien';
  }
  if (speciesName.includes('dog') || speciesName.includes('cat') || speciesName.includes('thú cưng') || speciesName.includes('chó') || speciesName.includes('mèo') || speciesName.includes('hamster') || speciesName.includes('vẹt') || speciesName.includes('thỏ')) {
    return 'thucung';
  }

  if (text.includes('chó') || text.includes('mèo') || text.includes('hamster') || text.includes('vẹt') || text.includes('thỏ')) {
    return 'thucung';
  }
  if (text.includes('thức ăn') || text.includes('pate') || text.includes('hạt') || text.includes('bánh thưởng') || text.includes('royal') || text.includes('whiskas')) {
    return 'thucan';
  }
  return 'phukien';
}

export function labelCategory(product) {
  const category = classifyProduct(product);
  return category === 'thucung' ? 'Thú cưng' : category === 'thucan' ? 'Thức ăn' : 'Phụ kiện';
}

export function productCategoryKey(product) {
  return classifyProduct(product);
}

export function getCategoryFromSpeciesName(speciesName) {
  const normalized = (speciesName || '').toString().trim().toLowerCase();
  if (!normalized) {
    return 'thucung';
  }

  if (normalized.includes('thức ăn') || normalized.includes('food')) {
    return 'thucan';
  }
  if (normalized.includes('phụ kiện') || normalized.includes('accessory')) {
    return 'phukien';
  }
  return 'thucung';
}
