<%--
    Document  : upload
    Created on : Feb 27, 2026, 11:00:49 AM
    Author        : Anhpika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Upload Image</title>
    </head>
    <body>
        <h2>Upload Image</h2>
        <form action="upload" method="post" enctype="multipart/form-data">
            <input type="file" name="file" accept="image/*" required />
            <button type="submit">Upload</button>
        </form>

        <c:if test="${not empty imageUrl}">
            <div style="margin-top:10px">
                <img src="${imageUrl}" alt="uploaded" style="max-width:400px" />
                <div>URL: ${imageUrl}</div>
            </div>
        </c:if>

    </body>
</html>
