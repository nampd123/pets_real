-- Update hardcoded example.local image URLs to /uploads paths
UPDATE pet_images
SET image_url = REPLACE(image_url, 'https://images.example.local/pets/', '/uploads/pets/')
WHERE image_url LIKE 'https://images.example.local/pets/%';
