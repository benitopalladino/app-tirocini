<!DOCTYPE html>
<html>
<head>
    <title>Azienda</title>
</head>
<body>

<h1>
    <#if company.id??>
        Modifica azienda
    <#else>
        Nuova azienda
    </#if>
</h1>

<#if error??>
     <div style="color:red">${error}</div>
</#if>

<form action="/companies" method="post" novalidate>

    <#if company.id??>
         <input type="hidden" name="id" value="${company.id}">
    </#if>

    <label>Nome:</label>
    <input type="text" name="name" value="${company.name!}">
    <#if errors?has_content && errors.hasFieldErrors("name")>
        <div style="color:red">${errors.getFieldError("name").defaultMessage}</div>
    </#if>
    <br>

    <label>Città:</label>
    <input type="text" name="city" value="${company.city!}">
    <#if errors?has_content && errors.hasFieldErrors("city")>
        <div style="color:red">${errors.getFieldError("city").defaultMessage}</div>
    </#if>
    <br>

    <label>Referente:</label>
    <input type="text" name="contactName" value="${company.contactName!}">
    <#if errors?has_content && errors.hasFieldErrors("contactName")>
        <div style="color:red">${errors.getFieldError("contactName").defaultMessage}</div>
    </#if>
    <br>

    <label>Email Referente:</label>
    <input type="email" name="contactEmail" value="${company.contactEmail!}">
    <#if errors?has_content && errors.hasFieldErrors("contactEmail")>
        <div style="color:red">${errors.getFieldError("contactEmail").defaultMessage}</div>
    </#if>
    <br><br>

    <button type="submit">Salva</button>
    <a href="/companies">Annulla</a>
</form>

</body>
</html>