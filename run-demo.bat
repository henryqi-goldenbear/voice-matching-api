@echo off
REM Kill existing Java processes
taskkill /F /IM java.exe 2>nul
timeout /t 2 /nobreak

REM Start the app on port 3001
cd /d C:\Users\zhiha\voice-matching-api
set SPRING_PROFILES_ACTIVE=demo
set PORT=3001
java -jar target\voice-matching-api-0.0.1-SNAPSHOT.jar
