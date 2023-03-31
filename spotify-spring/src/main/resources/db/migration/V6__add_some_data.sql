UPDATE track
SET image_url = 'https://upload.wikimedia.org/wikipedia/en/6/6d/Breakingbenjamindearagony.jpg',
    is_liked  = true,
    genre = 'ROCK'
WHERE id = 39;

UPDATE track
SET image_url = 'https://i.scdn.co/image/ab67616d0000b2734202da6f60a9bc8c6c3d1559',
    is_liked  = true,
    genre = 'ROCK'
WHERE id = 13;

UPDATE track
SET image_url = 'https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/3a/33/17/3a3317c5-d8ad-8973-b729-7292f1c06c34/cover.jpg/1200x1200bf-60.jpg',
    is_liked = true,
    genre = 'ROCK'
WHERE id = 23;

UPDATE track
SET image_url = 'https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/3a/33/17/3a3317c5-d8ad-8973-b729-7292f1c06c34/cover.jpg/1200x1200bf-60.jpg',
    is_liked = true,
    genre = 'ROCK'
WHERE id = 43;

UPDATE track
SET image_url = 'https://is1-ssl.mzstatic.com/image/thumb/Music114/v4/3a/33/17/3a3317c5-d8ad-8973-b729-7292f1c06c34/cover.jpg/1200x1200bf-60.jpg',
    is_liked = true,
    genre = 'ROCK'
WHERE id = 27;

UPDATE track
SET genre = 'POP'
WHERE id IN(1, 2, 3, 4, 5);

UPDATE track
SET genre = 'RAP'
WHERE id IN(6, 7, 8, 9, 10);

UPDATE track
SET genre = 'SOUNDTRACK'
WHERE id IN(45, 42, 11, 12, 28);

