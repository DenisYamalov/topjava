<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<form method="POST" action='meals' id="form">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Date Time</dt>
        <dd>
            <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
        </dd>
    </dl>
    <dl>
        <dt>Description</dt>
        <dd>
            <input type="text" name="description" value="${meal.description}">
        </dd>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dd>
            <input type="number" name="calories" value="${meal.calories}">
        </dd>
    </dl>

    <button type="submit">Save</button>
    <button type="reset" onclick="window.history.back()">Cancel</button>
</form>

</body>
</html>
