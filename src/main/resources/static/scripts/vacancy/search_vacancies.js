const searchInput = document.getElementById('searchInput');
const searchResults = document.getElementById('searchResults');
const clearSearchBtn = document.getElementById('clearSearchBtn');

searchInput.addEventListener('input', function () {
    const query = this.value.trim();
    if (query.length < 2) {
        searchResults.style.display = 'none';
        clearSearchBtn.style.display = 'none';
        return;
    }
    clearSearchBtn.style.display = 'inline-block';

    fetch(`/vacancies/search?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            if (data.length === 0) {
                searchResults.innerHTML = '<div class="p-2 text-muted">Ничего не найдено</div>';
            } else {
                searchResults.innerHTML = data.map(vacancy =>
                    `<div class="search-result-item p-2 border-bottom">
                        <a href="/vacancies/${vacancy.vacancyId}" class="text-decoration-none">${vacancy.name}</a>
                     </div>`).join('');
            }
            searchResults.style.display = 'block';
        })
        .catch(err => {
            console.error(err);
            searchResults.innerHTML = '<div class="p-2 text-danger">Ошибка поиска</div>';
            searchResults.style.display = 'block';
        });
});

clearSearchBtn.addEventListener('click', () => {
    searchInput.value = '';
    searchResults.style.display = 'none';
    clearSearchBtn.style.display = 'none';
});
