# Mesterséges inteligencia 2022/23/1
## Első házi feladat: Connect4 game
Ebben a házi feladatban a feladat egy ágens implementálása, amely képes egy másik ágenst legyozni a ˝
Connect4 nevu játékban. A játék kétszemélyes, egy 6 ˝ ×7-es táblán játszódik. A játékosok felváltva ejtik bele a táblába a saját színüket. A gyozelemhez 4 saját színt kell egy vonalban kirakni (függ ˝ olege- ˝
sen/vízszintesen/átlósan). A játék, egy már megoldott játék, tehát annak jelenlegi állapotát ismerve a kimenete ismert, feltéve, hogy a játékosok tökéletesen játszanak. A tökéletes játékhoz azonban vagy elozetes ˝
számítások, vagy lépésenként sok ido szükséges. Ezért a MiniMax ˝
1 algoritmus mélységkorlátozott változatának használatát javasoljuk. A feladat maximális pontot éro megoldásához szükség lehet bizonyos extrák ˝
használatára, ilyenek többek között az α-β nyesés2 és a transzpozíciós tábla.
Az ágens implementálható java és python nyelven is. A kiértékelés 3 lépésben történik, mindhárom esetben
maximum 40 (java) ill. 50 (python) másodperc alatt kell lefutnia a programnak, és bele kell férnie 500 MB
memóriába.
1. Játék egy mohó játékos ellen. (4 pont)
2. Játék egy olyan játékos ellen, amelyik 3 mélységig járja be a keresési fát. (4 pont)
3. Játék egy olyan játékos ellen, amelyik 5 mélységig járja be a keresési fát. (4 pont)
A feladat megoldásához kiadunk egy ahhoz nagyon hasonló futtatókörnyezetet, mint amin élesben fog történni a kiértékelés. Kérjük, hogy elso körben ezen történjen meg a házi feladat tesztelése.
