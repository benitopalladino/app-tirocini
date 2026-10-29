<!DOCTYPE html>
<html>
<head>
    <title>Tirocini</title>
</head>
<body>

<h1>Tirocini</h1>

<a href="/internships/new">+ Nuovo tirocinio</a>

<h3>Filtri</h3>
<form action="/internships" method="get">

    <label>Azienda:</label>
    <select name="companyId">
        <option value="">-- tutte --</option>
        <#list companies as c>
            <option value="${c.id}" <#if companyId?? && (companyId == c.id)>selected</#if>>
                ${c.name}
            </option>
        </#list>
    </select>

    <label>Status:</label>
    <select name="status">
        <option value="">-- tutti --</option>
        <#list statuses as st>
            <option value="${st}" <#if status?? && (status == st)>selected</#if>>
                ${st}
            </option>
        </#list>
    </select>

    <label>Da:</label>
    <input type="date" name="from" value="${from!}">

    <label>A:</label>
    <input type="date" name="to" value="${to!}">

    <button type="submit">Filtra</button>
</form>

<br>

<#if message??>
    <div style="color:green">${message}</div>
</#if>
<#if error??>
    <div style="color:red">${error}</div>
</#if>

<table border="1" cellpadding="6">
    <tr>
        <th>Studente</th>
        <th>Azienda</th>
        <th>Data Inizio</th>
        <th>Data Fine</th>
        <th>Status</th>
        <th>Azioni</th>
    </tr>

    <#list internships.content as i>
        <tr>
            <td>${i.student.firstName} ${i.student.lastName}</td>
            <td>${i.company.name}</td>
            <td>${i.startDate}</td>
            <td>${i.endDate}</td>
            <td>${i.status}</td>
            <td>
                <a href="/internships/${i.id}/edit">Modifica</a>
                <form action="/internships/${i.id}/delete" method="post" style="display:inline">
                      <button type="submit">Elimina</button>
                </form>
            </td>
        </tr>
    </#list>
</table>

<br>
Pagina ${internships.number + 1} di ${internships.totalPages}

<#if internships.hasPrevious()>
    <a href="?page=${internships.number -1}">Precedente</a>
</#if>

<#if internships.hasNext()>
    <a href="?page=${internships.number + 1}">Successiva</a>
</#if>

</body>
</html>