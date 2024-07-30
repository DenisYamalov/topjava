[![Codacy Badge](https://app.codacy.com/project/badge/Grade/21292153ee87494a9f1174d877a26ab1)](https://app.codacy.com/gh/DenisYamalov/topjava/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

Java Enterprise Online Project
===============================

Get meals for current user:
curl http://localhost:8080/topjava/rest/meals

Get meal with id=100005:
curl http://localhost:8080/topjava/rest/meals/100005

Get meals filtered by date (from 2020-01-30 to 2020-01-30) and time (from 10:00 to 20:00):
curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-30&endTime=20:00"

Create new meal:
curl -X POST -H "Content-Type: application/json;charset=utf-8" -d '{"dateTime": "2024-07-30T11:00","description": "Завтрак","calories": 500}' http://localhost:8080/topjava/rest/meals

Delete meal
curl -X "DELETE" http://localhost:8080/topjava/rest/meals/100012

Update meal
curl -H 'Content-Type: application/json;charset=utf-8' -X PUT --json '{"id":100009,"dateTime":"2020-01-31T20:00:00","description":"Ужин измененный","calories":1010}' http://localhost:8080/topjava/rest/meals/100009
