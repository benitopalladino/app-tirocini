<!DOCTYPE html>
<html>
<head>
    <title>Tirocinio</title>
</head>
<body>

<h1>
    <#if internship.id??>
        Modifica tirocinio
    <#else>
        Nuovo tirocinio
    </#if>
</h1>

<#if error??>
    <div style="color:red">${error}</div>
</#if>

<form action="/internships" method="post" novalidate>

    <#if internship.id??>
        <input type="hidden" name="id" value="${internship.id}">
    </#if>

    <label>Studente:</label>
    <select name="studentId">
        <#list students as s>
            <option value="${s.id}"
                    <#if internship.studentId?? && internship.studentId == s.id>selected</#if>>
                    ${s.firstName} ${s.lastName}
            </option>
        </#list>
    </select>
    <#if errors?has_content && errors.hasFieldErrors("studentId")>
        <div style="color:red">${errors.getFieldError("studentId").defaultMessage}</div>
    </#if>
    <br>

    <label>Azienda:</label>
    <select name="companyId">
        <#list companies as c>
            <option value="${c.id}"
                    <#if internship.companyId?? && internship.companyId == c.id>selected</#if>>
                    ${c.name}
            </option>
        </#list>
    </select>
    <#if errors?has_content && errors.hasFieldErrors("companyId")>
        <div style="color:red">${errors.getFieldError("companyId").defaultMessage}</div>
    </#if>
    <br>

    <label>Data Inizio:</label>
    <input type="date" name="startDate" value="${internship.startDate!}">
    <#if errors?has_content && errors.hasFieldErrors("startDate")>
        <div style="color:red">${errors.getFieldError("startDate").defaultMessage}</div>
    </#if>
    <br>

    <label>Data Fine:</label>
    <input type="date" name="endDate" value="${internship.endDate!}">
    <#if errors?has_content && errors.hasFieldErrors("endDate")>
        <div style="color:red">${errors.getFieldError("endDate").defaultMessage}</div>
    </#if>
    <br>

    <label>Status:</label>
    <select name="status">
        <#list statuses as st>
            <option value="${st}" <#if internship.status?? && internship.status == st>selected</#if>>
                ${st}
            </option>
        </#list>
    </select>
    <#if errors?has_content && errors.hasFieldErrors("status")>
        <div style="color:red">${errors.getFieldError("status").defaultMessage}</div>
    </#if>
    <br>

    <label>Note:</label>
    <textarea name="notes" rows="4" cols="40">${internship.notes!}</textarea>

    <br><br>
    <button type="submit">Salva</button>
    <a href="/internships">Annulla</a>
</form>

</body>
</html>