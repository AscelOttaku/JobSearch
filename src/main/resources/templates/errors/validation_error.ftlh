<!DOCTYPE html>
<html lang="validation">
<head>
    <title>Validation Error</title>
</head>
<body>
<h1>Validation Error</h1>
<p><strong>Status Code:</strong> ${exception.statusCode}</p>
<p><strong>Method:</strong> ${exception.method}</p>
<p><strong>Error:</strong> ${exception.error}</p>
<p><strong>Cause:</strong> ${exception.cause}</p>
<p><strong>Message:</strong> ${exception.message}</p>
<p><strong>Exception:</strong> ${exception.exception}</p>
<p><strong>Path:</strong> ${exception.path}</p>

<#if exception.errors?? && exception.errors?size gt 0>
    <h2>Validation Errors</h2>
    <ul>
        <#list exception.errors as err>
            <li>
                <strong>Object:</strong> ${err.objectName} <br>
                <strong>Field:</strong> ${err.fieldName} <br>
                <strong>Rejected Value:</strong> ${err.rejectedValue} <br>
                <strong>Message:</strong> ${err.message}
            </li>
        </#list>
    </ul>
</#if>
</body>
</html>