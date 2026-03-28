@echo off
chcp 65001 > nul
echo ========================================
echo  Snowplow Skeleton Automatikus Tesztelo
echo ========================================

REM Ha az IDE-d automatikusan fordit, ezt a részt akár ki is törölheted,
REM de parancssoros futtatásnál biztosra megyünk:
echo [1/2] Java fajlok forditasa...
javac -encoding UTF8 SnowplowSkeletonTestProgram.java src\*.java tests\*.java

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
    
    REM 1. A Java program futtatasa, a testfiles bemenetkent adasa, es a kimenet elmentese
    java SnowplowSkeletonTestProgram < testfiles\test%%i_in.txt > temp_out.txt
    
    REM 2. A generalt kimenet es az elvart (assert) kimenet osszehasonlitasa (szokozok es ures sorok ignoralasaval)
    fc /W /N asserts\test%%i_assert.txt temp_out.txt > nul
    
    REM 3. Eredmeny kiiertekelese
    if errorlevel 1 (
        echo [PIROS] A %%i. teszt ELBUKOTT! 
        echo (Nezd meg a temp_out.txt fajlt, hogy miben ter el az asserts/test%%i_assert.txt-tol!^)
    ) else (
        echo [ZOLD] A %%i. teszt SIKERES! Minden egyezik.
    )
)

echo ----------------------------------------
echo Teszteles befejezve.
del temp_out.txt
pause