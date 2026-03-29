@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion
echo ========================================
echo  Snowplow Skeleton Automatikus Tesztelo
echo ========================================

echo [1/2] Java fajlok forditasa...
if not exist bin mkdir bin
javac -d bin src/*.java tests/*.java SnowplowSkeletonTestProgram.java
if %errorlevel% neq 0 (
    echo [HIBA] A forditas sikertelen!
    pause
    exit /b
)

echo.
echo [2/2] Tesztek futtatasa...

REM Végigmegyünk a 19-től 25-ig tartó teszteken
for %%i in (1 2 3 4 5 6 7 8 9 10 11 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33) do (
    echo ----------------------------------------
    echo Teszt %%i futtatasa...

    java -cp bin skeleton.SnowplowSkeletonTestProgram %%i < testfiles\test%%i_in.txt > temp_out.txt

    fc /W /N asserts\test%%i_assert.txt temp_out.txt > nul

)

echo ----------------------------------------
echo Teszteles befejezve.
del temp_out.txt
pause