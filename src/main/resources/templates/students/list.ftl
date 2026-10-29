<!DOCTYPE html>
<html>
<head>
    <title>Studenti</title>
</head>
<body>

<h1>Studenti</h1>

<a href="/students/new">+ Nuovo Studente</a>

<#if message??>
     <div style="color:green">${message}</div>
</#if>
<#if error??>
     <div style="color:red">${error}</div>
</#if>

<table border="1" cellpadding="6">
    <tr>
        <th>Matricola</th>
        <th>Nome</th>
        <th>Cognome</th>
        <th>Email</th>
        <th>Azioni</th>
    </tr>

    <#list students as s>
        <tr>
            <td>${s.matricola}</td>
            <td>${s.firstName}</td>
            <td>${s.lastName}</td>
            <td>${s.email}</td>
            <td>
                <a href="/students/${s.id}/edit">Modifica</a>
                <form action="/students/${s.id}/delete" method="post" style="display:inline">
                    <button type="submit">Elimina</button>
                </form>
            </td>
        </tr>
    </#list>
</table>

</body>
</html>