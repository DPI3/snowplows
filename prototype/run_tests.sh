#!/usr/bin/env bash
# ============================================================
#  Zuzmaravaros Tesztelo Rendszer (Prototipus) — Mac / Linux
# ============================================================

echo "=== Zuzmaravaros Tesztelo Rendszer (Prototipus) ==="
echo "Java kodok forditasa..."

# Letrehozzuk a bin es az output mappakat, ha meg nem leteznek
mkdir -p bin
mkdir -p test_data/output

# Leforditjuk az osszes java fajlt a src es a tests mappakbol
javac -encoding UTF-8 -d bin src/*.java tests/*.java

if [ $? -ne 0 ]; then
    echo "[Hiba] A forditas sikertelen! Kerdlek javitsd a szintaktikai hibakat."
    exit 1
fi

echo "Forditas sikeres. Tesztek futtatasa..."
echo ""

# ---- Futtatando tesztek: numerikus sorrendben (test1..test99) ----
TEST_NAMES=""
for i in $(seq 1 99); do
    if [ -f "tests/test${i}.java" ]; then
        TEST_NAMES="$TEST_NAMES test${i}"
    fi
done
SIKERES=0
HIBAS=0

for T in $TEST_NAMES; do
    echo "Fut: $T..."

    # 1. Kimenet atiranyitasa fajlba
    java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp bin tests.MainRunner "$T" > "test_data/output/${T}_out.txt"

    # 2. Osszehasonlitas: CRLF / trailing newline normalizalasa, majd tartalmi egyezes
    # (a Windows-os fc-vel ekvivalens toleráns viselkedés)
    EXPECTED=$(tr -d '\r' < "test_data/assert/${T}_assert.txt")
    ACTUAL=$(tr -d '\r' < "test_data/output/${T}_out.txt")
    if [ "$EXPECTED" = "$ACTUAL" ]; then
        echo "  [ OK ] $T sikeres! Egyezes talalva az assert fajllal."
        SIKERES=$((SIKERES + 1))
    else
        echo "  [HIBA] $T elbukott! Elteres az assert es az output kozott."
        echo "  -- Elvart kimenet (assert): --"
        cat "test_data/assert/${T}_assert.txt"
        echo ""
        echo "  -- Tenyleges kimenet (output): --"
        cat "test_data/output/${T}_out.txt"
        echo ""
        echo "  -- Diff (< elvart  > tenyleges, CRLF normalizalva): --"
        diff <(echo "$EXPECTED") <(echo "$ACTUAL")
        echo ""
        HIBAS=$((HIBAS + 1))
    fi
done

echo ""
echo "=== Teszteles Befejezve ==="
echo "Sikeres tesztek: $SIKERES"
echo "Hibas tesztek:   $HIBAS"
