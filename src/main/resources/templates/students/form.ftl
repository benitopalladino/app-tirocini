<!DOCTYPE html>
<html>
<head>
    <title>Studente</title>
</head>
<body>

<h1>
    <#if student.id??>
        Modifica studente
    <#else>
        Nuovo studente
    </#if>
</h1>

<#if error??>
     <div style="color:red">${error}</div>
</#if>

<form action="/students" method="post" novalidate>

    <#if student.id??>
        <input type="hidden" name="id" value="${student.id}">
    </#if>

    <label>Matricola:</label>
    <input type="text" name="matricola" value="${student.matricola!}">
    <#if errors?has_content && errors.hasFieldErrors("matricola")>
        <div style="color:red">${errors.getFieldError("matricola").defaultMessage}</div>
    </#if>
    <br>

    <label>Nome:</label>
    <input type="text" name="firstName" value="${student.firstName!}">
    <#if errors?has_content && errors.hasFieldErrors("firstName")>
        <div style="color:red">${errors.getFieldError("firstName").defaultMessage}</div>
    </#if>
    <br>

    <label>Cognome:</label>
    <input type="text" name="lastName" value="${student.lastName!}">
    <#if errors?has_content && errors.hasFieldErrors("lastName")>
        <div style="color:red">${errors.getFieldError("lastName").defaultMessage}</div>
    </#if>
    <br>

    <label>Email:</label>
    <input type="email" name="email" value="${student.email!}">
    <#if errors?has_content && errors.hasFieldErrors("email")>
        <div style="color:red">${errors.getFieldError("email").defaultMessage}</div>
    </#if>
    <br><br>

    <button type="submit">Salva</button>
    <a href="/students">Annulla</a>
</form>

</body>
</html>