@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion
echo ========================================
echo  Snowplow Skeleton Automatikus Tesztelo
echo ========================================

REM Ha az IDE-d automatikusan fordit, ezt a részt akár ki is törölheted,
REM de parancssoros futtatásnál biztosra megyünk:
echo [1/2] Java fajlok forditasa...
if not exist bin mkdir bin
javac -d bin src/*.java tests/*.java skeleton.SnowplowSkeletonTestProgram.java

if %errorlevel% neq 0 (
    echo [HIBA] A forditas sikertelen! Kerd meg az IDE-det a hibak javitasaert.
    pause
    exit /b
)

echo.
echo [2/2] Tesztek futtatasa...

REM Végigmegyünk a 19-től 25-ig tartó teszteken
for %%i in (19 20 21 22 23 24 25) do (
    echo ----------------------------------------
    echo Teszt %%i futtatasa...

    java -cp bin skeleton.SnowplowSkeletonTestProgram %%i > temp_out.txt

    fc /W /N asserts\test%%i_assert.txt temp_out.txt > nul

    if errorlevel 1 (
        echo [PIROS] A %%i. teszt ELBUKOTT!
    ) else (
        echo [ZOLD] A %%i. teszt SIKERES!
    )
)

echo ----------------------------------------
echo Teszteles befejezve.
del temp_out.txt
pause