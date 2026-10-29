<!DOCTYPE html>
<html>
<head>
    <title>Aziende</title>
</head>
<body>

<h1>Aziende</h1>

<a href="/companies/new">+ Nuova azienda</a>

<#if message??>
     <div style="color:green">${message}</div>
</#if>
<#if error??>
     <div style="color:red">${error}</div>
</#if>

<table border="1" cellpadding="6">
    <tr>
        <th>Nome</th>
        <th>Città</th>
        <th>Referente</th>
        <th>Email Referente</th>
        <th>Azioni</th>
    </tr>

    <#list companies as c>
         <tr>
             <td>${c.name}</td>
             <td>${c.city!}</td>
             <td>${c.contactName!}</td>
             <td>${c.contactEmail!}</td>
             <td>
                 <a href="/companies/${c.id}/edit">Modifica</a>
                 <form action="/companies/${c.id}/delete" method="post" style="display:inline">
                     <button type="submit">Elimina</button>
                 </form>
             </td>
         </tr>
    </#list>
</table>

</body>
</html>