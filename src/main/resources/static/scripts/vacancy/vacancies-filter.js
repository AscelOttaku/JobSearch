function onClickFilter(event) {
    const filterArg = event.currentTarget
    const href = filterArg.href;
    const url = new URL(href);

    if (!href.includes('filterType'))
        return fetch(href)
    else {
        localStorage.setItem('filterType',url.searchParams.get('filterType'));
        return fetch(href)
    }
}

function sendUri(event) {
    event.preventDefault()
    const url = event.currentTarget.href;
    console.log(url)
    const filterArg = localStorage.getItem('filterType');

    if (!filterArg || url.includes('filterType'))
       window.location.href = url;

    return window.location.href = url + "?filterType=" + filterArg
}