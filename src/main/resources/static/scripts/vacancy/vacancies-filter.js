function onClickFilter(event) {
    const filterArg = event.currentTarget
    const href = filterArg.href;
    const url = new URL(href);
    const authUser = window.currentUser

    if (href.includes('filterType'))
        if (authUser) {
            const userFilterInfo = {user: authUser, filterType: url.searchParams.get('filterType')}
            localStorage.setItem('userFilterInfo', JSON.stringify(userFilterInfo))
        } else
            localStorage.setItem('filterType', url.searchParams.get('filterType'));
}

function sendUri(event) {
    event.preventDefault()
    let url = event.currentTarget.href;
    console.log(url)
    let filterArg = JSON.parse(localStorage.getItem('userFilterInfo'))

    if (!filterArg || filterArg.user === window.currentUser) {
        filterArg = localStorage.getItem('filterType');

        if (!filterArg || url.includes('filterType'))
            return window.location.href = url;
        else {
            url = url.replace('actives', 'filtered');
            return window.location.href = url + "?filterType=" + filterArg
        }
    }

    url = url.replace('actives', 'filtered');
    return window.location.href = url + "?filterType=" + filterArg.filterType;
}
