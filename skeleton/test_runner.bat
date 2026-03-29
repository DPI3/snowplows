@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion
echo ========================================
echo  Snowplow Skeleton Automatikus Tesztelo
echo ========================================

echo [1/2] Java fajlok forditasa...
if not exist bin mkdir bin
REM ez a teljes listája az összes testnek alábbi sorban, amik nem fordulnak le hibát dobnak
REM javac -d bin src/*.java tests/*.java SnowplowSkeletonTestProgram.java
javac -d bin src/*.java tests/TestCase.java tests/Test19.java tests/Test20.java tests/Test21.java tests/Test22.java tests/Test23.java tests/Test24.java tests/Test25.java SnowplowSkeletonTestProgram.java
if %errorlevel% neq 0 (
    echo [HIBA] A forditas sikertelen!
    pause
    exit /b
)

echo.
echo [2/2] Tesztek futtatasa...

REM Végigmegyünk a 19-től 25-ig tartó teszteken
for %%i in (21) do (
    echo ----------------------------------------
    echo Teszt %%i futtatasa...

    java -cp bin skeleton.SnowplowSkeletonTestProgram %%i < testfiles\test%%i_in.txt > temp_out.txt

    fc /W /N asserts\test%%i_assert.txt temp_out.txt > nul

    if errorlevel 1 (
        echo [PIROS] A %%i. teszt ELBUKOTT!
    ) else (
        echo [ZOLD] A %%i. teszt SIKERES!
    )
)

echo ----------------------------------------
echo Teszteles befejezve.
REM del temp_out.txt
pause