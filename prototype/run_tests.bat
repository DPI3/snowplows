@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo === Zuzmaravaros Tesztelo Rendszer (Prototipus) ===
echo Java kodok forditasa...

rem Letrehozzuk a bin es az output mappakat, ha meg nem leteznek
if not exist bin mkdir bin
if not exist test_data\output mkdir test_data\output

rem Leforditjuk az osszes java fajlt a src es a tests mappakbol
javac -d bin src\*.java tests\*.java

if %errorlevel% neq 0 (
    echo [Hiba] A forditas sikertelen! Kerdlek javitsd a szintaktikai hibakat.
    pause
    exit /b
)

echo Forditas sikeres. Tesztek futtatasa...
echo.

rem Itt soroljuk fel a lefuttatni kivant tesztek neveit 
set TEST_NAMES=test16 test17 test18 test19
set SIKERES=0
set HIBAS=0

for %%T in (%TEST_NAMES%) do (
    echo Fut: %%T...
    
    rem 1. Kimenet atiranyitasa a fajlba (A MainRunner a tests mappaban van)
    java -cp bin tests.MainRunner %%T > test_data\output\%%T_out.txt
    
    rem 2. Osszehasonlitas az fc paranccsal
    fc test_data\assert\%%T_assert.txt test_data\output\%%T_out.txt >nul
    
    rem 3. Eredmeny kiertekelese
    if !errorlevel! equ 0 (
        echo   [ OK ] %%T sikeres! Egyezes talalva az assert fajllal.
        set /a SIKERES+=1
    ) else (
        echo   [HIBA] %%T elbukott! Elteres az assert es az output kozott.
        echo   -- Elvart kimenet (assert): --
        type test_data\assert\%%T_assert.txt
        echo.
        echo   -- Tenyleges kimenet (output): --
        type test_data\output\%%T_out.txt
        echo.
        set /a HIBAS+=1
    )
)

echo.
echo === Teszteles Befejezve ===
echo Sikeres tesztek: !SIKERES!
echo Hibas tesztek:   !HIBAS!
pause