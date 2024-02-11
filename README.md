# Mesterséges inteligencia 2022/23/1
## Első házi feladat: Connect4 game
Ebben a házi feladatban a feladat egy ágens implementálása, amely képes egy másik ágenst legyozni a Connect4 nevu játékban. A játék kétszemélyes, egy 6×7-es táblán játszódik. A játékosok felváltva ejtik bele a táblába a saját színüket. A gyozelemhez 4 saját színt kell egy vonalban kirakni (függolegesen/vizszintesen/átlósan). A játék, egy már megoldott játék, tehát annak jelenlegi állapotát ismerve a kimenete ismert, feltéve, hogy a játékosok tökéletesen játszanak. A tökéletes játékhoz azonban vagy elozetes számítások, vagy lépésenként sok ido szükséges. Ezért a MiniMax algoritmus mélységkorlátozott változatának használatát javasoljuk. A feladat maximális pontot éro megoldásához szükség lehet bizonyos extrák használatára, ilyenek többek között az α-β nyesés2 és a transzpozíciós tábla.
1. Játék egy mohó játékos ellen.
2. Játék egy olyan játékos ellen, amelyik 3 mélységig járja be a keresési fát. 
3. Játék egy olyan játékos ellen, amelyik 5 mélységig járja be a keresési fát. 
