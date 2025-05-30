<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Enhanced Search</title>

    <style>
        .search-container {
            position: relative;
            max-width: 600px;
            margin: 20px auto;
        }

        .search-results-dropdown {
            position: absolute;
            width: 100%;
            background: #fff;
            border: 1px solid #dee2e6;
            border-radius: 0.375rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
            z-index: 1000;
            display: none;
            max-height: 300px;
            overflow-y: auto;
            margin-top: 0.25rem;
        }

        .search-results-dropdown .list-group-item {
            border-left: none;
            border-right: none;
        }

        .search-results-dropdown .list-group-item:first-child {
            border-top: none;
        }

        .search-results-dropdown .list-group-item:last-child {
            border-bottom: none;
        }

        .search-results-dropdown .list-group-item:hover {
            background-color: #f8f9fa;
        }

        #searchInput:focus {
            border-color: #86b7fe;
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }
    </style>
    </head>
    <body class="bg-light">
    <div class="container py-5">
        <div class="search-container">
            <form action="#" method="get" id="searchForm" onsubmit="return false;">
                <div class="input-group">
                    <input id="searchInput"
                           class="form-control form-control-lg"
                           type="search"
                           name="query"
                           placeholder="Search vacancies..."
                           aria-label="Search"
                           autocomplete="off">
                    <button class="btn btn-outline-primary"
                            type="button"
                            id="clearSearchBtn"
                            style="display:none;">
                        <i class="bi bi-x-circle"></i>
                    </button>
                    <button class="btn btn-primary" type="submit">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </form>

            <div id="searchResults" class="search-results-dropdown">
                <!-- Search results will be dynamically inserted here -->
                <div class="list-group list-group-flush">
                    <!-- Example result item (will be replaced by dynamic content) -->
                    <a href="#" class="list-group-item list-group-item-action">
                        <div class="d-flex w-100 justify-content-between">
                            <h6 class="mb-1">Sample Vacancy Title</h6>
                            <small class="text-muted">Moscow</small>
                        </div>
                        <p class="mb-1">Brief description of the vacancy goes here...</p>
                        <small class="text-muted">Company Name</small>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script src="/static/scripts/vacancy/search_vacancies.js"></script>
    </body>
</@layout.layout>