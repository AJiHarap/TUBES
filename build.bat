@echo off
setlocal
cd /d "%~dp0"

echo Compiling for Java 8. This requires a JDK 9 or newer.
javac --release 8 -encoding UTF-8 Main.java Kamar.java Reservasi.java HotelManager.java
if errorlevel 1 exit /b %errorlevel%

echo Build successful. Run with: java Main
