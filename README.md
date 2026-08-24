## prem automation program for EPL prediction league

A java based automation tool that keeps a google sheets EPL score tracker up to date using football data APIs and google 
services.

### description

---

Premier League Score Automation is a Java application that automatically maintains a Premier League score tracker in 
Google Sheets. It retrieves upcoming fixtures and completed match results through the Football Data API and uses the 
Google Sheets API to add new matches and update existing scores and statuses.

The project solves the problem of manually maintaining match schedules and results by automating the entire process. 
It runs on a scheduled GitHub Actions workflow, allowing the spreadsheet to stay current without requiring the 
application to be running locally.


### tech stack

---

- java (cuz why not)
- google cloud services (google sheets api credentials + service authentication)
- google sheets api (reading and updating google sheets)
- footballdata.io API (https://footballdata.io/dashboard/)
- gh actions (scheduled automation)
- maven (dependency management)



