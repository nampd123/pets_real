const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    },
    ...options
  });

  if (!response.ok) {
    let message = 'Tài khoản hoặc mật khẩu không chính xác';
    try {
      const errorBody = await response.json();
      if (response.status === 401) {
        message = errorBody.message || message;
      } else {
        message = errorBody.message || errorBody.error || message;
      }
    } catch {
      const text = await response.clone().text();
      if (text) {
        message = response.status === 401 ? 'Tài khoản hoặc mật khẩu không chính xác' : text;
      }
    }
    throw new Error(message);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export async function fetchCatalog() {
  const [pets, images, shops, species] = await Promise.all([
    request('/api/pets'),
    request('/api/pet-images'),
    request('/api/shops'),
    request('/api/pet-species')
  ]);

  const imageMap = new Map();
  images.forEach((image) => {
    // Normalize image URLs: replace hardcoded domain with local /uploads path
    let normalizedUrl = image.imageUrl;
    if (normalizedUrl.includes('images.example.local')) {
      normalizedUrl = normalizedUrl.replace('https://images.example.local', '/uploads');
    }
    
    if (!imageMap.has(image.petId)) {
      imageMap.set(image.petId, normalizedUrl);
    }
    if (image.thumbnail) {
      imageMap.set(image.petId, normalizedUrl);
    }
  });

  const shopMap = new Map(shops.map((shop) => [shop.id, shop]));
  const speciesMap = new Map(species.map((item) => [item.id, item]));

  return pets.map((pet) => ({
    ...pet,
    imageUrl: imageMap.get(pet.id) || 'https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=900&q=80',
    shop: shopMap.get(pet.shopId) || null,
    species: speciesMap.get(pet.speciesId) || null
  }));
}

export async function login(payload) {
  return request('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function register(payload) {
  return request('/api/auth/register', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function getOrCreateCart(userId) {
  try {
    return await request(`/api/carts/by-user/${userId}`);
  } catch {
    return request(`/api/carts/by-user/${userId}`, { method: 'POST' });
  }
}

export async function getCartItems(cartId) {
  return request(`/api/cart-items/by-cart/${cartId}`);
}

export async function upsertCartItem(payload) {
  return request('/api/cart-items/upsert', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function deleteCartItem(itemId) {
  return request(`/api/cart-items/${itemId}`, { method: 'DELETE' });
}

export async function createOrder(payload) {
  return request('/api/orders', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function createOrderItem(payload) {
  return request('/api/order-items', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function createPayment(payload) {
  return request('/api/payments', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function fetchOrders() {
  return request('/api/orders');
}

export async function fetchOrderItems() {
  return request('/api/order-items');
}

export async function createPet(payload) {
  return request('/api/pets', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function createPetImage(payload) {
  return request('/api/pet-images', {
    method: 'POST',
    body: JSON.stringify(payload)
  });
}

export async function uploadPetImage(petId, file) {
  const formData = new FormData();
  formData.append('petId', petId);
  formData.append('imageFile', file);

  const response = await fetch(`${API_BASE_URL}/api/pet-images/upload`, {
    method: 'POST',
    body: formData
  });

  if (!response.ok) {
    let message = 'Request failed';
    try {
      const errorBody = await response.json();
      message = errorBody.message || errorBody.error || message;
    } catch {
      const text = await response.clone().text();
      if (text) {
        message = text;
      }
    }
    throw new Error(message);
  }

  return response.json();
}

export async function updatePet(petId, payload) {
  return request(`/api/pets/${petId}`, {
    method: 'PUT',
    body: JSON.stringify(payload)
  });
}

export async function deletePet(petId) {
  return request(`/api/pets/${petId}`, {
    method: 'DELETE'
  });
}

export async function fetchPets() {
  return request('/api/pets');
}

export async function fetchShops() {
  return request('/api/shops');
}

export async function fetchSpecies() {
  return request('/api/pet-species');
}

export async function fetchBreeds() {
  return request('/api/pet-breeds');
}