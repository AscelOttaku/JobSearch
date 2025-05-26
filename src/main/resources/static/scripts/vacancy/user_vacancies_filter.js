function onClickFilter(event) {
    const filterArg = event.currentTarget
    const href = filterArg.href;
    const url = new URL(href);

    if (href.includes('filterType'))
        localStorage.setItem('user_vacancies_filterType', url.searchParams.get('filterType'));
}

function sendUri(event) {
    event.preventDefault()
    let url = event.currentTarget.href;
    console.log(url)
    const filterArg = localStorage.getItem('user_vacancies_filterType');

    if (!filterArg || url.includes('filterType'))
        return window.location.href = url;

    return window.location.href = url + "/filtered?filterType=" + filterArg
}
