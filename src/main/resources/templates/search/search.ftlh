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

        .search-icon-link {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            color: #0d6efd;
            transition: all 0.2s ease;
            margin-bottom: 1rem;
        }

        .search-icon-link:hover {
            color: #0b5ed7;
            background-color: #e7f1ff;
            transform: scale(1.1);
            text-decoration: none;
        }

        .search-icon-link:active {
            transform: scale(0.95);
        }

        .search-icon-link:focus {
            outline: 2px solid #0d6efd;
            outline-offset: 2px;
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
            transition: background-color 0.2s ease;
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

        @media (max-width: 992px) {
            .search-container form {
                display: none !important;
            }
            .search-icon-link {
                display: flex !important;
            }
        }
    </style>

    <body class="bg-light">
    <div class="container py-5">
        <div class="search-container">
            <a class="nav-link search-link" href="/vacancies/search/vacancies/dynamic/page"
               style="position: relative;
              display: inline-flex;
              align-items: center;
              padding: 0.5rem 1rem;
              margin: 0 0.5rem;
              color: #2c3e50;
              background-color: #f8f9fa;
              border-radius: 50px;
              transition: all 0.3s ease;
              box-shadow: 0 2px 5px rgba(0,0,0,0.1);">

                <i class="bi bi-search me-2"
                   style="font-size: 1.1rem;
                  color: #e74c3c;"></i>

                <span style="font-weight: 500;">
            <@spring.message "custom_search"/>
        </span>

                <span style="position: absolute;
               bottom: 0;
               left: 50%;
               width: 0;
               height: 2px;
               background-color: #e74c3c;
               transition: all 0.3s ease;"></span>
            </a>
        </div>
        <div class="search-container">
            <form action="#" method="get" id="searchForm" class="d-none d-lg-block" onsubmit="return false;">
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
                            style="display:none;"
                            aria-label="Clear search">
                        <i class="bi bi-x-circle"></i>
                    </button>
                    <button class="btn btn-primary" type="submit" aria-label="Submit search">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </form>

            <div id="searchResults" class="search-results-dropdown">
                <div class="list-group list-group-flush">
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
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });

        });
    </script>
    <script src="/static/scripts/vacancy/search_vacancies.js"></script>
    </body>
</@layout.layout>