<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
    <div class="container">
        <a class="navbar-brand" href="/">
            <i class="bi bi-briefcase me-2"></i>JobSearch
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/vacancies">
                        <i class="bi bi-search me-1"></i> Вакансии
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/resumes">
                        <i class="bi bi-file-earmark-text me-1"></i> Резюме
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/users/profile/1">
                        <i class="bi bi-clock-history me-1"></i> Профиль
                    </a>
                </li>
            </ul>

            <div class="d-flex">
                <form class="d-flex me-3" action="/search" method="get">
                    <div class="input-group">
                        <input class="form-control" type="search" name="query" placeholder="Поиск..." aria-label="Search">
                        <button class="btn btn-outline-light" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                </form>

                <#if user??>
                    <div class="dropdown">
                        <a class="btn btn-outline-light dropdown-toggle" href="#" role="button" id="userMenu" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle me-1"></i> ${user.name!"Профиль"}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="/users/profile"><i class="bi bi-person me-2"></i>Профиль</a></li>
                            <li><a class="dropdown-item" href="/users/responses"><i class="bi bi-envelope me-2"></i>Отклики</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="/logout"><i class="bi bi-box-arrow-right me-2"></i>Выйти</a></li>
                        </ul>
                    </div>
                <#else>
                    <a class="btn btn-outline-light me-2" href="/auth/login">
                        <i class="bi bi-box-arrow-in-right me-1"></i> Вход
                    </a>
                    <a class="btn btn-primary" href="/auth/register">
                        <i class="bi bi-person-plus me-1"></i> Регистрация
                    </a>
                </#if>
            </div>
        </div>
    </div>
</nav>